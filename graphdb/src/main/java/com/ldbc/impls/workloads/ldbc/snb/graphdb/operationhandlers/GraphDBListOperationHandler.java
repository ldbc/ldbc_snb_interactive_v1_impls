package com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.GraphDBConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.ArrayList;
import java.util.List;

public abstract class GraphDBListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
		implements ListOperationHandler<TOperationResult, TOperation, GraphDBConnectionState> {

	@Override
	public void executeOperation(TOperation operation, GraphDBConnectionState dbConnectionState, ResultReporter resultReporter)
			throws DbException {
		List<TOperationResult> results = new ArrayList<>();
		int resultCount = 0;
		//results.clear();

		final String queryString = getQueryString(dbConnectionState, operation);
		try (RepositoryConnection conn = dbConnectionState.getConnection()) {
			TupleQueryResult resultIter = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString).evaluate();
			while (resultIter.hasNext()) {
				BindingSet bindingSet = resultIter.next();

				TOperationResult tuple = convertSingleResult(bindingSet);
				if (dbConnectionState.isPrintResults()) {
					System.out.println(tuple.toString());
				}
				results.add(tuple);

				resultCount++;
			}
		}
		resultReporter.report(resultCount, results, operation);
	}

	public abstract TOperationResult convertSingleResult(BindingSet result);
}
