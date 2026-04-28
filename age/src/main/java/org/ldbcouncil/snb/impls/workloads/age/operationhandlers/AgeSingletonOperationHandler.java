package org.ldbcouncil.snb.impls.workloads.age.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.age.AgeDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base handler for IS operations returning a single result (or null if not found).
 */
public abstract class AgeSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, AgeDbConnectionState> {

    protected abstract TOperationResult toResult(ResultSet row) throws SQLException;

    @Override
    public void executeOperation(TOperation operation, AgeDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        String sql = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), sql);
        try {
            TOperationResult result = null;
            int count = 0;
            synchronized (state.getConnection()) {
                try (Statement stmt = state.getConnection().createStatement()) {
                    AgeListOperationHandler.executeTwoPartSql(stmt, sql);
                    try (ResultSet rs = stmt.getResultSet()) {
                        if (rs != null && rs.next()) {
                            count = 1;
                            result = toResult(rs);
                        }
                    }
                }
            }
            resultReporter.report(count, result, operation);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
