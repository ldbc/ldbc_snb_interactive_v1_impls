package com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresDbConnectionState;

import java.sql.Connection;
import java.sql.Statement;

public abstract class PostgresUpdateOperationHandler<
            OperationType extends Operation<LdbcNoResult>
        > implements OperationHandler<OperationType, PostgresDbConnectionState> {

    @Override
    public void executeOperation(OperationType operation, PostgresDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        Connection conn = state.getConnection();
        String queryString = getQueryString(state, operation);
        try (final Statement stmt = conn.createStatement()) {
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            stmt.execute(queryString);
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

    public abstract String getQueryString(PostgresDbConnectionState state, OperationType operation);
}
