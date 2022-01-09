package com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.TygrysekDbConnectionState;
import io.github.karol_brejna_i.tigergraph.restppclient.api.QueryApi;
import io.github.karol_brejna_i.tigergraph.restppclient.invoker.ApiException;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.util.Map;


public abstract class TygrysekUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, TygrysekDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, TygrysekDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        System.out.println("-------Executing update operation: " + operation);
        final String queryName = getQueryName();
        QueryApi apiInstance = state.getApiInstance();
        String graphName = state.getGraphName();

        Map<String, String> params = constructParams(operation);
        QueryResponse queryResponse = null;
        try {
            queryResponse = apiInstance.runInstalledQueryGet(graphName, queryName,
                    null, null, null, null, null, null,
                    params);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#runQueryGet");
            e.printStackTrace();
            throw new DbException( e );
        }

        System.out.println(queryResponse);

        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

    public abstract String getQueryName();
    protected abstract Map<String, String> constructParams(Operation o);
}
