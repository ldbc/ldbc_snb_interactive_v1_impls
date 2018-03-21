package com.ldbc.impls.workloads.ldbc.snb.sparql;

import java.io.IOException;
import java.util.Map;

public class SparqlPoolingDbConnectionState<DbQueryStore> extends SparqlDriverConnectionState<DbQueryStore> {

	public SparqlPoolingDbConnectionState(Map<String, String> properties, DbQueryStore store) {
		super(properties, store);
	}

	@Override
	public void close() throws IOException {
	}
}
