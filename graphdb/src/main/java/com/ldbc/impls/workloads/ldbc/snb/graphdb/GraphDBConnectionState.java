package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.ldbcouncil.snb.impls.workloads.BaseDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.QueryStore;

import java.util.Map;
import java.util.TimeZone;

public class GraphDBConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore> {

	protected final HTTPRepository graphDBHTTPRepository;

	public GraphDBConnectionState(Map<String, String> properties, TDbQueryStore queryStore) {
		super(properties, queryStore);

		String endpoint = properties.get("endpoint");
		graphDBHTTPRepository = new HTTPRepository(endpoint);

		if (properties.containsKey("user") && properties.containsKey("password")) {
			String user = properties.get("user");
			String password = properties.get("password");
			graphDBHTTPRepository.setUsernameAndPassword(user, password);
		}
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	}

	public RepositoryConnection getConnection() {
		return graphDBHTTPRepository.getConnection();
	}

	@Override
	public void close() {
		if (graphDBHTTPRepository.getConnection() != null) {
			graphDBHTTPRepository.getConnection().close();
		}
	}
}
