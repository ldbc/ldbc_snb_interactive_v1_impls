package org.ldbcouncil.snb.impls.workloads.tigergraph;

import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.driver.DbException;

public class TigerGraphQueryStore extends QueryStore {

    public TigerGraphQueryStore(String path) throws DbException {
        super(path, ".gsql");
    }
    
    @Override
    protected String loadQueryFromFile(String path, String filename) {
        return null;
    }
}
