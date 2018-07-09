package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;

public class CypherInteractiveQueryStore extends InteractiveQueryStore implements CypherQueryStore {

    public CypherInteractiveQueryStore(String path) throws DbException {
        super(path, "interactive-", ".cypher");
    }

}
