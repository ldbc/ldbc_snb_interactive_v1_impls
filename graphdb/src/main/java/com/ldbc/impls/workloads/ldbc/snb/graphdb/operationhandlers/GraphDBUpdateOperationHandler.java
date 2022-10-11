package com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers;

import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import com.ldbc.impls.workloads.ldbc.snb.graphdb.GraphDBConnectionState;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcNoResult;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.UpdateOperationHandler;

public abstract class GraphDBUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
		implements UpdateOperationHandler<TOperation, GraphDBConnectionState> {

	@Override
	public void executeOperation(TOperation operation, GraphDBConnectionState dbConnectionState,
			ResultReporter resultReporter) throws DbException {
		String updateString = getQueryString(dbConnectionState, operation);
		try (RepositoryConnection conn = dbConnectionState.getConnection()) {
			Update update = conn.prepareUpdate(QueryLanguage.SPARQL, updateString);
			update.execute();
		} catch (Exception e) {
			throw new DbException(e);
		}
		resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
	}
}

