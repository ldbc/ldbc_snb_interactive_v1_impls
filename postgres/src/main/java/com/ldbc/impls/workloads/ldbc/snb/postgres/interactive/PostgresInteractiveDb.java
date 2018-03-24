package com.ldbc.impls.workloads.ldbc.snb.postgres.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
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
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresDb;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresPoolingDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresSingletonOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgresInteractiveDb extends PostgresDb<PostgresInteractiveQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new PostgresPoolingDbConnectionState(properties, new PostgresInteractiveQueryStore(properties.get("queryDir")));
        } catch (ClassNotFoundException | SQLException e) {
            throw new DbException(e);
        }

        registerOperationHandler(LdbcQuery1.class, Query1.class); //ARRAY RESULT
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
        registerOperationHandler(LdbcQuery12.class, Query12.class); //ARRAY RESULT
        registerOperationHandler(LdbcQuery13.class, Query13.class); //NESTED WITH
        registerOperationHandler(LdbcQuery14.class, Query14.class); //NESTED WITH
//		registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
//		registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class); //BUG
//		registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
//		registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
//		registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
//		registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
//		registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);
//		registerOperationHandler(LdbcUpdate1AddPerson.class, Update1AddPerson.class);
//		registerOperationHandler(LdbcUpdate2AddPostLike.class, Update2AddPostLike.class);
//		registerOperationHandler(LdbcUpdate3AddCommentLike.class, Update3AddCommentLike.class);
//		registerOperationHandler(LdbcUpdate4AddForum.class, Update4AddForum.class);
//		registerOperationHandler(LdbcUpdate5AddForumMembership.class, Update5AddForumMembership.class);
//		registerOperationHandler(LdbcUpdate6AddPost.class, Update6AddPost.class);
//		registerOperationHandler(LdbcUpdate7AddComment.class, Update7AddComment.class);
//		registerOperationHandler(LdbcUpdate8AddFriendship.class, Update8AddFriendship.class);
    }

    public static class Query1 extends PostgresListOperationHandler<LdbcQuery1, LdbcQuery1Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
            LdbcQuery1Result qr = new LdbcQuery1Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getInt(3),
                    stringTimestampToEpoch(result, 4),
                    stringTimestampToEpoch(result, 5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    arrayToStringArray(result, 9),
                    arrayToStringArray(result, 10),
                    result.getString(11),
                    convertLists(arrayToObjectArray(result, 12)),
                    convertLists(arrayToObjectArray(result, 13)));
            return qr;
        }

        @SuppressWarnings("unchecked")
        public Iterable<List<Object>> convertLists(Iterable<List<Object>> arr) {
            for (List<Object> entry : arr) {
                entry.set(1, Integer.parseInt((String) entry.get(1)));
            }
            return arr;
        }
    }

    public static class Query2 extends PostgresListOperationHandler<LdbcQuery2, LdbcQuery2Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcQuery2Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery2Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    result.getLong(4),
                    result.getString(5),
                    stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query3 extends PostgresListOperationHandler<LdbcQuery3, LdbcQuery3Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation);
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

    public static class Query4 extends PostgresListOperationHandler<LdbcQuery4, LdbcQuery4Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery4Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query5 extends PostgresListOperationHandler<LdbcQuery5, LdbcQuery5Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery5Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query6 extends PostgresListOperationHandler<LdbcQuery6, LdbcQuery6Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery6Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query7 extends PostgresListOperationHandler<LdbcQuery7, LdbcQuery7Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery7Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getBoolean(8));
        }

    }

    public static class Query8 extends PostgresListOperationHandler<LdbcQuery8, LdbcQuery8Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery8Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6));
        }

    }

    public static class Query9 extends PostgresListOperationHandler<LdbcQuery9, LdbcQuery9Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcQuery9Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery9Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    result.getLong(4),
                    result.getString(5),
                    stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query10 extends PostgresListOperationHandler<LdbcQuery10, LdbcQuery10Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10(operation);
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

    public static class Query11 extends PostgresListOperationHandler<LdbcQuery11, LdbcQuery11Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11(operation);
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

    public static class Query12 extends PostgresListOperationHandler<LdbcQuery12, LdbcQuery12Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
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

    public static class Query13 extends PostgresSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }

    }

    public static class Query14 extends PostgresListOperationHandler<LdbcQuery14, LdbcQuery14Result, PostgresInteractiveQueryStore> {

        @Override
        public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    convertLists(arrayToObjectArray(result, 1)),
                    result.getDouble(2));
        }

        public Iterable<Long> convertLists(Iterable<List<Object>> arr) {
            List<Long> new_arr = new ArrayList<>();
            List<List<Object>> better_arr = (List<List<Object>>) arr;
            for (List<Object> entry : better_arr) {
                new_arr.add((Long) entry.get(0));
            }
            new_arr.add((Long) better_arr.get(better_arr.size() - 1).get(1));
            return new_arr;
        }

    }

//	public static class ShortQuery1PersonProfile extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery1PersonProfile operation) {
//			return state.getQueryStore().getShortQuery1PersonProfile(operation);
//		}
//
//		@Override
//		public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery1PersonProfileResult(
//					result.getString(1),
//					result.getString(2),
//					stringTimestampToEpoch(result, 3),
//					result.getString(4),
//					result.getString(5),
//					result.getLong(6),
//					result.getString(7),
//					stringTimestampToEpoch(result, 8));
//		}
//
//	}
//
//	public static class ShortQuery2PersonPosts extends PostgresListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery2PersonPosts operation) {
//			return state.getQueryStore().getShortQuery2PersonPosts(operation);
//		}
//
//		@Override
//		public LdbcShortQuery2PersonPostsResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery2PersonPostsResult(
//					result.getLong(1),
//					result.getString(2),
//					stringTimestampToEpoch(result, 3),
//					result.getLong(4),
//					result.getLong(5),
//					result.getString(6),
//					result.getString(7));
//		}
//
//	}
//
//	public static class ShortQuery3PersonFriends extends PostgresListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery3PersonFriends operation) {
//			return state.getQueryStore().getShortQuery3PersonFriends(operation);
//		}
//
//		@Override
//		public LdbcShortQuery3PersonFriendsResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery3PersonFriendsResult(
//					result.getLong(1),
//					result.getString(2),
//					result.getString(3),
//					stringTimestampToEpoch(result, 4));
//		}
//
//	}
//
//	public static class ShortQuery4MessageContent extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery4MessageContent operation) {
//			return state.getQueryStore().getShortQuery4MessageContent(operation);
//		}
//
//		@Override
//		public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery4MessageContentResult(
//					result.getString(1),
//					stringTimestampToEpoch(result, 2));
//		}
//
//	}
//
//	public static class ShortQuery5MessageCreator extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery5MessageCreator operation) {
//			return state.getQueryStore().getShortQuery5MessageCreator(operation);
//		}
//
//		@Override
//		public LdbcShortQuery5MessageCreatorResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery5MessageCreatorResult(
//					result.getLong(1),
//					result.getString(2),
//					result.getString(3));
//		}
//
//	}
//
//	public static class ShortQuery6MessageForum extends JdbcPreparedSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery6MessageForum operation) {
//			return state.getQueryStore().getShortQuery6MessageForum(operation);
//		}
//
//		@Override
//		public LdbcShortQuery6MessageForumResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery6MessageForumResult(
//					result.getLong(1),
//					result.getString(2),
//					result.getLong(3),
//					result.getString(4),
//					result.getString(5));
//		}
//
//	}
//
//	public static class ShortQuery7MessageReplies extends PostgresListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcShortQuery7MessageReplies operation) {
//			return state.getQueryStore().getShortQuery7MessageReplies(operation);
//		}
//
//		@Override
//		public LdbcShortQuery7MessageRepliesResult convertSingleResult(ResultSet result) throws SQLException {
//			return new LdbcShortQuery7MessageRepliesResult(
//					result.getLong(1),
//					result.getString(2),
//					stringTimestampToEpoch(result, 3),
//					result.getLong(4),
//					result.getString(5),
//					result.getString(6),
//					result.getBoolean(7));
//		}
//
//	}
//
//	public static class Update1AddPerson extends JdbcPreparedMultipleUpdateOperationHandler<LdbcUpdate1AddPerson, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public List<PreparedStatement> getStatements(Connection con, PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate1AddPerson operation) throws SQLException {
//			return state.getQueryStore().getUpdate1AddPerson(operation);
//		}
//	}
//
//	public static class Update2AddPostLike extends JdbcPreparedUpdateOperationHandler<LdbcUpdate2AddPostLike, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate2AddPostLike operation) {
//			return state.getQueryStore().getUpdate2AddPostLike(operation);
//		}
//
//	}
//
//	public static class Update3AddCommentLike extends JdbcPreparedUpdateOperationHandler<LdbcUpdate3AddCommentLike, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate3AddCommentLike operation) {
//			return state.getQueryStore().getUpdate3AddCommentLike(operation);
//		}
//	}
//
//	public static class Update4AddForum extends JdbcPreparedMultipleUpdateOperationHandler<LdbcUpdate4AddForum, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public List<PreparedStatement> getStatements(Connection con, PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate4AddForum operation) throws SQLException {
//			return state.getQueryStore().getUpdate4AddForum(operation);
//		}
//	}
//
//	public static class Update5AddForumMembership extends JdbcPreparedUpdateOperationHandler<LdbcUpdate5AddForumMembership, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate5AddForumMembership operation) {
//			return state.getQueryStore().getUpdate5AddForumMembership(operation);
//		}
//	}
//
//	public static class Update6AddPost extends JdbcPreparedMultipleUpdateOperationHandler<LdbcUpdate6AddPost, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public List<PreparedStatement> getStatements(Connection con, PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate6AddPost operation) throws SQLException {
//			return state.getQueryStore().getUpdate6AddPost(operation, con);
//		}
//	}
//
//	public static class Update7AddComment extends JdbcPreparedMultipleUpdateOperationHandler<LdbcUpdate7AddComment, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public List<PreparedStatement> getStatements(Connection con, PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate7AddComment operation) throws SQLException {
//			return state.getQueryStore().getUpdate7AddComment(operation, con);
//		}
//
//	}
//
//	public static class Update8AddFriendship extends JdbcPreparedUpdateOperationHandler<LdbcUpdate8AddFriendship, LdbcNoResult, PostgresInteractiveQueryStore> {
//
//		@Override
//		public String getQueryString(PostgresDbConnectionState<PostgresInteractiveQueryStore> state, LdbcUpdate8AddFriendship operation) {
//			return state.getQueryStore().getUpdate8AddFriendship(operation);
//		}
//
//	}
}
