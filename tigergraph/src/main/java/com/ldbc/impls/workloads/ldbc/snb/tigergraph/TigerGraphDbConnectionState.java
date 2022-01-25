package com.ldbc.impls.workloads.ldbc.snb.tigergraph;

import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import io.github.karol_brejna_i.tigergraph.restppclient.api.QueryApi;
import io.github.karol_brejna_i.tigergraph.restppclient.invoker.ApiClient;
import io.github.karol_brejna_i.tigergraph.restppclient.invoker.Configuration;

import java.io.IOException;
import java.util.Map;

public class TigerGraphDbConnectionState extends BaseDbConnectionState<TigerGraphQueryStore> {

    protected final String endpoint;
    private final QueryApi apiInstance;
    private final String graphName;
    private final boolean debug;

    public TigerGraphDbConnectionState(Map<String, String> properties, TigerGraphQueryStore store) {
        super(properties, store);
        this.endpoint = properties.get("endpoint");
        this.graphName = properties.get("databaseName");
        String debugValue = properties.getOrDefault("debug", "false");
        this.debug = Boolean.parseBoolean(debugValue);

        ApiClient defaultApiClient = Configuration.getDefaultApiClient();
        defaultApiClient.setBasePath(this.endpoint);
        Configuration.setDefaultApiClient(defaultApiClient);

        this.apiInstance = new QueryApi();
    }

    @Override
    public void close() throws IOException {
        // no-op
    }

    public QueryApi getApiInstance() {
        return apiInstance;
    }

    public String getGraphName() {
        return graphName;
    }

    public boolean isPrintNames() {
        return printNames;
    }

    public boolean isDebug() {
        return debug;
    }
}
