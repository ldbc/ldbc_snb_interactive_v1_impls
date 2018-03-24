package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;

public abstract class SparqlSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, TQueryStore extends QueryStore>
        implements OperationHandler<OperationType, SparqlDbConnectionState<TQueryStore>> {

    @Override
    public void executeOperation(OperationType operation, SparqlDbConnectionState<TQueryStore> state,
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
                if (state.isPrintResults()) {
                    System.out.println(tuple);
                }
            }
            queryResults.close();

            resultReporter.report(resultCount, tuple, operation);
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public abstract String getQueryString(SparqlDbConnectionState<TQueryStore> state, OperationType operation);

    public abstract OperationResult convertSingleResult(BindingSet bs);
}
