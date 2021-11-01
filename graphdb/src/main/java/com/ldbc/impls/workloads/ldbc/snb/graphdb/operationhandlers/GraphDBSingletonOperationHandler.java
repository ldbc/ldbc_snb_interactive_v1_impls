package com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.GraphDBConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.List;

public abstract class GraphDBSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
		implements SingletonOperationHandler<TOperationResult, TOperation, GraphDBConnectionState> {

	@Override
	public void executeOperation(TOperation operation, GraphDBConnectionState dbConnectionState, ResultReporter resultReporter)
			throws DbException {
		TOperationResult tuple = null;
		int resultCount = 0;

		final String queryString = getQueryString(dbConnectionState, operation);
		try (RepositoryConnection conn = dbConnectionState.getConnection()) {
			dbConnectionState.logQuery(operation.getClass().getSimpleName(), queryString);

			try (TupleQueryResult queryResult = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString).evaluate()) {
				if (queryResult.hasNext()) {
					BindingSet bindingSet = queryResult.next();

					tuple = convertSingleResult(queryResult.getBindingNames(), bindingSet);
					if (dbConnectionState.isPrintResults()) {
						System.out.println(tuple.toString());
					}
					resultCount++;
				}
			}
		}
		resultReporter.report(resultCount, tuple, operation);
	}

	public abstract TOperationResult convertSingleResult(List<String> variableNames, BindingSet bindingSet);
}
