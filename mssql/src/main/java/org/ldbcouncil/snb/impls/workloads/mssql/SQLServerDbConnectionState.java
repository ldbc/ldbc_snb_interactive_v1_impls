package org.ldbcouncil.snb.impls.workloads.mssql;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.BaseDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;
import java.util.Map;

public class SQLServerDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

    protected String endPoint;
    protected HikariDataSource ds;

    public SQLServerDbConnectionState(Map<String, String> properties, TDbQueryStore store) throws ClassNotFoundException {
        super(properties, store);

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Properties props = new Properties();
        endPoint = properties.get("endpoint");
        props.setProperty("dataSource.databaseName", properties.get("databaseName"));
        props.setProperty("dataSource.encrypt", "false");
        HikariConfig config = new HikariConfig(props);
        config.setPassword(properties.get("password"));
        config.setUsername(properties.get("user"));
        config.setJdbcUrl(endPoint);
        // config.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
        config.setConnectionTimeout(300000);
        config.setLeakDetectionThreshold(300000);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws DbException {
        Connection connection = null;
        try {
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
