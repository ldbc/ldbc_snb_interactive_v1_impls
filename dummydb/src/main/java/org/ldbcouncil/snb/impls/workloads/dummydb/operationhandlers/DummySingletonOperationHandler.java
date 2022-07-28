package org.ldbcouncil.snb.impls.workloads.dummydb.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.dummydb.DummyConnectionState;

public abstract class DummySingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, DummyConnectionState> {

    @Override
    public void executeOperation(TOperation operation, DummyConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        TOperationResult tuple = null;
        int resultCount = 0;

        state.logQuery(operation.getClass().getSimpleName(), "query");

        resultCount++;

        tuple = convertSingleResult();
        if (state.isPrintResults())
            System.out.println(tuple.toString());

        resultReporter.report(resultCount, tuple, operation);
    }

    public abstract TOperationResult convertSingleResult();
}