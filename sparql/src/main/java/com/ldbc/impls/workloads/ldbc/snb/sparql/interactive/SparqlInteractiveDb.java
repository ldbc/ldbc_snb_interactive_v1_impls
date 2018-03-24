package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
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
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlSingletonOperationHandler;
import org.openrdf.query.BindingSet;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class SparqlInteractiveDb extends SparqlDb<SparqlInteractiveQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new SparqlDbConnectionState(properties, new SparqlInteractiveQueryStore(properties.get("queryDir")));

        registerOperationHandler(LdbcQuery1.class, Query1.class);
        registerOperationHandler(LdbcQuery2.class, Query2.class);
        registerOperationHandler(LdbcQuery3.class, Query3.class);
        registerOperationHandler(LdbcQuery4.class, Query4.class);
        registerOperationHandler(LdbcQuery5.class, Query5.class);
        registerOperationHandler(LdbcQuery6.class, Query6.class);
        registerOperationHandler(LdbcQuery7.class, Query7.class);
        registerOperationHandler(LdbcQuery8.class, Query8.class);
        registerOperationHandler(LdbcQuery9.class, Query9.class);
        registerOperationHandler(LdbcQuery10.class, Query10.class);
        registerOperationHandler(LdbcQuery11.class, Query11.class);
        registerOperationHandler(LdbcQuery12.class, Query12.class);
        registerOperationHandler(LdbcQuery13.class, Query13.class);
        registerOperationHandler(LdbcQuery14.class, Query14.class);

//		registerOperationHandler(LdbcShortQuery1PersonProfile.class, LdbcShortQuery1.class);
//		registerOperationHandler(LdbcShortQuery2PersonPosts.class, LdbcShortQuery2.class);
//		registerOperationHandler(LdbcShortQuery3PersonFriends.class, LdbcShortQuery3.class);
//		registerOperationHandler(LdbcShortQuery4MessageContent.class, LdbcShortQuery4.class);
//		registerOperationHandler(LdbcShortQuery5MessageCreator.class, LdbcShortQuery5.class);
//		registerOperationHandler(LdbcShortQuery6MessageForum.class, LdbcShortQuery6.class);
//		registerOperationHandler(LdbcShortQuery7MessageReplies.class, LdbcShortQuery7.class);

//		registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonSparql.class);
//		registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeSparql.class);
//		registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeSparql.class);
//		registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumSparql.class);
//		registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipSparql.class);
//		registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostSparql.class);
//		registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentSparql.class);
//		registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipSparql.class);
    }

    public static class Query1 extends SparqlListOperationHandler<LdbcQuery1, LdbcQuery1Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcQuery1Result convertSingleResult(BindingSet bs) throws ParseException {
            long friendId = convertLong(bs, "friendId");
            String friendLastName = convertString(bs, "friendLastName");
            int distanceFromPerson = convertInteger(bs, "distanceFromPerson");
            long friendBirthday = convertDate(bs, "friendBirthDay");
            long friendCreationDate = convertDate(bs, "friendCreationDate");
            String friendGender = convertString(bs, "friendGender");
            String friendBrowserUsed = convertString(bs, "friendBrowserUsed");
            String friendLocationIp = convertString(bs, "friendLocationIp");
            Iterable<String> friendEmails = convertStringList(bs, "friendEmails");
            Iterable<String> friendLanguages = convertStringList(bs, "friendLanguages");
            String friendCityName = convertString(bs, "friendCityName");
            Iterable<List<Object>> friendUniversities = convertSisList(bs, "friendUniversities");
            Iterable<List<Object>> friendCompanies = convertSisList(bs, "friendCompanies");

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

    public static class Query2 extends SparqlListOperationHandler<LdbcQuery2, LdbcQuery2Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcQuery2Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long postOrCommentId = convertLong(bs, "postOrCommentId");
            String postOrCommentContent = convertString(bs, "postOrCommentContent");
            long postOrCommentCreationDate = convertDate(bs, "postOrCommentCreationDate");
            return new LdbcQuery2Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
        }

    }

    public static class Query3 extends SparqlListOperationHandler<LdbcQuery3, LdbcQuery3Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery3 operation) {
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


    public static class Query4 extends SparqlListOperationHandler<LdbcQuery4, LdbcQuery4Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcQuery4Result convertSingleResult(BindingSet bs) {
            String tagName = convertString(bs, "tagName");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcQuery4Result(tagName, postCount);
        }

    }


    public static class Query5 extends SparqlListOperationHandler<LdbcQuery5, LdbcQuery5Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcQuery5Result convertSingleResult(BindingSet bs) {
            String forumTitle = convertString(bs, "forumTitle");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcQuery5Result(forumTitle, postCount);
        }

    }


    public static class Query6 extends SparqlListOperationHandler<LdbcQuery6, LdbcQuery6Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcQuery6Result convertSingleResult(BindingSet bs) {
            String tagName = convertString(bs, "tagName");
            int postCount = convertInteger(bs, "postCount");
            return new LdbcQuery6Result(tagName, postCount);
        }

    }

    public static class Query7 extends SparqlListOperationHandler<LdbcQuery7, LdbcQuery7Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcQuery7Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long likeCreationDate = convertDate(bs, "likeCreationDate");
            long commentOrPostId = convertLong(bs, "commentOrPostId");
            String commentOrPostContent = convertString(bs, "commentOrPostContent");
            int minutesLatency = convertInteger(bs, "minutesLatency");
            boolean isNew = convertBoolean(bs, "isNew");
            return new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, commentOrPostId, commentOrPostContent, minutesLatency, isNew);
        }

    }

    public static class Query8 extends SparqlListOperationHandler<LdbcQuery8, LdbcQuery8Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcQuery8Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long commentCreationDate = convertDate(bs, "commentCreationDate");
            long commentId = convertDate(bs, "commentId");
            String commentContent = convertString(bs, "commentContent");
            return new LdbcQuery8Result(personId, personFirstName, personLastName, commentCreationDate, commentId, commentContent);
        }

    }

    public static class Query9 extends SparqlListOperationHandler<LdbcQuery9, LdbcQuery9Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcQuery9Result convertSingleResult(BindingSet bs) throws ParseException {
            long personId = convertLong(bs, "personId");
            String personFirstName = convertString(bs, "personFirstName");
            String personLastName = convertString(bs, "personLastName");
            long commentOrPostId = convertDate(bs, "commentOrPostId");
            String commentOrPostContent = convertString(bs, "commentOrPostContent");
            long commentOrPostCreationDate = convertDate(bs, "commentOrPostCreationDate");
            return new LdbcQuery9Result(personId, personFirstName, personLastName, commentOrPostId, commentOrPostContent, commentOrPostCreationDate);
        }

    }


    public static class Query10 extends SparqlListOperationHandler<LdbcQuery10, LdbcQuery10Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery10 operation) {
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

    public static class Query11 extends SparqlListOperationHandler<LdbcQuery11, LdbcQuery11Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery11 operation) {
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

    public static class Query12 extends SparqlListOperationHandler<LdbcQuery12, LdbcQuery12Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery12 operation) {
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

    public static class Query13 extends SparqlSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result convertSingleResult(BindingSet bs) {
            int shortestPathLength = convertInteger(bs, "shortestPathLength");
            return new LdbcQuery13Result(shortestPathLength);
        }

    }

    public static class Query14 extends SparqlListOperationHandler<LdbcQuery14, LdbcQuery14Result, SparqlInteractiveQueryStore> {

        @Override
        public String getQueryString(SparqlDbConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery14 operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcQuery14Result convertSingleResult(BindingSet bs) {
            Iterable<Long> personIdsInPath = convertLongList(bs, "personIdsInPath");
            double pathWeight = convertDouble(bs, "pathWeight");
            return new LdbcQuery14Result(personIdsInPath, pathWeight);
        }

    }

}

