package org.ldbcouncil.snb.impls.workloads.postgres.operationhandlers;

import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.MultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.postgres.PostgresDbConnectionState;

import java.util.List;

public abstract class PostgresMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        extends PostgresOperationHandler
        implements MultipleUpdateOperationHandler<TOperation, PostgresDbConnectionState> {

    @Override
    public List<String> getQueryString(PostgresDbConnectionState state, TOperation operation) {
        throw new IllegalStateException();
    }
}
