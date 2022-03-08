package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.TimeZone;

public class PostgresDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    protected String endPoint;
    protected PGConnectionPoolDataSource ds;
    protected Connection connection;

    public PostgresDbConnectionState(Map<String, String> properties, TDbQueryStore store) throws ClassNotFoundException {
        super(properties, store);

        endPoint = properties.get("endpoint");
        ds = new PGConnectionPoolDataSource();
        ds.setDatabaseName(properties.get("databaseName"));
        ds.setServerName(endPoint);
        ds.setUser(properties.get("user"));
        ds.setPassword(properties.get("password"));
    }

    public Connection getConnection() throws DbException {
        try {
            if (connection == null) {
                TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
