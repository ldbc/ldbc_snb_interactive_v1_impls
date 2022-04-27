package org.ldbcouncil.snb.impls.workloads.ldbc.snb.umbra;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.QueryStore;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.ldbc.snb.umbra.converter.UmbraConverter;

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
