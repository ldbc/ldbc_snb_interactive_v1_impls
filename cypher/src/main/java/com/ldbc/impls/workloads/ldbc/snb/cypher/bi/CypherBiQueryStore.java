package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

public class CypherBiQueryStore extends BiQueryStore {

	public CypherBiQueryStore(String path) throws DbException {
		super(path, "bi-", ".cypher");
	}

}
