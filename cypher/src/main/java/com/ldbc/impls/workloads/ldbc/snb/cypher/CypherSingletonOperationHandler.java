package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.SnbQueryStore;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public abstract class CypherSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, QueryStore extends SnbQueryStore>
implements OperationHandler<OperationType, CypherDriverConnectionState<QueryStore>> {

@Override
public void executeOperation(OperationType operation, CypherDriverConnectionState<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Session session = state.getSession();
	OperationResult tuple = null;
	int resultCount = 0;

	final String queryString = getQueryString(state, operation);
	state.logQuery(operation.getClass().getSimpleName(), queryString);
	final StatementResult result = session.run(queryString);
	if (result.hasNext()) {
		final Record record = result.next();
		resultCount++;

		tuple = convertSingleResult(record);
		if (state.isPrintResults()) {
			System.out.println(tuple.toString());
		}
	}
	session.close();

	resultReporter.report(resultCount, tuple, operation);			
}

public abstract String getQueryString(CypherDriverConnectionState<QueryStore> state, OperationType operation);
public abstract OperationResult convertSingleResult(Record record);
}
