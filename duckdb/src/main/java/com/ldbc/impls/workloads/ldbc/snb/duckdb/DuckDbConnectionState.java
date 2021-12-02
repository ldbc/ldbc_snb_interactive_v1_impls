package com.ldbc.impls.workloads.ldbc.snb.duckdb;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TimeZone;

public class DuckDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    protected Connection connection;

    public DuckDbConnectionState(Map<String, String> properties, TDbQueryStore store) throws ClassNotFoundException, SQLException {
        super(properties, store);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        connection = DriverManager.getConnection("jdbc:duckdb:scratch/ldbc.duckdb");
        Statement statement = connection.createStatement();
        statement.execute("PRAGMA threads=1;");
    }

    public Connection getConnection() throws DbException {
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
