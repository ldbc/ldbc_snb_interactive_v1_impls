package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class SparqlListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, QueryStore>
	implements OperationHandler<OperationType, SparqlDriverConnectionState<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, SparqlDriverConnectionState<QueryStore> state,
			ResultReporter resultReporter) throws DbException {
		try {
            final List<OperationResult> results = new ArrayList<OperationResult>();
            int resultCount = 0;
            results.clear();

            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);

			TupleQuery tupleQuery = state.getRepository().getConnection().prepareTupleQuery(queryString);
			TupleQueryResult queryResults = tupleQuery.evaluate();

			while (queryResults.hasNext()) {
				final BindingSet bs = queryResults.next();

				resultCount++;
				OperationResult tuple;
				tuple = convertSingleResult(bs);
				results.add(tuple);
			}
			queryResults.close();

            resultReporter.report(resultCount, results, operation);
		} catch (Exception e) {
			throw new DbException(e);
		}
	}
	
	public abstract String getQueryString(SparqlDriverConnectionState<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(BindingSet record) throws ParseException;
}
