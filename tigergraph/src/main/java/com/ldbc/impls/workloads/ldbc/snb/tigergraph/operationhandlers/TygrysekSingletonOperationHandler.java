package com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers;

import com.google.gson.internal.LinkedTreeMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.TygrysekDbConnectionState;
import io.github.karol_brejna_i.tigergraph.restppclient.api.QueryApi;
import io.github.karol_brejna_i.tigergraph.restppclient.invoker.ApiException;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public abstract class TygrysekSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, TygrysekDbConnectionState> {

    @Override
    public String getQueryString(TygrysekDbConnectionState state, TOperation operation) {
        return null;
    }

    public Map<String, Object> getParameters(TOperation operation) {
        return operation.parameterMap();
    }

    public abstract String getQueryName();


    @Override
    public void executeOperation(TOperation operation, TygrysekDbConnectionState state, ResultReporter resultReporter) throws DbException {
        System.out.println("-------Executing singleton operation: " + operation);

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
        LinkedTreeMap<String, Object> record = getRecord(queryResponse);
        if (record != null) {
            try {
                resultReporter.report(1, toResult(record), operation);
            } catch (ParseException e) {
                resultReporter.report(0, null, operation);
            }
        } else {
            resultReporter.report(0, null, operation);
        }


    }

    protected abstract Map<String, String> constructParams(Operation o);

    public abstract TOperationResult toResult(LinkedTreeMap<String, Object> record) throws ParseException;

    public LinkedTreeMap<String, Object> getRecord(QueryResponse queryResponse) {
        List<Object> results = queryResponse.getResults();
        return (LinkedTreeMap<String, Object>) results.get(0);
    }
}
