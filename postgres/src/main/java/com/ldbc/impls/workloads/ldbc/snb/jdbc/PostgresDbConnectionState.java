package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.DriverConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.SnbQueryStore;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

public abstract class PostgresDbConnectionState<DbQueryStore extends SnbQueryStore> extends DriverConnectionState<DbQueryStore> {

	public PostgresDbConnectionState(Map<String, String> properties, DbQueryStore store) {
		super(properties, store);
	}

	public abstract Connection getConnection() throws DbException;

	public abstract void freeConnection(Connection con) throws DbException;

	@Override
	public void close() throws IOException {}
}
