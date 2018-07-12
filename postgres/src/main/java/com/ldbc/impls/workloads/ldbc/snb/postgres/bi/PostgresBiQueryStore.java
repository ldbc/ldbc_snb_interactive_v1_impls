package com.ldbc.impls.workloads.ldbc.snb.postgres.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

public class PostgresBiQueryStore extends BiQueryStore implements PostgresQueryStore {

    public PostgresBiQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
