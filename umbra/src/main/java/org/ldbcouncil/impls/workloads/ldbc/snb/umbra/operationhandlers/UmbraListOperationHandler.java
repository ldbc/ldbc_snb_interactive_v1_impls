package org.ldbcouncil.impls.workloads.ldbc.snb.umbra.operationhandlers;

import org.ldbcouncil.driver.DbException;
import org.ldbcouncil.driver.Operation;
import org.ldbcouncil.driver.ResultReporter;
import org.ldbcouncil.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import org.ldbcouncil.impls.workloads.ldbc.snb.umbra.UmbraDbConnectionState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class UmbraListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, UmbraDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, UmbraDbConnectionState state,
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
