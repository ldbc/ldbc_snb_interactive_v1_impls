package com.ldbc.impls.workloads.ldbc.snb.mssql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SQLServerDbConnectionState extends BaseDbConnectionState<SQLServerQueryStore> {

    protected String endPoint;
    protected HikariDatasourceHandler dsHandler;

    private Connection connection;

    public SQLServerDbConnectionState(Map<String, String> properties, SQLServerQueryStore store) throws ClassNotFoundException {
        super(properties, store);
        
        dsHandler = HikariDatasourceHandler.getInstance();
    }

    public Connection getConnection() throws DbException {
        if (connection == null){
            try{
                HikariDataSource ds = dsHandler.getDatasource();
                
                connection = ds.getConnection();
            }
            catch(SQLException e){
                throw new DbException(e);
            }
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
