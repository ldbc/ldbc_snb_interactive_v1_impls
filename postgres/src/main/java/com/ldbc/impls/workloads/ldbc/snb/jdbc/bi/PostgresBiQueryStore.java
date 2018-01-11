package com.ldbc.impls.workloads.ldbc.snb.jdbc.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.util.Converter;
import com.ldbc.impls.workloads.ldbc.snb.util.PostgresConverter;

import java.util.Map;

public class PostgresBiQueryStore extends BiQueryStore {

	public PostgresBiQueryStore(String path) throws DbException {
		super(path, "query", ".sql");
	}

	@Override
	protected Converter getConverter() {
		return new PostgresConverter();
	}

	@Override
	protected String prepare(QueryType queryType, Map<String, String> paramaterSubstitutions) {
		String query = queries.get(queryType);
		for (String parameter : queryType.parameters) {
			query = query.replace(":" + parameter, paramaterSubstitutions.get(parameter));
		}
		return query;
	}

}
