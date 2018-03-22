package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.converter.CypherConverter;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;

import java.util.Map;

public class CypherInteractiveQueryStore extends InteractiveQueryStore {

	public CypherInteractiveQueryStore(String path) throws DbException {
		super(path, "interactive-", ".cypher");
	}

	@Override
	protected Converter getConverter() {
		return new CypherConverter();
	}

	@Override
	protected String prepare(InteractiveQuery interactiveQuery, Map<String, String> parameterSubstitutions) {
		String querySpecification = queries.get(interactiveQuery);
		for (String parameter : interactiveQuery.getParameters()) {
			querySpecification = querySpecification.replace("$" + parameter, parameterSubstitutions.get(parameter));
		}
		return querySpecification;
	}

}
