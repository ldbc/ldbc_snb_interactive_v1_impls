package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;

public abstract class JdbcDbConnectionStore<DbQueryStore> extends DbConnectionState {
	private DbQueryStore queryStore;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	public JdbcDbConnectionStore(Map<String, String> properties, DbQueryStore store) throws ClassNotFoundException, SQLException, DbException {
		super();
		
		queryStore = store;
		printNames = properties.get("printQueryNames").equals("true") ? true : false;
		printStrings = properties.get("printQueryStrings").equals("true") ? true : false;
		printResults = properties.get("printQueryResults").equals("true") ? true : false;
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
