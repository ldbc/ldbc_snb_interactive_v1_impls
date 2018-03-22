package com.ldbc.impls.workloads.ldbc.snb.postgres.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;

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
	protected String prepare(BiQuery biQuery, Map<String, String> parameterSubstitutions) {
		String query = queries.get(biQuery);
		for (String parameter : biQuery.getParameters()) {
			query = query.replace(":" + parameter, parameterSubstitutions.get(parameter));
		}
		return query;
	}

}
