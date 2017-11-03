package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiQueryStore;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.io.IOException;
import java.util.Map;

public class CypherPoolingDbConnectionStore extends CypherDriverConnectionStore {
	protected final Driver driver;

	public CypherPoolingDbConnectionStore(Map<String, String> properties, CypherBiQueryStore store) {
		super(properties, store);

		String endPoint = properties.get("endpoint");
		String user = properties.get("user");
		String password = properties.get("password");
		driver = GraphDatabase.driver(endPoint, AuthTokens.basic(user, password));
	}

	@Override
	public Session getSession() throws DbException {
		return driver.session();
	}

	@Override
	public void freeSession(Session session) throws DbException {
		session.close();
	}

	@Override
	public void close() throws IOException {
		driver.close();
	}
}
