package org.ldbcouncil.snb.impls.workloads.mssql;

import java.util.ArrayList;
import java.util.List;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.mssql.converter.SQLServerConverter;

public class SQLServerQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new SQLServerConverter();
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public SQLServerQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
