package org.ldbcouncil.snb.impls.workloads.dummydb;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.QueryStore;

public class DummyQueryStore extends QueryStore {

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public DummyQueryStore(String path) throws DbException {
        super(path, "");
    }
}