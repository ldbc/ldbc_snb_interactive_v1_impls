package com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers;

import com.google.gson.internal.LinkedTreeMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.TigerGraphDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector.QueryRunner;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TigerGraphListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, TigerGraphDbConnectionState> {

    @Override
    public String getQueryString(TigerGraphDbConnectionState state, TOperation operation) {
        final String queryName = getQueryName();
        Map<String, String> params = constructParams(operation);
        return queryName + ":" + TigerGraphDbConnectionState.mapToString(params);
    }

    public abstract String getQueryName();

    protected abstract Map<String, String> constructParams(TOperation o);

    public abstract TOperationResult toResult(LinkedTreeMap<String, Object> record) throws ParseException;

    @Override
    public void executeOperation(TOperation operation, TigerGraphDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        if (state.isDebug()) {
            System.out.println("-------Executing list operation: " + operation);
        }

        final String queryName = getQueryName();
        Map<String, String> params = constructParams(operation);

        QueryResponse queryResponse = QueryRunner.runQuery(queryName, params, state);

        // collect and convert results
        final List<TOperationResult> results = new ArrayList<>();
        try {
            ArrayList<LinkedTreeMap<String, Object>> records = getRecords(queryResponse, "result");
            if (records != null) {
                for (LinkedTreeMap<String, Object> record : records) {
                    results.add(toResult(record));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DbException(e);
        }

        resultReporter.report(results.size(), results, operation);
    }

    public ArrayList<LinkedTreeMap<String, Object>> getRecords(QueryResponse queryResponse, String resultKey) {
        List<Object> results = queryResponse.getResults();
        ArrayList<LinkedTreeMap<String, Object>> result = null;
        // TODO current version (3.4.0) of TigerGraph returns empty (null) results for invalid vertex parameter (non-existing vertex)
        // it does not return an error, the response looks OK (HTTP code 200). We need to hava additional check like this:
        if (results != null) {
            LinkedTreeMap<String, Object> row = (LinkedTreeMap<String, Object>) results.get(0);
            result = (ArrayList<LinkedTreeMap<String, Object>>) row.get(resultKey);
        }
        return result;
    }
}
