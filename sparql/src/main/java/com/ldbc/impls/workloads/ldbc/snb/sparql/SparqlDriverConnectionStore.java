package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.bigdata.rdf.sail.remote.BigdataSailRemoteRepository;
import com.bigdata.rdf.sail.remote.BigdataSailRemoteRepositoryConnection;
import com.bigdata.rdf.sail.webapp.client.RemoteRepositoryManager;
import com.ldbc.driver.DbConnectionState;
import org.openrdf.repository.RepositoryException;

import java.util.Map;

public abstract class SparqlDriverConnectionStore<DbQueryStore> extends DbConnectionState {
	private DbQueryStore queryStore;
	private boolean printNames;
	private boolean printStrings;
	private boolean printResults;

	private final String serviceURL = "http://localhost:9999/bigdata";
	private final BigdataSailRemoteRepository repository;

	public SparqlDriverConnectionStore(Map<String, String> properties, DbQueryStore store) {
		super();

		final RemoteRepositoryManager remoteRepositoryManager = new RemoteRepositoryManager(serviceURL, false );
		repository = remoteRepositoryManager.getRepositoryForDefaultNamespace().getBigdataSailRemoteRepository();

		queryStore = store;
		printNames = Boolean.valueOf(properties.get("printQueryNames"));
		printStrings = Boolean.valueOf(properties.get("printQueryStrings"));
		printResults = Boolean.valueOf(properties.get("printQueryResults"));
	}

	public final DbQueryStore getQueryStore() {
		return queryStore;
	}

	public final boolean isPrintResults() {
		return printResults;
	}

	public final void logQuery(String queryType, String query) {
		if (printNames) {
			System.out.println("########### " + queryType);
		}
		if (printStrings) {
			System.out.println(query);
		}
	}

	public BigdataSailRemoteRepositoryConnection getConnection() throws RepositoryException {
		return repository.getConnection();
	}
}
