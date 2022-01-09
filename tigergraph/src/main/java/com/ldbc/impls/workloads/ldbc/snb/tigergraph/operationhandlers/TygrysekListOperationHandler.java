package com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers;

import com.google.gson.internal.LinkedTreeMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.TygrysekDbConnectionState;
import io.github.karol_brejna_i.tigergraph.restppclient.api.QueryApi;
import io.github.karol_brejna_i.tigergraph.restppclient.invoker.ApiException;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class TygrysekListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, TygrysekDbConnectionState> {

    @Override
    public String getQueryString(TygrysekDbConnectionState state, TOperation operation) {
        return null;
    }

    public abstract String getQueryName();

    protected abstract Map<String, String> constructParams(Operation o);

    public abstract TOperationResult toResult(LinkedTreeMap<String, Object> record) throws ParseException;


    @Override
    public void executeOperation(TOperation operation, TygrysekDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        System.out.println("-------Executing list operation: " + operation);

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



        // collect and convert results
        final List<TOperationResult> results = new ArrayList<>();
        try {
            ArrayList<LinkedTreeMap<String, Object>> records = getRecords(queryResponse, "result");
            for (LinkedTreeMap<String, Object> record : records) {
                results.add(toResult(record));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resultReporter.report(results.size(), results, operation);
    }

    public ArrayList<LinkedTreeMap<String, Object>> getRecords(QueryResponse queryResponse, String resultKey) {
        List<Object> results = queryResponse.getResults();
        LinkedTreeMap<String, Object> row = (LinkedTreeMap<String, Object>) results.get(0);
        ArrayList<LinkedTreeMap<String, Object>> records = (ArrayList<LinkedTreeMap<String, Object>>) row.get(resultKey);
        return records;
    }

    public ArrayList<LinkedTreeMap<String, Object>> zapasowe_getRecords(QueryResponse queryResponse, String resultKey) {
        List<Object> results = queryResponse.getResults();
        LinkedTreeMap<String, Object> row = (LinkedTreeMap<String, Object>) results.get(0);
        ArrayList records;
        if (row instanceof LinkedTreeMap) {
            LinkedTreeMap<String, Object> record = (LinkedTreeMap<String, Object>) row.get(resultKey);
            records = new ArrayList<LinkedTreeMap<String, Object>>(Collections.singleton(record));
        } else {
            records = (ArrayList<LinkedTreeMap<String, Object>>) row.get(resultKey);
        }
        return records;
    }


}
