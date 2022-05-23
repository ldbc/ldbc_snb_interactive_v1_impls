package org.ldbcouncil.snb.impls.workloads.postgres;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.BaseDbConnectionState;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class PostgresDbConnectionState extends BaseDbConnectionState<PostgresQueryStore> {

    protected String endPoint;
    protected HikariDataSource ds;

    public PostgresDbConnectionState(Map<String, String> properties, PostgresQueryStore store) throws ClassNotFoundException {
        super(properties, store);
        endPoint = properties.get("endpoint");

        Properties props = new Properties();
        endPoint = properties.get("endpoint");
        props.setProperty("dataSource.databaseName", properties.get("databaseName"));
        props.setProperty("dataSource.assumeMinServerVersion", "9.0");
        props.setProperty("dataSource.ssl", "false");
        HikariConfig config = new HikariConfig(props);
        config.setPassword(properties.get("password"));
        config.setUsername(properties.get("user"));
        config.setJdbcUrl(endPoint);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws DbException {
        Connection connection = null;
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
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
