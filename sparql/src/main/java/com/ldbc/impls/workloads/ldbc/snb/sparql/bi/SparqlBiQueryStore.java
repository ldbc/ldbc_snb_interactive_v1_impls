package com.ldbc.impls.workloads.ldbc.snb.sparql.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.util.Converter;
import com.ldbc.impls.workloads.ldbc.snb.util.SparqlConverter;

import java.util.Map;

public class SparqlBiQueryStore extends BiQueryStore {

	public SparqlBiQueryStore(String path) throws DbException {
		super(path, "bi-", ".sparql");
	}

	@Override
	protected Converter getConverter() {
		return new SparqlConverter();
	}

	@Override
	protected String prepare(QueryType queryType, Map<String, String> paramaterSubstitutions) {
		String query = queries.get(queryType);
		for (String parameter : queryType.parameters) {
			query = query.replace("$" + parameter, paramaterSubstitutions.get(parameter));
		}
		return query;
	}

}
