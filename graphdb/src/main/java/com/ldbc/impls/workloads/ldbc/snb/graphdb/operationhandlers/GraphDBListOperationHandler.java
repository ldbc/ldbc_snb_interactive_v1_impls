package com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.GraphDBConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import org.eclipse.rdf4j.query.BindingSet;

import java.sql.ResultSet;
import java.util.List;

public abstract class GraphDBListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
		implements ListOperationHandler<TOperationResult, TOperation, GraphDBConnectionState> {

	@Override
	public void executeOperation(TOperation operation, GraphDBConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException {

	}

	public abstract TOperationResult convertSingleResult(BindingSet result);
}
