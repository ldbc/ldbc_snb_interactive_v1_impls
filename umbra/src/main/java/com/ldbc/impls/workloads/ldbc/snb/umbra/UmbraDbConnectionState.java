package com.ldbc.impls.workloads.ldbc.snb.umbra;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.jdbc.PreferQueryMode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.TimeZone;

public class UmbraDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    protected String endPoint;
    protected PGConnectionPoolDataSource ds;
    protected Connection connection;

    public UmbraDbConnectionState(Map<String, String> properties, TDbQueryStore store) throws ClassNotFoundException {
        super(properties, store);

        endPoint = properties.get("endpoint");
        ds = new PGConnectionPoolDataSource();
        ds.setDatabaseName(properties.get("databaseName"));
        ds.setServerName(endPoint);
        ds.setUser(properties.get("user"));
        ds.setPassword(properties.get("password"));
        ds.setPreferQueryMode(PreferQueryMode.SIMPLE);
        ds.setAssumeMinServerVersion("9.0");
        ds.setSsl(false);
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
