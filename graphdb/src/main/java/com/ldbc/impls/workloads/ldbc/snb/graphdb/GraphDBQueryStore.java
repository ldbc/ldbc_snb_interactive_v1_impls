package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;

public class GraphDBQueryStore extends QueryStore {

	protected Converter getConverter() {
		return new GraphDBConverter();
	}

	public GraphDBQueryStore(String path) throws DbException {
		super(path, ".rq");
	}
}
