package com.ldbc.impls.workloads.ldbc.snb.jdbc.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

public class PostgresQueryStore extends BiQueryStore {

	public PostgresQueryStore(String path) throws DbException {
		super(path, "query", ".sql");
	}

}
