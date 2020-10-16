package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresUpdateOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class PostgresDb extends BaseDb<PostgresQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new PostgresDbConnectionState<>(properties, new PostgresQueryStore(properties.get("queryDir")));
        } catch (ClassNotFoundException e) {
            throw new DbException(e);
        }
    }

    // Interactive complex reads

    public static class Query1 extends PostgresListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
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
                    convertLists(PostgresConverter.arrayToObjectArray(result, 12)),
                    convertLists(PostgresConverter.arrayToObjectArray(result, 13)));
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

    public static class Query2 extends PostgresListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery2 operation) {
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
                    PostgresConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query3 extends PostgresListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery3 operation) {
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

    public static class Query4 extends PostgresListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
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
            return state.getQueryStore().getQuery5(operation);
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
            return state.getQueryStore().getQuery6(operation);
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
            return state.getQueryStore().getQuery7(operation);
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
            return state.getQueryStore().getQuery8(operation);
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
                    PostgresConverter.stringTimestampToEpoch(result, 6));
        }

    }

    public static class Query10 extends PostgresListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery10 operation) {
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

    public static class Query11 extends PostgresListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery11 operation) {
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

    public static class Query12 extends PostgresListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
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
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(ResultSet result) throws SQLException {
            return new LdbcQuery13Result(result.getInt(1));
        }

    }

    public static class Query14 extends PostgresListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation);
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
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
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
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
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
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
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
            return state.getQueryStore().getShortQuery4MessageContent(operation);
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

    public static class ShortQuery6MessageForum extends PostgresSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery6MessageForum operation) {
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

    public static class ShortQuery7MessageReplies extends PostgresListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
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

    public static class Update1AddPerson extends PostgresMultipleUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public List<String> getQueryString(PostgresDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Multiple(operation);
        }

    }

    public static class Update2AddPostLike extends PostgresUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }

    }

    public static class Update3AddCommentLike extends PostgresUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }
    }

    public static class Update4AddForum extends PostgresMultipleUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public List<String> getQueryString(PostgresDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Multiple(operation);
        }
    }

    public static class Update5AddForumMembership extends PostgresUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }
    }

    public static class Update6AddPost extends PostgresMultipleUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public List<String> getQueryString(PostgresDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Multiple(operation);
        }
    }

    public static class Update7AddComment extends PostgresMultipleUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public List<String> getQueryString(PostgresDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Multiple(operation);
        }

    }

    public static class Update8AddFriendship extends PostgresUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }

    }

    // BI queries

    public static class BiQuery1 extends PostgresListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery1PostingSummary operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(ResultSet result) throws SQLException {
            int messageYear = result.getInt(1);
            boolean isComment = result.getBoolean(2);
            int lengthCategory = result.getInt(3);
            long messageCount = result.getLong(4);
            int averageMessageLength = result.getInt(5);
            int sumMessageLength = result.getInt(6);
            double percentageOfMessages = result.getDouble(7);
            return new LdbcSnbBiQuery1PostingSummaryResult(messageYear, isComment, lengthCategory, messageCount, averageMessageLength, sumMessageLength, (float) percentageOfMessages);
        }

    }

    public static class BiQuery2 extends PostgresListOperationHandler<LdbcSnbBiQuery2TagEvolution, LdbcSnbBiQuery2TagEvolutionResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery2TagEvolution operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcSnbBiQuery2TagEvolutionResult convertSingleResult(ResultSet result) throws SQLException {
            String tagName = result.getString(1);
            int countMonth1 = result.getInt(2);
            int countMonth2 = result.getInt(3);
            int diff = result.getInt(4);
            return new LdbcSnbBiQuery2TagEvolutionResult(tagName, countMonth1, countMonth2, diff);
        }

    }

    public static class BiQuery3 extends PostgresListOperationHandler<LdbcSnbBiQuery3PopularCountryTopics, LdbcSnbBiQuery3PopularCountryTopicsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery3PopularCountryTopics operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcSnbBiQuery3PopularCountryTopicsResult convertSingleResult(ResultSet result) throws SQLException {
            long forumId = result.getLong(1);
            String forumTitle = result.getString(2);
            long forumCreationDate = PostgresConverter.stringTimestampToEpoch(result, 3);
            long personId = result.getLong(4);
            int postCount = result.getInt(5);
            return new LdbcSnbBiQuery3PopularCountryTopicsResult(forumId, forumTitle, forumCreationDate, personId, postCount);
        }

    }

    public static class BiQuery4 extends PostgresListOperationHandler<LdbcSnbBiQuery4TopCountryPosters, LdbcSnbBiQuery4TopCountryPostersResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery4TopCountryPosters operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcSnbBiQuery4TopCountryPostersResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String personFirstName = result.getString(2);
            String personLastName = result.getString(3);
            long personCreationDate = PostgresConverter.stringTimestampToEpoch(result, 4);
            int postCount = result.getInt(5);
            return new LdbcSnbBiQuery4TopCountryPostersResult(personId, personFirstName, personLastName, personCreationDate, postCount);
        }

    }

    public static class BiQuery5 extends PostgresListOperationHandler<LdbcSnbBiQuery5ActivePosters, LdbcSnbBiQuery5ActivePostersResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery5ActivePosters operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcSnbBiQuery5ActivePostersResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int replyCount = result.getInt(2);
            int likeCount = result.getInt(3);
            int messageCount = result.getInt(4);
            int score = result.getInt(5);
            return new LdbcSnbBiQuery5ActivePostersResult(personId, replyCount, likeCount, messageCount, score);
        }

    }

    public static class BiQuery6 extends PostgresListOperationHandler<LdbcSnbBiQuery6AuthoritativeUsers, LdbcSnbBiQuery6AuthoritativeUsersResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery6AuthoritativeUsers operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcSnbBiQuery6AuthoritativeUsersResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int authorityScore = result.getInt(2);
            return new LdbcSnbBiQuery6AuthoritativeUsersResult(personId, authorityScore);
        }

    }

    public static class BiQuery7 extends PostgresListOperationHandler<LdbcSnbBiQuery7RelatedTopics, LdbcSnbBiQuery7RelatedTopicsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery7RelatedTopics operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcSnbBiQuery7RelatedTopicsResult convertSingleResult(ResultSet result) throws SQLException {
            String relatedTagName = result.getString(1);
            int count = result.getInt(2);
            return new LdbcSnbBiQuery7RelatedTopicsResult(relatedTagName, count);
        }

    }

    public static class BiQuery8 extends PostgresListOperationHandler<LdbcSnbBiQuery8TagPerson, LdbcSnbBiQuery8TagPersonResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery8TagPerson operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcSnbBiQuery8TagPersonResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int score = result.getInt(2);
            int friendsScore = result.getInt(3);
            return new LdbcSnbBiQuery8TagPersonResult(personId, score, friendsScore);
        }

    }

    public static class BiQuery9 extends PostgresListOperationHandler<LdbcSnbBiQuery9TopThreadInitiators, LdbcSnbBiQuery9TopThreadInitiatorsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery9TopThreadInitiators operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcSnbBiQuery9TopThreadInitiatorsResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String personFirstName = result.getString(2);
            String personLastName = result.getString(3);
            int threadCount = result.getInt(4);
            int messageCount = result.getInt(5);
            return new LdbcSnbBiQuery9TopThreadInitiatorsResult(personId, personFirstName, personLastName, threadCount, messageCount);
        }
    }

    public static class BiQuery10 extends PostgresListOperationHandler<LdbcSnbBiQuery10ExpertsInSocialCircle, LdbcSnbBiQuery10ExpertsInSocialCircleResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery10ExpertsInSocialCircle operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcSnbBiQuery10ExpertsInSocialCircleResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String tagName = result.getString(2);
            int messageCount = result.getInt(3);
            return new LdbcSnbBiQuery10ExpertsInSocialCircleResult(personId, tagName, messageCount);
        }
    }

    public static class BiQuery11 extends PostgresSingletonOperationHandler<LdbcSnbBiQuery11FriendshipTriangles, LdbcSnbBiQuery11FriendshipTrianglesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery11FriendshipTriangles operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcSnbBiQuery11FriendshipTrianglesResult convertSingleResult(ResultSet result) throws SQLException {
            int count = result.getInt(1);
            return new LdbcSnbBiQuery11FriendshipTrianglesResult(count);
        }
    }

    public static class BiQuery12 extends PostgresListOperationHandler<LdbcSnbBiQuery12PersonPostCounts, LdbcSnbBiQuery12PersonPostCountsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery12PersonPostCounts operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcSnbBiQuery12PersonPostCountsResult convertSingleResult(ResultSet result) throws SQLException {
            int messageCount = result.getInt(1);
            int personCount = result.getInt(2);
            return new LdbcSnbBiQuery12PersonPostCountsResult(messageCount, personCount);
        }
    }

    public static class BiQuery13 extends PostgresListOperationHandler<LdbcSnbBiQuery13Zombies, LdbcSnbBiQuery13ZombiesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery13Zombies operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcSnbBiQuery13ZombiesResult convertSingleResult(ResultSet result) throws SQLException {
            long zombieId = result.getLong(1);
            int zombieLikeCount = result.getInt(2);
            int totalLikeCount = result.getInt(3);
            int zombieScore = result.getInt(4);
            return new LdbcSnbBiQuery13ZombiesResult(zombieId, zombieLikeCount, totalLikeCount, zombieScore);
        }
    }

    public static class BiQuery14 extends PostgresListOperationHandler<LdbcSnbBiQuery14InternationalDialog, LdbcSnbBiQuery14InternationalDialogResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery14InternationalDialog operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcSnbBiQuery14InternationalDialogResult convertSingleResult(ResultSet result) throws SQLException {
            long person1Id = result.getLong(1);
            long person2Id = result.getLong(2);
            String city1Name = result.getString(3);
            int score = result.getInt(4);
            return new LdbcSnbBiQuery14InternationalDialogResult(person1Id, person2Id, city1Name, score);
        }
    }

    public static class BiQuery15 extends PostgresListOperationHandler<LdbcSnbBiQuery15WeightedPaths, LdbcSnbBiQuery15WeightedPathsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery15WeightedPaths operation) {
            return state.getQueryStore().getQuery15(operation);
        }

        @Override
        public LdbcSnbBiQuery15WeightedPathsResult convertSingleResult(ResultSet result) throws SQLException {
            final Long[] array = (Long[]) result.getArray(1).getArray();
            final List<Long> personIds = Arrays.asList(array);
            double weight = result.getDouble(2);
            return new LdbcSnbBiQuery15WeightedPathsResult(personIds, weight);
        }
    }

}
