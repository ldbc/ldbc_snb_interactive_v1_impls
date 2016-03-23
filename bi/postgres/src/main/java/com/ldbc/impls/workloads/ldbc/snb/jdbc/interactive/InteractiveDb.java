package com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfileResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPostsResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriendsResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContentResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreatorResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForumResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageRepliesResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate5AddForumMembership;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDb;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcUpdateOperationHandler;

public class InteractiveDb extends JdbcDb<InteractiveQueryStore> {
	
	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		try {
			dbs = new JdbcDbConnectionStore<InteractiveQueryStore>(properties, new InteractiveQueryStore(properties.get("queryDir")));
		} catch (ClassNotFoundException | SQLException e) {
			throw new DbException(e);
		}
		
		registerOperationHandler(LdbcQuery1.class, Query1.class);
		registerOperationHandler(LdbcQuery2.class, Query2.class);
		registerOperationHandler(LdbcQuery3.class, Query3.class);
		registerOperationHandler(LdbcQuery4.class, Query4.class);
		registerOperationHandler(LdbcQuery5.class, Query5.class);
		registerOperationHandler(LdbcQuery6.class, Query6.class);
		registerOperationHandler(LdbcQuery7.class, Query7.class);
		registerOperationHandler(LdbcQuery8.class, Query8.class);
		registerOperationHandler(LdbcQuery9.class, Query9.class);
		registerOperationHandler(LdbcQuery10.class, Query10.class);
		registerOperationHandler(LdbcQuery11.class, Query11.class);
		registerOperationHandler(LdbcQuery12.class, Query12.class);
		registerOperationHandler(LdbcQuery13.class, Query13.class);
		registerOperationHandler(LdbcQuery14.class, Query14.class);
		registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
		registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class);
		registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
		registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
		registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
		registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
		registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);
		registerOperationHandler(LdbcUpdate1AddPerson.class, Update1AddPerson.class);
		registerOperationHandler(LdbcUpdate2AddPostLike.class, Update2AddPostLike.class);
		registerOperationHandler(LdbcUpdate3AddCommentLike.class, Update3AddCommentLike.class);
		registerOperationHandler(LdbcUpdate4AddForum.class, Update4AddForum.class);
		registerOperationHandler(LdbcUpdate5AddForumMembership.class, Update5AddForumMembership.class);
		registerOperationHandler(LdbcUpdate6AddPost.class, Update6AddPost.class);
		registerOperationHandler(LdbcUpdate7AddComment.class, Update7AddComment.class);
		registerOperationHandler(LdbcUpdate8AddFriendship.class, Update8AddFriendship.class);
	}

	public static class Query1 extends JdbcListOperationHandler<LdbcQuery1, LdbcQuery1Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery1 operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery1Result(0, "", 0, 0, 0, "", "", "", new ArrayList<String>(), new ArrayList<String>(), "", new ArrayList<List<Object>>(), new ArrayList<List<Object>>());
		}
		
	}
	
	public static class Query2 extends JdbcListOperationHandler<LdbcQuery2, LdbcQuery2Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery2 operation) {
			return state.getQueryStore().getQuery2(operation);
		}

		@Override
		public LdbcQuery2Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery2Result(0, "", "", 0, "", 0);
		}
		
	}
	
	public static class Query3 extends JdbcListOperationHandler<LdbcQuery3, LdbcQuery3Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery3 operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcQuery3Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery3Result(0, "", "", 0, 0, 0);
		}
		
	}
	
	public static class Query4 extends JdbcListOperationHandler<LdbcQuery4, LdbcQuery4Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery4 operation) {
			return state.getQueryStore().getQuery4(operation);
		}

		@Override
		public LdbcQuery4Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery4Result("", 0);
		}
		
	}
	
	public static class Query5 extends JdbcListOperationHandler<LdbcQuery5, LdbcQuery5Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery5 operation) {
			return state.getQueryStore().getQuery5(operation);
		}

		@Override
		public LdbcQuery5Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery5Result("", 0);
		}
		
	}
	
	public static class Query6 extends JdbcListOperationHandler<LdbcQuery6, LdbcQuery6Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery6 operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcQuery6Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery6Result("", 0);
		}
		
	}
	
	public static class Query7 extends JdbcListOperationHandler<LdbcQuery7, LdbcQuery7Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery7 operation) {
			return state.getQueryStore().getQuery7(operation);
		}

		@Override
		public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery7Result(0, "", "", 0, 0, "", 0, false);
		}
		
	}
	
	public static class Query8 extends JdbcListOperationHandler<LdbcQuery8, LdbcQuery8Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery8 operation) {
			return state.getQueryStore().getQuery8(operation);
		}

		@Override
		public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery8Result(0, "", "", 0, 0, "");
		}
		
	}
	
	public static class Query9 extends JdbcListOperationHandler<LdbcQuery9, LdbcQuery9Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery9 operation) {
			return state.getQueryStore().getQuery9(operation);
		}

		@Override
		public LdbcQuery9Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery9Result(0, "", "", 0, "", 0);
		}
		
	}
	
	public static class Query10 extends JdbcListOperationHandler<LdbcQuery10, LdbcQuery10Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery10 operation) {
			return state.getQueryStore().getQuery10(operation);
		}

		@Override
		public LdbcQuery10Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery10Result(0, "", "", 0, "", "");
		}
		
	}
	
	public static class Query11 extends JdbcListOperationHandler<LdbcQuery11, LdbcQuery11Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery11 operation) {
			return state.getQueryStore().getQuery11(operation);
		}

		@Override
		public LdbcQuery11Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery11Result(0, "", "", "", 0);
		}
		
	}
	
	public static class Query12 extends JdbcListOperationHandler<LdbcQuery12, LdbcQuery12Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery12 operation) {
			return state.getQueryStore().getQuery12(operation);
		}

		@Override
		public LdbcQuery12Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery12Result(0, "", "", new ArrayList<String>(), 0);
		}
		
	}
	
	public static class Query13 extends JdbcSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery13 operation) {
			return state.getQueryStore().getQuery13(operation);
		}

		@Override
		public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery13Result(0);
		}
		
	}
	
	public static class Query14 extends JdbcListOperationHandler<LdbcQuery14, LdbcQuery14Result, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery14 operation) {
			return state.getQueryStore().getQuery14(operation);
		}

		@Override
		public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery14Result(new ArrayList<Integer>(), 0.0);
		}
		
	}
	
	public static class ShortQuery1PersonProfile extends JdbcSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery1PersonProfile operation) {
			return state.getQueryStore().getShortQuery1PersonProfile(operation);
		}

		@Override
		public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery1PersonProfileResult(
					result.getString(1),
					result.getString(2),
					timestampToTimestamp(result, 3),
					result.getString(4),
					result.getString(5),
					result.getLong(6),
					result.getString(7),
					timestampToTimestamp(result, 8));
		}
		
	}
	
	public static class ShortQuery2PersonPosts extends JdbcListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery2PersonPosts operation) {
			return state.getQueryStore().getShortQuery2PersonPosts(operation);
		}

		@Override
		public LdbcShortQuery2PersonPostsResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery2PersonPostsResult(
					result.getLong(1),
					result.getString(2),
					timestampToTimestamp(result, 3),
					result.getLong(4),
					result.getLong(5),
					result.getString(6),
					result.getString(7));
		}
		
	}
	
	public static class ShortQuery3PersonFriends extends JdbcListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery3PersonFriends operation) {
			return state.getQueryStore().getShortQuery3PersonFriends(operation);
		}

		@Override
		public LdbcShortQuery3PersonFriendsResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery3PersonFriendsResult(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					timestampToTimestamp(result, 4));
		}
		
	}
	
	public static class ShortQuery4MessageContent extends JdbcSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery4MessageContent operation) {
			return state.getQueryStore().getShortQuery4MessageContent(operation);
		}

		@Override
		public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery4MessageContentResult(
					result.getString(1),
					timestampToTimestamp(result, 2));
		}
		
	}
	
	public static class ShortQuery5MessageCreator extends JdbcSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery5MessageCreator operation) {
			return state.getQueryStore().getShortQuery5MessageCreator(operation);
		}

		@Override
		public LdbcShortQuery5MessageCreatorResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery5MessageCreatorResult(
					result.getLong(1),
					result.getString(2),
					result.getString(3));
		}
		
	}
	
	public static class ShortQuery6MessageForum extends JdbcSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery6MessageForum operation) {
			return state.getQueryStore().getShortQuery6MessageForum(operation);
		}

		@Override
		public LdbcShortQuery6MessageForumResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery6MessageForumResult(
					result.getLong(1),
					result.getString(2),
					result.getLong(3),
					result.getString(4),
					result.getString(5));
		}
		
	}
	
	public static class ShortQuery7MessageReplies extends JdbcListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery7MessageReplies operation) {
			return state.getQueryStore().getShortQuery7MessageReplies(operation);
		}

		@Override
		public LdbcShortQuery7MessageRepliesResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery7MessageRepliesResult(
					result.getLong(1),
					result.getString(2),
					timestampToTimestamp(result, 3),
					result.getLong(4),
					result.getString(5),
					result.getString(6),
					result.getBoolean(7));
		}
		
	}
	
	public static class Update1AddPerson extends JdbcMultipleUpdateOperationHandler<LdbcUpdate1AddPerson, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public List<String> getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate1AddPerson operation) {
			return state.getQueryStore().getUpdate1AddPerson(operation);
		}
	}
	
	public static class Update2AddPostLike extends JdbcUpdateOperationHandler<LdbcUpdate2AddPostLike, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate2AddPostLike operation) {
			return state.getQueryStore().getUpdate2AddPostLike(operation);
		}
		
	}
	
	public static class Update3AddCommentLike extends JdbcUpdateOperationHandler<LdbcUpdate3AddCommentLike, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate3AddCommentLike operation) {
			return state.getQueryStore().getUpdate3AddCommentLike(operation);
		}
	}
	
	public static class Update4AddForum extends JdbcMultipleUpdateOperationHandler<LdbcUpdate4AddForum, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public List<String> getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate4AddForum operation) {
			return state.getQueryStore().getUpdate4AddForum(operation);
		}
	}
	
	public static class Update5AddForumMembership extends JdbcUpdateOperationHandler<LdbcUpdate5AddForumMembership, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate5AddForumMembership operation) {
			return state.getQueryStore().getUpdate5AddForumMembership(operation);
		}
	}
	
	public static class Update6AddPost extends JdbcMultipleUpdateOperationHandler<LdbcUpdate6AddPost, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public List<String> getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate6AddPost operation) {
			return state.getQueryStore().getUpdate6AddPost(operation);
		}
	}
	
	public static class Update7AddComment extends JdbcMultipleUpdateOperationHandler<LdbcUpdate7AddComment, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public List<String> getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate7AddComment operation) {
			return state.getQueryStore().getUpdate7AddComment(operation);
		}
		
	}
	
	public static class Update8AddFriendship extends JdbcUpdateOperationHandler<LdbcUpdate8AddFriendship, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate8AddFriendship operation) {
			return state.getQueryStore().getUpdate8AddFriendship(operation);
		}
		
	}
}
