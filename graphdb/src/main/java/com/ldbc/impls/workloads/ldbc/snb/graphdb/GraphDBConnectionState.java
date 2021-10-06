package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import com.ontotext.graphdb.repository.http.GraphDBHTTPRepository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.Map;
import java.util.TimeZone;

public class GraphDBConnectionState extends BaseDbConnectionState<GraphDBQueryStore> {

	protected final GraphDBHTTPRepository graphDBHTTPRepository;
	protected RepositoryConnection repositoryConnection;

	public GraphDBConnectionState(Map<String, String> properties, GraphDBQueryStore queryStore) {
		super(properties, queryStore);
		String endpoint = properties.get("endpoint");
		graphDBHTTPRepository = new GraphDBHTTPRepository(endpoint);

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
