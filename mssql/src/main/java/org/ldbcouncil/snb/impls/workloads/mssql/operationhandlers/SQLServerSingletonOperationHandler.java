package org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.SQLServerDbConnectionState;

import java.sql.*;

public abstract class SQLServerSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        extends SQLServerOperationHandler
        implements SingletonOperationHandler<TOperationResult, TOperation, SQLServerDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SQLServerDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            Connection conn = state.getConnection();
            TOperationResult tuple = null;
            int resultCount = 0;
    
            ResultSet result = null;
            PreparedStatement stmt = null;
            String queryString = getQueryString(state, operation);
            replaceParameterNamesWithQuestionMarks(operation, queryString);
            try {
                stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
    
                result = stmt.executeQuery();
                if (result.next()) {
                    resultCount++;
    
                    tuple = convertSingleResult(result);
                    if (state.isPrintResults()) {
                        System.out.println(tuple.toString());
                    }
                }
            } catch (SQLException e) {
                throw new DbException(e);
            }
            finally{
                if (result != null) {
                    result.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                conn.close();
            }
            resultReporter.report(resultCount, tuple, operation);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    public abstract TOperationResult convertSingleResult(ResultSet result) throws SQLException;
}
