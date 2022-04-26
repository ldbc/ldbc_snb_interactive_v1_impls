package org.ldbcouncil.impls.workloads.ldbc.snb.operationhandlers;

import org.ldbcouncil.driver.DbConnectionState;
import org.ldbcouncil.driver.DbException;
import org.ldbcouncil.driver.Operation;
import org.ldbcouncil.driver.OperationHandler;
import org.ldbcouncil.driver.ResultReporter;

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
