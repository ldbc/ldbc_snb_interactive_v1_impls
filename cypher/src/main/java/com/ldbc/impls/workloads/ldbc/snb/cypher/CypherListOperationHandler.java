package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class CypherListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, TQueryStore extends QueryStore>
        implements OperationHandler<OperationType, CypherDbConnectionState<TQueryStore>> {

    @Override
    public void executeOperation(OperationType operation, CypherDbConnectionState<TQueryStore> state,
                                 ResultReporter resultReporter) throws DbException {
        Session session = state.getSession();
        List<OperationResult> results = new ArrayList<OperationResult>();
        int resultCount = 0;
        results.clear();

        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);
        final StatementResult result = session.run(queryString);
        while (result.hasNext()) {
            final Record record = result.next();

            resultCount++;
            OperationResult tuple = null;
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

    public abstract String getQueryString(CypherDbConnectionState<TQueryStore> state, OperationType operation);

    public abstract OperationResult convertSingleResult(Record record) throws ParseException;
}
