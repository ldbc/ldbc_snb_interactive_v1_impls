package org.ldbcouncil.snb.impls.workloads.age;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.*;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;
import org.ldbcouncil.snb.impls.workloads.age.operationhandlers.AgeListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.age.operationhandlers.AgeSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.age.operationhandlers.AgeUpdateOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AgeDb extends BaseDb<AgeQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        String graphName = properties.getOrDefault("age_graph_name", "ldbc_snb");
        String queryDir = properties.get("queryDir");
        dcs = new AgeDbConnectionState(properties, new AgeQueryStore(queryDir, graphName));
    }

    // -------------------------------------------------------------------------
    // IC handlers
    // -------------------------------------------------------------------------

    public static class InteractiveQuery1
            extends AgeListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        protected LdbcQuery1Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery1Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    (int) AgeConverter.toLong(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toLong(row.getObject(5)),
                    AgeConverter.toStr(row.getObject(6)),
                    AgeConverter.toStr(row.getObject(7)),
                    AgeConverter.toStr(row.getObject(8)),
                    AgeConverter.toStringList(row.getObject(9)),
                    AgeConverter.toStringList(row.getObject(10)),
                    AgeConverter.toStr(row.getObject(11)),
                    AgeConverter.asOrganizationList(row.getObject(12)),
                    AgeConverter.asOrganizationList(row.getObject(13))
            );
        }
    }

    public static class InteractiveQuery2
            extends AgeListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        protected LdbcQuery2Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery2Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toStr(row.getObject(5)),
                    AgeConverter.toLong(row.getObject(6))
            );
        }
    }

    public static class InteractiveQuery3
            extends AgeListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        protected LdbcQuery3Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery3Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    (int) AgeConverter.toLong(row.getObject(4)),
                    (int) AgeConverter.toLong(row.getObject(5)),
                    (int) AgeConverter.toLong(row.getObject(6))
            );
        }
    }

    public static class InteractiveQuery4
            extends AgeListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        protected LdbcQuery4Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery4Result(
                    AgeConverter.toStr(row.getObject(1)),
                    (int) AgeConverter.toLong(row.getObject(2))
            );
        }
    }

    public static class InteractiveQuery5
            extends AgeListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        protected LdbcQuery5Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery5Result(
                    AgeConverter.toStr(row.getObject(1)),
                    (int) AgeConverter.toLong(row.getObject(2))
            );
        }
    }

    public static class InteractiveQuery6
            extends AgeListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        protected LdbcQuery6Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery6Result(
                    AgeConverter.toStr(row.getObject(1)),
                    (int) AgeConverter.toLong(row.getObject(2))
            );
        }
    }

    public static class InteractiveQuery7
            extends AgeListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        protected LdbcQuery7Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery7Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toLong(row.getObject(5)),
                    AgeConverter.toStr(row.getObject(6)),
                    (int) AgeConverter.toLong(row.getObject(7)),
                    AgeConverter.toBoolean(row.getObject(8))
            );
        }
    }

    public static class InteractiveQuery8
            extends AgeListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        protected LdbcQuery8Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery8Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toLong(row.getObject(5)),
                    AgeConverter.toStr(row.getObject(6))
            );
        }
    }

    public static class InteractiveQuery9
            extends AgeListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        protected LdbcQuery9Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery9Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toStr(row.getObject(5)),
                    AgeConverter.toLong(row.getObject(6))
            );
        }
    }

    public static class InteractiveQuery10
            extends AgeListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        protected LdbcQuery10Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery10Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    (int) AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toStr(row.getObject(5)),
                    AgeConverter.toStr(row.getObject(6))
            );
        }
    }

    public static class InteractiveQuery11
            extends AgeListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        protected LdbcQuery11Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery11Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toStr(row.getObject(4)),
                    (int) AgeConverter.toLong(row.getObject(5))
            );
        }
    }

    public static class InteractiveQuery12
            extends AgeListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        protected LdbcQuery12Result toResult(ResultSet row) throws SQLException {
            return new LdbcQuery12Result(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toStringList(row.getObject(4)),
                    (int) AgeConverter.toLong(row.getObject(5))
            );
        }
    }

    // -------------------------------------------------------------------------
    // IS handlers
    // -------------------------------------------------------------------------

    public static class ShortQuery1PersonProfile
            extends AgeSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
        }

        @Override
        protected LdbcShortQuery1PersonProfileResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery1PersonProfileResult(
                    AgeConverter.toStr(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toLong(row.getObject(3)),
                    AgeConverter.toStr(row.getObject(4)),
                    AgeConverter.toStr(row.getObject(5)),
                    AgeConverter.toLong(row.getObject(6)),
                    AgeConverter.toStr(row.getObject(7)),
                    AgeConverter.toLong(row.getObject(8))
            );
        }
    }

    public static class ShortQuery2PersonPosts
            extends AgeListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
        }

        @Override
        protected LdbcShortQuery2PersonPostsResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery2PersonPostsResult(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toLong(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toLong(row.getObject(5)),
                    AgeConverter.toStr(row.getObject(6)),
                    AgeConverter.toStr(row.getObject(7))
            );
        }
    }

    public static class ShortQuery3PersonFriends
            extends AgeListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
        }

        @Override
        protected LdbcShortQuery3PersonFriendsResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery3PersonFriendsResult(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4))
            );
        }
    }

    public static class ShortQuery4MessageContent
            extends AgeSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation);
        }

        @Override
        protected LdbcShortQuery4MessageContentResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery4MessageContentResult(
                    AgeConverter.toStr(row.getObject(1)),
                    AgeConverter.toLong(row.getObject(2))
            );
        }
    }

    public static class ShortQuery5MessageCreator
            extends AgeSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreator(operation);
        }

        @Override
        protected LdbcShortQuery5MessageCreatorResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery5MessageCreatorResult(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toStr(row.getObject(3))
            );
        }
    }

    public static class ShortQuery6MessageForum
            extends AgeSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForum(operation);
        }

        @Override
        protected LdbcShortQuery6MessageForumResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery6MessageForumResult(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toLong(row.getObject(3)),
                    AgeConverter.toStr(row.getObject(4)),
                    AgeConverter.toStr(row.getObject(5))
            );
        }
    }

    public static class ShortQuery7MessageReplies
            extends AgeListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
        }

        @Override
        protected LdbcShortQuery7MessageRepliesResult toResult(ResultSet row) throws SQLException {
            return new LdbcShortQuery7MessageRepliesResult(
                    AgeConverter.toLong(row.getObject(1)),
                    AgeConverter.toStr(row.getObject(2)),
                    AgeConverter.toLong(row.getObject(3)),
                    AgeConverter.toLong(row.getObject(4)),
                    AgeConverter.toStr(row.getObject(5)),
                    AgeConverter.toStr(row.getObject(6)),
                    AgeConverter.toBoolean(row.getObject(7))
            );
        }
    }

    // -------------------------------------------------------------------------
    // IU handlers
    // -------------------------------------------------------------------------

    public static class Update1AddPerson extends AgeUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Single(operation);
        }
    }

    public static class Update2AddPostLike extends AgeUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }
    }

    public static class Update3AddCommentLike extends AgeUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }
    }

    public static class Update4AddForum extends AgeUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Single(operation);
        }
    }

    public static class Update5AddForumMembership extends AgeUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }
    }

    public static class Update6AddPost extends AgeUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Single(operation);
        }
    }

    public static class Update7AddComment extends AgeUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Single(operation);
        }
    }

    public static class Update8AddFriendship extends AgeUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(AgeDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }
    }
}
