package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.io.IOException;
import java.util.Map;

public class CypherDbConnectionState extends BaseDbConnectionState<CypherQueryStore> {

    protected final Driver driver;

    public CypherDbConnectionState(Map<String, String> properties, CypherQueryStore store) {
        super(properties, store);
        String endPoint = properties.get("endpoint");
        String user = properties.get("user");
        String password = properties.get("password");
        driver = GraphDatabase.driver(endPoint, AuthTokens.basic(user, password));
    }

    public Session getSession() throws DbException {
        return driver.session();
    }

    @Override
    public void close() throws IOException {
        driver.close();
    }

}
