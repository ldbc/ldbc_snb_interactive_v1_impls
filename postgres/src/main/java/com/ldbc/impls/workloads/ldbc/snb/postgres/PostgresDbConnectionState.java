package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.postgresql.ds.jdbc4.AbstractJdbc4PoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class PostgresDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    protected String endPoint;
    protected AbstractJdbc4PoolingDataSource ds;
    protected Connection connection;

    public PostgresDbConnectionState(Map<String, String> properties, TDbQueryStore store) throws ClassNotFoundException {
        super(properties, store);

        endPoint = properties.get("endpoint");
        try {
            ds = this.getClass().getClassLoader()
                    .loadClass(properties.get("jdbcDriver"))
                    .asSubclass(AbstractJdbc4PoolingDataSource.class).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not instantiate pooling data source", e);
        }

        ds.setDatabaseName(properties.get("databaseName"));
        ds.setServerName(endPoint);
        ds.setUser(properties.get("user"));
        ds.setPassword(properties.get("password"));
        ds.setMaxConnections(1);
    }

    public Connection getConnection() throws DbException {
        try {
            if (connection == null) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return connection;
    }

    @Override
    public void close() {
        ds.close();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
