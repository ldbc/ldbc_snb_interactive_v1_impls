package com.ldbc.impls.workloads.ldbc.snb.mssql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.mssql.converter.SQLServerConverter;

import java.util.HashMap;
import java.util.Map;

public class SQLServerQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new SQLServerConverter();
    }

    public SQLServerQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
