package org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers;

import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.MultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.UmbraDbConnectionState;

import java.util.List;

public abstract class UmbraMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends UmbraOperationHandler
        implements MultipleUpdateOperationHandler<TOperation, UmbraDbConnectionState> {

    @Override
    public List<String> getQueryString(UmbraDbConnectionState state, TOperation operation) {
        throw new IllegalStateException();
    }
}
