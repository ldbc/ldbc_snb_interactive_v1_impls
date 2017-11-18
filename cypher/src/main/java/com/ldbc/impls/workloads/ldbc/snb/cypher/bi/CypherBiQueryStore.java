package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.util.Converter;
import com.ldbc.impls.workloads.ldbc.snb.util.CypherConverter;

public class CypherBiQueryStore extends BiQueryStore {

	public CypherBiQueryStore(String path) throws DbException {
		super(path, "bi-", ".cypher");
	}

	@Override
	protected Converter getConverter() {
		return new CypherConverter();
	}
}
