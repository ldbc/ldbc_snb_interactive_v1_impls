package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import com.ldbc.driver.DbException;
import org.postgresql.ds.jdbc4.AbstractJdbc4PoolingDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JdbcPoolingDbConnectionStore<DbQueryStore> extends JdbcDbConnectionStore<DbQueryStore> {
	AbstractJdbc4PoolingDataSource ds;
	private String endPoint;

	public JdbcPoolingDbConnectionStore(Map<String, String> properties, DbQueryStore store) throws ClassNotFoundException, SQLException, DbException {
		super(properties, store);

		endPoint = properties.get("endpoint");
		try {
			ds = this.getClass().getClassLoader()
			        .loadClass(properties.get("jdbcDriver"))
			        .asSubclass(AbstractJdbc4PoolingDataSource.class).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate pooling data source", e);
		}

		ds.setDataSourceName("MyPool");
		ds.setDatabaseName(properties.get("database"));
		ds.setServerName(endPoint);
		ds.setUser(properties.get("user"));
		ds.setPassword(properties.get("password"));
		ds.setMaxConnections(1);
	}

	@Override
	public Connection getConnection() throws DbException {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}
	
	@Override
	public void freeConnection(Connection con) throws DbException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}

	@Override
	public void close() throws IOException {
		ds.close();
		super.close();
	}
}
