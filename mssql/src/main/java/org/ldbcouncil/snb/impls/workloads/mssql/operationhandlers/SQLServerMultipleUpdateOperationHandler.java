package org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.MultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.SQLServerDbConnectionState;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class SQLServerMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements MultipleUpdateOperationHandler<TOperation, SQLServerDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SQLServerDbConnectionState state,
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
