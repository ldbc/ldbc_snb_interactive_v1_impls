package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.cypher.converter.CypherConverter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.operationhandlers.CypherUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import org.neo4j.driver.Record;

import java.text.ParseException;
import java.time.ZoneId;
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
            long friendBirthday = record.get(3).asLocalDate().atStartOfDay(ZoneId.of("GMT")).toEpochSecond();
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
            long birthday = record.get(2).asLocalDate().atStartOfDay(ZoneId.of("GMT")).toEpochSecond();
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

}
