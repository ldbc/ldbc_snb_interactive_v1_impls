package com.ldbc.impls.workloads.ldbc.snb.tigergraph;

import com.google.common.collect.ImmutableMap;
import com.google.gson.internal.LinkedTreeMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector.ResultConverter;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector.VertexResult;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers.TygrysekListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers.TygrysekSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tigergraph.operationhandlers.TygrysekUpdateOperationHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public abstract class TygrysekDb extends BaseDb<TygrysekQueryStore> {
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void addStringArrayParam(List<String> items, ImmutableMap.Builder<String, String> builder, String key) {
        int idx = 0;
        for (String item : items) {
            builder.put(key + "[" + idx++ + "]", item);

        }
    }

    private static void addLongArrayParam(List<Long> items, ImmutableMap.Builder<String, String> builder, String key) {
        int idx = 0;
        for (Long item : items) {
            builder.put(key + "[" + idx++ + "]", Long.toString((Long) item));
        }
    }

    private static void addOrgsParam(ImmutableMap.Builder<String, String> builder, List<LdbcUpdate1AddPerson.Organization> orgs, String key) {
        if (orgs != null && orgs.size() > 0) {
            builder.put(key, ResultConverter.orgsToString(orgs));
        }
    }

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new TygrysekDbConnectionState(properties, new TygrysekQueryStore(properties.get("queryDir")));
    }

    // Interactive complex reads
    public static class InteractiveQuery1 extends TygrysekListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex1";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery1 operation = (LdbcQuery1) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery1.PERSON_ID, Long.toString(operation.personId()))
                    .put(LdbcQuery1.FIRST_NAME, operation.firstName())
                    .build();
        }

        @Override
        public LdbcQuery1Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            VertexResult vertexResult = new VertexResult(record);

            long friendId = Long.parseLong(vertexResult.getVertexId()); // "others" use Long, TG uses "string". wouldn't longs be more efficient?
            String friendLastName = vertexResult.getString("friendLastName");
            int distanceFromPerson = vertexResult.getInt("distanceFromPerson");
            long friendBirthday = vertexResult.getDateTimeAsEpoch("friendBirthday");
            long friendCreationDate = vertexResult.getDateTimeAsEpoch("friendCreationDate");
            String friendGender = vertexResult.getString("friendGender");
            String friendBrowserUsed = vertexResult.getString("friendBrowserUsed");
            String friendLocationIp = vertexResult.getString("friendLocationIp");
            String friendCityName = vertexResult.getString("friendCityName");
            Iterable<String> friendEmails = vertexResult.getStringList("friendEmails");
            Iterable<String> friendLanguages = vertexResult.getStringList("friendEmails");
            List<Object> univs = vertexResult.getObjectList("friendUniversities");
            Iterable<List<Object>> universities = ResultConverter.toOrgList(univs);
            // XXX TODO: when ready, test the toOrgListNew method with the following change (sema goes for companies)
            //  List<List> comps = Collections.singletonList(vertexResult.getObjectList("friendCompanies"));
            List<Object> comps = vertexResult.getObjectList("friendCompanies");
            Iterable<List<Object>> companies = ResultConverter.toOrgList(comps);

            return new LdbcQuery1Result(friendId, friendLastName, distanceFromPerson, friendBirthday, friendCreationDate,
                    friendGender, friendBrowserUsed, friendLocationIp, friendEmails, friendLanguages, friendCityName, universities, companies);
        }
    }

    public static class InteractiveQuery2 extends TygrysekListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex2";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery2 operation = (LdbcQuery2) o;
            String maxDate = dateFormat.format(operation.maxDate().getTime());

            return ImmutableMap.<String, String>builder().put(LdbcQuery2.PERSON_ID, Long.toString(operation.personId())).put(LdbcQuery2.MAX_DATE, maxDate).build();
        }

        @Override
        public LdbcQuery2Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long personId = (Long) record.get("personId");
            Long messageId = (Long) record.get("messageId");

            String personFirstName = (String) record.get("personFirstName");
            String messageCreationDate = (String) record.get("messageCreationDate");
            String personLastName = (String) record.get("personLastName");
            String messageContent = (String) record.get("messageContent");

            Date aDate = dateFormat.parse(messageCreationDate);

            return new LdbcQuery2Result(personId, personFirstName, personLastName, messageId, messageContent, aDate.getTime());
        }
    }

    public static class InteractiveQuery3 extends TygrysekListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex3";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery3 operation = (LdbcQuery3) o;
            String startDate = dateFormat.format(operation.startDate().getTime());

            return ImmutableMap.<String, String>builder().put(LdbcQuery3.PERSON_ID, Long.toString(operation.personId())).put(LdbcQuery3.COUNTRY_X_NAME, operation.countryXName()).put(LdbcQuery3.COUNTRY_Y_NAME, operation.countryYName()).put(LdbcQuery3.START_DATE, startDate).put(LdbcQuery3.DURATION_DAYS, Integer.toString(operation.durationDays())).build();
        }

        @Override
        public LdbcQuery3Result toResult(LinkedTreeMap<String, Object> record) {
            Long personId = (Long) record.get("personId");
            String personFirstName = (String) record.get("personFirstName");
            String personLastName = (String) record.get("personLastName");
            Long xCount = (Long) record.get("xCount");
            Long yCount = (Long) record.get("yCount");
            Long count = (Long) record.get("xyCount");

            return new LdbcQuery3Result(personId, personFirstName, personLastName, xCount, yCount, count);
        }
    }

    public static class InteractiveQuery4 extends TygrysekListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex4";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery4 operation = (LdbcQuery4) o;
            String startDate = dateFormat.format(operation.startDate().getTime());

            return ImmutableMap.<String, String>builder().put(LdbcQuery3.PERSON_ID, Long.toString(operation.personId())).put(LdbcQuery3.START_DATE, startDate).put(LdbcQuery3.DURATION_DAYS, Integer.toString(operation.durationDays())).build();
        }

        @Override
        public LdbcQuery4Result toResult(LinkedTreeMap<String, Object> record) {
            String tagName = (String) record.get("tagName");
            int postCount = Math.toIntExact((Long) record.get("postCount"));
            return new LdbcQuery4Result(tagName, postCount);
        }

    }

    public static class InteractiveQuery5 extends TygrysekListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex5";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery5 operation = (LdbcQuery5) o;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery5.PERSON_ID, Long.toString(operation.personId()))
                    .put(LdbcQuery5.MIN_DATE, dateFormat.format(operation.minDate().getTime()))
                    .build();
        }

        @Override
        public LdbcQuery5Result toResult(LinkedTreeMap<String, Object> record) {
            String forumTitle = (String) record.get("forumTitle");
            Integer postCount = Math.toIntExact((Long) record.get("postCount"));
            return new LdbcQuery5Result(forumTitle, postCount);
        }
    }

    public static class InteractiveQuery6 extends TygrysekListOperationHandler<LdbcQuery6, LdbcQuery6Result> {
        @Override
        public String getQueryName() {
            return "interactiveComplex6";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery6 operation = (LdbcQuery6) o;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery6.PERSON_ID, Long.toString(operation.personId()))
                    .put(LdbcQuery6.TAG_NAME, operation.tagName())
                    .build();
        }

        @Override
        public LdbcQuery6Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            String tagName = (String) record.get("tagName");
            Integer postCount = Math.toIntExact((Long) record.get("postCount"));

            return new LdbcQuery6Result(tagName, postCount);
        }
    }

    public static class InteractiveQuery7 extends TygrysekListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex7";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery7 operation = (LdbcQuery7) o;
            return ImmutableMap.<String, String>builder().put(LdbcQuery7.PERSON_ID, Long.toString(operation.personId())).build();
        }

        @Override
        public LdbcQuery7Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long personId = (Long) record.get("personId");
            String personFirstName = (String) record.get("personFirstName");
            String personLastName = (String) record.get("personLastName");
            String likeCreationDate = (String) record.get("likeCreationDate");
            Long messageId = (Long) record.get("messageId");
            String messageContent = (String) record.get("messageContent");
            int minutesLatency = Math.toIntExact((Long) record.get("minutesLatency"));
            boolean isNew = (boolean) record.get("isNew");

            Date aDate = dateFormat.parse(likeCreationDate);
            return new LdbcQuery7Result(personId, personFirstName, personLastName, aDate.getTime(), messageId, messageContent, minutesLatency, isNew);
        }
    }

    public static class InteractiveQuery8 extends TygrysekListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex8";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery8 operation = (LdbcQuery8) o;
            return ImmutableMap.<String, String>builder().put(LdbcQuery8.PERSON_ID, Long.toString(operation.personId())).build();
        }

        @Override
        public LdbcQuery8Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long personId = (Long) record.get("personId");
            String personFirstName = (String) record.get("personFirstName");
            String personLastName = (String) record.get("personLastName");
            Long commentId = (Long) record.get("commentId");
            String commentContent = (String) record.get("commentContent");
            String commentCreationDate = (String) record.get("commentCreationDate");
            Date aDate = dateFormat.parse(commentCreationDate);

            return new LdbcQuery8Result(personId, personFirstName, personLastName, aDate.getTime(), commentId, commentContent);
        }
    }

    public static class InteractiveQuery9 extends TygrysekListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex9";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery9 operation = (LdbcQuery9) o;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery9.PERSON_ID, Long.toString(operation.personId()))
                    .put(LdbcQuery9.MAX_DATE, dateFormat.format(operation.maxDate().getTime()))
                    .build();
        }

        @Override
        public LdbcQuery9Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long personId = (Long) record.get("personId");
            Long messageId = (Long) record.get("messageId");

            String personFirstName = (String) record.get("personFirstName");
            String messageCreationDate = (String) record.get("messageCreationDate");
            String personLastName = (String) record.get("personLastName");
            String messageContent = (String) record.get("messageContent");

            Date aDate = dateFormat.parse(messageCreationDate);
            return new LdbcQuery9Result(personId, personFirstName, personLastName, messageId, messageContent, aDate.getTime());
        }
    }

    public static class InteractiveQuery10 extends TygrysekListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex10";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery10 operation = (LdbcQuery10) o;
            return ImmutableMap.<String, String>builder().put(LdbcQuery10.PERSON_ID, Long.toString(operation.personId())).put(LdbcQuery10.MONTH, Integer.toString(operation.month())).build();
        }

        @Override
        public LdbcQuery10Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long personId = (Long) record.get("personId");
            String personFirstName = (String) record.get("personFirstName");
            String personLastName = (String) record.get("personLastName");
            String personGender = (String) record.get("personGender");
            String personCityName = (String) record.get("personCityName");
            int commonInterestScore = Math.toIntExact((Long) record.get("commonInterestScore"));

            return new LdbcQuery10Result(personId, personFirstName, personLastName, commonInterestScore, personGender, personCityName);
        }
    }

    public static class InteractiveQuery11 extends TygrysekListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex11";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery11 operation = (LdbcQuery11) o;
            return ImmutableMap.<String, String>builder().put(LdbcQuery11.PERSON_ID, Long.toString(operation.personId())).put(LdbcQuery11.COUNTRY_NAME, operation.countryName()).put(LdbcQuery11.WORK_FROM_YEAR, Integer.toString(operation.workFromYear())).build();
        }

        @Override
        public LdbcQuery11Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long personId = (Long) record.get("personId");
            String personFirstName = (String) record.get("personFirstName");
            String personLastName = (String) record.get("personLastName");
            String organizationName = (String) record.get("organizationName");
            int organizationWorkFromYear = Math.toIntExact((Long) record.get("organizationWorkFromYear"));

            return new LdbcQuery11Result(personId, personFirstName, personLastName, organizationName, organizationWorkFromYear);
        }
    }

    public static class InteractiveQuery12 extends TygrysekListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex12";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery12 operation = (LdbcQuery12) o;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery12.PERSON_ID, Long.toString(operation.personId()))
                    .put(LdbcQuery12.TAG_CLASS_NAME, operation.tagClassName())
                    .build();
        }

        @Override
        public LdbcQuery12Result toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            VertexResult vertexResult = new VertexResult(record);
            Long personId = vertexResult.getLong("personId");
            String personFirstName = vertexResult.getString("personFirstName");
            String personLastName = vertexResult.getString("personLastName");
            Integer replyCount = vertexResult.getInt("replyCount");
            Iterable<String> tagNames = vertexResult.getStringList("tagNames");

//            Long personId = (Long) record.get("personId");
//            String personFirstName = (String) record.get("personFirstName");
//            String personLastName = (String) record.get("personLastName");
//            Integer replyCount = Math.toIntExact((Long) record.get("shortestPathLength"));
//
//            List<String> tagNames = new ArrayList<>();
//            /// XXX TODO get the names!
            return new LdbcQuery12Result(personId, personFirstName, personLastName, tagNames, replyCount);
        }
    }

    // Interactive short reads

    public static class InteractiveQuery13 extends TygrysekSingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {
        @Override
        public String getQueryName() {
            return "interactiveComplex13";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery13 operation = (LdbcQuery13) o;
            return ImmutableMap.<String, String>builder().put(LdbcQuery13.PERSON1_ID, Long.toString(operation.person1Id())).put(LdbcQuery13.PERSON2_ID, Long.toString(operation.person2Id())).build();
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcQuery13 operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcQuery13Result toResult(LinkedTreeMap<String, Object> record) {
            return new LdbcQuery13Result(Math.toIntExact((Long) record.get("shortestPathLength")));
        }

    }

    public static class InteractiveQuery14 extends TygrysekListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public String getQueryName() {
            return "interactiveComplex14";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcQuery14 operation = (LdbcQuery14) o;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery14.PERSON1_ID, Long.toString(operation.person1Id()))
                    .put(LdbcQuery14.PERSON2_ID, Long.toString(operation.person2Id())).build();
        }

        @Override
        public LdbcQuery14Result toResult(LinkedTreeMap<String, Object> record) {
            int pathWeight = Math.toIntExact((Long) record.get("pathWeight"));
            Iterable personIdsInPath = (List) record.get("personIdsInPath");

            return new LdbcQuery14Result(personIdsInPath, pathWeight);
        }

    }

    public static class ShortQuery1PersonProfile extends TygrysekSingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public String getQueryName() {
            return "interactiveShort1";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery1PersonProfile operation = (LdbcShortQuery1PersonProfile) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery1.PERSON_ID, Long.toString(operation.personId()))
                    .build();
        }

        @Override
        public LdbcShortQuery1PersonProfileResult toResult(LinkedTreeMap<String, Object> record) throws ParseException {
//            VertexResult vertexResult = new VertexResult((LinkedTreeMap<String, Object>) record.get("result"));

            record = (LinkedTreeMap<String, Object>) record.get("result");
            String firstName = (String) record.get("firstName");
            String lastName = (String) record.get("lastName");
            String birthday = (String) record.get("birthday");
            String locationIP = (String) record.get("locationIP");
            String browserUsed = (String) record.get("browserUsed");
            String gender = (String) record.get("gender");
            String creationDate = (String) record.get("creationDate");
            Long cityId = (Long) record.get("cityId");

            return new LdbcShortQuery1PersonProfileResult(firstName, lastName, dateFormat.parse(birthday).getTime(),
                    locationIP, browserUsed, cityId, gender, dateFormat.parse(creationDate).getTime());
        }

    }

    public static class ShortQuery2PersonPosts extends TygrysekListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public String getQueryName() {
            return "interactiveShort2";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery2PersonPosts operation = (LdbcShortQuery2PersonPosts) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcQuery2.PERSON_ID, Long.toString(operation.personId()))
                    .build();
        }

        @Override
        public LdbcShortQuery2PersonPostsResult toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            Long messageId = (Long) record.get("messageId");

            String messageContent = (String) record.get("messageContent");
            String messageCreationDate = (String) record.get("messageCreationDate");
            Long originalPostId = (Long) record.get("originalPostId");
            Long originalPostAuthorId = (Long) record.get("originalPostAuthorId");
            String originalPostAuthorFirstName = (String) record.get("originalPostAuthorFirstName");
            String originalPostAuthorLastName = (String) record.get("originalPostAuthorLastName");

            return new LdbcShortQuery2PersonPostsResult(messageId, messageContent, dateFormat.parse(messageCreationDate).getTime(), originalPostId, originalPostAuthorId, originalPostAuthorFirstName, originalPostAuthorLastName);
        }
    }

    public static class ShortQuery3PersonFriends extends TygrysekListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public String getQueryName() {
            return "interactiveShort3";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery3PersonFriends operation = (LdbcShortQuery3PersonFriends) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcShortQuery3PersonFriends.PERSON_ID, Long.toString(operation.personId()))
                    .build();
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            VertexResult vertexResult = new VertexResult(record);
            Long personId = vertexResult.getLong("personId");
            String firstName = vertexResult.getString("firstName");
            String lastName = vertexResult.getString("lastName");
            long friendshipCreationDate = vertexResult.getDateTimeAsEpoch("friendshipCreationDate");
            return new LdbcShortQuery3PersonFriendsResult(personId, firstName, lastName, friendshipCreationDate);
        }
    }

    public static class ShortQuery4MessageContent extends TygrysekSingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {
        @Override

        public String getQueryName() {
            return "interactiveShort4";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery4MessageContent operation = (LdbcShortQuery4MessageContent) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcShortQuery4MessageContent.MESSAGE_ID, Long.toString(operation.messageId()))
                    .build();
        }

        @Override
        public LdbcShortQuery4MessageContentResult toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            record = (LinkedTreeMap<String, Object>) ((List) record.get("result")).get(0);
            VertexResult vertexResult = new VertexResult(record);
            Long messageCreationDate = vertexResult.getDateTimeAsEpoch("messageCreationDate");
            String messageContent = vertexResult.getString("messageContent");

            return new LdbcShortQuery4MessageContentResult(messageContent, messageCreationDate);
        }
    }

    public static class ShortQuery5MessageCreator extends TygrysekSingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public String getQueryName() {
            return "interactiveShort5";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery5MessageCreator operation = (LdbcShortQuery5MessageCreator) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcShortQuery5MessageCreator.MESSAGE_ID, Long.toString(operation.messageId()))
                    .build();
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult toResult(LinkedTreeMap<String, Object> record) {
            record = (LinkedTreeMap<String, Object>) ((List) record.get("result")).get(0);
            VertexResult vertexResult = new VertexResult(record);

            long personId = vertexResult.getLong("personId");
            String firstName = vertexResult.getString("firstName");
            String lastName = vertexResult.getString("lastName");
            return new LdbcShortQuery5MessageCreatorResult(personId, firstName, lastName);
        }

    }

    // Interactive inserts

    public static class ShortQuery6MessageForum extends TygrysekSingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public String getQueryName() {
            return "interactiveShort6";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery6MessageForum operation = (LdbcShortQuery6MessageForum) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcShortQuery5MessageCreator.MESSAGE_ID, Long.toString(operation.messageId()))
                    .build();
        }

        @Override
        public LdbcShortQuery6MessageForumResult toResult(LinkedTreeMap<String, Object> record) {
            record = (LinkedTreeMap<String, Object>) ((List) record.get("result")).get(0);
            VertexResult vertexResult = new VertexResult(record);
            long forumId = vertexResult.getLong("forumId");
            String forumTitle = vertexResult.getString("forumTitle");
            long moderatorId = vertexResult.getLong("moderatorId");
            String moderatorFirstName = vertexResult.getString("moderatorFirstName");
            String moderatorLastName = vertexResult.getString("moderatorLastName");
            return new LdbcShortQuery6MessageForumResult(forumId, forumTitle, moderatorId, moderatorFirstName, moderatorLastName);
        }
    }

    public static class ShortQuery7MessageReplies extends TygrysekListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public String getQueryName() {
            return "interactiveShort7";
        }

        @Override
        protected Map<String, String> constructParams(Operation o) {
            LdbcShortQuery7MessageReplies operation = (LdbcShortQuery7MessageReplies) o;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcShortQuery7MessageReplies.MESSAGE_ID, Long.toString(operation.messageId()))
                    .build();
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult toResult(LinkedTreeMap<String, Object> record) throws ParseException {
            long commentId = (Long) record.get("commentId");
            String commentContent = (String) record.get("commentContent");
            long replyAuthorId = (Long) record.get("replyAuthorId");
            String replyAuthorFirstName = (String) record.get("replyAuthorFirstName");
            String replyAuthorLastName = (String) record.get("replyAuthorLastName");
            String commentCreationDate = (String) record.get("commentCreationDate");
            boolean replyAuthorKnowsOriginalMessageAuthor = (boolean) record.get("replyAuthorKnowsOriginalMessageAuthor");


            return new LdbcShortQuery7MessageRepliesResult(commentId, commentContent, dateFormat.parse(commentCreationDate).getTime(),
                    replyAuthorId, replyAuthorFirstName, replyAuthorLastName, replyAuthorKnowsOriginalMessageAuthor);
        }
    }

    public static class Update1AddPerson extends TygrysekUpdateOperationHandler<LdbcUpdate1AddPerson> {
        @Override
        public String getQueryName() {
            return "interactiveInsert1";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate1AddPerson operation) {
            return state.getQueryStore().getUpdate1Single(operation);
        }

        @Override

        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate1AddPerson o = (LdbcUpdate1AddPerson) operation;

            ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate1AddPerson.PERSON_ID, Long.toString(o.personId()))
                    .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, o.personFirstName())
                    .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, o.personLastName())
                    .put(LdbcUpdate1AddPerson.GENDER, o.gender())
                    .put(LdbcUpdate1AddPerson.BIRTHDAY, ResultConverter.dateToEpochString(o.birthday()))
                    .put(LdbcUpdate1AddPerson.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .put(LdbcUpdate1AddPerson.LOCATION_IP, o.locationIp())
                    .put(LdbcUpdate1AddPerson.BROWSER_USED, o.browserUsed())
                    .put(LdbcUpdate1AddPerson.CITY_ID, Long.toString(o.cityId()));


            addOrgsParam(builder, o.studyAt(), LdbcUpdate1AddPerson.STUDY_AT);
            addOrgsParam(builder, o.workAt(), LdbcUpdate1AddPerson.WORK_AT);

            addStringArrayParam(o.languages(), builder, LdbcUpdate1AddPerson.LANGUAGES);
            addStringArrayParam(o.emails(), builder, LdbcUpdate1AddPerson.EMAILS);
            addLongArrayParam(o.tagIds(), builder, LdbcUpdate1AddPerson.TAG_IDS);

            return builder.build();

        }
    }

    public static class Update2AddPostLike extends TygrysekUpdateOperationHandler<LdbcUpdate2AddPostLike> {
        @Override
        public String getQueryName() {
            return "interactiveInsert2";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            return state.getQueryStore().getUpdate2(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate2AddPostLike o = (LdbcUpdate2AddPostLike) operation;

            return ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate2AddPostLike.PERSON_ID, Long.toString(o.personId()))
                    .put(LdbcUpdate2AddPostLike.POST_ID, Long.toString(o.postId()))
                    .put(LdbcUpdate2AddPostLike.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .build();
        }
    }

    public static class Update3AddCommentLike extends TygrysekUpdateOperationHandler<LdbcUpdate3AddCommentLike> {
        @Override
        public String getQueryName() {
            return "interactiveInsert3";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            return state.getQueryStore().getUpdate3(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate3AddCommentLike o = (LdbcUpdate3AddCommentLike) operation;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate3AddCommentLike.PERSON_ID, Long.toString(o.personId()))
                    .put(LdbcUpdate3AddCommentLike.COMMENT_ID, Long.toString(o.commentId()))
                    .put(LdbcUpdate3AddCommentLike.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .build();
        }
    }

    public static class Update4AddForum extends TygrysekUpdateOperationHandler<LdbcUpdate4AddForum> {
        @Override
        public String getQueryName() {
            return "interactiveInsert4";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate4AddForum operation) {
            return state.getQueryStore().getUpdate4Single(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate4AddForum o = (LdbcUpdate4AddForum) operation;
            ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate4AddForum.FORUM_ID, Long.toString(o.forumId()))
                    .put(LdbcUpdate4AddForum.FORUM_TITLE, o.forumTitle())
                    .put(LdbcUpdate4AddForum.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .put(LdbcUpdate4AddForum.MODERATOR_PERSON_ID, Long.toString(o.moderatorPersonId()));
            addLongArrayParam(o.tagIds(), builder, LdbcUpdate4AddForum.TAG_IDS);
            return builder.build();
        }
    }

    public static class Update5AddForumMembership extends TygrysekUpdateOperationHandler<LdbcUpdate5AddForumMembership> {
        @Override
        public String getQueryName() {
            return "interactiveInsert5";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            return state.getQueryStore().getUpdate5(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate5AddForumMembership o = (LdbcUpdate5AddForumMembership) operation;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate5AddForumMembership.FORUM_ID, Long.toString(o.forumId()))
                    .put(LdbcUpdate5AddForumMembership.PERSON_ID, Long.toString(o.personId()))
                    .put(LdbcUpdate5AddForumMembership.JOIN_DATE, ResultConverter.dateToEpochString(o.joinDate()))
                    .build();
        }
    }

    public static class Update6AddPost extends TygrysekUpdateOperationHandler<LdbcUpdate6AddPost> {
        @Override
        public String getQueryName() {
            return "interactiveInsert6";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate6AddPost operation) {
            return state.getQueryStore().getUpdate6Single(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate6AddPost o = (LdbcUpdate6AddPost) operation;

            ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate6AddPost.POST_ID, Long.toString(o.forumId()))
                    .put(LdbcUpdate6AddPost.IMAGE_FILE, o.imageFile())
                    .put(LdbcUpdate6AddPost.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .put(LdbcUpdate6AddPost.LOCATION_IP, o.locationIp())
                    .put(LdbcUpdate6AddPost.BROWSER_USED, o.browserUsed())
                    .put(LdbcUpdate6AddPost.LANGUAGE, o.language())
                    .put(LdbcUpdate6AddPost.CONTENT, o.content())
                    .put(LdbcUpdate6AddPost.LENGTH, Integer.toString(o.length()))
                    .put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, Long.toString(o.authorPersonId()))
                    .put(LdbcUpdate6AddPost.FORUM_ID, Long.toString(o.forumId()))
                    .put(LdbcUpdate6AddPost.COUNTRY_ID, Long.toString(o.countryId()));

            addLongArrayParam(o.tagIds(), builder, LdbcUpdate6AddPost.TAG_IDS);
            return builder.build();
        }
    }

    public static class Update7AddComment extends TygrysekUpdateOperationHandler<LdbcUpdate7AddComment> {
        @Override
        public String getQueryName() {
            return "interactiveInsert7";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate7AddComment operation) {
            return state.getQueryStore().getUpdate7Single(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate7AddComment o = (LdbcUpdate7AddComment) operation;

            ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate7AddComment.COMMENT_ID, Long.toString(o.commentId()))
                    .put(LdbcUpdate7AddComment.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .put(LdbcUpdate7AddComment.LOCATION_IP, o.locationIp())
                    .put(LdbcUpdate7AddComment.BROWSER_USED, o.browserUsed())
                    .put(LdbcUpdate7AddComment.CONTENT, o.content())
                    .put(LdbcUpdate7AddComment.LENGTH, Integer.toString(o.length()))
                    .put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, Long.toString(o.authorPersonId()))
                    .put(LdbcUpdate7AddComment.COUNTRY_ID, Long.toString(o.countryId()))
                    .put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, Long.toString(o.replyToPostId()))
                    .put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, Long.toString(o.replyToCommentId()));

            addLongArrayParam(o.tagIds(), builder, LdbcUpdate6AddPost.TAG_IDS);
            return builder.build();
        }
    }

    public static class Update8AddFriendship extends TygrysekUpdateOperationHandler<LdbcUpdate8AddFriendship> {
        @Override
        public String getQueryName() {
            return "interactiveInsert8";
        }

        @Override
        public String getQueryString(TygrysekDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            return state.getQueryStore().getUpdate8(operation);
        }

        @Override
        protected Map<String, String> constructParams(Operation operation) {
            LdbcUpdate8AddFriendship o = (LdbcUpdate8AddFriendship) operation;
            return ImmutableMap.<String, String>builder()
                    .put(LdbcUpdate8AddFriendship.PERSON1_ID, Long.toString(o.person1Id()))
                    .put(LdbcUpdate8AddFriendship.PERSON2_ID, Long.toString(o.person2Id()))
                    .put(LdbcUpdate8AddFriendship.CREATION_DATE, ResultConverter.dateToEpochString(o.creationDate()))
                    .build();
        }
    }

}
