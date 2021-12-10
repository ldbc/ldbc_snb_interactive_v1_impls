package com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;

import java.util.Map;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

public abstract class CypherUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation,CypherDbConnectionState>
{

    @Override
    public String getQueryString( CypherDbConnectionState state, TOperation operation )
    {
        return null;
    }

    public abstract String getQueryFile();

    public Map<String,Object> getParameters( TOperation operation )
    {
        return operation.parameterMap();
    }

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
        final Map<String,Object> parameters = getParameters( operation );

        final SessionConfig config = SessionConfig.builder().withDefaultAccessMode( AccessMode.WRITE ).build();
        try ( final Session session = state.getSession( config ) )
        {
            final Result result = session.run( query, parameters );
            result.consume();
        }
        catch ( Exception e )
        {
            throw new DbException( e );
        }

        resultReporter.report( 0, LdbcNoResult.INSTANCE, operation );
    }
}
