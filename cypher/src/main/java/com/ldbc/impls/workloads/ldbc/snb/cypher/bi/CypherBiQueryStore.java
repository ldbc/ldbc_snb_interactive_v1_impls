package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.CypherConverter;

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
	protected String prepare(BiQuery biQuery, Map<String, String> parameterSubstitutions) {
		String querySpecification = queries.get(biQuery);
		for (String parameter : biQuery.getParameters()) {
			querySpecification = querySpecification.replace("$" + parameter, parameterSubstitutions.get(parameter));
		}
		return querySpecification;
	}

}
