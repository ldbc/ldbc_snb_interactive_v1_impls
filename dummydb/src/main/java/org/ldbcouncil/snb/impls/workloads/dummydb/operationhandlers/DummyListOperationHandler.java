package org.ldbcouncil.snb.impls.workloads.dummydb.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.ListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.dummydb.DummyConnectionState;

import java.util.ArrayList;
import java.util.List;

public abstract class DummyListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, DummyConnectionState> {

    @Override
    public void executeOperation(TOperation operation, DummyConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        List<TOperationResult> results = new ArrayList<>();
        int resultCount = 0;
        results.clear();

        state.logQuery(operation.getClass().getSimpleName(), "query");
        
        while (resultCount < 20) {
            resultCount++;

            TOperationResult tuple = convertSingleResult();
            if (state.isPrintResults()) {
                System.out.println(tuple.toString());
            }
            results.add(tuple);
        }

        resultReporter.report(resultCount, results, operation);
    }

    public abstract TOperationResult convertSingleResult();
}
