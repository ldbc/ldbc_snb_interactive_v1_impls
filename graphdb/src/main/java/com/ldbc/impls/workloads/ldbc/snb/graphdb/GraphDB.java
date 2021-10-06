package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBUpdateOperationHandler;

import org.eclipse.rdf4j.query.BindingSet;

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


	public static class InteractiveQuery8 extends GraphDBListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery8 operation) {
			return state.getQueryStore().getQuery8(operation);
		}
		//?from ?first ?last ?dt ?rep ?content
		@Override
		public LdbcQuery8Result convertSingleResult(BindingSet bindingSet, List<String> bindingNames) {
			GraphDBConverter graphDbConverter = new GraphDBConverter();
			return new LdbcQuery8Result(
					graphDbConverter.asLong(bindingSet.getBinding(bindingNames.get(0)).getValue()),
					bindingSet.getBinding(bindingNames.get(1)).getValue().stringValue(),
					bindingSet.getBinding(bindingNames.get(2)).getValue().stringValue(),
					GraphDBConverter.stringTimestampToEpoch(bindingSet.getBinding(bindingNames.get(3)).getValue().stringValue()),
					graphDbConverter.asLong(bindingSet.getBinding(bindingNames.get(4)).getValue()),
					bindingSet.getBinding(bindingNames.get(5)).getValue().stringValue());
		}

	}

	public static class InteractiveQuery9 extends GraphDBListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery9 operation) {
			return state.getQueryStore().getQuery9(operation);
		}

		//?fr ?first ?last ?post ?content ?date
		@Override
		public LdbcQuery9Result convertSingleResult(BindingSet bindingSet, List<String> bindingNames) {
			GraphDBConverter graphDbConverter = new GraphDBConverter();
			return new LdbcQuery9Result(
					graphDbConverter.asLong(bindingSet.getBinding(bindingNames.get(0)).getValue()),
					bindingSet.getBinding(bindingNames.get(1)).getValue().stringValue(),
					bindingSet.getBinding(bindingNames.get(2)).getValue().stringValue(),
					graphDbConverter.asLong(bindingSet.getBinding(bindingNames.get(3)).getValue()),
					bindingSet.getBinding(bindingNames.get(4)).getValue().stringValue(),
					GraphDBConverter.stringTimestampToEpoch(bindingSet.getBinding(bindingNames.get(5)).getValue().stringValue()));

		}
	}

	// public static class InteractiveQuery10 extends GraphDBListOperationHandler<LdbcQuery10, LdbcQuery9Result> {
	//
	// 	@Override
	// 	public String getQueryString(GraphDBConnectionState state, LdbcQuery9 operation) {
	// 		return state.getQueryStore().getQuery9(operation);
	// 	}
	//
	// 	//?first ?last  ?score ?fof ?gender ?locationname
	// 	@Override
	// 	public LdbcQuery10Result convertSingleResult(BindingSet bindingSet, List<String> bindingNames) {
	// 		GraphDBConverter graphDbConverter = new GraphDBConverter();
	// 		return new LdbcQuery10Result(
	// 				bindingSet.getBinding(bindingNames.get(1)).getValue().stringValue(),
	// 				bindingSet.getBinding(bindingNames.get(2)).getValue().stringValue(),
	// 				graphDbConverter.asLong(bindingSet.getBinding(bindingNames.get(3)).getValue()),
	// 				bindingSet.getBinding(bindingNames.get(4)).getValue().stringValue(),
	// 				GraphDBConverter.stringTimestampToEpoch(bindingSet.getBinding(bindingNames.get(5)).getValue().stringValue()));
	//
	// 	}
	// }

	// Interactive writes

	public static class Update2AddPostLike extends GraphDBUpdateOperationHandler<LdbcUpdate2AddPostLike> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate2AddPostLike operation) {
			return state.getQueryStore().getUpdate2(operation);
		}
	}

}
