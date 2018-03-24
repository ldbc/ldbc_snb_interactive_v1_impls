package com.ldbc.impls.workloads.ldbc.snb.db;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

import java.io.IOException;

public abstract class BaseDb<TQueryStore extends QueryStore> extends Db {

    protected BaseDbConnectionState<TQueryStore> dcs;

    @Override
    protected void onClose() throws IOException {
        try {
            dcs.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected com.ldbc.driver.DbConnectionState getConnectionState() throws DbException {
        return dcs;
    }

}
