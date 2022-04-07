package com.ldbc.impls.workloads.ldbc.snb.mssql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.TimeZone;
import java.sql.DriverManager;
public class SQLServerDbConnectionState extends BaseDbConnectionState<SQLServerQueryStore> {

    protected String endPoint;
    protected Connection connection;
    private String connectionString;

    public SQLServerDbConnectionState(Map<String, String> properties, SQLServerQueryStore store) throws ClassNotFoundException {
        super(properties, store);

        endPoint = properties.get("endpoint");
        connectionString = endPoint 
                             + ";databaseName=" + properties.get("databaseName")
                             + ";user=" + properties.get("user")
                             + ";password=" + properties.get("password")
                             + ";encrypt=false";
    }

    public Connection getConnection() throws DbException {
        try {
            if (connection == null) {
                TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
                connection = DriverManager.getConnection(connectionString);
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
