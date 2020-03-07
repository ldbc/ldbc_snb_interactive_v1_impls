package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.google.common.primitives.Ints;
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
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.cypher.converter.CypherConverter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherDeleteOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import org.neo4j.driver.Record;
import org.neo4j.driver.Values;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CypherDb extends BaseDb<CypherQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new CypherDbConnectionState(properties, new CypherQueryStore(properties.get("queryDir")));
    }

    // Interactive complex reads

    public static class InteractiveQuery1 extends CypherListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(Record record) throws ParseException {

            List<String> emails;
            if (!record.get(8).isNull()) {
                emails = record.get(8).asList((e) -> e.asString());
            } else {
                emails = new ArrayList<>();
            }

            List<String> languages;
            if (!record.get(9).isNull()) {
                languages = record.get(9).asList((e) -> e.asString());
            } else {
                languages = new ArrayList<>();
            }

            List<List<Object>> universities;
            if (!record.get(11).isNull()) {
                universities = record.get(11).asList((e) ->
                        e.asList());
            } else {
                universities = new ArrayList<>();
            }

            List<List<Object>> companies;
            if (!record.get(12).isNull()) {
                companies = record.get(12).asList((e) ->
                        e.asList());
            } else {
                companies = new ArrayList<>();
            }

            long friendId = record.get(0).asLong();
            String friendLastName = record.get(1).asString();
            int distanceFromPerson = record.get(2).asInt();
            long friendBirthday = record.get(3).asZonedDateTime().toEpochSecond();
            long friendCreationDate = record.get(4).asZonedDateTime().toEpochSecond();
            String friendGender = record.get(5).asString();
            String friendBrowserUsed = record.get(6).asString();
            String friendLocationIp = record.get(7).asString();
            String friendCityName = record.get(10).asString();
            return new LdbcQuery1Result(
                    friendId,
                    friendLastName,
                    distanceFromPerson,
                    friendBirthday,
                    friendCreationDate,
                    friendGender,
                    friendBrowserUsed,
                    friendLocationIp,
                    emails,
                    languages,
                    friendCityName,
                    universities,
                    companies);
        }
    }

    public static class InteractiveQuery2 extends CypherListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcQuery2Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            long messageId = record.get(3).asLong();
            String messageContent = record.get(4).asString();
            long messageCreationDate = record.get(5).asZonedDateTime().toEpochSecond();

            return new LdbcQuery2Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate);
        }
    }

    public static class InteractiveQuery3 extends CypherListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcQuery3Result convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            int xCount = record.get(3).asInt();
            int yCount = record.get(4).asInt();
            int count = record.get(5).asInt();
            return new LdbcQuery3Result(
                    personId,
                    personFirstName,
                    personLastName,
                    xCount,
                    yCount,
                    count);
        }
    }

    public static class InteractiveQuery4 extends CypherListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(Record record) {
            String tagName = record.get(0).asString();
            int postCount = record.get(1).asInt();
            return new LdbcQuery4Result(tagName, postCount);
        }

    }

    public static class InteractiveQuery5 extends CypherListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(Record record) {
            String forumTitle = record.get(0).asString();
            int postCount = record.get(1).asInt();
            return new LdbcQuery5Result(forumTitle, postCount);
        }
    }

    public static class InteractiveQuery6 extends CypherListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(Record record) {
            String tagName = record.get(0).asString();
            int postCount = record.get(1).asInt();
            return new LdbcQuery6Result(tagName, postCount);
        }
    }

    public static class InteractiveQuery7 extends CypherListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            long likeCreationDate = record.get(3).asZonedDateTime().toEpochSecond();
            long messageId = record.get(4).asLong();
            String messageContent = record.get(5).asString();
            int minutesLatency = record.get(6).asInt();
            boolean isNew = record.get(7).asBoolean();
            return new LdbcQuery7Result(
                    personId,
                    personFirstName,
                    personLastName,
                    likeCreationDate,
                    messageId,
                    messageContent,
                    minutesLatency,
                    isNew);
        }
    }

    public static class InteractiveQuery8 extends CypherListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            long commentCreationDate = record.get(3).asZonedDateTime().toEpochSecond();
            long commentId = record.get(4).asLong();
            String commentContent = record.get(5).asString();
            return new LdbcQuery8Result(
                    personId,
                    personFirstName,
                    personLastName,
                    commentCreationDate,
                    commentId,
                    commentContent);
        }
    }

    public static class InteractiveQuery9 extends CypherListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcQuery9Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            long messageId = record.get(3).asLong();
            String messageContent = record.get(4).asString();
            long messageCreationDate = record.get(5).asZonedDateTime().toEpochSecond();
            return new LdbcQuery9Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate);
        }
    }

    public static class InteractiveQuery10 extends CypherListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcQuery10Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            int commonInterestScore = record.get(3).asInt();
            String personGender = record.get(4).asString();
            String personCityName = record.get(5).asString();
            return new LdbcQuery10Result(
                    personId,
                    personFirstName,
                    personLastName,
                    commonInterestScore,
                    personGender,
                    personCityName);
        }
    }

    public static class InteractiveQuery11 extends CypherListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcQuery11Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            String organizationName = record.get(3).asString();
            int organizationWorkFromYear = record.get(4).asInt();
            return new LdbcQuery11Result(
                    personId,
                    personFirstName,
                    personLastName,
                    organizationName,
                    organizationWorkFromYear);
        }
    }

    public static class InteractiveQuery12 extends CypherListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcQuery12Result convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            List<String> tagNames = new ArrayList<>();
            if (!record.get(3).isNull()) {
                tagNames = record.get(3).asList((e) -> e.asString());
            }
            int replyCount = record.get(4).asInt();
            return new LdbcQuery12Result(
                    personId,
                    personFirstName,
                    personLastName,
                    tagNames,
                    replyCount);
        }
    }

    public static class InteractiveQuery13 extends CypherSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(Record record) {
            return new LdbcQuery13Result(record.get(0).asInt());
        }
    }

    public static class InteractiveQuery14 extends CypherListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(Record record) throws ParseException {
            List<Long> personIdsInPath = new ArrayList<>();
            if (!record.get(0).isNull()) {
                personIdsInPath = record.get(0).asList((e) -> e.asLong());
            }
            double pathWight = record.get(1).asDouble();
            return new LdbcQuery14Result(
                    personIdsInPath,
                    pathWight);
        }
    }

    // Interactive short reads

    public static class ShortQuery1PersonProfile extends CypherSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(Record record) throws ParseException {
            String firstName = record.get(0).asString();
            String lastName = record.get(1).asString();
            long birthday = record.get(2).asZonedDateTime().toEpochSecond();
            String locationIP = record.get(3).asString();
            String browserUsed = record.get(4).asString();
            long cityId = record.get(5).asLong();
            String gender = record.get(6).asString();
            long creationDate = record.get(7).asZonedDateTime().toEpochSecond();
            return new LdbcShortQuery1PersonProfileResult(
                    firstName,
                    lastName,
                    birthday,
                    locationIP,
                    browserUsed,
                    cityId,
                    gender,
                    creationDate);
        }
    }

    public static class ShortQuery2PersonPosts extends CypherListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(Record record) throws ParseException {
            long messageId = record.get(0).asLong();
            String messageContent = record.get(1).asString();
            long messageCreationDate = record.get(2).asZonedDateTime().toEpochSecond();
            long originalPostId = record.get(3).asLong();
            long originalPostAuthorId = record.get(4).asLong();
            String originalPostAuthorFirstName = record.get(5).asString();
            String originalPostAuthorLastName = record.get(6).asString();
            return new LdbcShortQuery2PersonPostsResult(
                    messageId,
                    messageContent,
                    messageCreationDate,
                    originalPostId,
                    originalPostAuthorId,
                    originalPostAuthorFirstName,
                    originalPostAuthorLastName);
        }
    }

    public static class ShortQuery3PersonFriends extends CypherListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String firstName = record.get(1).asString();
            String lastName = record.get(2).asString();
            long friendshipCreationDate = record.get(3).asZonedDateTime().toEpochSecond();
            return new LdbcShortQuery3PersonFriendsResult(
                    personId,
                    firstName,
                    lastName,
                    friendshipCreationDate);
        }
    }

    public static class ShortQuery4MessageContent extends CypherSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(Record record) throws ParseException {
            // Pay attention, the spec's and the implementation's parameter orders are different.
            long messageCreationDate = record.get(0).asZonedDateTime().toEpochSecond();
            String messageContent = record.get(1).asString();
            return new LdbcShortQuery4MessageContentResult(
                    messageContent,
                    messageCreationDate);
        }
    }

    public static class ShortQuery5MessageCreator extends CypherSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreator(operation);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String firstName = record.get(1).asString();
            String lastName = record.get(2).asString();
            return new LdbcShortQuery5MessageCreatorResult(
                    personId,
                    firstName,
                    lastName);
        }
    }

    public static class ShortQuery6MessageForum extends CypherSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForum(operation);
        }

        @Override
        public LdbcShortQuery6MessageForumResult convertSingleResult(Record record) {
            long forumId = record.get(0).asLong();
            String forumTitle = record.get(1).asString();
            long moderatorId = record.get(2).asLong();
            String moderatorFirstName = record.get(3).asString();
            String moderatorLastName = record.get(4).asString();
            return new LdbcShortQuery6MessageForumResult(
                    forumId,
                    forumTitle,
                    moderatorId,
                    moderatorFirstName,
                    moderatorLastName);
        }
    }

    public static class ShortQuery7MessageReplies extends CypherListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(Record record) throws ParseException {
            long commentId = record.get(0).asLong();
            String commentContent = record.get(1).asString();
            long commentCreationDate = record.get(2).asZonedDateTime().toEpochSecond();
            long replyAuthorId = record.get(3).asLong();
            String replyAuthorFirstName = record.get(4).asString();
            String replyAuthorLastName = record.get(5).asString();
            boolean replyAuthorKnowsOriginalMessageAuthor = record.get(6).asBoolean();
            return new LdbcShortQuery7MessageRepliesResult(
                    commentId,
                    commentContent,
                    commentCreationDate,
                    replyAuthorId,
                    replyAuthorFirstName,
                    replyAuthorLastName,
                    replyAuthorKnowsOriginalMessageAuthor);
        }
    }

    // Interactive inserts

    public static class Update1AddPerson extends CypherUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Single(operation);
        }
    }

    public static class Update2AddPostLike extends CypherUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }
    }

    public static class Update3AddCommentLike extends CypherUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }
    }

    public static class Update4AddForum extends CypherUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Single(operation);
        }
    }

    public static class Update5AddForumMembership extends CypherUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }
    }

    public static class Update6AddPost extends CypherUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Single(operation);
        }
    }

    public static class Update7AddComment extends CypherUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Single(operation);
        }
    }

    public static class Update8AddFriendship extends CypherUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }
    }

    // Interactive deletes

    public static class Delete1RemovePerson extends CypherDeleteOperationHandler<LdbcDelete1RemovePerson> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete1RemovePerson operation) {
            return state.getQueryStore().getDelete1(operation);
        }
    }

    public static class Delete2RemovePostLike extends CypherDeleteOperationHandler<LdbcDelete2RemovePostLike> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete2RemovePostLike operation) {
            return state.getQueryStore().getDelete2(operation);
        }
    }

    public static class Delete3RemoveCommentLike extends CypherDeleteOperationHandler<LdbcDelete3RemoveCommentLike> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete3RemoveCommentLike operation) {
            return state.getQueryStore().getDelete3(operation);
        }
    }

    public static class Delete4RemoveForum extends CypherDeleteOperationHandler<LdbcDelete4RemoveForum> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete4RemoveForum operation) {
            return state.getQueryStore().getDelete4(operation);
        }
    }

    public static class Delete5RemoveForumMembership extends CypherDeleteOperationHandler<LdbcDelete5RemoveForumMembership> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete5RemoveForumMembership operation) {
            return state.getQueryStore().getDelete5(operation);
        }
    }

    public static class Delete6RemovePost extends CypherDeleteOperationHandler<LdbcDelete6RemovePost> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete6RemovePost operation) {
            return state.getQueryStore().getDelete6(operation);
        }
    }

    public static class Delete7RemoveComment extends CypherDeleteOperationHandler<LdbcDelete7RemoveComment> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete7RemoveComment operation) {
            return state.getQueryStore().getDelete7(operation);
        }
    }

    public static class Delete8RemoveFriendship extends CypherDeleteOperationHandler<LdbcDelete8RemoveFriendship> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete8RemoveFriendship operation) {
            return state.getQueryStore().getDelete8(operation);
        }
    }

    // BI queries

    public static class BiQuery1 extends CypherListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery1PostingSummary operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(Record record) {
            int year = record.get(0).asInt();
            boolean isComment = record.get(1).asBoolean();
            int size = record.get(2).asInt();
            long count = record.get(3).asLong();
            int avgLen = record.get(4).asInt();
            int total = record.get(5).asInt();
            double pct = record.get(6).asDouble();

            return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, (float) pct);
        }

    }

    public static class BiQuery2 extends CypherListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery2TopTags operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcSnbBiQuery2TopTagsResult convertSingleResult(Record record) {
            String country = record.get(0).asString();
            int month = record.get(1).asInt();
            String gender = record.get(2).asString();
            int ageGroup = record.get(3).asInt();
            String tag = record.get(4).asString();
            int count = record.get(5).asInt();
            return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
        }

    }

    public static class BiQuery3 extends CypherListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery3TagEvolution operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(Record record) {
            String tagName = record.get(0).asString();
            int countA = record.get(1).asInt();
            int countB = record.get(2).asInt();
            int diffCount = record.get(3).asInt();
            return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
        }

    }

    public static class BiQuery4 extends CypherListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery4PopularCountryTopics operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(Record record) throws ParseException {
            long forumId = record.get(0).asLong();
            String title = record.get(1).asString();
            long creationDate = record.get(2).asZonedDateTime().toEpochSecond();
            long moderator = record.get(3).asLong();
            int count = record.get(4).asInt();
            return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
        }

    }

    public static class BiQuery5 extends CypherListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery5TopCountryPosters operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String firstName = record.get(1).asString();
            String lastName = record.get(2).asString();
            long creationDate = record.get(3).asZonedDateTime().toEpochSecond();
            int count = record.get(4).asInt();
            return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
        }

    }

    public static class BiQuery6 extends CypherListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery6ActivePosters operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int postCount = record.get(1).asInt();
            int replyCount = record.get(2).asInt();
            int likeCount = record.get(3).asInt();
            int score = record.get(4).asInt();
            return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
        }

    }

    public static class BiQuery7 extends CypherListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int score = record.get(1).asInt();
            return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
        }

    }

    public static class BiQuery8 extends CypherListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery8RelatedTopics operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(Record record) {
            String tag = record.get(0).asString();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
        }

    }

    public static class BiQuery9 extends CypherListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery9RelatedForums operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(Record record) {
            long forumId = record.get(0).asLong();
            int sumA = record.get(1).asInt();
            int sumB = record.get(2).asInt();
            return new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB);
        }

    }

    public static class BiQuery10 extends CypherListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery10TagPerson operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcSnbBiQuery10TagPersonResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int score = record.get(1).asInt();
            int friendsScore = record.get(2).asInt();
            return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
        }

    }

    public static class BiQuery11 extends CypherListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery11UnrelatedReplies operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String tagName = record.get(1).asString();
            int countLikes = record.get(2).asInt();
            int countReplies = record.get(3).asInt();
            return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, countLikes, countReplies);
        }

    }

    public static class BiQuery12 extends CypherListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery12TrendingPosts operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            long creationDate = record.get(1).asZonedDateTime().toEpochSecond();
            String firstName = record.get(2).asString();
            String lastName = record.get(3).asString();
            int likeCount = record.get(4).asInt();
            return new LdbcSnbBiQuery12TrendingPostsResult(personId, creationDate, firstName, lastName, likeCount);
        }
    }

    public static class BiQuery13 extends CypherListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(Record record) {
            int year = record.get(0).asInt();
            int month = record.get(1).asInt();
            final List<List<Object>> tagPopularitiesRaw = record.get(2).asList(Values.ofList());

            final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> tagPopularities = new ArrayList<>();
            for (List<Object> tagPopularityRaw : tagPopularitiesRaw) {
                final String tag = (String) tagPopularityRaw.get(0);
                final int popularity = Ints.saturatedCast((long) tagPopularityRaw.get(1));
                tagPopularities.add(new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag, popularity));
            }

            return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tagPopularities);
        }
    }

    public static class BiQuery14 extends CypherListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery14TopThreadInitiators operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String firstName = record.get(1).asString();
            String lastName = record.get(2).asString();
            int count = record.get(3).asInt();
            int threadCount = record.get(4).asInt();
            return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
        }
    }

    public static class BiQuery15 extends CypherListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery15SocialNormals operation) {
            return state.getQueryStore().getQuery15(operation);
        }

        @Override
        public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
        }
    }

    public static class BiQuery16 extends CypherListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
            return state.getQueryStore().getQuery16(operation);
        }

        @Override
        public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String tag = record.get(1).asString();
            int count = record.get(2).asInt();
            return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
        }
    }

    public static class BiQuery17 extends CypherSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery17FriendshipTriangles operation) {
            return state.getQueryStore().getQuery17(operation);
        }

        @Override
        public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(Record record) {
            int count = record.get(0).asInt();
            return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
        }
    }

    public static class BiQuery18 extends CypherListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery18PersonPostCounts operation) {
            return state.getQueryStore().getQuery18(operation);
        }

        @Override
        public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(Record record) {
            int postCount = record.get(0).asInt();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery18PersonPostCountsResult(postCount, count);
        }
    }


    public static class BiQuery19 extends CypherListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery19StrangerInteraction operation) {
            return state.getQueryStore().getQuery19(operation);
        }

        @Override
        public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int strangerCount = record.get(1).asInt();
            int count = record.get(2).asInt();
            return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
        }
    }

    public static class BiQuery20 extends CypherListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery20HighLevelTopics operation) {
            return state.getQueryStore().getQuery20(operation);
        }

        @Override
        public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(Record record) {
            String tagClass = record.get(0).asString();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
        }
    }

    public static class BiQuery21 extends CypherListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery21Zombies operation) {
            return state.getQueryStore().getQuery21(operation);
        }

        @Override
        public LdbcSnbBiQuery21ZombiesResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int zombieCount = record.get(1).asInt();
            int realCount = record.get(2).asInt();
            double score = record.get(3).asDouble();
            return new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
        }
    }

    public static class BiQuery22 extends CypherListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery22InternationalDialog operation) {
            return state.getQueryStore().getQuery22(operation);
        }

        @Override
        public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(Record record) {
            long personIdA = record.get(0).asLong();
            long personIdB = record.get(1).asLong();
            String city1Name = record.get(2).asString();
            int score = record.get(3).asInt();
            return new LdbcSnbBiQuery22InternationalDialogResult(personIdA, personIdB, city1Name, score);
        }
    }

    public static class BiQuery23 extends CypherListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery23HolidayDestinations operation) {
            return state.getQueryStore().getQuery23(operation);
        }

        @Override
        public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(Record record) {
            int messageCount = record.get(0).asInt();
            String country = record.get(1).asString();
            int month = record.get(2).asInt();
            return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, country, month);
        }
    }


    public static class BiQuery24 extends CypherListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery24MessagesByTopic operation) {
            return state.getQueryStore().getQuery24(operation);
        }

        @Override
        public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(Record record) {
            int messageCount = record.get(0).asInt();
            int likeCount = record.get(1).asInt();
            int year = record.get(2).asInt();
            int month = record.get(3).asInt();
            String continent = record.get(4).asString();
            return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
        }
    }

    public static class BiQuery25 extends CypherListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcSnbBiQuery25WeightedPaths operation) {
            return state.getQueryStore().getQuery25(operation);
        }

        @Override
        public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(Record record) {
            List<Long> personIds = record.get(0).asList(Values.ofLong());
            double weight = record.get(1).asDouble();
            return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
        }
    }

}
