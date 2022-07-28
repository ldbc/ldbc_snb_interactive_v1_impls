package org.ldbcouncil.snb.impls.workloads.dummydb.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.UpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.dummydb.DummyConnectionState;

public abstract class DummyUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, DummyConnectionState> {

    @Override
    public void executeOperation(TOperation operation, DummyConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        state.logQuery(operation.getClass().getSimpleName(), operation.getClass().getSimpleName());
        
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

    @Override
    public String getQueryString(DummyConnectionState state, TOperation operation){
        return "query";
    }

}
