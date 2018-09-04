package com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDbConnectionState;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class SparqlListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, SparqlDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SparqlDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        final List<TOperationResult> results = new ArrayList<>();
        int resultCount = 0;
        results.clear();

        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);
        try (final RepositoryConnection conn = state.getRepository().getConnection()) {

            TupleQuery tupleQuery = conn.prepareTupleQuery(queryString);
            TupleQueryResult queryResults = tupleQuery.evaluate();

            while (queryResults.hasNext()) {
                final BindingSet bs = queryResults.next();
                resultCount++;
                TOperationResult tuple;
                tuple = convertSingleResult(bs);
                results.add(tuple);
                if (state.isPrintResults()) {
                    System.out.println(tuple);
                }
            }
            queryResults.close();

            resultReporter.report(resultCount, results, operation);
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public abstract TOperationResult convertSingleResult(BindingSet record) throws ParseException;

}
