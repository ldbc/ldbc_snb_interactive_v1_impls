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
import java.util.ArrayList;
import java.util.List;

public abstract class PostgresListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, TQueryStore extends QueryStore>
        implements OperationHandler<OperationType, PostgresDbConnectionState<TQueryStore>> {

    @Override
    public void executeOperation(OperationType operation, PostgresDbConnectionState<TQueryStore> state,
                                 ResultReporter resultReporter) throws DbException {
        Connection conn = state.getConnection();
        List<OperationResult> results = new ArrayList<OperationResult>();
        int resultCount = 0;
        results.clear();

        String queryString = getQueryString(state, operation);
        try {
            Statement stmt = conn.createStatement();

            state.logQuery(operation.getClass().getSimpleName(), queryString);

            ResultSet result = stmt.executeQuery(queryString);
            while (result.next()) {
                resultCount++;

                OperationResult tuple = convertSingleResult(result);
                if (state.isPrintResults())
                    System.out.println(tuple.toString());
                results.add(tuple);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new DbException(queryString + " " + e);
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(resultCount, results, operation);
    }

    public abstract String getQueryString(PostgresDbConnectionState<TQueryStore> state, OperationType operation);

    public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}
