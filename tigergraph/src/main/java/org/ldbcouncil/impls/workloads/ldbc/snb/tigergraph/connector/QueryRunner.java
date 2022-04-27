package org.ldbcouncil.impls.workloads.ldbc.snb.tigergraph.connector;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.impls.workloads.ldbc.snb.tigergraph.TigerGraphDbConnectionState;
import io.github.karol_brejna_i.tigergraph.restppclient.api.QueryApi;
import io.github.karol_brejna_i.tigergraph.restppclient.invoker.ApiException;
import io.github.karol_brejna_i.tigergraph.restppclient.model.QueryResponse;

import java.util.Map;

public class QueryRunner {

    public static QueryResponse runQuery(String queryName, Map<String, String> params, TigerGraphDbConnectionState connectionState) throws DbException {
        if (connectionState.isPrintNames()) {
            System.out.println("Query: " + queryName);
        }
        QueryApi apiInstance = connectionState.getApiInstance();
        String graphName = connectionState.getGraphName();

        QueryResponse queryResponse;
        try {
            queryResponse = apiInstance.runInstalledQueryGet(graphName, queryName,
                    null, null, null, null, null, null,
                    params);
        } catch (ApiException e) {
            System.err.println("Exception when calling " + queryName);
            e.printStackTrace();
            throw new DbException(e);
        }

        if (connectionState.isPrintResults()) {
            System.out.println(queryResponse);
        }
        return queryResponse;
    }
}
