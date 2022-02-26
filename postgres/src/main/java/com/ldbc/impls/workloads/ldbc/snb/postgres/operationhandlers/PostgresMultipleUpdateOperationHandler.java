package com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.MultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresDbConnectionState;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public abstract class PostgresMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends PostgresOperationHandler
        implements MultipleUpdateOperationHandler<TOperation, PostgresDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, PostgresDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        Connection conn = state.getConnection();
        try {
            List<String> queryStrings = getQueryString(state, operation);
            for (String queryString : queryStrings) {
                Statement stmt = conn.createStatement();
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.execute(queryString);
                stmt.close();
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

}
