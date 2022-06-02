package org.ldbcouncil.snb.impls.workloads.tigergraph.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.UpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.tigergraph.TigerGraphDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.tigergraph.connector.QueryRunner;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.util.Map;


public abstract class TigerGraphUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, TigerGraphDbConnectionState> {
    
    @Override
    public String getQueryString(TigerGraphDbConnectionState state, TOperation operation) {
        final String queryName = getQueryName();
        Map<String, String> params = constructParams(operation);
        return queryName + ":" + TigerGraphDbConnectionState.mapToString(params);
    }

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
