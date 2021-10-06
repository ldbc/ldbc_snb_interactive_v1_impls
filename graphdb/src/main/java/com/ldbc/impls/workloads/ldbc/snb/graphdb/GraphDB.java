package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBListOperationHandler;

import org.eclipse.rdf4j.query.BindingSet;

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
					cnv.timestampToEpoch(bs, names.get(3)),
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
}

