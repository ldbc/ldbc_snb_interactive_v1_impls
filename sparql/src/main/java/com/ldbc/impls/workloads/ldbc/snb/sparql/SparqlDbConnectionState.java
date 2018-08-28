package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.sesame.StardogRepository;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.openrdf.repository.Repository;

import java.util.Map;

public abstract class SparqlDbConnectionState<TQueryStore extends QueryStore> extends BaseDbConnectionState<TQueryStore> {

    public SparqlDbConnectionState(Map<String, String> properties, TQueryStore queryStore) {
        super(properties, queryStore);
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

    public abstract Repository getRepository();

    public void beginTransaction() {
        getRepository().getConnection().begin();
    }

    @Override
    public void endTransaction() {
        getRepository().getConnection().commit();
    }

    @Override
    public void rollbackTransaction() {
        getRepository().getConnection().rollback();
    }

    @Override
    public void close() {}
}
