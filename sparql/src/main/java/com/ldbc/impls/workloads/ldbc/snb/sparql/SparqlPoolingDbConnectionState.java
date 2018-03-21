package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlBiQueryStore;

import java.io.IOException;
import java.util.Map;

public class SparqlPoolingDbConnectionState extends SparqlDriverConnectionState {

	public SparqlPoolingDbConnectionState(Map<String, String> properties, SparqlBiQueryStore store) {
		super(properties, store);

		String endPoint = properties.get("endpoint");
		String user = properties.get("user");
		String password = properties.get("password");
	}

	@Override
	public void close() throws IOException {}
}
