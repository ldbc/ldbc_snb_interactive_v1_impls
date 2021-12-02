package com.ldbc.impls.workloads.ldbc.snb.duckdb;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.duckdb.converter.DuckDbConverter;

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
