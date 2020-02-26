package com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import org.neo4j.driver.v1.Session;

public abstract class CypherDeleteOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, CypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, CypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try (Session session = state.getSession()) {
            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            session.run(queryString);
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }
}
