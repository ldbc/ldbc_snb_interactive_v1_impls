package com.ldbc.impls.workloads.ldbc.snb.mssql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public class HikariDatasourceHandler {

    private static final String endPoint = "jdbc:sqlserver://localhost:1433";
    private static HikariDatasourceHandler instance = new HikariDatasourceHandler();

    private static HikariDataSource ds;

    private HikariDatasourceHandler(){
        Properties props = new Properties();
        props.setProperty("dataSource.databaseName", "ldbc");
        props.setProperty("dataSource.encrypt", "false");
        HikariConfig config = new HikariConfig(props);
        config.setConnectionTimeout(5000);
        config.setLeakDetectionThreshold(5000);
        config.setPassword("MySecr3tP4ssword");
        config.setUsername("SA");
        config.setJdbcUrl(endPoint);
        ds = new HikariDataSource(config);
    }

    public static HikariDatasourceHandler getInstance(){
        return instance;
    }

    public HikariDataSource getDatasource(){
        return ds;
    }
}
