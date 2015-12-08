package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.postgresql.ds.jdbc4.AbstractJdbc4PoolingDataSource;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;

public class JdbcDbConnectionStore<DbQueryStore> extends DbConnectionState {
	AbstractJdbc4PoolingDataSource ds;
	private DbQueryStore queryStore;
	private String endPoint;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	public JdbcDbConnectionStore(Map<String, String> properties, DbQueryStore store) throws ClassNotFoundException, SQLException, DbException {
		super();

		endPoint = properties.get("endpoint");
		try {
			ds = this.getClass().getClassLoader()
			        .loadClass(properties.get("jdbcDriver"))
			        .asSubclass(AbstractJdbc4PoolingDataSource.class).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate pooling data source", e);
		}

		ds.setDataSourceName("MyPool");
		ds.setDatabaseName(properties.get("jdbcdatabase"));
		ds.setServerName(endPoint);
		ds.setUser(properties.get("user"));
		ds.setPassword(properties.get("password"));
		ds.setMaxConnections(64);
		queryStore = store;
		printNames = properties.get("printQueryNames").equals("true") ? true : false;
		printStrings = properties.get("printQueryStrings").equals("true") ? true : false;
		printResults = properties.get("printQueryResults").equals("true") ? true : false;
	}

	public DbQueryStore getQueryStore() {
		return queryStore;
	}

	public boolean isPrintResults() {
		return printResults;
	}

	public void logQuery(String queryType, String query) {
		if (printNames) {
			System.out.println("########### " + queryType);
		}
		if (printStrings) {
			System.out.println(query);
		}
	}

	public Connection getConnection() throws DbException {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}

	@Override
	public void close() throws IOException {
		ds.close();
	}

	@Deprecated
	public boolean isPrintNames() {
		return printNames;
	}

	@Deprecated
	public boolean isPrintStrings() {
		return printStrings;
	}
}
