package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.cypher.interactive.CypherQueryStore;

public class CypherBiQueryStore extends BiQueryStore implements CypherQueryStore {

    public CypherBiQueryStore(String path) throws DbException {
        super(path, "bi-", ".cypher");
    }

}
