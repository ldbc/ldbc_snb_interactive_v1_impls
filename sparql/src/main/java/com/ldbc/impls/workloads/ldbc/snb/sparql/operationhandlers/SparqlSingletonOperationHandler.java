package com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDbConnectionState;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;

import java.text.ParseException;

public abstract class SparqlSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, SparqlDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SparqlDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
            TOperationResult tuple = null;
            int resultCount = 0;

            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);

        try (final RepositoryConnection conn = state.getRepository().getConnection()) {
            TupleQuery tupleQuery = conn.prepareTupleQuery(queryString);
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

    public abstract TOperationResult convertSingleResult(BindingSet bs) throws ParseException;

}
