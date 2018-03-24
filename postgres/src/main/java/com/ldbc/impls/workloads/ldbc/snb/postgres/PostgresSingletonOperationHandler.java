package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class PostgresSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, TQueryStore extends QueryStore>
        implements OperationHandler<OperationType, PostgresDbConnectionState<TQueryStore>> {

    @Override
    public void executeOperation(OperationType operation, PostgresDbConnectionState<TQueryStore> state,
                                 ResultReporter resultReporter) throws DbException {
        Connection conn = state.getConnection();
        OperationResult tuple = null;
        int resultCount = 0;
        String queryString = getQueryString(state, operation);
        try {
            Statement stmt = conn.createStatement();

            state.logQuery(operation.getClass().getSimpleName(), queryString);

            ResultSet result = stmt.executeQuery(queryString);
            if (result.next()) {
                resultCount++;

                tuple = convertSingleResult(result);
                if (state.isPrintResults())
                    System.out.println(tuple.toString());
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new DbException(queryString + e);
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(resultCount, tuple, operation);
    }

    public abstract String getQueryString(PostgresDbConnectionState<TQueryStore> state, OperationType operation);

    public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}
