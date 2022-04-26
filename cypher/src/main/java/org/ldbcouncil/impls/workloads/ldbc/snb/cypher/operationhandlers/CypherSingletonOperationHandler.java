package org.ldbcouncil.impls.workloads.ldbc.snb.cypher.operationhandlers;

import org.ldbcouncil.driver.DbException;
import org.ldbcouncil.driver.Operation;
import org.ldbcouncil.driver.ResultReporter;
import org.ldbcouncil.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import org.ldbcouncil.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;

import java.text.ParseException;
import java.util.Map;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.summary.ResultSummary;

public abstract class CypherSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult,TOperation,CypherDbConnectionState>
{

    @Override
    public String getQueryString( CypherDbConnectionState state, TOperation operation )
    {
        return null;
    }

    public abstract String getQueryFile();

    public abstract TOperationResult toResult( Record record ) throws ParseException;

    public Map<String, Object> getParameters( TOperation operation )
    {
        return operation.parameterMap();
    }

    @Override
    public void executeOperation( TOperation operation, CypherDbConnectionState state,
                                  ResultReporter resultReporter ) throws DbException
    {
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
            final Result result = session.run( query, parameters );
            if ( result.hasNext() )
            {
                try
                {
                    resultReporter.report( 1, toResult( result.next() ), operation );
                    final ResultSummary summary = result.consume();
                }
                catch ( ParseException e )
                {
                    throw new DbException( e );
                }
            }
            else
            {
                resultReporter.report( 0, null, operation );
            }
        }
    }
}
