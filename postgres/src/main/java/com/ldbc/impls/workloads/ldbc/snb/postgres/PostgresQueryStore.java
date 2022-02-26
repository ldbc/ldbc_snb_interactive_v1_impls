package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;

import java.util.HashMap;
import java.util.Map;

public class PostgresQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new PostgresConverter();
    }

    public PostgresQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
