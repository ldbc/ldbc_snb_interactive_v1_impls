package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import com.ldbc.driver.DbException;

public class JdbcCustomPoolingDbConnectionStore<DbQueryStore> extends JdbcDbConnectionStore<DbQueryStore> {
	
	private String endPoint;
	ArrayBlockingQueue<Connection> q;

	public JdbcCustomPoolingDbConnectionStore(Map<String, String> properties, DbQueryStore store) throws ClassNotFoundException, SQLException, DbException {
		super(properties, store);

		endPoint = properties.get("endpoint");
//		try {
//			ds = this.getClass().getClassLoader()
//			        .loadClass(properties.get("jdbcDriver"))
//			        .asSubclass(AbstractJdbc4PoolingDataSource.class).newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			throw new RuntimeException("Could not instantiate pooling data source", e);
//		}
//
//		ds.setDataSourceName("MyPool");
//		ds.setDatabaseName(properties.get("jdbcdatabase"));
//		ds.setServerName(endPoint);
//		ds.setUser(properties.get("user"));
//		ds.setPassword(properties.get("password"));
//		ds.setMaxConnections(1);
		
		int queueSize=8; // TODO: Make this flexible
		
		q=new ArrayBlockingQueue<>(queueSize);
		
		for (int i = 0; i < queueSize; i++) {
			Connection c = DriverManager.getConnection("jdbc:postgresql://"+endPoint+"/"+properties.get("jdbcdatabase"), properties.get("user"), properties.get("password"));
			c.setAutoCommit(true);
			q.offer(c);			
		}
	}

	@Override
	public Connection getConnection() throws DbException {
		try {
			return q.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void freeConnection(Connection con) throws DbException {
		try {
			q.put(con);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() throws IOException {
		super.close();
	}
}
