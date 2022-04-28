package org.ldbcouncil.snb.impls.workloads.mssql;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.mssql.converter.SQLServerConverter;

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
