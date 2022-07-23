package org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.UpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.UmbraDbConnectionState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UmbraUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends UmbraOperationHandler
        implements UpdateOperationHandler<TOperation, UmbraDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, UmbraDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            Connection conn = state.getConnection();
            String queryString = getQueryString(state, operation);
            replaceParameterNamesWithQuestionMarks(operation, queryString);
            final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            
            try {
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            finally {
                stmt.close();
                conn.close();
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
        catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
