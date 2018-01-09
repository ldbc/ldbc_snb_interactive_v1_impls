package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.util.Converter;
import com.ldbc.impls.workloads.ldbc.snb.util.CypherConverter;

import java.util.Map;

public class CypherBiQueryStore extends BiQueryStore {

	public CypherBiQueryStore(String path) throws DbException {
		super(path, "bi-", ".cypher");
	}

	@Override
	protected Converter getConverter() {
		return new CypherConverter();
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
