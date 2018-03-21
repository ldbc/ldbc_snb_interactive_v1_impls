package com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.converter.PostgresConverter;

import java.util.Map;

public class PostgresInteractiveQueryStore extends InteractiveQueryStore {

	public PostgresInteractiveQueryStore(String path) throws DbException {
		super(path, "query", ".sql");
	}

	@Override
	protected Converter getConverter() {
		return new PostgresConverter();
	}

	@Override
	protected String prepare(InteractiveQuery interactiveQuery, Map<String, String> parameterSubstitutions) {
		String query = queries.get(interactiveQuery);
		for (String parameter : interactiveQuery.getParameters()) {
			query = query.replace(":" + parameter, parameterSubstitutions.get(parameter));
		}
		return query;
	}

}
