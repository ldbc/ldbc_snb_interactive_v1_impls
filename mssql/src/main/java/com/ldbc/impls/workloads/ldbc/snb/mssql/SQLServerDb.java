package com.ldbc.impls.workloads.ldbc.snb.mssql;

import com.google.common.collect.ImmutableList;
import com.ldbc.driver.DbException;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.mssql.converter.SQLServerConverter;
import com.ldbc.impls.workloads.ldbc.snb.mssql.operationhandlers.SQLServerListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.mssql.operationhandlers.SQLServerSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.mssql.operationhandlers.SQLServerUpdateOperationHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery1);
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
                    convertLists(SQLServerConverter.arrayToObjectArray(result, 12)),
                    convertLists(SQLServerConverter.arrayToObjectArray(result, 13)));
            return qr;
        }

        @SuppressWarnings("unchecked")
        public Iterable<List<Object>> convertLists(Iterable<List<Object>> arr) {
            for (List<Object> entry : arr) {
                entry.set(1, entry.get(1));
            }
            return arr;
        }
    }

    public static class Query2 extends SQLServerListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery2);
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

    public static class Query3 extends SQLServerListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery3);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery4);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery5);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery6);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery7);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery8);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery9);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery10);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery11);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery12);
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

    public static class Query13 extends SQLServerSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery13);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }

    }

    public static class Query14 extends SQLServerListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveComplexQuery14);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    SQLServerConverter.convertLists(SQLServerConverter.arrayToObjectArray(result, 1)),
                    result.getDouble(2));
        }

    }

    public static class ShortQuery1PersonProfile extends SQLServerSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery1);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
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

    }

    public static class ShortQuery2PersonPosts extends SQLServerListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery2);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery3);
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
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery4);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery4MessageContentResult(
                    result.getString(1),
                    SQLServerConverter.stringTimestampToEpoch(result, 2));
        }

    }

    public static class ShortQuery5MessageCreator extends SQLServerSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery5);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery5MessageCreatorResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3));
        }

    }

    public static class ShortQuery6MessageForum extends SQLServerSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery6);
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

    public static class ShortQuery7MessageReplies extends SQLServerListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(SQLServerDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveShortQuery7);
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

    public static class Update1AddPerson extends SQLServerUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public void executeOperation(LdbcUpdate1AddPerson operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                // InteractiveUpdate1AddPerson
                String queryStringAddPerson = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate1AddPerson);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPerson);
                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPerson, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPerson);
                stmt1.executeUpdate();

                // InteractiveUpdate1AddPersonCompanies
                String queryStringAddPersonCompanies = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate1AddPersonCompanies);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonCompanies, ImmutableList.of("organizationId", "worksFromYear"));
                final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddPersonCompanies, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonCompanies);
                stmt2.setLong(1, operation.personId());
                for (LdbcUpdate1AddPerson.Organization o : operation.workAt()) {
                    stmt2.setLong(2, o.organizationId());
                    stmt2.setInt(3, o.year());
                    stmt2.executeUpdate();
                }

                // InteractiveUpdate1AddPersonEmails
                String queryStringAddPersonEmails = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate1AddPersonEmails);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonEmails, ImmutableList.of("email"));
                final PreparedStatement stmt3 = prepareSnbStatement(queryStringAddPersonEmails, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonEmails);
                stmt3.setLong(1, operation.personId());
                for (String email : operation.emails()) {
                    stmt3.setString(2, email);
                    stmt3.executeUpdate();
                }

                // InteractiveUpdate1AddPersonLanguages
                String queryStringAddPersonLanguages = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate1AddPersonLanguages);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonLanguages,
                        ImmutableList.of("language"));
                final PreparedStatement stmt4 = prepareSnbStatement(queryStringAddPersonLanguages, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonLanguages);
                stmt4.setLong(1, operation.personId());
                for (String language : operation.languages()) {
                    stmt4.setString(2, language);
                    stmt4.executeUpdate();
                }

                // InteractiveUpdate1AddPersonTags
                String queryStringAddPersonTags = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate1AddPersonTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonTags, ImmutableList.of("tagId"));
                final PreparedStatement stmt5 = prepareSnbStatement(queryStringAddPersonTags, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonTags);
                stmt5.setLong(1, operation.personId());
                for (long tagId : operation.tagIds()) {
                    stmt5.setLong(2, tagId);
                    stmt5.executeUpdate();
                }

                // InteractiveUpdate1AddPersonUniversities
                String queryStringAddPersonUniversities = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate1AddPersonUniversities);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonUniversities, ImmutableList.of("organizationId", "studiesFromYear"));
                final PreparedStatement stmt6 = prepareSnbStatement(queryStringAddPersonUniversities, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonUniversities);
                stmt6.setLong(1, operation.personId());
                for (LdbcUpdate1AddPerson.Organization o : operation.studyAt()) {
                    stmt6.setLong(2, o.organizationId());
                    stmt6.setInt(3, o.year());
                    stmt6.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update2AddPostLike extends SQLServerUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public void executeOperation(LdbcUpdate2AddPostLike operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                String queryString = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate2);
                replaceParameterNamesWithQuestionMarks(operation, queryString);
                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update3AddCommentLike extends SQLServerUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public void executeOperation(LdbcUpdate3AddCommentLike operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                String queryString = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate3);
                replaceParameterNamesWithQuestionMarks(operation, queryString);

                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update4AddForum extends SQLServerUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public void executeOperation(LdbcUpdate4AddForum operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                // InteractiveUpdate4AddForum
                String queryStringAddForum = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate4AddForum);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddForum);

                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddForum, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddForum);
                stmt1.executeUpdate();

                // InteractiveUpdate4AddForumTags
                String queryStringAddForumTags = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate4AddForumTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddForumTags, ImmutableList.of("tagId"));
                final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddForumTags, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddForumTags);
                stmt2.setLong(1, operation.forumId());
                for (long tagId: operation.tagIds()) {
                    stmt2.setLong(2, tagId);
                    stmt2.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update5AddForumMembership extends SQLServerUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public void executeOperation(LdbcUpdate5AddForumMembership operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                String queryString = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate5);
                replaceParameterNamesWithQuestionMarks(operation, queryString);

                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update6AddPost extends SQLServerUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public void executeOperation(LdbcUpdate6AddPost operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                // InteractiveUpdate6AddPost
                String queryStringAddPost = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate6AddPost);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPost);

                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPost, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPost);
                stmt1.executeUpdate();

                // InteractiveUpdate6AddPostTags
                String queryStringAddPostTags = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate6AddPostTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPostTags, ImmutableList.of("tagId"));
                final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddPostTags, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPostTags);
                stmt2.setLong(1, operation.postId());
                for (long tagId: operation.tagIds()) {
                    stmt2.setLong(2, tagId);
                    stmt2.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update7AddComment extends SQLServerUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public void executeOperation(LdbcUpdate7AddComment operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                // InteractiveUpdate7AddComment
                String queryStringAddComment = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate7AddComment);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddComment);
                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddComment, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddComment);
                stmt1.executeUpdate();

                // InteractiveUpdate7AddCommentTags
                String queryStringAddCommentTags = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate7AddCommentTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddCommentTags, ImmutableList.of("tagId"));
                final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddCommentTags, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddCommentTags);
                stmt2.setLong(1, operation.commentId());
                for (long tagId: operation.tagIds()) {
                    stmt2.setLong(2, tagId);
                    stmt2.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update8AddFriendship extends SQLServerUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public void executeOperation(LdbcUpdate8AddFriendship operation, SQLServerDbConnectionState state, ResultReporter resultReporter) throws DbException {
            Connection conn = state.getConnection();

            try {
                String queryString = state.getQueryStore().getParameterizedQuery(QueryStore.QueryType.InteractiveUpdate8);
                replaceParameterNamesWithQuestionMarks(operation, queryString);
                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

}
