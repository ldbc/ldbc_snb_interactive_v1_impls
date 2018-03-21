package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.converter.SparqlConverter;

import java.util.Map;

public class SparqlInteractiveQueryStore extends InteractiveQueryStore {

	public SparqlInteractiveQueryStore(String path) throws DbException {
		super(path, "interactive-", ".sparql");
	}

	@Override
	protected Converter getConverter() {
		return new SparqlConverter();
	}

	@Override
	protected String prepare(InteractiveQuery queryType, Map<String, String> paramaterSubstitutions) {
		String query = queries.get(queryType);
		for (String parameter : queryType.getParameters()) {
			query = query.replace("$" + parameter, paramaterSubstitutions.get(parameter));
		}
		return query;
	}

}
