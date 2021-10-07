package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBUpdateOperationHandler;

import org.eclipse.rdf4j.query.BindingSet;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class GraphDB extends BaseDb<GraphDBQueryStore> {

	private static final GraphDBConverter cnv = new GraphDBConverter();

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		dcs = new GraphDBConnectionState(properties, new GraphDBQueryStore(properties.get("queryDir")));
	}

	// Interactive complex reads

	public static class InteractiveQuery1 extends GraphDBListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery1 operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcQuery1Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery1Result(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asInt(bs, names.get(2)),
					cnv.localDateToEpoch(bs, names.get(3)),
					cnv.timestampToEpoch(bs, names.get(4)),
					cnv.asString(bs, names.get(5)),
					cnv.asString(bs, names.get(6)),
					cnv.asString(bs, names.get(7)),
					cnv.asStringCollection(bs, names.get(8)),
					cnv.asStringCollection(bs, names.get(9)),
					cnv.asString(bs, names.get(10)),
					cnv.asObjectCollection(bs, names.get(11)),
					cnv.asObjectCollection(bs, names.get(12)));
		}
	}

	public static class InteractiveQuery2 extends GraphDBListOperationHandler<LdbcQuery2, LdbcQuery2Result> {
		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery2 operation) {
			return state.getQueryStore().getQuery2(operation);
		}

		@Override
		public LdbcQuery2Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery2Result(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asString(bs, names.get(2)),
					cnv.asLong(bs, names.get(3)),
					cnv.asString(bs, names.get(4)),
					cnv.timestampToEpoch(bs, names.get(5)));
		}
	}

	public static class InteractiveQuery3 extends GraphDBListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery3 operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcQuery3Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery3Result(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asString(bs, names.get(2)),
					cnv.asInt(bs, names.get(3)),
					cnv.asInt(bs, names.get(4)),
					cnv.asInt(bs, names.get(5)));
		}
	}

	public static class InteractiveQuery4 extends GraphDBListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery4 operation) {
			return state.getQueryStore().getQuery4(operation);
		}

		@Override
		public LdbcQuery4Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery4Result(cnv.asString(bs, names.get(0)),
					cnv.asInt(bs, names.get(1)));
		}
	}

	public static class InteractiveQuery5 extends GraphDBListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery5 operation) {
			return state.getQueryStore().getQuery5(operation);
		}

		@Override
		public LdbcQuery5Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery5Result(cnv.asString(bs, names.get(0)),
					cnv.asInt(bs, names.get(1)));
		}
	}

	public static class InteractiveQuery6 extends GraphDBListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery6 operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcQuery6Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery6Result(cnv.asString(bs, names.get(0)),
					cnv.asInt(bs, names.get(1)));
		}
	}

	public static class InteractiveQuery7 extends GraphDBListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery7 operation) {
			return state.getQueryStore().getQuery7(operation);
		}

		@Override
		public LdbcQuery7Result convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcQuery7Result(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asString(bs, names.get(2)),
					cnv.timestampToEpoch(bs, names.get(3)),
					cnv.asLong(bs, names.get(4)),
					cnv.asString(bs, names.get(5)),
					cnv.asInt(bs, names.get(6)),
					cnv.asBoolean(bs, names.get(7)));
		}
	}

	public static class InteractiveQuery8 extends GraphDBListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery8 operation) {
			return state.getQueryStore().getQuery8(operation);
		}

		//?from ?first ?last ?dt ?rep ?content
		@Override
		public LdbcQuery8Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			return new LdbcQuery8Result(
					cnv.asLong(bindingSet, bindingNames.get(0)),
					bindingSet.getBinding(bindingNames.get(1)).getValue().stringValue(),
					bindingSet.getBinding(bindingNames.get(2)).getValue().stringValue(),
					cnv.timestampToEpoch(bindingSet, bindingNames.get(3)),
					cnv.asLong(bindingSet, bindingNames.get(4)),
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
		public LdbcQuery9Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			return new LdbcQuery9Result(
					cnv.asLong(bindingSet, bindingNames.get(0)),
					bindingSet.getBinding(bindingNames.get(1)).getValue().stringValue(),
					bindingSet.getBinding(bindingNames.get(2)).getValue().stringValue(),
					cnv.asLong(bindingSet, bindingNames.get(3)),
					bindingSet.getBinding(bindingNames.get(4)).getValue().stringValue(),
					cnv.timestampToEpoch(bindingSet, bindingNames.get(5)));

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

	// Interactive short reads

	public static class ShortQuery1PersonProfile extends GraphDBSingletonOperationHandler<LdbcShortQuery1PersonProfile,
			LdbcShortQuery1PersonProfileResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery1PersonProfile operation) {
			return state.getQueryStore().getShortQuery1PersonProfile(operation);
		}

		@Override
		public LdbcShortQuery1PersonProfileResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery1PersonProfileResult(cnv.asString(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.localDateToEpoch(bs, names.get(2)),
					cnv.asString(bs, names.get(3)),
					cnv.asString(bs, names.get(4)),
					cnv.asLong(bs, names.get(5)),
					cnv.asString(bs, names.get(6)),
					cnv.timestampToEpoch(bs, names.get(7)));
		}
	}

}
