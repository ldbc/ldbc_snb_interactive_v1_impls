package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.converter.CypherConverter;

import java.util.Calendar;
import java.util.Date;

public class CypherQueryStore extends QueryStore {

    public Converter getConverter() {
        return new CypherConverter();
    }

    public CypherQueryStore(String path) throws DbException {
        super(path, ".cypher");
    }

}
