package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.DbConnectionState;

import java.util.Map;

public abstract class DriverConnectionState<TSnbQueryStore extends SnbQueryStore> extends DbConnectionState {

	private TSnbQueryStore queryStore;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	public DriverConnectionState(Map<String, String> properties, TSnbQueryStore store) {
		super();
		
		queryStore = store;
		printNames = Boolean.valueOf(properties.get("printQueryNames"));
		printStrings = Boolean.valueOf(properties.get("printQueryStrings"));
		printResults = Boolean.valueOf(properties.get("printQueryResults"));
	}

	public final TSnbQueryStore getQueryStore() {
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
}
