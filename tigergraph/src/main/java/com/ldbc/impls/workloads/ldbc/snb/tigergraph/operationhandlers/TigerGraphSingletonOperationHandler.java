package com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers;

import com.google.gson.internal.LinkedTreeMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.TigerGraphDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector.QueryRunner;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.io.*;

public abstract class TigerGraphSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, TigerGraphDbConnectionState> {

    @Override
    public String getQueryString(TigerGraphDbConnectionState state, TOperation operation) {
        final String queryName = getQueryName();
        Map<String, String> params = constructParams(operation);
        return queryName + ":" + TigerGraphDbConnectionState.mapToString(params);
    }

    public LinkedTreeMap<String, Object> getRecord(QueryResponse queryResponse) {
        List<Object> results = queryResponse.getResults();
        LinkedTreeMap<String, Object> result = null;
        // TODO current version (3.4.0) of TigerGraph returns empty (null) results for invalid vertex parameter (non-existing vertex_
        // it does not return an error, the response looks OK (HTTP code 200). We need to hava additional check like this:
        if (results != null) {
            result = (LinkedTreeMap<String, Object>) results.get(0);
        }
        return result;
    }

    @Override
    public void executeOperation(TOperation operation, TigerGraphDbConnectionState state, ResultReporter resultReporter) throws DbException {
        if (state.isDebug()) {
            System.out.println("-------Executing singleton operation: " + operation);
        }

        final String queryName = getQueryName();
        Map<String, String> params = constructParams(operation);

        QueryResponse queryResponse = QueryRunner.runQuery(queryName, params, state);

        // collect and convert results
        LinkedTreeMap<String, Object> record = getRecord(queryResponse);
        if (record != null) {
            try {
                resultReporter.report(1, toResult(record), operation);
            } catch (ParseException|IndexOutOfBoundsException e) {
                //resultReporter.report(0, null, operation);
                System.err.println("Empty results for " + queryName + ", paramters: " + TigerGraphDbConnectionState.mapToString(params));
            }
        } else {
//            throw new DbException("Cannot serialize null result.");
//            // TODO XXX with the following construct, we get `Caused by: java.lang.NullPointerException
//            //	at com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13.serializeResult(LdbcQuery13.java:111)`
//            //resultReporter.report(0, null, operation);
        }

    }

    protected abstract Map<String, String> constructParams(TOperation o);
    public abstract TOperationResult toResult(LinkedTreeMap<String, Object> record) throws ParseException;
    public abstract String getQueryName();
}
