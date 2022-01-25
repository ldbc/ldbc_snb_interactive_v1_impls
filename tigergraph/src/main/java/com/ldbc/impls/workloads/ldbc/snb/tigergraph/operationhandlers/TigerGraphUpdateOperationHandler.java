package com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.TigerGraphDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector.QueryRunner;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.util.Map;


public abstract class TigerGraphUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, TigerGraphDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, TigerGraphDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        if (state.isDebug()) {
            System.out.println("-------Executing update operation: " + operation);
        }

        final String queryName = getQueryName();
        Map<String, String> params = constructParams(operation);

        QueryResponse queryResponse = QueryRunner.runQuery(queryName, params, state);
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

    public abstract String getQueryName();
    protected abstract Map<String, String> constructParams(TOperation o);
}
