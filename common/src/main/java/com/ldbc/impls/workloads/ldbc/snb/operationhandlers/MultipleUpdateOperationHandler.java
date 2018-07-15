package com.ldbc.impls.workloads.ldbc.snb.operationhandlers;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;

import java.util.List;

public interface MultipleUpdateOperationHandler<
        TOperation extends Operation<LdbcNoResult>,
        TDbConnectionState extends DbConnectionState>
        extends OperationHandler<TOperation, TDbConnectionState> {

    @Override
    void executeOperation(TOperation operation, TDbConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException;

    List<String> getQueryString(TDbConnectionState state, TOperation operation);

}
