package org.ldbcouncil.snb.impls.workloads.age.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.age.AgeDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.ListOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Base handler for IC/IS operations returning a List of results.
 * Executes the two-statement SQL file (SET search_path + SELECT FROM cypher()).
 */
public abstract class AgeListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, AgeDbConnectionState> {

    protected abstract TOperationResult toResult(ResultSet row) throws SQLException;

    @Override
    public void executeOperation(TOperation operation, AgeDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        String sql = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), sql);
        try {
            List<TOperationResult> results = new ArrayList<>();
            synchronized (state.getConnection()) {
                try (Statement stmt = state.getConnection().createStatement()) {
                    executeTwoPartSql(stmt, sql);
                    try (ResultSet rs = stmt.getResultSet()) {
                        while (rs != null && rs.next()) {
                            results.add(toResult(rs));
                        }
                    }
                }
            }
            resultReporter.report(results.size(), results, operation);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /**
     * Splits the SQL at the first semicolon not inside a $$-block, executes the
     * SET statement, then executes the SELECT and leaves the ResultSet accessible.
     */
    protected static void executeTwoPartSql(Statement stmt, String sql) throws SQLException {
        int split = findSplitPoint(sql);
        if (split > 0) {
            stmt.execute(sql.substring(0, split).trim());
            stmt.execute(sql.substring(split + 1).trim());
        } else {
            stmt.execute(sql.trim());
        }
    }

    /** Returns index of first semicolon not inside a $$-dollar-quote block. */
    static int findSplitPoint(String sql) {
        boolean inDollarQuote = false;
        for (int i = 0; i < sql.length() - 1; i++) {
            if (sql.charAt(i) == '$' && sql.charAt(i + 1) == '$') {
                inDollarQuote = !inDollarQuote;
                i++;
            } else if (!inDollarQuote && sql.charAt(i) == ';') {
                return i;
            }
        }
        return -1;
    }
}
