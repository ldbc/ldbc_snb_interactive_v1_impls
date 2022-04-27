package org.ldbcouncil.snb.impls.workloads.ldbc.snb.cypher.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

public abstract class CypherListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult,TOperation,CypherDbConnectionState>
{

    @Override
    public String getQueryString( CypherDbConnectionState state, TOperation operation )
    {
        return null;
    }

    public abstract String getQueryFile();

    public Map<String, Object> getParameters( TOperation operation )
    {
        return operation.parameterMap();
    }

    public abstract TOperationResult toResult( Record record ) throws ParseException;

    @Override
    public void executeOperation( TOperation operation, CypherDbConnectionState state,
                                  ResultReporter resultReporter ) throws DbException
    {
        // caches the query for future use
        final String queryFile = getQueryFile();
        if ( !state.hasQuery( queryFile ) )
        {
            final boolean successful = state.addQuery( queryFile );
            if ( !successful )
            {
                throw new DbException( String.format( "Unable to load query for operation: %s", operation.toString() ) );
            }
        }

        final String query = state.getQuery( queryFile );
        final Map<String, Object> parameters = getParameters( operation );

        final SessionConfig config = SessionConfig.builder().withDefaultAccessMode( AccessMode.READ ).build();
        try ( final Session session = state.getSession( config ) )
        {
            final List<TOperationResult> results = new ArrayList<>();
            final Result result = session.run( query, parameters );
            for ( Record record : result.list() )
            {
                try
                {
                    results.add( toResult( record ) );
                }
                catch ( ParseException e )
                {
                    throw new DbException( e );
                }
            }
            result.consume();
            resultReporter.report( results.size(), results, operation );
        }
    }
}
