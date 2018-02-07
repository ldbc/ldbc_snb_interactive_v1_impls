package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.sesame.StardogRepository;
import com.ldbc.driver.DbConnectionState;
import org.openrdf.repository.Repository;

import java.util.Map;

public abstract class SparqlDriverConnectionStore<DbQueryStore> extends DbConnectionState {
	private DbQueryStore queryStore;
	private final boolean printNames;
	private final boolean printStrings;
	private final boolean printResults;
	private final String endpoint;
	private final Repository repository;

	public SparqlDriverConnectionStore(Map<String, String> properties, DbQueryStore store) {
		super();

		printNames = Boolean.valueOf(properties.get("printQueryNames"));
		printStrings = Boolean.valueOf(properties.get("printQueryStrings"));
		printResults = Boolean.valueOf(properties.get("printQueryResults"));
		endpoint = properties.get("endpoint");

		repository = new StardogRepository(ConnectionConfiguration
				.to("testSesame")
				.credentials("admin", "admin"));
		queryStore = store;
	}

	public final DbQueryStore getQueryStore() {
		return queryStore;
	}

	public final boolean isPrintResults() {
		return printResults;
	}

	public final void logQuery(String queryType, String query) {
		if (printNames) {
			System.out.println("########### " + queryType);
		}
		if (printStrings) {
			System.out.println(query);
		}
	}

	public Repository getRepository() {
		return repository;
	}

}
