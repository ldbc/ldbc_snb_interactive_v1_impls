package com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDbConnectionState;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class SparqlListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult>
        implements OperationHandler<OperationType, SparqlDbConnectionState> {

    @Override
    public void executeOperation(OperationType operation, SparqlDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try {
            final List<OperationResult> results = new ArrayList<>();
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

    public abstract String getQueryString(SparqlDbConnectionState state, OperationType operation);

    public abstract OperationResult convertSingleResult(BindingSet record) throws ParseException;
}
