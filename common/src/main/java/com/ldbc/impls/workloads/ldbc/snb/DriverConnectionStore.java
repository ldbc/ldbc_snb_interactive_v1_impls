package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

import java.util.Map;

public abstract class DriverConnectionStore<TBiQueryStore extends BiQueryStore>
		extends DbConnectionState {
	private TBiQueryStore queryStore;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	public DriverConnectionStore(Map<String, String> properties, TBiQueryStore store) {
		super();
		
		queryStore = store;
		printNames = Boolean.valueOf(properties.get("printQueryNames"));
		printStrings = Boolean.valueOf(properties.get("printQueryStrings"));
		printResults = Boolean.valueOf(properties.get("printQueryResults"));
	}

	public final TBiQueryStore getQueryStore() {
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
