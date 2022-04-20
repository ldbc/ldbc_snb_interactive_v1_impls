package com.ldbc.impls.workloads.ldbc.snb.mssql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;
import java.util.Map;
import java.util.TimeZone;

public class SQLServerDbConnectionState extends BaseDbConnectionState<SQLServerQueryStore> {

    protected String endPoint;
    protected HikariDataSource ds;

    public SQLServerDbConnectionState(Map<String, String> properties, SQLServerQueryStore store) throws ClassNotFoundException {
        super(properties, store);

        Properties props = new Properties();
        endPoint = properties.get("endpoint");
        props.setProperty("dataSource.databaseName", properties.get("databaseName"));
        props.setProperty("dataSource.encrypt", "false");
        HikariConfig config = new HikariConfig(props);
        config.setPassword(properties.get("password"));
        config.setUsername(properties.get("user"));
        config.setJdbcUrl(endPoint);
        config.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");

        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws DbException {
        Connection connection = null;
        try {
            // TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return connection;
    }

    @Override
    public void close() {
        if (ds != null) {
            ds.close();
        }
    }
}
