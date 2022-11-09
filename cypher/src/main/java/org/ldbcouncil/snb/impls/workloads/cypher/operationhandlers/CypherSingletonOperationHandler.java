package org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.cypher.CypherDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;

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
    public abstract TOperationResult toResult( Record record ) throws ParseException;

    public abstract Map<String, Object> getParameters(CypherDbConnectionState state, TOperation operation );

    @Override
    public void executeOperation( TOperation operation, CypherDbConnectionState state,
                                  ResultReporter resultReporter ) throws DbException
    {
        String query = getQueryString(state, operation);
        final Map<String, Object> parameters = getParameters(state, operation );

        final SessionConfig config = SessionConfig.builder().withDefaultAccessMode( AccessMode.READ ).build();

        state.logQuery(operation.getClass().getSimpleName(), query);
        try ( final Session session = state.getSession( config ) )
        {
            final Result result = session.run( query, parameters );
            if ( result.hasNext() )
            {
                resultReporter.report( 1, toResult( result.next() ), operation );
                final ResultSummary summary = result.consume();
            }
            else
            {
                resultReporter.report( 0, toResult(null), operation );
            }
        }
        catch ( ParseException e )
        {
            throw new DbException( e );
        }
    }
}
