package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.sesame.StardogRepository;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.openrdf.repository.Repository;

import java.util.Map;

public class SparqlDbConnectionState<TQueryStore extends QueryStore> extends BaseDbConnectionState<TQueryStore> {

    private final String endpoint;
    private final String databaseName;
    private final Repository repository;

    public SparqlDbConnectionState(Map<String, String> properties, TQueryStore queryStore) {
        super(properties, queryStore);

        endpoint = properties.get("endpoint");
        databaseName = properties.get("databaseName");
        repository = new StardogRepository(ConnectionConfiguration
                .from(endpoint + databaseName)
                .credentials("admin", "admin"));
    }

    public final void logQuery(String queryType, String query) {
        if (printNames) {
            System.out.println("#" + queryType);
        }
        if (printStrings) {
            System.out.print(query);
            //System.out.print(query.replaceAll("\\n", " "));
            //System.out.println("#" + queryType);
        }
    }

    @Override
    public void beginTransaction() {
        repository.getConnection().begin();
    }

    @Override
    public void endTransaction() {
        repository.getConnection().commit();
    }

    @Override
    public void rollbackTransaction() {
        repository.getConnection().rollback();
    }

    public Repository getRepository() {
        return repository;
    }

    @Override
    public void close() {}
}
