package org.ldbcouncil.snb.impls.workloads;

import org.ldbcouncil.snb.driver.DbConnectionState;

import java.util.Map;

public abstract class BaseDbConnectionState<TQueryStore extends QueryStore> extends DbConnectionState {

    protected TQueryStore queryStore;
    protected boolean printNames;
    protected boolean printStrings;
    protected boolean printResults;

    public BaseDbConnectionState(Map<String, String> properties, TQueryStore queryStore) {
        super();

        this.queryStore = queryStore;
        this.printNames = Boolean.valueOf(properties.get("printQueryNames"));
        this.printStrings = Boolean.valueOf(properties.get("printQueryStrings"));
        this.printResults = Boolean.valueOf(properties.get("printQueryResults"));
    }

    public final TQueryStore getQueryStore() {
        return queryStore;
    }

    public final boolean isPrintResults() {
        return printResults;
    }

    public void logQuery(String queryType, String query) {
        if (printNames) {
            System.out.println("########### " + queryType);
        }
        if (printStrings) {
            System.out.println(query);
        }
    }

}
