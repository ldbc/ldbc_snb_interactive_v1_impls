package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;

public abstract class SparqlSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, QueryStore>
	implements OperationHandler<OperationType, SparqlDriverConnectionState<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, SparqlDriverConnectionState<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
		try {
			OperationResult tuple = null;
			int resultCount = 0;

			final String queryString = getQueryString(state, operation);
			state.logQuery(operation.getClass().getSimpleName(), queryString);

			TupleQuery tupleQuery = state.getRepository().getConnection().prepareTupleQuery(queryString);
			TupleQueryResult queryResults = tupleQuery.evaluate();

			if (queryResults.hasNext()) {
				final BindingSet bs = queryResults.next();
				resultCount++;

				tuple = convertSingleResult(bs);
			}
			queryResults.close();

			resultReporter.report(resultCount, tuple, operation);
		} catch (Exception e) {
			throw new DbException(e);
		}
	}

	public abstract String getQueryString(SparqlDriverConnectionState<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(BindingSet bs);
}
