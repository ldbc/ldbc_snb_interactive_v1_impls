package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlQueryStore;

public abstract class SparqlDb<DbQueryStore extends QueryStore & SparqlQueryStore> extends BaseDb<DbQueryStore> {
}
