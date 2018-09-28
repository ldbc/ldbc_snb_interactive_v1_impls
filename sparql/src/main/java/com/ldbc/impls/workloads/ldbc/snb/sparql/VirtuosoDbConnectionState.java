package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.sesame.StardogRepository;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.openrdf.repository.Repository;
import virtuoso.sesame4.driver.VirtuosoRepository;

import java.util.Map;

public class VirtuosoDbConnectionState<TQueryStore extends QueryStore> extends SparqlDbConnectionState<TQueryStore> {

    private final String endpoint;
    private final String graphUri;
    private final Repository repository;

    public VirtuosoDbConnectionState(Map<String, String> properties, TQueryStore queryStore) {
        super(properties, queryStore);

        endpoint = properties.get("endpoint");
        graphUri = properties.get("graphUri");
        repository = new VirtuosoRepository(endpoint, "dba", "dba");
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    public String getGraphUri(){
        return graphUri;
    }

}
