package com.ldbc.impls.workloads.ldbc.snb.mssql.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.mssql.SQLServerDbConnectionState;

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
