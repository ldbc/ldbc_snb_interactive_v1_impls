package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.converter.CypherConverter;

public class CypherQueryStore extends QueryStore {

    public Converter getConverter() {
        return new CypherConverter();
    }

    public CypherQueryStore(String path) throws DbException {
        super(path, ".cypher");
    }


}
