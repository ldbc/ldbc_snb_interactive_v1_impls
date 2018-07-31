package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPersonResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedReplies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedRepliesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPostsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTagsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiatorsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormalsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircle;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircleResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTrianglesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCountsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteraction;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteractionResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummaryResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21Zombies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21ZombiesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialog;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialogResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinations;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinationsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopic;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopicResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPaths;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPathsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTagsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolutionResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPostersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePostersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForumsResult;
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
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers.PostgresUpdateOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static class BiQuery2 extends PostgresListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery2TopTags operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcSnbBiQuery2TopTagsResult convertSingleResult(ResultSet result) throws SQLException {
            String countryName = result.getString(1);
            int messageMonth = result.getInt(2);
            String personGender = result.getString(3);
            int ageGroup = result.getInt(4);
            String tagName = result.getString(5);
            int messageCount = result.getInt(6);
            return new LdbcSnbBiQuery2TopTagsResult(countryName, messageMonth, personGender, ageGroup, tagName, messageCount);
        }

    }

    public static class BiQuery3 extends PostgresListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery3TagEvolution operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(ResultSet result) throws SQLException {
            String tagName = result.getString(1);
            int countMonth1 = result.getInt(2);
            int countMonth2 = result.getInt(3);
            int diff = result.getInt(4);
            return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countMonth1, countMonth2, diff);
        }

    }

    public static class BiQuery4 extends PostgresListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery4PopularCountryTopics operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(ResultSet result) throws SQLException {
            long forumId = result.getLong(1);
            String forumTitle = result.getString(2);
            long forumCreationDate = PostgresConverter.stringTimestampToEpoch(result, 3);
            long personId = result.getLong(4);
            int postCount = result.getInt(5);
            return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, forumTitle, forumCreationDate, personId, postCount);
        }

    }

    public static class BiQuery5 extends PostgresListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery5TopCountryPosters operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String personFirstName = result.getString(2);
            String personLastName = result.getString(3);
            long personCreationDate = PostgresConverter.stringTimestampToEpoch(result, 4);
            int postCount = result.getInt(5);
            return new LdbcSnbBiQuery5TopCountryPostersResult(personId, personFirstName, personLastName, personCreationDate, postCount);
        }

    }

    public static class BiQuery6 extends PostgresListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery6ActivePosters operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int replyCount = result.getInt(2);
            int likeCount = result.getInt(3);
            int messageCount = result.getInt(4);
            int score = result.getInt(5);
            return new LdbcSnbBiQuery6ActivePostersResult(personId, replyCount, likeCount, messageCount, score);
        }

    }

    public static class BiQuery7 extends PostgresListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int authorityScore = result.getInt(2);
            return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, authorityScore);
        }

    }

    public static class BiQuery8 extends PostgresListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery8RelatedTopics operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(ResultSet result) throws SQLException {
            String relatedTagName = result.getString(1);
            int count = result.getInt(2);
            return new LdbcSnbBiQuery8RelatedTopicsResult(relatedTagName, count);
        }

    }

    public static class BiQuery9 extends PostgresListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery9RelatedForums operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(ResultSet result) throws SQLException {
            long forumId = result.getLong(1);
            int count1 = result.getInt(2);
            int count2 = result.getInt(3);
            return new LdbcSnbBiQuery9RelatedForumsResult(forumId, count1, count2);
        }

    }

    public static class BiQuery10 extends PostgresListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery10TagPerson operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcSnbBiQuery10TagPersonResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int score = result.getInt(2);
            int friendsScore = result.getInt(3);
            return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
        }

    }

    public static class BiQuery11 extends PostgresListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery11UnrelatedReplies operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String tagName = result.getString(2);
            int likeCount = result.getInt(3);
            int replyCount = result.getInt(4);
            return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, likeCount, replyCount);
        }

    }

    public static class BiQuery12 extends PostgresListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery12TrendingPosts operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(ResultSet result) throws SQLException {
            long messageId = result.getLong(1);
            long messageCreationDate = PostgresConverter.stringTimestampToEpoch(result, 2);
            String creatorFirstName = result.getString(3);
            String creatorLastName = result.getString(4);
            int likeCount = result.getInt(5);
            return new LdbcSnbBiQuery12TrendingPostsResult(messageId, messageCreationDate, creatorFirstName, creatorLastName, likeCount);
        }
    }

    public static class BiQuery13 extends PostgresListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(ResultSet result) throws SQLException {
            int year = result.getInt(1);
            int month = result.getInt(2);
            final Object array = result.getArray(3).getArray();

            final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> popularTags;
            if (array instanceof String[][]) {
                final String[][] nestedArray = (String[][]) (Object) array;
                popularTags = Arrays.stream(nestedArray).map(
                        el -> new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(el[0], Integer.parseInt(el[1]))
                ).collect(Collectors.toList());
            } else {
                popularTags = new ArrayList<>();
            }
            return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, popularTags);
        }
    }

    public static class BiQuery14 extends PostgresListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery14TopThreadInitiators operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String personFirstName = result.getString(2);
            String personLastName = result.getString(3);
            int threadCount = result.getInt(4);
            int messageCount = result.getInt(5);
            return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, personFirstName, personLastName, threadCount, messageCount);
        }
    }

    public static class BiQuery15 extends PostgresListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery15SocialNormals operation) {
            return state.getQueryStore().getQuery15(operation);
        }

        @Override
        public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int count = result.getInt(2);
            return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
        }
    }

    public static class BiQuery16 extends PostgresListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
            return state.getQueryStore().getQuery16(operation);
        }

        @Override
        public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            String tagName = result.getString(2);
            int messageCount = result.getInt(3);
            return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tagName, messageCount);
        }
    }

    public static class BiQuery17 extends PostgresSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery17FriendshipTriangles operation) {
            return state.getQueryStore().getQuery17(operation);
        }

        @Override
        public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(ResultSet result) throws SQLException {
            int count = result.getInt(1);
            return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
        }
    }

    public static class BiQuery18 extends PostgresListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery18PersonPostCounts operation) {
            return state.getQueryStore().getQuery18(operation);
        }

        @Override
        public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(ResultSet result) throws SQLException {
            int messageCount = result.getInt(1);
            int personCount = result.getInt(2);
            return new LdbcSnbBiQuery18PersonPostCountsResult(messageCount, personCount);
        }
    }


    public static class BiQuery19 extends PostgresListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery19StrangerInteraction operation) {
            return state.getQueryStore().getQuery19(operation);
        }

        @Override
        public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(ResultSet result) throws SQLException {
            long personId = result.getLong(1);
            int strangerCount = result.getInt(2);
            int interactionCount = result.getInt(3);
            return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, interactionCount);
        }
    }

    public static class BiQuery20 extends PostgresListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery20HighLevelTopics operation) {
            return state.getQueryStore().getQuery20(operation);
        }

        @Override
        public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(ResultSet result) throws SQLException {
            String tagClassName = result.getString(1);
            int messageCount = result.getInt(2);
            return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClassName, messageCount);
        }
    }

    public static class BiQuery21 extends PostgresListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery21Zombies operation) {
            return state.getQueryStore().getQuery21(operation);
        }

        @Override
        public LdbcSnbBiQuery21ZombiesResult convertSingleResult(ResultSet result) throws SQLException {
            long zombieId = result.getLong(1);
            int zombieLikeCount = result.getInt(2);
            int totalLikeCount = result.getInt(3);
            int zombieScore = result.getInt(4);
            return new LdbcSnbBiQuery21ZombiesResult(zombieId, zombieLikeCount, totalLikeCount, zombieScore);
        }
    }

    public static class BiQuery22 extends PostgresListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery22InternationalDialog operation) {
            return state.getQueryStore().getQuery22(operation);
        }

        @Override
        public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(ResultSet result) throws SQLException {
            long person1Id = result.getLong(1);
            long person2Id = result.getLong(2);
            String city1Name = result.getString(3);
            int score = result.getInt(4);
            return new LdbcSnbBiQuery22InternationalDialogResult(person1Id, person2Id, city1Name, score);
        }
    }

    public static class BiQuery23 extends PostgresListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery23HolidayDestinations operation) {
            return state.getQueryStore().getQuery23(operation);
        }

        @Override
        public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(ResultSet result) throws SQLException {
            int messageCount = result.getInt(1);
            String destinationName = result.getString(2);
            int month = result.getInt(3);
            return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, destinationName, month);
        }
    }


    public static class BiQuery24 extends PostgresListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery24MessagesByTopic operation) {
            return state.getQueryStore().getQuery24(operation);
        }

        @Override
        public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(ResultSet result) throws SQLException {
            int messageCount = result.getInt(1);
            int likeCount = result.getInt(2);
            int year = result.getInt(3);
            int month = result.getInt(4);
            String continentName = result.getString(5);
            return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continentName);
        }
    }

    public static class BiQuery25 extends PostgresListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult> {

        @Override
        public String getQueryString(PostgresDbConnectionState state, LdbcSnbBiQuery25WeightedPaths operation) {
            return state.getQueryStore().getQuery25(operation);
        }

        @Override
        public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(ResultSet result) throws SQLException {
            final Long[] array = (Long[]) result.getArray(1).getArray();
            final List<Long> personIds = Arrays.asList(array);
            double weight = result.getDouble(2);
            return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
        }
    }

}
