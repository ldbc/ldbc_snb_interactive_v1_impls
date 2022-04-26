package org.ldbcouncil.impls.workloads.ldbc.snb.operationhandlers;

import org.ldbcouncil.driver.DbConnectionState;
import org.ldbcouncil.driver.DbException;
import org.ldbcouncil.driver.Operation;
import org.ldbcouncil.driver.OperationHandler;
import org.ldbcouncil.driver.ResultReporter;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcNoResult;

public interface UpdateOperationHandler<
        TOperation extends Operation<LdbcNoResult>,
        TDbConnectionState extends DbConnectionState>
        extends OperationHandler<TOperation, TDbConnectionState> {

    @Override
    void executeOperation(TOperation operation, TDbConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException;

    String getQueryString(TDbConnectionState state, TOperation operation);

}
