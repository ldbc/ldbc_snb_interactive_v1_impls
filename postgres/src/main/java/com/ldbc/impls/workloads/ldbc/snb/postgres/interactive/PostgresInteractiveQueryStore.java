package com.ldbc.impls.workloads.ldbc.snb.postgres.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.postgres.bi.PostgresQueryStore;

public class PostgresInteractiveQueryStore extends InteractiveQueryStore implements PostgresQueryStore {

    public PostgresInteractiveQueryStore(String path) throws DbException {
        super(path, ".sql");
    }
    
}
