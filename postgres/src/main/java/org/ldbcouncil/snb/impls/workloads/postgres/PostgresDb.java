package org.ldbcouncil.snb.impls.workloads.postgres;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.*;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;
import org.ldbcouncil.snb.impls.workloads.postgres.converter.PostgresConverter;
import org.ldbcouncil.snb.impls.workloads.postgres.operationhandlers.PostgresListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.postgres.operationhandlers.PostgresSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.postgres.operationhandlers.PostgresUpdateOperationHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.ImmutableList;

public abstract class PostgresDb extends BaseDb<PostgresQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new PostgresDbConnectionState(properties, new PostgresQueryStore(properties.get("queryDir")));
        } catch (ClassNotFoundException e) {
            throw new DbException(e);
        }
    }

    // Interactive complex reads

    public static class Query1 extends PostgresListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery1);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
            LdbcQuery1Result qr = new LdbcQuery1Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getInt(3),
                    PostgresConverter.stringTimestampToEpoch(result, 4),
                    PostgresConverter.stringTimestampToEpoch(result, 5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    PostgresConverter.arrayToStringArray(result, 9),
                    PostgresConverter.arrayToStringArray(result, 10),
                    result.getString(11),
                    PostgresConverter.arrayToOrganizationArray(result, 12),
                    PostgresConverter.arrayToOrganizationArray(result, 13));
            return qr;
        }
    }

    public static class Query2 extends PostgresListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery2);
        }

        @Override
        public LdbcQuery2Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery2Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    result.getLong(4),
                    result.getString(5),
                    PostgresConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query3 extends PostgresListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery3);
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

    public static class Query4 extends PostgresListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery4);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery4Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query5 extends PostgresListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery5);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery5Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query6 extends PostgresListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery6);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery6Result(
                    result.getString(1),
                    result.getInt(2));
        }

    }

    public static class Query7 extends PostgresListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery7);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery7Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    PostgresConverter.stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getBoolean(8));
        }

    }

    public static class Query8 extends PostgresListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery8);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery8Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    PostgresConverter.stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6));
        }

    }

    public static class Query9 extends PostgresListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery9);
        }

        @Override
        public LdbcQuery9Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery9Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    result.getLong(4),
                    result.getString(5),
                    PostgresConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query10 extends PostgresListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery10);
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

    public static class Query11 extends PostgresListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery11);
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

    public static class Query12 extends PostgresListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery12);
        }

        @Override
        public LdbcQuery12Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery12Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    PostgresConverter.arrayToStringArray(result, 4),
                    result.getInt(5));
        }

    }

    public static class Query13 extends PostgresSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery13);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }

    }

    public static class Query14 extends PostgresListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery14);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    PostgresConverter.convertLists(PostgresConverter.arrayToObjectArray(result, 1)),
                    result.getDouble(2));
        }

    }

    public static class ShortQuery1PersonProfile extends PostgresSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery1);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery1PersonProfileResult(
                    result.getString(1),
                    result.getString(2),
                    PostgresConverter.stringTimestampToEpoch(result, 3),
                    result.getString(4),
                    result.getString(5),
                    result.getLong(6),
                    result.getString(7),
                    PostgresConverter.stringTimestampToEpoch(result, 8));
        }

    }

    public static class ShortQuery2PersonPosts extends PostgresListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery2);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery2PersonPostsResult(
                    result.getLong(1),
                    result.getString(2),
                    PostgresConverter.stringTimestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getLong(5),
                    result.getString(6),
                    result.getString(7));
        }

    }

    public static class ShortQuery3PersonFriends extends PostgresListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery3);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery3PersonFriendsResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    PostgresConverter.stringTimestampToEpoch(result, 4));
        }

    }

    public static class ShortQuery4MessageContent extends PostgresSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery4);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery4MessageContentResult(
                    result.getString(1),
                    PostgresConverter.stringTimestampToEpoch(result, 2));
        }

    }

    public static class ShortQuery5MessageCreator extends PostgresSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery5);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery5MessageCreatorResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3));
        }

    }

    public static class ShortQuery6MessageForum extends PostgresSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery6);
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

    public static class ShortQuery7MessageReplies extends PostgresListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery7);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery7MessageRepliesResult(
                    result.getLong(1),
                    result.getString(2),
                    PostgresConverter.stringTimestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getString(5),
                    result.getString(6),
                    result.getBoolean(7));
        }

    }

    public static class Update1AddPerson extends PostgresUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public void executeOperation(LdbcUpdate1AddPerson operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();

            try {
                // InteractiveUpdate1AddPerson
                String queryStringAddPerson = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate1AddPerson);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPerson);
                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPerson, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPerson);
                stmt1.executeUpdate();

                // InteractiveUpdate1AddPersonCompanies
                String queryStringAddPersonCompanies = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate1AddPersonCompanies);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonCompanies, ImmutableList.of("organizationId", "worksFromYear"));
                final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddPersonCompanies, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonCompanies);
                stmt2.setLong(1, operation.getPersonId());
                for (LdbcUpdate1AddPerson.Organization o : operation.getWorkAt()) {
                    stmt2.setLong(2, o.getOrganizationId());
                    stmt2.setInt(3, o.getYear());
                    stmt2.executeUpdate();
                }

                // InteractiveUpdate1AddPersonEmails
                String queryStringAddPersonEmails = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate1AddPersonEmails);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonEmails, ImmutableList.of("email"));
                final PreparedStatement stmt3 = prepareSnbStatement(queryStringAddPersonEmails, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonEmails);
                stmt3.setLong(1, operation.getPersonId());
                for (String email : operation.getEmails()) {
                    stmt3.setString(2, email);
                    stmt3.executeUpdate();
                }

                // InteractiveUpdate1AddPersonLanguages
                String queryStringAddPersonLanguages = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate1AddPersonLanguages);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonLanguages,
                        ImmutableList.of("language"));
                final PreparedStatement stmt4 = prepareSnbStatement(queryStringAddPersonLanguages, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonLanguages);
                stmt4.setLong(1, operation.getPersonId());
                for (String language : operation.getLanguages()) {
                    stmt4.setString(2, language);
                    stmt4.executeUpdate();
                }

                // InteractiveUpdate1AddPersonTags
                String queryStringAddPersonTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate1AddPersonTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonTags, ImmutableList.of("tagId"));
                final PreparedStatement stmt5 = prepareSnbStatement(queryStringAddPersonTags, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonTags);
                stmt5.setLong(1, operation.getPersonId());
                for (long tagId : operation.getTagIds()) {
                    stmt5.setLong(2, tagId);
                    stmt5.executeUpdate();
                }

                // InteractiveUpdate1AddPersonUniversities
                String queryStringAddPersonUniversities = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate1AddPersonUniversities);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonUniversities, ImmutableList.of("organizationId", "studiesFromYear"));
                final PreparedStatement stmt6 = prepareSnbStatement(queryStringAddPersonUniversities, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonUniversities);
                stmt6.setLong(1, operation.getPersonId());
                for (LdbcUpdate1AddPerson.Organization o : operation.getStudyAt()) {
                    stmt6.setLong(2, o.getOrganizationId());
                    stmt6.setInt(3, o.getYear());
                    stmt6.executeUpdate();
                }
        } catch (Exception e) {
                throw new DbException(e);
        }
        finally{
            conn.close();
        }
    } catch (Exception e) {
        throw new DbException(e);
}
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update2AddPostLike extends PostgresUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public void executeOperation(LdbcUpdate2AddPostLike operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();

            try {
                String queryString = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate2);
                replaceParameterNamesWithQuestionMarks(operation, queryString);
                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
        }
        finally{
            conn.close();
        }
    } catch (Exception e) {
        throw new DbException(e);
}
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update3AddCommentLike extends PostgresUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public void executeOperation(LdbcUpdate3AddCommentLike operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {

            try {
            Connection conn = state.getConnection();
            try {
                String queryString = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate3);
                replaceParameterNamesWithQuestionMarks(operation, queryString);

                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                stmt.executeUpdate();
        } catch (Exception e) {
                throw new DbException(e);
            }
            finally{
                conn.close();
            }
        } catch (Exception e) {
            throw new DbException(e);
    }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update4AddForum extends PostgresUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public void executeOperation(LdbcUpdate4AddForum operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();
                // InteractiveUpdate4AddForum
                String queryStringAddForum = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate4AddForum);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddForum);

                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddForum, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddForum);
            try {

                stmt1.executeUpdate();

            } catch (Exception e) {
                throw new DbException(e);
            }
            finally{
                stmt1.close();
            }

            // InteractiveUpdate4AddForumTags
            String queryStringAddForumTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate4AddForumTags);
            replaceParameterNamesWithQuestionMarks(operation, queryStringAddForumTags, ImmutableList.of("tagId"));
            final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddForumTags, conn);
            state.logQuery(operation.getClass().getSimpleName(), queryStringAddForumTags);
            stmt2.setLong(1, operation.getForumId());

            try {


                for (long tagId: operation.getTagIds()) {
                    stmt2.setLong(2, tagId);
                    stmt2.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            finally{
                stmt2.close();
                conn.close();
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update5AddForumMembership extends PostgresUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public void executeOperation(LdbcUpdate5AddForumMembership operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();
                String queryString = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate5);
                replaceParameterNamesWithQuestionMarks(operation, queryString);

                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
            try {

                stmt.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            finally{
                stmt.close();
                conn.close();
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }

    public static class Update6AddPost extends PostgresUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public void executeOperation(LdbcUpdate6AddPost operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();
                String queryStringAddPost = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate6AddPost);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPost);

                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPost, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPost);
                try {
                    // InteractiveUpdate6AddPost
                    stmt1.executeUpdate();
                } catch (Exception e) {
                    throw new DbException(e);
                }
                finally{
                    stmt1.close();
                }

                // InteractiveUpdate6AddPostTags
                String queryStringAddPostTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate6AddPostTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPostTags, ImmutableList.of("tagId"));
                final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddPostTags, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPostTags);
                stmt2.setLong(1, operation.getPostId());

                try{

                    for (long tagId: operation.getTagIds()) {
                        stmt2.setLong(2, tagId);
                        stmt2.executeUpdate();
                    }
                } catch (Exception e) {
                    throw new DbException(e);
                }
                finally{
                    stmt2.close();
                    conn.close();
                }
        } catch (Exception e) {
            throw new DbException(e);
        }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update7AddComment extends PostgresUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public void executeOperation(LdbcUpdate7AddComment operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();
                // InteractiveUpdate7AddComment
                String queryStringAddComment = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate7AddComment);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddComment);
                final PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddComment, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddComment);
            try {

                stmt1.executeUpdate();
            } catch (Exception e) {
                throw new DbException(e);
            }
            finally{
                stmt1.close();
            }
            // InteractiveUpdate7AddCommentTags
            String queryStringAddCommentTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate7AddCommentTags);
            replaceParameterNamesWithQuestionMarks(operation, queryStringAddCommentTags, ImmutableList.of("tagId"));
            final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddCommentTags, conn);
            state.logQuery(operation.getClass().getSimpleName(), queryStringAddCommentTags);
            stmt2.setLong(1, operation.getCommentId());
            try{

                for (long tagId: operation.getTagIds()) {
                    stmt2.setLong(2, tagId);
                    stmt2.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            finally{
                stmt2.close();
                conn.close();
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Update8AddFriendship extends PostgresUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public void executeOperation(LdbcUpdate8AddFriendship operation, PostgresDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try {
                Connection conn = state.getConnection();
                String queryString = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveUpdate8);
                replaceParameterNamesWithQuestionMarks(operation, queryString);
                final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryString, conn);
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                try {
                    stmt.executeUpdate();
                } catch (Exception e) {
                    throw new DbException(e);
                }
                finally{
                    stmt.close();
                    conn.close();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
                resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
            }
        }

}
