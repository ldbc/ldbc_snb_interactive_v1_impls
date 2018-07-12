package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.postgres.bi.PostgresQueryStore;

public abstract class PostgresDb<DbQueryStore extends QueryStore & PostgresQueryStore> extends BaseDb<DbQueryStore> {
}
