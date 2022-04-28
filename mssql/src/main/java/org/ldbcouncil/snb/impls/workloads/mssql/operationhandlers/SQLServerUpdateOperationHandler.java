package org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.UpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.SQLServerDbConnectionState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class SQLServerUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends SQLServerOperationHandler
        implements UpdateOperationHandler<TOperation, SQLServerDbConnectionState> {

    @Override
    public String getQueryString(SQLServerDbConnectionState state, TOperation operation) {
        throw new IllegalStateException();
    }

}
