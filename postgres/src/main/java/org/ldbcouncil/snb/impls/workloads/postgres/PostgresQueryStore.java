package org.ldbcouncil.snb.impls.workloads.postgres;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.postgres.converter.PostgresConverter;

public class PostgresQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new PostgresConverter();
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public PostgresQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}