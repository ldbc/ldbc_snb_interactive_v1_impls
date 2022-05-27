package org.ldbcouncil.snb.impls.workloads.postgres.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.MultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.postgres.PostgresDbConnectionState;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class PostgresDbMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends PostgresOperationHandler
        implements MultipleUpdateOperationHandler<TOperation, PostgresDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, PostgresDbConnectionState state,
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