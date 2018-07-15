package com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class CypherListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, CypherDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, CypherDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        Session session = state.getSession();
        List<TOperationResult> results = new ArrayList<>();
        int resultCount = 0;
        results.clear();

        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);
        final StatementResult result = session.run(queryString);
        while (result.hasNext()) {
            final Record record = result.next();

            resultCount++;
            TOperationResult tuple;
            try {
                tuple = convertSingleResult(record);
            } catch (ParseException e) {
                throw new DbException(e);
            }
            if (state.isPrintResults()) {
                System.out.println(tuple.toString());
            }
            results.add(tuple);
        }
        session.close();
        resultReporter.report(resultCount, results, operation);
    }

    public abstract TOperationResult convertSingleResult(Record record) throws ParseException;

}
