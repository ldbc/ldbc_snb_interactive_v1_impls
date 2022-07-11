package org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.cypher.CypherDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.UpdateOperationHandler;

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

    public abstract Map<String, Object> getParameters(TOperation operation );


    @Override
    public void executeOperation( TOperation operation, CypherDbConnectionState state,
                                  ResultReporter resultReporter ) throws DbException
    {
        String query = getQueryString(state, operation);
        final Map<String, Object> parameters = getParameters( operation );

        final SessionConfig config = SessionConfig.builder().withDefaultAccessMode( AccessMode.WRITE ).build();

        state.logQuery(operation.getClass().getSimpleName(), query);
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
