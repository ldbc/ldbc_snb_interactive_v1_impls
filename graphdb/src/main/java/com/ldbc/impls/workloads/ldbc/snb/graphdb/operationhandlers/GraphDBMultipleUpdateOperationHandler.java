package com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers;

import com.ldbc.impls.workloads.ldbc.snb.graphdb.GraphDBConnectionState;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.MultipleUpdateOperationHandler;

import java.util.List;

public abstract class GraphDBMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
		implements MultipleUpdateOperationHandler<TOperation, GraphDBConnectionState> {

	@Override
	public void executeOperation(TOperation operation, GraphDBConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException {
		List<String> queryStrings = getQueryString(dbConnectionState, operation);
		try (RepositoryConnection conn = dbConnectionState.getConnection()) {
			conn.begin();
			for (String queryString : queryStrings) {
				conn.prepareUpdate(QueryLanguage.SPARQL, queryString).execute();
			}
			conn.commit();
		} catch (Exception e) {
			throw new DbException(e);
		}
		resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
	}
}
