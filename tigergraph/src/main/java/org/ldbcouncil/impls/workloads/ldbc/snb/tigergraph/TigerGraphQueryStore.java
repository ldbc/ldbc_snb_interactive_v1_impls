package org.ldbcouncil.impls.workloads.ldbc.snb.tigergraph;

import org.ldbcouncil.impls.workloads.ldbc.snb.QueryStore;
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
