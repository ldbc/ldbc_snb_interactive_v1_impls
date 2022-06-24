package org.ldbcouncil.snb.impls.workloads.umbra;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;
import org.ldbcouncil.snb.impls.workloads.umbra.converter.UmbraConverter;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraMultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraUpdateOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class UmbraDb extends BaseDb<UmbraQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new UmbraDbConnectionState<>(properties, new UmbraQueryStore(properties.get("queryDir")));
        } catch (ClassNotFoundException e) {
            throw new DbException(e);
        }
    }

    // Interactive complex reads

    public static class Query1 extends UmbraListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
            LdbcQuery1Result qr = new LdbcQuery1Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getInt(3),
                    UmbraConverter.dateToEpoch(result, 4),
                    UmbraConverter.timestampToEpoch(result, 5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    UmbraConverter.arrayToStringArray(result, 9),
                    UmbraConverter.arrayToStringArray(result, 10),
                    result.getString(11),
                    UmbraConverter.arrayToObjectArray(result, 12),
                    UmbraConverter.arrayToObjectArray(result, 13));
            return qr;
        }

    }

    public static class Query2 extends UmbraListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery2 operation) {
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
                    UmbraConverter.timestampToEpoch(result, 6));
        }

    }

    public static class Query3 extends UmbraListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery3 operation) {
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

    public static class Query4 extends UmbraListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery4Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query5 extends UmbraListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery5Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query6 extends UmbraListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery6Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query7 extends UmbraListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery7Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.timestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getBoolean(8));
        }

    }

    public static class Query8 extends UmbraListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery8Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.timestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6));
        }

    }

    public static class Query9 extends UmbraListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery9 operation) {
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
                    UmbraConverter.timestampToEpoch(result, 6));
        }

    }

    public static class Query10 extends UmbraListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery10 operation) {
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

    public static class Query11 extends UmbraListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery11 operation) {
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

    public static class Query12 extends UmbraListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcQuery12Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery12Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.arrayToStringArray(result, 4),
                    result.getInt(5));
        }

    }

    public static class Query13 extends UmbraSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }

    }

    public static class Query14 extends UmbraListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    UmbraConverter.pathToList(result, 1),
                    result.getDouble(2));
        }

    }

    public static class ShortQuery1PersonProfile extends UmbraSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery1PersonProfileResult(
                    result.getString(1),
                    result.getString(2),
                    UmbraConverter.dateToEpoch(result, 3),
                    result.getString(4),
                    result.getString(5),
                    result.getLong(6),
                    result.getString(7),
                    UmbraConverter.timestampToEpoch(result, 8));
        }

    }

    public static class ShortQuery2PersonPosts extends UmbraListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery2PersonPostsResult(
                    result.getLong(1),
                    result.getString(2),
                    UmbraConverter.timestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getLong(5),
                    result.getString(6),
                    result.getString(7));
        }

    }

    public static class ShortQuery3PersonFriends extends UmbraListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery3PersonFriendsResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.timestampToEpoch(result, 4));
        }

    }

    public static class ShortQuery4MessageContent extends UmbraSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery4MessageContentResult(
                    result.getString(1),
                    UmbraConverter.timestampToEpoch(result, 2));
        }

    }

    public static class ShortQuery5MessageCreator extends UmbraSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
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

    public static class ShortQuery6MessageForum extends UmbraSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery6MessageForum operation) {
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

    public static class ShortQuery7MessageReplies extends UmbraListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery7MessageRepliesResult(
                    result.getLong(1),
                    result.getString(2),
                    UmbraConverter.timestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getString(5),
                    result.getString(6),
                    result.getBoolean(7));
        }

    }

    public static class Update1AddPerson extends UmbraMultipleUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public List<String> getQueryString(UmbraDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Multiple(operation);
        }

    }

    public static class Update2AddPostLike extends UmbraUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }

    }

    public static class Update3AddCommentLike extends UmbraUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }
    }

    public static class Update4AddForum extends UmbraMultipleUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public List<String> getQueryString(UmbraDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Multiple(operation);
        }
    }

    public static class Update5AddForumMembership extends UmbraUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }
    }

    public static class Update6AddPost extends UmbraMultipleUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public List<String> getQueryString(UmbraDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Multiple(operation);
        }
    }

    public static class Update7AddComment extends UmbraMultipleUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public List<String> getQueryString(UmbraDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Multiple(operation);
        }

    }

    public static class Update8AddFriendship extends UmbraUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }

    }

    // Deletions
    public static class Delete1RemovePerson extends UmbraUpdateOperationHandler<LdbcDelete1RemovePerson> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete1RemovePerson operation) {
            return state.getQueryStore().getDelete1(operation);
        }
    }

    public static class Delete2RemovePostLike extends UmbraUpdateOperationHandler<LdbcDelete2RemovePostLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete2RemovePostLike operation) {
            return state.getQueryStore().getDelete2(operation);
        }
    }

    public static class Delete3RemoveCommentLike extends UmbraUpdateOperationHandler<LdbcDelete3RemoveCommentLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete3RemoveCommentLike operation) {
            return state.getQueryStore().getDelete3(operation);
        }
    }

    public static class Delete4RemoveForum extends UmbraUpdateOperationHandler<LdbcDelete4RemoveForum> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete4RemoveForum operation) {
            return state.getQueryStore().getDelete4(operation);
        }
    }

    public static class Delete5RemoveForumMembership extends UmbraUpdateOperationHandler<LdbcDelete5RemoveForumMembership> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete5RemoveForumMembership operation) {
            return state.getQueryStore().getDelete5(operation);
        }
    }

    public static class Delete6RemovePostThread extends UmbraUpdateOperationHandler<LdbcDelete6RemovePostThread> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete6RemovePostThread operation) {
            return state.getQueryStore().getDelete6(operation);
        }
    }

    public static class Delete7RemoveCommentSubthread extends UmbraUpdateOperationHandler<LdbcDelete7RemoveCommentSubthread> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete7RemoveCommentSubthread operation) {
            return state.getQueryStore().getDelete7(operation);
        }
    }

    public static class Delete8RemoveFriendship extends UmbraUpdateOperationHandler<LdbcDelete8RemoveFriendship> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete8RemoveFriendship operation) {
            return state.getQueryStore().getDelete8(operation);
        }
    }

}