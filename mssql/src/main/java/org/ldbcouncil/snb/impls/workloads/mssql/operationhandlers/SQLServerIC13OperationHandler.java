package org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcQuery13;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcQuery13Result;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.SQLServerDbConnectionState;

import java.sql.*;

public abstract class SQLServerIC13OperationHandler
        implements SingletonOperationHandler<LdbcQuery13Result, LdbcQuery13, SQLServerDbConnectionState> {

    @Override
    public void executeOperation(LdbcQuery13 operation, SQLServerDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            LdbcQuery13Result tuple = null;
            Connection conn = state.getConnection();
            int resultCount = 0;
            String queryString = getQueryString(state, operation);

            try (final Statement stmt = conn.createStatement()) {
                state.logQuery(operation.getClass().getSimpleName(), queryString);
    
                ResultSet result = stmt.executeQuery(queryString);
                if (result.next()) {
                    resultCount++;
    
                    tuple = new LdbcQuery13Result(result.getInt(1));
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
            if ( tuple != null){
                resultReporter.report(resultCount, tuple, operation);
            }
            resultReporter.report(1, new LdbcQuery13Result(-1), operation);
        }
        catch (SQLException e){
            throw new DbException(e);
        }
    }
}
