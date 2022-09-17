package org.ldbcouncil.snb.impls.workloads.mssql;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;
import org.ldbcouncil.snb.impls.workloads.mssql.converter.SQLServerConverter;
import org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers.SQLServerListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers.SQLServerMultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers.SQLServerSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.mssql.operationhandlers.SQLServerUpdateOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class SQLServerDb extends BaseDb<SQLServerQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new SQLServerDbConnectionState(properties, new SQLServerQueryStore(properties.get("queryDir")));
        } catch (ClassNotFoundException e) {
            throw new DbException(e);
        }
    }

    // Interactive complex reads

    public static class Query1 extends SQLServerListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
            LdbcQuery1Result qr = new LdbcQuery1Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getInt(3),
                    SQLServerConverter.stringTimestampToEpoch(result, 4),
                    SQLServerConverter.stringTimestampToEpoch(result, 5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    SQLServerConverter.arrayToStringArray(result, 9),
                    SQLServerConverter.arrayToStringArray(result, 10),
                    result.getString(11),
                    SQLServerConverter.arrayToOrganizationArray(result, 12),
                    SQLServerConverter.arrayToOrganizationArray(result, 13));
            return qr;
        }
    }

    public static class Query2 extends SQLServerListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery2 operation) {
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
                    SQLServerConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query3a extends SQLServerListOperationHandler<LdbcQuery3a, LdbcQuery3Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery3a operation) {
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

    public static class Query3b extends SQLServerListOperationHandler<LdbcQuery3b, LdbcQuery3Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery3b operation) {
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

    public static class Query4 extends SQLServerListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery4Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query5 extends SQLServerListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery5Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query6 extends SQLServerListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery6Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query7 extends SQLServerListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery7Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    SQLServerConverter.stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getBoolean(8));
        }

    }

    public static class Query8 extends SQLServerListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery8Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    SQLServerConverter.stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6));
        }

    }

    public static class Query9 extends SQLServerListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery9 operation) {
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
                    SQLServerConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query10 extends SQLServerListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery10 operation) {
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

    public static class Query11 extends SQLServerListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery11 operation) {
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

    public static class Query12 extends SQLServerListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcQuery12Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery12Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    SQLServerConverter.arrayToStringArray(result, 4),
                    result.getInt(5));
        }

    }

    public static class Query13a extends SQLServerSingletonOperationHandler<LdbcQuery13a, LdbcQuery13Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery13a operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            if (result.next())
            {
                return new LdbcQuery13Result(result.getInt(1));
            }
            else
            {
                return new LdbcQuery13Result(-1);
            }
        }
    }

    public static class Query13b extends SQLServerSingletonOperationHandler<LdbcQuery13b, LdbcQuery13Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery13b operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            if (result.next())
            {
                return new LdbcQuery13Result(result.getInt(1));
            }
            else
            {
                return new LdbcQuery13Result(-1);
            }
        }
    }

    public static class Query14a extends SQLServerListOperationHandler<LdbcQuery14a, LdbcQuery14Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery14a operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    SQLServerConverter.arrayToLongArray(result, 1),
                    result.getLong(2));
        }
    }

    public static class Query14b extends SQLServerListOperationHandler<LdbcQuery14b, LdbcQuery14Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery14b operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    SQLServerConverter.arrayToLongArray(result, 1),
                    result.getLong(2));
        }
    }

    public static class ShortQuery1PersonProfile extends SQLServerSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
            if (result.next())
            {
                return new LdbcShortQuery1PersonProfileResult(
                    result.getString(1),
                    result.getString(2),
                    SQLServerConverter.stringTimestampToEpoch(result, 3),
                    result.getString(4),
                    result.getString(5),
                    result.getLong(6),
                    result.getString(7),
                    SQLServerConverter.stringTimestampToEpoch(result, 8));
            }
            else
            {
                return null;
            }
        }
    }

    public static class ShortQuery2PersonPosts extends SQLServerListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery2PersonPostsResult(
                    result.getLong(1),
                    result.getString(2),
                    SQLServerConverter.stringTimestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getLong(5),
                    result.getString(6),
                    result.getString(7));
        }

    }

    public static class ShortQuery3PersonFriends extends SQLServerListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery3PersonFriendsResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    SQLServerConverter.stringTimestampToEpoch(result, 4));
        }

    }

    public static class ShortQuery4MessageContent extends SQLServerSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
            if(result.next())
            {
                return new LdbcShortQuery4MessageContentResult(
                    result.getString(1),
                    SQLServerConverter.stringTimestampToEpoch(result, 2));
            }
            else
            {
                return null;
            }

        }

    }

    public static class ShortQuery5MessageCreator extends SQLServerSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreator(operation);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(ResultSet result) throws SQLException {
            if (result.next())
            {
                return new LdbcShortQuery5MessageCreatorResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3));
            }
            else
            {
                return null;
            }
        }

    }

    public static class ShortQuery6MessageForum extends SQLServerSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForum(operation);
        }

        @Override
        public LdbcShortQuery6MessageForumResult convertSingleResult(ResultSet result) throws SQLException {
            if (result.next())
            {
                return new LdbcShortQuery6MessageForumResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getLong(3),
                    result.getString(4),
                    result.getString(5));
            }
            else
            {
                return null;
            }
        }

    }

    public static class ShortQuery7MessageReplies extends SQLServerListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery7MessageRepliesResult(
                    result.getLong(1),
                    result.getString(2),
                    SQLServerConverter.stringTimestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getString(5),
                    result.getString(6),
                    result.getBoolean(7));
        }

    }
    public static class Insert1AddPerson extends SQLServerMultipleUpdateOperationHandler<LdbcInsert1AddPerson> {

        @Override
        public List<String> getQueryString(SQLServerDbConnectionState state, LdbcInsert1AddPerson operation) {
            return state.getQueryStore().getInsert1Multiple(operation);
        }

    }

    public static class Insert2AddPostLike extends SQLServerUpdateOperationHandler<LdbcInsert2AddPostLike> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcInsert2AddPostLike operation) {
            return state.getQueryStore().getInsert2(operation);
        }

    }

    public static class Insert3AddCommentLike extends SQLServerUpdateOperationHandler<LdbcInsert3AddCommentLike> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcInsert3AddCommentLike operation) {
            return state.getQueryStore().getInsert3(operation);
        }
    }

    public static class Insert4AddForum extends SQLServerMultipleUpdateOperationHandler<LdbcInsert4AddForum> {

        @Override
        public List<String> getQueryString(SQLServerDbConnectionState state, LdbcInsert4AddForum operation) {
            return state.getQueryStore().getInsert4Multiple(operation);
        }
    }

    public static class Insert5AddForumMembership extends SQLServerUpdateOperationHandler<LdbcInsert5AddForumMembership> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcInsert5AddForumMembership operation) {
            return state.getQueryStore().getInsert5(operation);
        }
    }

    public static class Insert6AddPost extends SQLServerMultipleUpdateOperationHandler<LdbcInsert6AddPost> {

        @Override
        public List<String> getQueryString(SQLServerDbConnectionState state, LdbcInsert6AddPost operation) {
            return state.getQueryStore().getInsert6Multiple(operation);
        }
    }

    public static class Insert7AddComment extends SQLServerMultipleUpdateOperationHandler<LdbcInsert7AddComment> {

        @Override
        public List<String> getQueryString(SQLServerDbConnectionState state, LdbcInsert7AddComment operation) {
            return state.getQueryStore().getInsert7Multiple(operation);
        }

    }

    public static class Insert8AddFriendship extends SQLServerUpdateOperationHandler<LdbcInsert8AddFriendship> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcInsert8AddFriendship operation) {
            return state.getQueryStore().getInsert8(operation);
        }
    }

    // Deletions
    public static class Delete1RemovePerson extends SQLServerUpdateOperationHandler<LdbcDelete1RemovePerson> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete1RemovePerson operation) {
            return state.getQueryStore().getDelete1(operation);
        }
    }

    public static class Delete2RemovePostLike extends SQLServerUpdateOperationHandler<LdbcDelete2RemovePostLike> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete2RemovePostLike operation) {
            return state.getQueryStore().getDelete2(operation);
        }
    }

    public static class Delete3RemoveCommentLike extends SQLServerUpdateOperationHandler<LdbcDelete3RemoveCommentLike> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete3RemoveCommentLike operation) {
            return state.getQueryStore().getDelete3(operation);
        }
    }

    public static class Delete4RemoveForum extends SQLServerUpdateOperationHandler<LdbcDelete4RemoveForum> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete4RemoveForum operation) {
            return state.getQueryStore().getDelete4(operation);
        }
    }

    public static class Delete5RemoveForumMembership extends SQLServerUpdateOperationHandler<LdbcDelete5RemoveForumMembership> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete5RemoveForumMembership operation) {
            return state.getQueryStore().getDelete5(operation);
        }
    }

    public static class Delete6RemovePostThread extends SQLServerUpdateOperationHandler<LdbcDelete6RemovePostThread> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete6RemovePostThread operation) {
            return state.getQueryStore().getDelete6(operation);
        }
    }

    public static class Delete7RemoveCommentSubthread extends SQLServerUpdateOperationHandler<LdbcDelete7RemoveCommentSubthread> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete7RemoveCommentSubthread operation) {
            return state.getQueryStore().getDelete7(operation);
        }
    }

    public static class Delete8RemoveFriendship extends SQLServerUpdateOperationHandler<LdbcDelete8RemoveFriendship> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcDelete8RemoveFriendship operation) {
            return state.getQueryStore().getDelete8(operation);
        }
    }
}
