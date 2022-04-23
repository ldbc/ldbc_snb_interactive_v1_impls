package com.ldbc.impls.workloads.ldbc.snb.umbra.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.umbra.UmbraDbConnectionState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UmbraSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, UmbraDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, UmbraDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            TOperationResult tuple = null;
            Connection conn = state.getConnection();
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
            }
            catch (Exception e) {
                throw new DbException(e);
            }
            finally {
                conn.close();
            }
            resultReporter.report(resultCount, tuple, operation);
        }
        catch (SQLException e){
            throw new DbException(e);
        }
    }

    public abstract TOperationResult convertSingleResult(ResultSet result) throws SQLException;
}
