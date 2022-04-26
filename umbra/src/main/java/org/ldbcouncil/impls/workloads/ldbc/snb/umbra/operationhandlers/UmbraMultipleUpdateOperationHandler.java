package org.ldbcouncil.impls.workloads.ldbc.snb.umbra.operationhandlers;

import org.ldbcouncil.driver.DbException;
import org.ldbcouncil.driver.Operation;
import org.ldbcouncil.driver.ResultReporter;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import org.ldbcouncil.impls.workloads.ldbc.snb.operationhandlers.MultipleUpdateOperationHandler;
import org.ldbcouncil.impls.workloads.ldbc.snb.umbra.UmbraDbConnectionState;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class UmbraMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements MultipleUpdateOperationHandler<TOperation, UmbraDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, UmbraDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            Connection conn = state.getConnection();
            try {
                List<String> queryStrings = getQueryString(state, operation);
                for (String queryString : queryStrings) {
                    Statement stmt = conn.createStatement();
                    state.logQuery(operation.getClass().getSimpleName(), queryString);
                    stmt.execute(queryString);
                    stmt.close();
                }
            }
            catch (SQLException e) {
                throw new DbException(e);
            }
            finally {
                conn.close();
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        } 
        catch (SQLException e) {
            throw new DbException(e);
        }
    }
}