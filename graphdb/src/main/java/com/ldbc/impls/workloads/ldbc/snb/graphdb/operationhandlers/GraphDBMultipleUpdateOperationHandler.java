package com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.GraphDBConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.MultipleUpdateOperationHandler;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.List;

public abstract class GraphDBMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
		implements MultipleUpdateOperationHandler<TOperation, GraphDBConnectionState> {
	@Override
	public void executeOperation(TOperation operation, GraphDBConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException {
		List<String> queryStrings = getQueryString(dbConnectionState, operation);
		try (RepositoryConnection conn = dbConnectionState.getConnection()) {
			for (String queryString : queryStrings) {
				Update update = conn.prepareUpdate(QueryLanguage.SPARQL, queryString);
				update.execute();
			}
		} catch (Exception e) {
			throw new DbException(e);
		}
		resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
	}
}
