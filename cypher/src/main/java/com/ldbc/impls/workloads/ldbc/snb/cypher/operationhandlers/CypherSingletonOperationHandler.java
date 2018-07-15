package com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public abstract class CypherSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, CypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, CypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        Session session = state.getSession();
        TOperationResult tuple = null;
        int resultCount = 0;

        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);
        final StatementResult result = session.run(queryString);
        if (result.hasNext()) {
            final Record record = result.next();
            resultCount++;

            tuple = convertSingleResult(record);
            if (state.isPrintResults()) {
                System.out.println(tuple.toString());
            }
        }
        session.close();

        resultReporter.report(resultCount, tuple, operation);
    }

    public abstract TOperationResult convertSingleResult(Record record);

}
