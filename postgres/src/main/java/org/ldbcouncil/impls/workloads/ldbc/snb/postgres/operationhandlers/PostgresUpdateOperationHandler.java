package org.ldbcouncil.impls.workloads.ldbc.snb.postgres.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcNoResult;
import org.ldbcouncil.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import org.ldbcouncil.impls.workloads.ldbc.snb.postgres.PostgresDbConnectionState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class PostgresUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends PostgresOperationHandler
        implements UpdateOperationHandler<TOperation, PostgresDbConnectionState> {

    @Override
    public String getQueryString(PostgresDbConnectionState state, TOperation operation) {
        throw new IllegalStateException();
    }

}
