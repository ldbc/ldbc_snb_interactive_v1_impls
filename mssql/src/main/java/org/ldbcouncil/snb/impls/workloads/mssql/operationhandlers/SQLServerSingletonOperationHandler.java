package org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.SQLServerDbConnectionState;

import java.sql.*;

public abstract class SQLServerSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, SQLServerDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SQLServerDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            TOperationResult tuple = null;
            Connection conn = state.getConnection();
            int resultCount = 0;
            String queryString = getQueryString(state, operation);

            try (final Statement stmt = conn.createStatement()) {
                state.logQuery(operation.getClass().getSimpleName(), queryString);
    
                ResultSet result = stmt.executeQuery(queryString);
                resultCount++;
                tuple = convertSingleResult(result);
                if (state.isPrintResults())
                    System.out.println(tuple.toString());
               
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
