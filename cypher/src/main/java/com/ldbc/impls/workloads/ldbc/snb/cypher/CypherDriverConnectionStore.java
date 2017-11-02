package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import org.neo4j.driver.v1.Session;

import java.util.Map;

public abstract class CypherDriverConnectionStore<DbQueryStore> extends DbConnectionState {
	private DbQueryStore queryStore;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	public CypherDriverConnectionStore(Map<String, String> properties, DbQueryStore store) {
		super();
		
		queryStore = store;
		printNames = Boolean.valueOf(properties.get("printQueryNames"));
		printStrings = Boolean.valueOf(properties.get("printQueryStrings"));
		printResults = Boolean.valueOf(properties.get("printQueryResults"));
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

	public abstract Session getSession() throws DbException;
	
	public abstract void freeSession(Session session) throws DbException;

}
