package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;

import java.io.IOException;
import java.util.Map;

public class GraphDBConnectionState  extends BaseDbConnectionState<GraphDBQueryStore> {
	public GraphDBConnectionState(Map<String, String> properties, GraphDBQueryStore queryStore) {
		super(properties, queryStore);
	}

	@Override
	public void close() throws IOException {

	}
}
