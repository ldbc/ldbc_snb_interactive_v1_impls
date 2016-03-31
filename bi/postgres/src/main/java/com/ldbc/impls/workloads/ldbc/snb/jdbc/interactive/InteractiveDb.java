package com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared.JdbcPreparedListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared.JdbcPreparedSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared.JdbcPreparedUpdateOperationHandler;

public class InteractiveDb extends JdbcDb<InteractiveQueryStore> {
	
	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		try {
			dbs = new JdbcDbConnectionStore<InteractiveQueryStore>(properties, new InteractiveQueryStore(properties.get("queryDir")));
		} catch (ClassNotFoundException | SQLException e) {
			throw new DbException(e);
		}
		
		//registerOperationHandler(LdbcQuery1.class, Query1.class); //ARRAY
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
		//registerOperationHandler(LdbcQuery12.class, Query12.class); //ARRAY
		//registerOperationHandler(LdbcQuery13.class, Query13.class);
		//registerOperationHandler(LdbcQuery14.class, Query14.class);
		//registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
		//registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class);
		//registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
		//registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
		//registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
		//registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
		//registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);
		registerOperationHandler(LdbcUpdate1AddPerson.class, Update1AddPerson.class);
		registerOperationHandler(LdbcUpdate2AddPostLike.class, Update2AddPostLike.class);
		registerOperationHandler(LdbcUpdate3AddCommentLike.class, Update3AddCommentLike.class);
		registerOperationHandler(LdbcUpdate4AddForum.class, Update4AddForum.class);
		registerOperationHandler(LdbcUpdate5AddForumMembership.class, Update5AddForumMembership.class);
		registerOperationHandler(LdbcUpdate6AddPost.class, Update6AddPost.class);
		registerOperationHandler(LdbcUpdate7AddComment.class, Update7AddComment.class);
		registerOperationHandler(LdbcUpdate8AddFriendship.class, Update8AddFriendship.class);
	}

	public static class Query1 extends JdbcPreparedListOperationHandler<LdbcQuery1, LdbcQuery1Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery1 operation) {
			return state.getQueryStore().getStmtQuery1(operation,con);
		}

		@Override
		public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
			LdbcQuery1Result qr = new LdbcQuery1Result(
					result.getLong(1),
					result.getString(2),
					result.getInt(13),
					timestampToTimestamp(result, 3),
					timestampToTimestamp(result, 4),
					result.getString(5),
					result.getString(6),
					result.getString(7),
					arrayToStringArray(result, 8),
					arrayToStringArray(result, 9),
					result.getString(10),
					convertLists(arrayToObjectArray(result, 11)),
					convertLists(arrayToObjectArray(result, 12)));
			return qr;
		}
		
		public Iterable<List<Object>> convertLists(Iterable<List<Object>> arr) {
			Iterable<ArrayList<Object>> better_arr = (Iterable<ArrayList<Object>>)(Object)arr;
			for (ArrayList<Object> entry : better_arr) {
				entry.set(1, Integer.parseInt((String)entry.get(1)));
			}
			return (Iterable<List<Object>>) (Object)better_arr;
		}
	}
	
	public static class Query2 extends JdbcPreparedListOperationHandler<LdbcQuery2, LdbcQuery2Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery2 operation) {
			return state.getQueryStore().getStmtQuery2(operation,con);
		}

		@Override
		public LdbcQuery2Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery2Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					result.getLong(4),
					result.getString(5),
					timestampToTimestamp(result, 6));
		}
		
	}
	
	public static class Query3 extends JdbcPreparedListOperationHandler<LdbcQuery3, LdbcQuery3Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery3 operation) {
			return state.getQueryStore().getStmtQuery3(operation,con);
		}

		@Override
		public LdbcQuery3Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery3Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					result.getInt(4),
					result.getInt(5),
					result.getInt(6));
		}
		
	}
	
	public static class Query4 extends JdbcPreparedListOperationHandler<LdbcQuery4, LdbcQuery4Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery4 operation) {
			return state.getQueryStore().getStmtQuery4(operation,con);
		}

		@Override
		public LdbcQuery4Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery4Result(
					result.getString(1),
					result.getInt(2));
		}
		
	}
	
	public static class Query5 extends JdbcPreparedListOperationHandler<LdbcQuery5, LdbcQuery5Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery5 operation) {
			return state.getQueryStore().getStmtQuery5(operation,con);
		}

		@Override
		public LdbcQuery5Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery5Result(
					result.getString(1),
					result.getInt(2));
		}
		
	}
	
	public static class Query6 extends JdbcPreparedListOperationHandler<LdbcQuery6, LdbcQuery6Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery6 operation) {
			return state.getQueryStore().getStmtQuery6(operation,con);
		}

		@Override
		public LdbcQuery6Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery6Result(
					result.getString(1),
					result.getInt(2));
		}
		
	}
	
	public static class Query7 extends JdbcPreparedListOperationHandler<LdbcQuery7, LdbcQuery7Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery7 operation) {
			return state.getQueryStore().getStmtQuery7(operation,con);
		}

		@Override
		public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
			// STUB
			return new LdbcQuery7Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					timestampToTimestamp(result, 4),
					result.getLong(5),
					result.getString(6),
					result.getInt(7),
					result.getBoolean(8)
					);
		}
		
	}
	
	public static class Query8 extends JdbcPreparedListOperationHandler<LdbcQuery8, LdbcQuery8Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery8 operation) {
			return state.getQueryStore().getStmtQuery8(operation,con);
		}

		@Override
		public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery8Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					timestampToTimestamp(result, 4),
					result.getLong(5),
					result.getString(6));
		}
		
	}
	
	public static class Query9 extends JdbcPreparedListOperationHandler<LdbcQuery9, LdbcQuery9Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery9 operation) {
			return state.getQueryStore().getStmtQuery9(operation,con);
		}

		@Override
		public LdbcQuery9Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery9Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					result.getLong(4),
					result.getString(5),
					timestampToTimestamp(result,6));
		}
		
	}
	
	public static class Query10 extends JdbcPreparedListOperationHandler<LdbcQuery10, LdbcQuery10Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery10 operation) {
			return state.getQueryStore().getStmtQuery10(operation,con);
		}

		@Override
		public LdbcQuery10Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery10Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					result.getInt(4),
					result.getString(5),
					result.getString(6));
		}
		
	}
	
	public static class Query11 extends JdbcPreparedListOperationHandler<LdbcQuery11, LdbcQuery11Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery11 operation) {
			return state.getQueryStore().getStmtQuery11(operation,con);
		}

		@Override
		public LdbcQuery11Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery11Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					result.getString(4),
					result.getInt(5));
		}
		
	}
	
	public static class Query12 extends JdbcPreparedListOperationHandler<LdbcQuery12, LdbcQuery12Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery12 operation) {
			return state.getQueryStore().getStmtQuery12(operation,con);
		}

		@Override
		public LdbcQuery12Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery12Result(
					result.getLong(1),
					result.getString(2),
					result.getString(3),
					arrayToStringArray(result, 4),
					result.getInt(5));
		}
		
	}
	
	public static class Query13 extends JdbcPreparedSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery13 operation) {
			return state.getQueryStore().getStmtQuery13(operation,con);
		}

		@Override
		public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery13Result(result.getInt(1));
		}
		
	}
	
	public static class Query14 extends JdbcPreparedListOperationHandler<LdbcQuery14, LdbcQuery14Result, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcQuery14 operation) {
			return state.getQueryStore().getStmtQuery14(operation,con);
		}

		@Override
		public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcQuery14Result(
					convertLists(arrayToObjectArray(result, 1)), 
					result.getInt(2));
		}
		
		public Iterable<Long> convertLists(Iterable<List<Object>> arr) {
			ArrayList<Long> new_arr = new ArrayList<>();
			ArrayList<ArrayList<Object>> better_arr = (ArrayList<ArrayList<Object>>)(Object)arr;
			for (ArrayList<Object> entry : better_arr) {
				new_arr.add((Long)entry.get(0));
			}
			new_arr.add((Long)better_arr.get(better_arr.size()-1).get(1));
			return new_arr;
		}
		
	}
	
	public static class ShortQuery1PersonProfile extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery1PersonProfile operation) {
			return state.getQueryStore().getStmtShortQuery1PersonProfile(operation,con);
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
	
	public static class ShortQuery2PersonPosts extends JdbcPreparedListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery2PersonPosts operation) {
			return state.getQueryStore().getStmtShortQuery2PersonPosts(operation,con);
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
	
	public static class ShortQuery3PersonFriends extends JdbcPreparedListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery3PersonFriends operation) {
			return state.getQueryStore().getStmtShortQuery3PersonFriends(operation,con);
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
	
	public static class ShortQuery4MessageContent extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery4MessageContent operation) {
			return state.getQueryStore().getStmtShortQuery4MessageContent(operation,con);
		}

		@Override
		public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery4MessageContentResult(
					result.getString(1),
					timestampToTimestamp(result, 2));
		}
		
	}
	
	public static class ShortQuery5MessageCreator extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery5MessageCreator operation) {
			return state.getQueryStore().getStmtShortQuery5MessageCreator(operation,con);
		}

		@Override
		public LdbcShortQuery5MessageCreatorResult convertSingleResult(ResultSet result) throws SQLException {
			return new LdbcShortQuery5MessageCreatorResult(
					result.getLong(1),
					result.getString(2),
					result.getString(3));
		}
		
	}
	
	public static class ShortQuery6MessageForum extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery6MessageForum operation) {
			return state.getQueryStore().getStmtShortQuery6MessageForum(operation,con);
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
	
	public static class ShortQuery7MessageReplies extends JdbcPreparedListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcShortQuery7MessageReplies operation) {
			return state.getQueryStore().getStmtShortQuery7MessageReplies(operation,con);
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
	
	public static class Update2AddPostLike extends JdbcPreparedUpdateOperationHandler<LdbcUpdate2AddPostLike, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate2AddPostLike operation) {
			return state.getQueryStore().getStmtUpdate2AddPostLike(operation,con);
		}
		
	}
	
	public static class Update3AddCommentLike extends JdbcPreparedUpdateOperationHandler<LdbcUpdate3AddCommentLike, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate3AddCommentLike operation) {
			return state.getQueryStore().getStmtUpdate3AddCommentLike(operation,con);
		}
	}
	
	public static class Update4AddForum extends JdbcMultipleUpdateOperationHandler<LdbcUpdate4AddForum, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public List<String> getQueryString(JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate4AddForum operation) {
			return state.getQueryStore().getUpdate4AddForum(operation);
		}
	}
	
	public static class Update5AddForumMembership extends JdbcPreparedUpdateOperationHandler<LdbcUpdate5AddForumMembership, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate5AddForumMembership operation) {
			return state.getQueryStore().getStmtUpdate5AddForumMembership(operation,con);
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
	
	public static class Update8AddFriendship extends JdbcPreparedUpdateOperationHandler<LdbcUpdate8AddFriendship, LdbcNoResult, InteractiveQueryStore> {

		@Override
		public PreparedStatement getStatement(Connection con, JdbcDbConnectionStore<InteractiveQueryStore> state, LdbcUpdate8AddFriendship operation) {
			return state.getQueryStore().getStmtUpdate8AddFriendship(operation,con);
		}
		
	}
}
