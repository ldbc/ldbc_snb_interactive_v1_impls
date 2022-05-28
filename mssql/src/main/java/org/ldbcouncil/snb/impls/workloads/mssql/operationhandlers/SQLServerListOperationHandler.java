package org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.ListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.SQLServerDbConnectionState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SQLServerListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, SQLServerDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SQLServerDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            ResultSet result = null;
            Connection conn = state.getConnection();
            List<TOperationResult> results = new ArrayList<>();
            int resultCount = 0;

            String queryString = getQueryString(state, operation);
            try (final Statement stmt = conn.createStatement()) {
                state.logQuery(operation.getClass().getSimpleName(), queryString);

                result = stmt.executeQuery(queryString);
                while (result.next()) {
                    resultCount++;

                    TOperationResult tuple = convertSingleResult(result);
                    if (state.isPrintResults()) {
                        System.out.println(tuple.toString());
                    }
                    results.add(tuple);
                }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        finally{
            if (result != null){
                result.close();
            }
            conn.close();
        }
        resultReporter.report(resultCount, results, operation);
    }
    catch (SQLException e) {
        throw new DbException(e);
    }
}

    public abstract TOperationResult convertSingleResult(ResultSet result) throws SQLException;

}
