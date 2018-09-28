package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.sesame.StardogRepository;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.openrdf.repository.Repository;

import java.util.Map;

public class StardogDbConnectionState<TQueryStore extends QueryStore> extends SparqlDbConnectionState {

    private final String endpoint;
    private final String databaseName;
    private final Repository repository;

    public StardogDbConnectionState(Map<String, String> properties, TQueryStore queryStore) {
        super(properties, queryStore);

        endpoint = properties.get("endpoint");
        databaseName = properties.get("databaseName");
        repository = new StardogRepository(ConnectionConfiguration
                .from(endpoint + databaseName)
                .credentials("admin", "admin"));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
