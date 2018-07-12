package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.cypher.interactive.CypherQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;

public abstract class CypherDb<DbQueryStore extends QueryStore & CypherQueryStore> extends BaseDb<DbQueryStore> {
}
