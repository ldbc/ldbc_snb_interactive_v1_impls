package com.ldbc.impls.workloads.ldbc.snb.duckdb.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.duckdb.DuckDbConnectionState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DuckDbSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, DuckDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, DuckDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        Connection conn = state.getConnection();
        TOperationResult tuple = null;
        int resultCount = 0;
        String queryString = getQueryString(state, operation);
        try (final Statement stmt = conn.createStatement()) {
            state.logQuery(operation.getClass().getSimpleName(), queryString);

            ResultSet result = stmt.executeQuery(queryString);
            if (result.next()) {
                resultCount++;

                tuple = convertSingleResult(result);
                if (state.isPrintResults())
                    System.out.println(tuple.toString());
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(resultCount, tuple, operation);
    }

    public abstract TOperationResult convertSingleResult(ResultSet result) throws SQLException;
}
