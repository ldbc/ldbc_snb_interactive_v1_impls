package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.operationhandlers.GraphDBMultipleUpdateOperationHandler;
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
					cnv.asString(bindingSet, bindingNames.get(1)),
					cnv.asString(bindingSet, bindingNames.get(2)),
					cnv.timestampToEpoch(bindingSet, bindingNames.get(3)),
					cnv.asLong(bindingSet, bindingNames.get(4)),
					cnv.asString(bindingSet, bindingNames.get(5)));
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
					cnv.asString(bindingSet, bindingNames.get(1)),
					cnv.asString(bindingSet, bindingNames.get(2)),
					cnv.asLong(bindingSet, bindingNames.get(3)),
					bindingSet.getBinding(bindingNames.get(4)).getValue().stringValue(),
					cnv.timestampToEpoch(bindingSet, bindingNames.get(5)));
		}
	}

	public static class InteractiveQuery10 extends GraphDBListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery10 operation) {
			return state.getQueryStore().getQuery10(operation);
		}

		//?fof ?first ?last  ?score  ?gender ?locationname
		@Override
		public LdbcQuery10Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			return new LdbcQuery10Result(
					cnv.asLong(bindingSet, bindingNames.get(0)),
					cnv.asString(bindingSet, bindingNames.get(1)),
					cnv.asString(bindingSet, bindingNames.get(2)),
					cnv.asInt(bindingSet, bindingNames.get(3)),
					cnv.asString(bindingSet, bindingNames.get(4)),
					cnv.asString(bindingSet, bindingNames.get(5)));
		}
	}

	public static class InteractiveQuery11 extends GraphDBListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery11 operation) {
			return state.getQueryStore().getQuery11(operation);
		}

		//select ?fr ?first ?last ?orgname ?startdate
		@Override
		public LdbcQuery11Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			return new LdbcQuery11Result(
					cnv.asLong(bindingSet, bindingNames.get(0)),
					cnv.asString(bindingSet, bindingNames.get(1)),
					cnv.asString(bindingSet, bindingNames.get(2)),
					cnv.asString(bindingSet, bindingNames.get(3)),
					cnv.asInt(bindingSet, bindingNames.get(4)));
		}
	}

	public static class InteractiveQuery12 extends GraphDBListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery12 operation) {
			return state.getQueryStore().getQuery12(operation);
		}

		//?exp ?first ?last ?tagnames ?count
		@Override
		public LdbcQuery12Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			return new LdbcQuery12Result(
					cnv.asLong(bindingSet, bindingNames.get(0)),
					cnv.asString(bindingSet, bindingNames.get(1)),
					cnv.asString(bindingSet, bindingNames.get(2)),
					cnv.asStringCollection(bindingSet, bindingNames.get(3)),
					cnv.asInt(bindingSet, bindingNames.get(4)));
		}
	}

	public static class InteractiveQuery13 extends GraphDBSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery13 operation) {
			return state.getQueryStore().getQuery13(operation);
		}

		@Override
		public LdbcQuery13Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			if(bindingNames.get(0).isEmpty()) {
				System.out.println("empty");
			}
			return new LdbcQuery13Result(
					cnv.asInt(bindingSet, bindingNames.get(0)));
		}
	}

	public static class InteractiveQuery14 extends GraphDBListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcQuery14 operation) {
			return state.getQueryStore().getQuery14(operation);
		}

		@Override
		public LdbcQuery14Result convertSingleResult(List<String> bindingNames, BindingSet bindingSet) {
			return new LdbcQuery14Result(
					cnv.asNumberCollection(bindingSet, bindingNames.get(0)),
					cnv.asDouble(bindingSet, bindingNames.get(1)));
		}
	}

	// Interactive writes

	public static class Update1AddPerson extends GraphDBMultipleUpdateOperationHandler<LdbcUpdate1AddPerson> {

		@Override
		public List<String> getQueryString(GraphDBConnectionState state, LdbcUpdate1AddPerson operation) {
			return state.getQueryStore().getUpdate1Multiple(operation);
		}
	}

//	public static class Update1AddPerson extends GraphDBUpdateOperationHandler<LdbcUpdate1AddPerson> {
//
//		@Override
//		public String getQueryString(GraphDBConnectionState state, LdbcUpdate1AddPerson operation) {
//			return state.getQueryStore().getUpdate1Single(operation);
//		}
//	}

	public static class Update2AddPostLike extends GraphDBUpdateOperationHandler<LdbcUpdate2AddPostLike> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate2AddPostLike operation) {
			return state.getQueryStore().getUpdate2(operation);
		}
	}

	public static class Update3AddCommentLike extends GraphDBUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate3AddCommentLike operation) {
			return state.getQueryStore().getUpdate3(operation);
		}
	}

	public static class Update4AddForum extends GraphDBUpdateOperationHandler<LdbcUpdate4AddForum> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate4AddForum operation) {
			return state.getQueryStore().getUpdate4Single(operation);
		}
	}

	public static class Update5AddForumMembership extends GraphDBUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate5AddForumMembership operation) {
			return state.getQueryStore().getUpdate5(operation);
		}
	}

	public static class Update6AddPost extends GraphDBUpdateOperationHandler<LdbcUpdate6AddPost> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate6AddPost operation) {
			return state.getQueryStore().getUpdate6Single(operation);
		}
	}

	public static class Update8AddFriendship extends GraphDBUpdateOperationHandler<LdbcUpdate8AddFriendship> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcUpdate8AddFriendship operation) {
			return state.getQueryStore().getUpdate8(operation);
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
					cnv.asInt(bs, names.get(5)),
					cnv.asString(bs, names.get(6)),
					cnv.timestampToEpoch(bs, names.get(7)));
		}
	}

	public static class ShortQuery2PersonPosts extends GraphDBListOperationHandler<LdbcShortQuery2PersonPosts,
			LdbcShortQuery2PersonPostsResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery2PersonPosts operation) {
			return state.getQueryStore().getShortQuery2PersonPosts(operation);
		}

		@Override
		public LdbcShortQuery2PersonPostsResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery2PersonPostsResult(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.timestampToEpoch(bs, names.get(2)),
					cnv.asLong(bs, names.get(3)),
					cnv.asLong(bs, names.get(4)),
					cnv.asString(bs, names.get(5)),
					cnv.asString(bs, names.get(6)));
		}
	}

	public static class ShortQuery3PersonFriends extends GraphDBListOperationHandler<LdbcShortQuery3PersonFriends,
			LdbcShortQuery3PersonFriendsResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery3PersonFriends operation) {
			return state.getQueryStore().getShortQuery3PersonFriends(operation);
		}

		@Override
		public LdbcShortQuery3PersonFriendsResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery3PersonFriendsResult(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asString(bs, names.get(2)),
					cnv.timestampToEpoch(bs, names.get(3)));
		}
	}

	public static class ShortQuery4MessageContent extends GraphDBSingletonOperationHandler<LdbcShortQuery4MessageContent,
			LdbcShortQuery4MessageContentResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery4MessageContent operation) {
			return state.getQueryStore().getShortQuery4MessageContent(operation);
		}

		@Override
		public LdbcShortQuery4MessageContentResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery4MessageContentResult(cnv.asString(bs, names.get(0)),
					cnv.timestampToEpoch(bs, names.get(1)));
		}
	}

	public static class ShortQuery5MessageCreator extends GraphDBSingletonOperationHandler<LdbcShortQuery5MessageCreator,
			LdbcShortQuery5MessageCreatorResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery5MessageCreator operation) {
			return state.getQueryStore().getShortQuery5MessageCreator(operation);
		}

		@Override
		public LdbcShortQuery5MessageCreatorResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery5MessageCreatorResult(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asString(bs, names.get(2)));
		}
	}
	public static class ShortQuery6MessageForum extends GraphDBSingletonOperationHandler<LdbcShortQuery6MessageForum,
			LdbcShortQuery6MessageForumResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery6MessageForum operation) {
			return state.getQueryStore().getShortQuery6MessageForum(operation);
		}

		@Override
		public LdbcShortQuery6MessageForumResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery6MessageForumResult(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.asLong(bs, names.get(2)),
					cnv.asString(bs, names.get(3)),
					cnv.asString(bs, names.get(4)));
		}
	}

	public static class ShortQuery7MessageReplies extends GraphDBListOperationHandler<LdbcShortQuery7MessageReplies,
			LdbcShortQuery7MessageRepliesResult> {

		@Override
		public String getQueryString(GraphDBConnectionState state, LdbcShortQuery7MessageReplies operation) {
			return state.getQueryStore().getShortQuery7MessageReplies(operation);
		}

		@Override
		public LdbcShortQuery7MessageRepliesResult convertSingleResult(List<String> names, BindingSet bs) {
			return new LdbcShortQuery7MessageRepliesResult(cnv.asLong(bs, names.get(0)),
					cnv.asString(bs, names.get(1)),
					cnv.timestampToEpoch(bs, names.get(2)),
					cnv.asLong(bs, names.get(3)),
					cnv.asString(bs, names.get(4)),
					cnv.asString(bs, names.get(5)),
					cnv.asBoolean(bs, names.get(6)));
		}
	}

}
