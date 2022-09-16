package org.ldbcouncil.snb.impls.workloads.umbra;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;
import org.ldbcouncil.snb.impls.workloads.umbra.converter.UmbraConverter;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraMultipleUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.umbra.operationhandlers.UmbraUpdateOperationHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.ImmutableList;

public abstract class UmbraDb extends BaseDb<UmbraQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new UmbraDbConnectionState(properties, new UmbraQueryStore(properties.get("queryDir")));
        } catch (ClassNotFoundException e) {
            throw new DbException(e);
        }
    }

    // Interactive complex reads

    public static class Query1 extends UmbraListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery1);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(ResultSet result) throws SQLException {
            LdbcQuery1Result qr = new LdbcQuery1Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getInt(3),
                    UmbraConverter.stringTimestampToEpoch(result, 4),
                    UmbraConverter.stringTimestampToEpoch(result, 5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    UmbraConverter.arrayToStringArray(result, 9),
                    UmbraConverter.arrayToStringArray(result, 10),
                    result.getString(11),
                    UmbraConverter.arrayToOrganizationArray(result, 12),
                    UmbraConverter.arrayToOrganizationArray(result, 13));
            return qr;
        }
    }

    public static class Query2 extends UmbraListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery2 operation) {
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
                    UmbraConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query3a extends UmbraListOperationHandler<LdbcQuery3a, LdbcQuery3Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery3a operation) {
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

    public static class Query3b extends UmbraListOperationHandler<LdbcQuery3b, LdbcQuery3Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery3b operation) {
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

    public static class Query4 extends UmbraListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery4);
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
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery5);
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
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery6);
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
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery7);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery7Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getBoolean(8));
        }

    }

    public static class Query8 extends UmbraListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery8);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery8Result(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.stringTimestampToEpoch(result, 4),
                    result.getLong(5),
                    result.getString(6));
        }

    }

    public static class Query9 extends UmbraListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery9 operation) {
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
                    UmbraConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query10 extends UmbraListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery10 operation) {
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

    public static class Query11 extends UmbraListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery11 operation) {
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

    public static class Query12 extends UmbraListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery12);
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

    public static class Query13a extends UmbraSingletonOperationHandler<LdbcQuery13a, LdbcQuery13Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery13a operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery13);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }
    }

    public static class Query13b extends UmbraSingletonOperationHandler<LdbcQuery13b, LdbcQuery13Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery13b operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery13);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }
    }

    public static class Query14a extends UmbraListOperationHandler<LdbcQuery14a, LdbcQuery14Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery14a operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery14);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    UmbraConverter.arrayToLongArray(result, 1),
                    result.getLong(2));
        }
    }

    public static class Query14b extends UmbraListOperationHandler<LdbcQuery14b, LdbcQuery14Result> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcQuery14b operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery14);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery14Result(
                    UmbraConverter.arrayToLongArray(result, 1),
                    result.getLong(2));
        }
    }

    public static class ShortQuery1PersonProfile extends UmbraSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery1);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery1PersonProfileResult(
                    result.getString(1),
                    result.getString(2),
                    UmbraConverter.stringTimestampToEpoch(result, 3),
                    result.getString(4),
                    result.getString(5),
                    result.getLong(6),
                    result.getString(7),
                    UmbraConverter.stringTimestampToEpoch(result, 8));
        }

    }

    public static class ShortQuery2PersonPosts extends UmbraListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery2);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery2PersonPostsResult(
                    result.getLong(1),
                    result.getString(2),
                    UmbraConverter.stringTimestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getLong(5),
                    result.getString(6),
                    result.getString(7));
        }

    }

    public static class ShortQuery3PersonFriends extends UmbraListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery3);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery3PersonFriendsResult(
                    result.getLong(1),
                    result.getString(2),
                    result.getString(3),
                    UmbraConverter.stringTimestampToEpoch(result, 4));
        }

    }

    public static class ShortQuery4MessageContent extends UmbraSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery4);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery4MessageContentResult(
                    result.getString(1),
                    UmbraConverter.stringTimestampToEpoch(result, 2));
        }

    }

    public static class ShortQuery5MessageCreator extends UmbraSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
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

    public static class ShortQuery6MessageForum extends UmbraSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery6MessageForum operation) {
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

    public static class ShortQuery7MessageReplies extends UmbraListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery7);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcShortQuery7MessageRepliesResult(
                    result.getLong(1),
                    result.getString(2),
                    UmbraConverter.stringTimestampToEpoch(result, 3),
                    result.getLong(4),
                    result.getString(5),
                    result.getString(6),
                    result.getBoolean(7));
        }

    }


    public static class Insert1AddPerson extends UmbraMultipleUpdateOperationHandler<LdbcInsert1AddPerson> {

        @Override
        public void executeOperation(LdbcInsert1AddPerson operation, UmbraDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try (Connection conn = state.getConnection()) {
                // InteractiveInsert1AddPerson
                String queryStringAddPerson = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert1AddPerson);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPerson);
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPerson);
                try ( PreparedStatement stmt1 = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPerson, conn)){
                    stmt1.executeUpdate();
                }

                // InteractiveInsert1AddPersonCompanies
                String queryStringAddPersonCompanies = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert1AddPersonCompanies);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonCompanies, ImmutableList.of("organizationId", "worksFromYear"));
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonCompanies);
                try (final PreparedStatement stmt2 = prepareSnbStatement(queryStringAddPersonCompanies, conn)) {
                    for (LdbcInsert1AddPerson.Organization o : operation.getWorkAt()) {
                        stmt2.setObject(1, UmbraConverter.convertDateToOffsetDateTime(operation.getCreationDate()));
                        stmt2.setLong(2, operation.getPersonId());
                        stmt2.setLong(3, o.getOrganizationId());
                        stmt2.setInt(4, o.getYear());
                        stmt2.addBatch();
                    }
                    stmt2.executeBatch();
                }

                // InteractiveInsert1AddPersonTags
                String queryStringAddPersonTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert1AddPersonTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonTags, ImmutableList.of("tagId"));
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonTags);
                try (PreparedStatement stmt5 = prepareSnbStatement(queryStringAddPersonTags, conn))
                {
                    for (long tagId : operation.getTagIds()) {
                        stmt5.setObject(1, UmbraConverter.convertDateToOffsetDateTime(operation.getCreationDate()));
                        stmt5.setLong(2, operation.getPersonId());
                        stmt5.setLong(3, tagId);
                        stmt5.addBatch();
                    }
                    stmt5.executeBatch();
                }

                // InteractiveInsert1AddPersonUniversities
                String queryStringAddPersonUniversities = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert1AddPersonUniversities);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPersonUniversities, ImmutableList.of("organizationId", "studiesFromYear"));
                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPersonUniversities);
                try ( PreparedStatement stmt6 = prepareSnbStatement(queryStringAddPersonUniversities, conn)){
                    for (LdbcInsert1AddPerson.Organization o : operation.getStudyAt()) {
                        stmt6.setObject(1, UmbraConverter.convertDateToOffsetDateTime(operation.getCreationDate()));
                        stmt6.setLong(2, operation.getPersonId());
                        stmt6.setLong(3, o.getOrganizationId());
                        stmt6.setInt(4, o.getYear());
                        stmt6.addBatch();
                    }
                    stmt6.executeBatch();
                }

            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }

    public static class Insert2AddPostLike extends UmbraUpdateOperationHandler<LdbcInsert2AddPostLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcInsert2AddPostLike operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert2);
        }
    }

    public static class Insert3AddCommentLike extends UmbraUpdateOperationHandler<LdbcInsert3AddCommentLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcInsert3AddCommentLike operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert3);
        }
    }

    public static class Insert4AddForum extends UmbraMultipleUpdateOperationHandler<LdbcInsert4AddForum> {

        @Override
        public void executeOperation(LdbcInsert4AddForum operation, UmbraDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try (Connection conn = state.getConnection()) {
                // InteractiveInsert4AddForum
                String queryStringAddForum = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert4AddForum);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddForum);

                state.logQuery(operation.getClass().getSimpleName(), queryStringAddForum);
                try (PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryStringAddForum, conn)) {
                    stmt.executeUpdate();
                }

                // InteractiveInsert4AddForumTags
                String queryStringAddForumTagIds = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert4AddForumTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddForumTagIds);

                state.logQuery(operation.getClass().getSimpleName(), queryStringAddForumTagIds);
                try (final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryStringAddForumTagIds, conn)) {
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Insert5AddForumMembership extends UmbraUpdateOperationHandler<LdbcInsert5AddForumMembership> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcInsert5AddForumMembership operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert5);
        }
    }

    public static class Insert6AddPost extends UmbraMultipleUpdateOperationHandler<LdbcInsert6AddPost> {
        @Override
        public void executeOperation(LdbcInsert6AddPost operation, UmbraDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try (Connection conn = state.getConnection()) {
                // InteractiveInsert6AddPost
                String queryStringAddPost = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert6AddPost);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPost);

                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPost);
                try (final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPost, conn)) {
                    stmt.executeUpdate();
                }

                // InteractiveInsert6AddPostTags
                String queryStringAddPostTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert6AddPostTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddPostTags);

                state.logQuery(operation.getClass().getSimpleName(), queryStringAddPostTags);
                try (final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryStringAddPostTags, conn)) {
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }

    public static class Insert7AddComment extends UmbraMultipleUpdateOperationHandler<LdbcInsert7AddComment> {

        @Override
        public void executeOperation(LdbcInsert7AddComment operation, UmbraDbConnectionState state, ResultReporter resultReporter) throws DbException {
            try (Connection conn = state.getConnection()) {
                // InteractiveInsert7AddComment
                String queryStringAddComment = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert7AddComment);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddComment);

                state.logQuery(operation.getClass().getSimpleName(), queryStringAddComment);
                try (final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryStringAddComment, conn)) {
                    stmt.executeUpdate();
                }

                // InteractiveInsert7AddCommentTags
                String queryStringAddCommentTags = state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert7AddCommentTags);
                replaceParameterNamesWithQuestionMarks(operation, queryStringAddCommentTags);

                state.logQuery(operation.getClass().getSimpleName(), queryStringAddCommentTags);
                try (final PreparedStatement stmt = prepareAndSetParametersInPreparedStatement(operation, queryStringAddCommentTags, conn)) {
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                throw new DbException(e);
            }
            resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }

    }

    public static class Insert8AddFriendship extends UmbraUpdateOperationHandler<LdbcInsert8AddFriendship> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcInsert8AddFriendship operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert8);
        }
    }

    // Deletions
    public static class Delete1RemovePerson extends UmbraUpdateOperationHandler<LdbcDelete1RemovePerson> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete1RemovePerson operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete1);
        }
    }

    public static class Delete2RemovePostLike extends UmbraUpdateOperationHandler<LdbcDelete2RemovePostLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete2RemovePostLike operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete2);
        }
    }

    public static class Delete3RemoveCommentLike extends UmbraUpdateOperationHandler<LdbcDelete3RemoveCommentLike> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete3RemoveCommentLike operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete3);
        }
    }

    public static class Delete4RemoveForum extends UmbraUpdateOperationHandler<LdbcDelete4RemoveForum> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete4RemoveForum operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete4);
        }
    }

    public static class Delete5RemoveForumMembership extends UmbraUpdateOperationHandler<LdbcDelete5RemoveForumMembership> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete5RemoveForumMembership operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete5);
        }
    }

    public static class Delete6RemovePostThread extends UmbraUpdateOperationHandler<LdbcDelete6RemovePostThread> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete6RemovePostThread operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete6);
        }
    }

    public static class Delete7RemoveCommentSubthread extends UmbraUpdateOperationHandler<LdbcDelete7RemoveCommentSubthread> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete7RemoveCommentSubthread operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete7);
        }
    }

    public static class Delete8RemoveFriendship extends UmbraUpdateOperationHandler<LdbcDelete8RemoveFriendship> {

        @Override
        public String getQueryString(UmbraDbConnectionState state, LdbcDelete8RemoveFriendship operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveDelete8);
        }
    }
}
