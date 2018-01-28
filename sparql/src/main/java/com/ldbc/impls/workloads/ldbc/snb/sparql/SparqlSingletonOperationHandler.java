package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.bigdata.rdf.sail.webapp.client.IPreparedTupleQuery;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

public abstract class SparqlSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, QueryStore>
	implements OperationHandler<OperationType, SparqlDriverConnectionStore<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, SparqlDriverConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
		try {
			OperationResult tuple = null;
			int resultCount = 0;

			final String queryString = getQueryString(state, operation);
			state.logQuery(operation.getClass().getSimpleName(), queryString);

			final IPreparedTupleQuery tupleQuery = state.getRepository().prepareTupleQuery(queryString);
			TupleQueryResult queryResults = tupleQuery.evaluate();

			if (queryResults.hasNext()) {
				final BindingSet bs = queryResults.next();
				resultCount++;

				tuple = convertSingleResult(bs);
				if (state.isPrintResults()) {
					System.out.println(tuple.toString());
				}
			}

			resultReporter.report(resultCount, tuple, operation);
		} catch (Exception e) {
			throw new DbException(e);
		}
	}

	public abstract String getQueryString(SparqlDriverConnectionStore<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(BindingSet bs);
}
