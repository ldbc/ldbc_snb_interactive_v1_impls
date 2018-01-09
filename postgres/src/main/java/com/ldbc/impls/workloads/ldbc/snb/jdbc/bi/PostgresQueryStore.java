package com.ldbc.impls.workloads.ldbc.snb.jdbc.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.util.Converter;
import com.ldbc.impls.workloads.ldbc.snb.util.PostgresConverter;

import java.util.Map;

public class PostgresQueryStore extends BiQueryStore {

	public PostgresQueryStore(String path) throws DbException {
		super(path, "query", ".sql");
	}

	@Override
	protected Converter getConverter() {
		return new PostgresConverter();
	}

	@Override
	protected String prepare(QueryType queryType, Map<String, String> paramaterSubstitutions) {
		String query = queries.get(queryType);
		for (int i = 0; i < queryType.parameters.size(); i++) {
			final String parameter = queryType.parameters.get(i);
			// Postgres indexes from 1
			query = query.replace("$" + (i+1), paramaterSubstitutions.get(parameter));
		}
		return query;
	}

}
