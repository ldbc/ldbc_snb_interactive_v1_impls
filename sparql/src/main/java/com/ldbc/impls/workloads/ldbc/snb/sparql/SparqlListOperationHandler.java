package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.bigdata.rdf.sail.webapp.client.IPreparedTupleQuery;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class SparqlListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, QueryStore>
	implements OperationHandler<OperationType, SparqlDriverConnectionStore<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, SparqlDriverConnectionStore<QueryStore> state,
			ResultReporter resultReporter) throws DbException {
		try {
            final List<OperationResult> results = new ArrayList<OperationResult>();
            int resultCount = 0;
            results.clear();

            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);

            final IPreparedTupleQuery tupleQuery = state.getRepository().prepareTupleQuery(queryString);
            TupleQueryResult queryResults = tupleQuery.evaluate();

			while (queryResults.hasNext()) {
				final BindingSet bs = queryResults.next();

				resultCount++;
				OperationResult tuple;
				tuple = convertSingleResult(bs);
				if (state.isPrintResults()) {
					System.out.println(tuple.toString());
				}
				results.add(tuple);
			}

			queryResults.close();
            resultReporter.report(resultCount, results, operation);
		} catch (Exception e) {
			throw new DbException(e);
		}
	}
	
	public abstract String getQueryString(SparqlDriverConnectionStore<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(BindingSet record) throws ParseException;
}
