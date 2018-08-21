package com.ldbc.impls.workloads.ldbc.snb.sparql;

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
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlUpdateOperationHandler;
import org.openrdf.query.BindingSet;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertBoolean;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertDate;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertDateTime;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertDouble;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertInteger;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertLong;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertNestedList;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertString;
import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.convertStringList;

public abstract class SparqlDb extends BaseDb<SparqlQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new SparqlDbConnectionState(properties, new SparqlQueryStore(properties.get("queryDir")));
    }

    public static class Query1 extends SparqlListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(BindingSet bs) throws ParseException {
            long friendId = convertLong(bs, "friendId");
            String friendLastName = convertString(bs, "friendLastName");
            int distanceFromPerson = convertInteger(bs, "distanceFromPerson");
            long friendBirthday = convertDate(bs, "friendBirthday");
            long friendCreationDate = convertDateTime(bs, "friendCreationDate");
            String friendGender = convertString(bs, "friendGender");
            String friendBrowserUsed = convertString(bs, "friendBrowserUsed");
            String friendLocationIp = convertString(bs, "friendLocationIp");
            Iterable<String> friendEmails = convertStringList(bs, "friendEmails");
            Iterable<String> friendLanguages = convertStringList(bs, "friendLanguages");
            String friendCityName = convertString(bs, "friendCityName");
            Iterable<List<Object>> friendUniversities = convertNestedList(bs, "friendUniversities");
            Iterable<List<Object>> friendCompanies = convertNestedList(bs, "friendCompanies");

            return new LdbcQuery1Result(
                    friendId,
                    friendLastName,
                    distanceFromPerson,
                    friendBirthday,
                    friendCreationDate,
                    friendGender,
                    friendBrowserUsed,
                    friendLocationIp,
                    friendEmails,
                    friendLanguages,
                    friendCityName,
                    friendUniversities,
                    friendCompanies);
        }

    }

    public static class Query2 extends SparqlListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcQuery2Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long postOrCommentId = convertLong(bs, "postOrCommentId");
            String postOrCommentContent = convertString(bs, "postOrCommentContent");
            long postOrCommentCreationDate = convertDateTime(bs, "postOrCommentCreationDate");
            return new LdbcQuery2Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
        }

    }

    public static class Query3 extends SparqlListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcQuery3Result convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            int xCount = convertInteger(bs, "xCount");
            int yCount = convertInteger(bs, "yCount");
            int count = convertInteger(bs, "count");
            return new LdbcQuery3Result(personId, personFirstName, personLastName, xCount, yCount, count);
        }

    }

    public static class Query4 extends SparqlListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(BindingSet bs) {
            String tagName = convertString(bs, "tagName");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcQuery4Result(tagName, postCount);
        }

    }

    public static class Query5 extends SparqlListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(BindingSet bs) {
            String forumTitle = convertString(bs, "forumTitle");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcQuery5Result(forumTitle, postCount);
        }

    }


    public static class Query6 extends SparqlListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(BindingSet bs) {
            String tagName = convertString(bs, "tagName");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcQuery6Result(tagName, postCount);
        }

    }

    public static class Query7 extends SparqlListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long likeCreationDate = convertDateTime(bs, "likeCreationDate");
            long commentOrPostId = convertLong(bs, "commentOrPostId");
            String commentOrPostContent = convertString(bs, "commentOrPostContent");
            int minutesLatency = convertInteger(bs, "minutesLatency");
            boolean isNew = convertBoolean(bs, "isNew");
            return new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, commentOrPostId, commentOrPostContent, minutesLatency, isNew);
        }

    }

    public static class Query8 extends SparqlListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long commentCreationDate = convertDateTime(bs, "commentCreationDate");
            long commentId = convertLong(bs, "commentId");
            String commentContent = convertString(bs, "commentContent");
            return new LdbcQuery8Result(personId, personFirstName, personLastName, commentCreationDate, commentId, commentContent);
        }

    }

    public static class Query9 extends SparqlListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcQuery9Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long commentOrPostId = convertLong(bs, "commentOrPostId");
            String commentOrPostContent = convertString(bs, "commentOrPostContent");
            long commentOrPostCreationDate = convertDateTime(bs, "commentOrPostCreationDate");
            return new LdbcQuery9Result(personId, personFirstName, personLastName, commentOrPostId, commentOrPostContent, commentOrPostCreationDate);
        }

    }

    public static class Query10 extends SparqlListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcQuery10Result convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            int commonInterestScore = convertInteger(bs, "commonInterestScore");
            String personGender = convertString(bs, "personGender");
            String personCityName = convertString(bs, "personCityName");
            return new LdbcQuery10Result(personId, personFirstName, personLastName, commonInterestScore, personGender, personCityName);
        }

    }

    public static class Query11 extends SparqlListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcQuery11Result convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            String organisationName = convertString(bs, "organisationName");
            int organizationWorkFromYear = convertInteger(bs, "organizationWorkFromYear");
            return new LdbcQuery11Result(personId, personFirstName, personLastName, organisationName, organizationWorkFromYear);
        }

    }

    public static class Query12 extends SparqlListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcQuery12Result convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            Iterable<String> tagNames = convertStringList(bs, "tagNames");
            int replyCount = convertInteger(bs, "replyCount");

            return new LdbcQuery12Result(personId, personFirstName, personLastName, tagNames, replyCount);
        }

    }

    public static class Query13 extends SparqlSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(BindingSet bs) {
            int shortestPathLength = convertInteger(bs, "shortestPathLength");
            return new LdbcQuery13Result(shortestPathLength);
        }

    }

    public static class ShortQuery1PersonProfile extends SparqlSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfile(operation);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult(BindingSet bs) throws ParseException {
            String firstName = convertString(bs, "firstName");
            String lastName = convertString(bs, "lastName");
            long friendBirthday = convertDate(bs, "birthday");
            String locationIP = convertString(bs, "locationIP");
            String browserUsed = convertString(bs, "browserUsed");
            long cityId = convertLong(bs, "cityId");
            String gender = convertString(bs, "gender");
            long creationDate = convertDateTime(bs, "creationDate");

            return new LdbcShortQuery1PersonProfileResult(firstName, lastName, friendBirthday, locationIP, browserUsed, cityId, gender, creationDate);
        }

    }

    public static class ShortQuery2PersonPosts extends SparqlListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPosts(operation);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult(BindingSet bs) throws ParseException {
            long messageId = convertLong(bs, "messageId");
            String messageContent = convertString(bs, "messageContent");
            long messageCreationDate = convertDateTime(bs, "messageCreationDate");
            long originalPostId = convertLong(bs, "originalPostId");
            long originalPostAuthorId = convertLong(bs, "originalPostAuthorId");
            String originalPostAuthorFirstName = convertString(bs, "originalPostAuthorFirstName");
            String originalPostAuthorLastName = convertString(bs, "originalPostAuthorLastName");

            return new LdbcShortQuery2PersonPostsResult(messageId, messageContent, messageCreationDate, originalPostId, originalPostAuthorId, originalPostAuthorFirstName, originalPostAuthorLastName);
        }

    }

    public static class ShortQuery3PersonFriends extends SparqlListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriends(operation);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String firstName = convertString(bs, "firstName");
            String lastName = convertString(bs, "lastName");
            long friendshipCreationDate = convertDateTime(bs, "friendshipCreationDate");

            return new LdbcShortQuery3PersonFriendsResult(personId, firstName, lastName, friendshipCreationDate);
        }

    }

    public static class ShortQuery4MessageContent extends SparqlSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContent(operation);
        }

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult(BindingSet bs) throws ParseException {
            String messageContent = convertString(bs, "messageContent");
            long messageCreationDate = convertDateTime(bs, "messageCreationDate");

            return new LdbcShortQuery4MessageContentResult(messageContent, messageCreationDate);
        }

    }

    public static class ShortQuery5MessageCreator extends SparqlSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreator(operation);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String firstName = convertString(bs, "firstName");
            String lastName = convertString(bs, "lastName");

            return new LdbcShortQuery5MessageCreatorResult(personId, firstName, lastName);
        }

    }

    public static class ShortQuery6MessageForum extends SparqlSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForum(operation);
        }

        @Override
        public LdbcShortQuery6MessageForumResult convertSingleResult(BindingSet bs) {
            long forumId = convertLong(bs, "forumId");
            String forumTitle = convertString(bs, "forumTitle");
            long moderatorId = convertLong(bs, "moderatorId");
            String moderatorFirstName = convertString(bs, "moderatorFirstName");
            String moderatorLastName = convertString(bs, "moderatorLastName");

            return new LdbcShortQuery6MessageForumResult(forumId, forumTitle, moderatorId, moderatorFirstName, moderatorLastName);
        }

    }

    public static class ShortQuery7MessageReplies extends SparqlListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageReplies(operation);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult(BindingSet bs) throws ParseException {
            long commentId = convertLong(bs, "commentId");
            String commentContent = convertString(bs, "commentContent");
            long commentCreationDate = convertDateTime(bs, "commentCreationDate");
            long replyAuthorId = convertLong(bs, "replyAuthorId");
            String replyAuthorFirstName = convertString(bs, "replyAuthorFirstName");
            String replyAuthorLastName = convertString(bs, "replyAuthorLastName");
            boolean replyAuthorKnowsOriginalMessageAuthor = convertBoolean(bs, "replyAuthorKnowsOriginalMessageAuthor");

            return new LdbcShortQuery7MessageRepliesResult(commentId, commentContent, commentCreationDate, replyAuthorId, replyAuthorFirstName, replyAuthorLastName, replyAuthorKnowsOriginalMessageAuthor);
        }

    }

    public static class Update1AddPerson extends SparqlMultipleUpdateOperationHandler<LdbcUpdate1AddPerson> {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Multiple(operation);
        }

    }

    public static class Update2AddPostLike extends SparqlUpdateOperationHandler<LdbcUpdate2AddPostLike> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }

    }

    public static class Update3AddCommentLike extends SparqlUpdateOperationHandler<LdbcUpdate3AddCommentLike> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }
    }

    public static class Update4AddForum extends SparqlMultipleUpdateOperationHandler<LdbcUpdate4AddForum> {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Multiple(operation);
        }
    }

    public static class Update5AddForumMembership extends SparqlUpdateOperationHandler<LdbcUpdate5AddForumMembership> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }
    }

    public static class Update6AddPost extends SparqlMultipleUpdateOperationHandler<LdbcUpdate6AddPost> {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Multiple(operation);
        }
    }

    public static class Update7AddComment extends SparqlMultipleUpdateOperationHandler<LdbcUpdate7AddComment> {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Multiple(operation);
        }

    }

    public static class Update8AddFriendship extends SparqlUpdateOperationHandler<LdbcUpdate8AddFriendship> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }

    }
    
    public static class BiQuery1 extends SparqlListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery1PostingSummary operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(BindingSet bs) {
            int messageYear = convertInteger(bs, "messageYear");
            boolean isComment = convertBoolean(bs, "isComment");
            int lengthCategory = convertInteger(bs, "lengthCategory");
            long messageCount = convertLong(bs, "messageCount");
            int averageMessageLength = convertInteger(bs, "averageMessageLength");
            int sumMessageLength = convertInteger(bs, "sumMessageLength");
            double percentageOfMessages = convertDouble(bs, "percentageOfMessages");
            return new LdbcSnbBiQuery1PostingSummaryResult(messageYear, isComment, lengthCategory, messageCount, averageMessageLength, sumMessageLength, (float) percentageOfMessages);
        }

    }

    public static class BiQuery2 extends SparqlListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery2TopTags operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcSnbBiQuery2TopTagsResult convertSingleResult(BindingSet bs) {
            String countryName = convertString(bs, "countryName");
            int messageMonth = convertInteger(bs, "messageMonth");
            String personGender = convertString(bs, "personGender");
            int ageGroup = convertInteger(bs, "ageGroup");
            String tagName = convertString(bs, "tagName");
            int messageCount = convertInteger(bs, "messageCount");
            return new LdbcSnbBiQuery2TopTagsResult(countryName, messageMonth, personGender, ageGroup, tagName, messageCount);
        }

    }

    public static class BiQuery3 extends SparqlListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery3TagEvolution operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(BindingSet bs) {
            String tagName = convertString(bs, "tagName");
            int countMonth1 = convertInteger(bs, "countMonth1");
            int countMonth2 = convertInteger(bs, "countMonth2");
            int diff = convertInteger(bs, "diff");
            return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countMonth1, countMonth2, diff);
        }

    }

    public static class BiQuery4 extends SparqlListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery4PopularCountryTopics operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(BindingSet bs) throws ParseException {
            long forumId = convertLong(bs, "forumId");
            String forumTitle = convertString(bs, "forumTitle");
            long forumCreationDate = convertDateTime(bs, "forumCreationDate");
            long personId = convertLong(bs, "personId");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, forumTitle, forumCreationDate, personId, postCount);
        }

    }

    public static class BiQuery5 extends SparqlListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery5TopCountryPosters operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long personCreationDate = convertDateTime(bs, "personCreationDate");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcSnbBiQuery5TopCountryPostersResult(personId, personFirstName, personLastName, personCreationDate, postCount);
        }

    }

    public static class BiQuery6 extends SparqlListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery6ActivePosters operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            int replyCount = convertInteger(bs, "replyCount");
            int likeCount = convertInteger(bs, "likeCount");
            int messageCount = convertInteger(bs, "messageCount");
            int score = convertInteger(bs, "score");
            return new LdbcSnbBiQuery6ActivePostersResult(personId, replyCount, likeCount, messageCount, score);
        }

    }

    public static class BiQuery7 extends SparqlListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            int authorityScore = convertInteger(bs, "authorityScore");
            return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, authorityScore);
        }

    }

    public static class BiQuery8 extends SparqlListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery8RelatedTopics operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(BindingSet bs) {
            String relatedTagName = convertString(bs, "relatedTagName");
            int count = convertInteger(bs, "count");
            return new LdbcSnbBiQuery8RelatedTopicsResult(relatedTagName, count);
        }

    }

    public static class BiQuery9 extends SparqlListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery9RelatedForums operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(BindingSet bs) {
            long forumId = convertLong(bs, "forumId");
            int count1 = convertInteger(bs, "count1");
            int count2 = convertInteger(bs, "count2");
            return new LdbcSnbBiQuery9RelatedForumsResult(forumId, count1, count2);
        }

    }

    public static class BiQuery10 extends SparqlListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery10TagPerson operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcSnbBiQuery10TagPersonResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            int score = convertInteger(bs, "score");
            int friendsScore = convertInteger(bs, "friendsScore");
            return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
        }

    }

    public static class BiQuery11 extends SparqlListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery11UnrelatedReplies operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String tagName = convertString(bs, "tagName");
            int likeCount = convertInteger(bs, "likeCount");
            int replyCount = convertInteger(bs, "replyCount");
            return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, likeCount, replyCount);
        }

    }

    public static class BiQuery12 extends SparqlListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery12TrendingPosts operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(BindingSet bs) throws ParseException {
            long messageId = convertLong(bs, "messageId");
            long messageCreationDate = convertDateTime(bs, "messageCreationDate");
            String creatorFirstName = convertString(bs, "creatorFirstName");
            String creatorLastName = convertString(bs, "creatorLastName");
            int likeCount = convertInteger(bs, "likeCount");
            return new LdbcSnbBiQuery12TrendingPostsResult(messageId, messageCreationDate, creatorFirstName, creatorLastName, likeCount);
        }
    }

    public static class BiQuery13 extends SparqlListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(BindingSet bs) {
            int year = convertInteger(bs, "year");
            int month = convertInteger(bs, "month");
            final String[] popularTagsArray = bs.getBinding("popularTags").getValue().stringValue().split("\\|");
            final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> popularTags = Arrays.stream(popularTagsArray).filter(popularTag -> {
                return popularTag.length() > 0;
            }).map(popularTag -> {
                final String[] tag = popularTag.split("#");
                return new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag[0], Integer.parseInt(tag[1]));
            }).limit(5).collect(Collectors.toList());

            return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, popularTags);
        }
    }

    public static class BiQuery14 extends SparqlListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery14TopThreadInitiators operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            int threadCount = convertInteger(bs, "threadCount");
            int messageCount = convertInteger(bs, "messageCount");
            return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, personFirstName, personLastName, threadCount, messageCount);
        }
    }

    public static class BiQuery15 extends SparqlListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery15SocialNormals operation) {
            return state.getQueryStore().getQuery15(operation);
        }

        @Override
        public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            int count = convertInteger(bs, "count");
            return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
        }
    }

    public static class BiQuery16 extends SparqlListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
            return state.getQueryStore().getQuery16(operation);
        }

        @Override
        public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            String tagName = convertString(bs, "tagName");
            int messageCount = convertInteger(bs, "messageCount");
            return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tagName, messageCount);
        }
    }

    public static class BiQuery17 extends SparqlSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery17FriendshipTriangles operation) {
            return state.getQueryStore().getQuery17(operation);
        }

        @Override
        public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(BindingSet bs) {
            int count = convertInteger(bs, "count");
            return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
        }
    }

    public static class BiQuery18 extends SparqlListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery18PersonPostCounts operation) {
            return state.getQueryStore().getQuery18(operation);
        }

        @Override
        public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(BindingSet bs) {
            int messageCount = convertInteger(bs, "messageCount");
            int personCount = convertInteger(bs, "personCount");
            return new LdbcSnbBiQuery18PersonPostCountsResult(messageCount, personCount);
        }
    }

    public static class BiQuery19 extends SparqlListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery19StrangerInteraction operation) {
            return state.getQueryStore().getQuery19(operation);
        }

        @Override
        public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(BindingSet bs) {
            long personId = convertLong(bs, "personId");
            int strangerCount = convertInteger(bs, "strangerCount");
            int interactionCount = convertInteger(bs, "interactionCount");
            return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, interactionCount);
        }
    }

    public static class BiQuery20 extends SparqlListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery20HighLevelTopics operation) {
            return state.getQueryStore().getQuery20(operation);
        }

        @Override
        public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(BindingSet bs) {
            String tagClassName = convertString(bs, "tagClassName");
            int messageCount = convertInteger(bs, "messageCount");
            return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClassName, messageCount);
        }
    }

    public static class BiQuery21 extends SparqlListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery21Zombies operation) {
            return state.getQueryStore().getQuery21(operation);
        }

        @Override
        public LdbcSnbBiQuery21ZombiesResult convertSingleResult(BindingSet bs) {
            long zombieId = convertLong(bs, "zombieId");
            int zombieLikeCount = convertInteger(bs, "zombieLikeCount");
            int totalLikeCount = convertInteger(bs, "totalLikeCount");
            double zombieScore = convertDouble(bs, "zombieScore");
            return new LdbcSnbBiQuery21ZombiesResult(zombieId, zombieLikeCount, totalLikeCount, zombieScore);
        }
    }

    public static class BiQuery22 extends SparqlListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery22InternationalDialog operation) {
            return state.getQueryStore().getQuery22(operation);
        }

        @Override
        public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(BindingSet bs) {
            long person1Id = convertLong(bs, "person1Id");
            long person2Id = convertLong(bs, "person2Id");
            String city1Name = convertString(bs, "city1Name");
            int score = convertInteger(bs, "score");
            return new LdbcSnbBiQuery22InternationalDialogResult(person1Id, person2Id, city1Name, score);
        }
    }

    public static class BiQuery23 extends SparqlListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery23HolidayDestinations operation) {
            return state.getQueryStore().getQuery23(operation);
        }

        @Override
        public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(BindingSet bs) {
            int messageCount = convertInteger(bs, "messageCount");
            String destinationName = convertString(bs, "destinationName");
            int month = convertInteger(bs, "month");
            return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, destinationName, month);
        }
    }


    public static class BiQuery24 extends SparqlListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery24MessagesByTopic operation) {
            return state.getQueryStore().getQuery24(operation);
        }

        @Override
        public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(BindingSet bs) {
            int messageCount = convertInteger(bs, "messageCount");
            int likeCount = convertInteger(bs, "likeCount");
            int year = convertInteger(bs, "year");
            int month = convertInteger(bs, "month");
            String continentName = convertString(bs, "continentName");
            return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continentName);
        }
    }

    public static class BiQuery25 extends SparqlListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult> {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcSnbBiQuery25WeightedPaths operation) {
            return state.getQueryStore().getQuery25(operation);
        }

        @Override
        public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(BindingSet bs) {
            String[] personIdStrings = bs.getBinding("personIds").getValue().stringValue().split(",");
            final List<Long> personIds = Arrays.stream(personIdStrings).map(Long::parseLong).collect(Collectors.toList());
            double weight = convertDouble(bs, "percentageOfMessages");
            return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
        }
    }

}
