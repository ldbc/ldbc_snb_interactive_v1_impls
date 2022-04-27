package org.ldbcouncil.snb.impls.workloads.ldbc.snb.duckdb;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.QueryStore;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.duckdb.converter.DuckDbConverter;

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
