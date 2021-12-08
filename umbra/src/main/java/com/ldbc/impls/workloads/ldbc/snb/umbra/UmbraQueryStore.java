package com.ldbc.impls.workloads.ldbc.snb.umbra;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.umbra.converter.UmbraConverter;

public class UmbraQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new UmbraConverter();
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public UmbraQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
