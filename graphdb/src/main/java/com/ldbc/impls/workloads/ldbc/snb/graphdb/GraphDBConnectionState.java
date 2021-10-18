package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;

import java.util.Map;
import java.util.TimeZone;

public class GraphDBConnectionState extends BaseDbConnectionState<GraphDBQueryStore> {

	protected final HTTPRepository graphDBHTTPRepository;
	protected RepositoryConnection repositoryConnection;

	public GraphDBConnectionState(Map<String, String> properties, GraphDBQueryStore queryStore) {
		super(properties, queryStore);
		String endpoint = properties.get("endpoint");
		graphDBHTTPRepository = new HTTPRepository(endpoint);

		if (properties.containsKey("user") && properties.containsKey("password")) {
			String user = properties.get("user");
			String password = properties.get("password");
			graphDBHTTPRepository.setUsernameAndPassword(user, password);
		}
	}

	public RepositoryConnection getConnection() {
		if (repositoryConnection == null) {
			TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
			repositoryConnection = graphDBHTTPRepository.getConnection();
		}

		return repositoryConnection;
	}

	@Override
	public void close() {
		if (repositoryConnection != null) {
			repositoryConnection.close();
		}
	}
}
