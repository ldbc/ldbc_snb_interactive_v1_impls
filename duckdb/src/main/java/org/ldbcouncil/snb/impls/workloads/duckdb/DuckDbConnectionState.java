package org.ldbcouncil.snb.impls.workloads.duckdb;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.BaseDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.QueryStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TimeZone;

public class DuckDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    protected static Connection connection;

    public DuckDbConnectionState(Map<String, String> properties, TDbQueryStore store) throws ClassNotFoundException, SQLException {
        super(properties, store);
        if (connection == null) {
            // TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT+0"));
            connection = DriverManager.getConnection("jdbc:duckdb:scratch/ldbc.duckdb");
        }
    }

    public Connection getConnection() throws DbException {
        return connection;
    }

    @Override
    public void close() {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}
