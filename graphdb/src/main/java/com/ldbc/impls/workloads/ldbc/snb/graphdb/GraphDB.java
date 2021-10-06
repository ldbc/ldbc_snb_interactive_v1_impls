package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8Result;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBListOperationHandler;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.query.BindingSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphDB extends BaseDb<GraphDBQueryStore> {
	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {

	}

	// Interactive complex reads

	public static class InteractiveQuery1 extends GraphDBListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery1 operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcQuery1Result convertSingleResult(BindingSet bindingSet) {
			return null;
		}
	}


	// public static class InteractiveQuery8 extends GraphDBListOperationHandler<LdbcQuery8, LdbcQuery8Result> {
	//
	// 	@Override
	// 	public String getQueryString(GraphDBConnectionState state, LdbcQuery8 operation) {
	// 		return state.getQueryStore().getQuery8(operation);
	// 	}
	//
	// 	@Override
	// 	public LdbcQuery8Result convertSingleResult(BindingSet bindingSet) {
	// 		return new LdbcQuery8Result(
	// 				Long.parseLong(bindingSet.getBinding("personId").getValue().stringValue()),
	// 				result.getString(2),
	// 				result.getString(3),
	// 				PostgresConverter.stringTimestampToEpoch(result, 4),
	// 				Long.parseLong(bindingSet.getBinding("personId").getValue().stringValue()),
	// 				result.getString(6));
	// 	}
	// 	}
	//}
}
