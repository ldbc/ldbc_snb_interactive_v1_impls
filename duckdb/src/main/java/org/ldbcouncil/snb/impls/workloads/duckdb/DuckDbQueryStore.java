package org.ldbcouncil.snb.impls.workloads.duckdb;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.duckdb.converter.DuckDbConverter;

public class DuckDbQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new DuckDbConverter();
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public DuckDbQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
