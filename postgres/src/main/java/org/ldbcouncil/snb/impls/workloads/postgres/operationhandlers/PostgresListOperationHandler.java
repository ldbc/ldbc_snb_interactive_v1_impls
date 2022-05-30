package org.ldbcouncil.snb.impls.workloads.postgres.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.ListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.postgres.PostgresDbConnectionState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PostgresListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        extends PostgresOperationHandler
        implements ListOperationHandler<TOperationResult, TOperation, PostgresDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, PostgresDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            ResultSet result = null;
            Connection conn = state.getConnection();
            List<TOperationResult> results = new ArrayList<>();
            int resultCount = 0;
            results.clear();
    
            String queryString = getQueryString(state, operation);
            replaceParameterNamesWithQuestionMarks(operation, queryString);
            final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            
            try {
                result = stmt.executeQuery();
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
                stmt.close();
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
