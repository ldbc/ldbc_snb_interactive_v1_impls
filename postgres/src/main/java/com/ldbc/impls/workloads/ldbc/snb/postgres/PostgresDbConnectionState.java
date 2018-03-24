package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

import java.sql.Connection;
import java.util.Map;

public abstract class PostgresDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    public PostgresDbConnectionState(Map<String, String> properties, TDbQueryStore store) {
        super(properties, store);
    }

    public abstract Connection getConnection() throws DbException;

    public abstract void freeConnection(Connection con) throws DbException;

}
