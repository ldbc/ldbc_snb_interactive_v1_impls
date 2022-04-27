package org.ldbcouncil.snb.impls.workloads.operationhandlers;

import org.ldbcouncil.snb.driver.DbConnectionState;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.OperationHandler;
import org.ldbcouncil.snb.driver.ResultReporter;

import java.util.List;

public interface ListOperationHandler<
        TOperationResult,
        TOperation extends Operation<List<TOperationResult>>,
        TDbConnectionState extends DbConnectionState>
        extends OperationHandler<TOperation, TDbConnectionState> {

    @Override
    void executeOperation(TOperation operation, TDbConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException;

    String getQueryString(TDbConnectionState state, TOperation operation);

}
