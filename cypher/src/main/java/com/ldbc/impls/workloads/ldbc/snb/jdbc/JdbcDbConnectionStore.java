package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public abstract class JdbcDbConnectionStore<DbQueryStore> extends DbConnectionState {
	private DbQueryStore queryStore;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	public JdbcDbConnectionStore(Map<String, String> properties, DbQueryStore store) throws ClassNotFoundException, SQLException, DbException {
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

	public abstract Connection getConnection() throws DbException;
	
	public abstract void freeConnection(Connection con) throws DbException;
	
	public void close() throws IOException {} 
}
