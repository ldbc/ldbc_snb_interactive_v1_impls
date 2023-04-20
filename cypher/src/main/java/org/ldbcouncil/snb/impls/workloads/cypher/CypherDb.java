package org.ldbcouncil.snb.impls.workloads.cypher;

import com.google.common.collect.ImmutableMap;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.cypher.converter.CypherConverter;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherUpdateOperationHandler;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

public class CypherDb extends BaseDb<CypherQueryStore>
{

    CypherQueryStore queryStore;

    @Override
    protected void onInit( Map<String, String> properties, LoggingService loggingService ) throws DbException
    {

        dcs = new CypherDbConnectionState<>(properties, new CypherQueryStore(properties.get("queryDir")));
        queryStore = new CypherQueryStore( properties.get( "queryDir" ) );
    }

    // Interactive complex reads

    public static class InteractiveQuery1 extends CypherListOperationHandler<LdbcQuery1,LdbcQuery1Result>
    {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery1);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery1 operation) {
            return state.getQueryStore().getQuery1Map(operation);
        }

        @Override
        public LdbcQuery1Result toResult( Record record ) throws ParseException
        {

            List<String> emails;
            if ( !record.get( 8 ).isNull() )
            {
                emails = record.get( 8 ).asList( Value::asString );
            }
            else
            {
                emails = new ArrayList<>();
            }

            List<String> languages;
            if ( !record.get( 9 ).isNull() )
            {
                languages = record.get( 9 ).asList( Value::asString );
            }
            else
            {
                languages = new ArrayList<>();
            }

            List<LdbcQuery1Result.Organization> universities;
            if ( !record.get( 11 ).isNull() )
            {
                universities = CypherConverter.asOrganization(record.get( 11 ).asList( Value::asList ));
            }
            else
            {
                universities = new ArrayList<>();
            }

            List<LdbcQuery1Result.Organization> companies;
            if ( !record.get( 12 ).isNull() )
            {
                companies = CypherConverter.asOrganization(record.get( 12 ).asList( Value::asList ));
            }
            else
            {
                companies = new ArrayList<>();
            }

            long friendId = record.get( 0 ).asLong();
            String friendLastName = record.get( 1 ).asString();
            int distanceFromPerson = record.get( 2 ).asInt();
            long friendBirthday = record.get( 3 ).asLong();
            long friendCreationDate = record.get( 4 ).asLong();
            String friendGender = record.get( 5 ).asString();
            String friendBrowserUsed = record.get( 6 ).asString();
            String friendLocationIp = record.get( 7 ).asString();
            String friendCityName = record.get( 10 ).asString();
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
                    companies );
        }
    }

    public static class InteractiveQuery2 extends CypherListOperationHandler<LdbcQuery2,LdbcQuery2Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery2);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery2 operation) {
            return state.getQueryStore().getQuery2Map(operation);
        }

        @Override
        public LdbcQuery2Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            long messageId = record.get( 3 ).asLong();
            String messageContent = record.get( 4 ).asString();
            long messageCreationDate = record.get( 5 ).asLong();

            return new LdbcQuery2Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate );
        }
    }

    public static class InteractiveQuery3a extends CypherListOperationHandler<LdbcQuery3a, LdbcQuery3Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery3a operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery3);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery3a operation) {
            return state.getQueryStore().getQuery3Map(operation);
        }

        @Override
        public LdbcQuery3Result toResult( Record record )
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            int xCount = record.get( 3 ).asInt();
            int yCount = record.get( 4 ).asInt();
            int count = record.get( 5 ).asInt();
            return new LdbcQuery3Result(
                    personId,
                    personFirstName,
                    personLastName,
                    xCount,
                    yCount,
                    count );
        }
    }

    public static class InteractiveQuery3b extends CypherListOperationHandler<LdbcQuery3b, LdbcQuery3Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery3b operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery3);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery3b operation) {
            return state.getQueryStore().getQuery3Map(operation);
        }

        @Override
        public LdbcQuery3Result toResult( Record record )
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            int xCount = record.get( 3 ).asInt();
            int yCount = record.get( 4 ).asInt();
            int count = record.get( 5 ).asInt();
            return new LdbcQuery3Result(
                    personId,
                    personFirstName,
                    personLastName,
                    xCount,
                    yCount,
                    count );
        }
    }

    public static class InteractiveQuery4 extends CypherListOperationHandler<LdbcQuery4,LdbcQuery4Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery4);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery4 operation) {
            return state.getQueryStore().getQuery4Map(operation);
        }

        @Override
        public LdbcQuery4Result toResult( Record record )
        {
            String tagName = record.get( 0 ).asString();
            int postCount = record.get( 1 ).asInt();
            return new LdbcQuery4Result( tagName, postCount );
        }
    }

    public static class InteractiveQuery5 extends CypherListOperationHandler<LdbcQuery5,LdbcQuery5Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery5);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery5 operation) {
            return state.getQueryStore().getQuery5Map(operation);
        }

        @Override
        public LdbcQuery5Result toResult( Record record )
        {
            String forumTitle = record.get( 0 ).asString();
            int postCount = record.get( 1 ).asInt();
            return new LdbcQuery5Result( forumTitle, postCount );
        }
    }

    public static class InteractiveQuery6 extends CypherListOperationHandler<LdbcQuery6,LdbcQuery6Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery6);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery6 operation) {
            return state.getQueryStore().getQuery6Map(operation);
        }

        @Override
        public LdbcQuery6Result toResult( Record record )
        {
            String tagName = record.get( 0 ).asString();
            int postCount = record.get( 1 ).asInt();
            return new LdbcQuery6Result( tagName, postCount );
        }
    }

    public static class InteractiveQuery7 extends CypherListOperationHandler<LdbcQuery7,LdbcQuery7Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery7);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery7 operation) {
            return state.getQueryStore().getQuery7Map(operation);
        }

        @Override
        public LdbcQuery7Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            long likeCreationDate = record.get( 3 ).asLong();
            long messageId = record.get( 4 ).asLong();
            String messageContent = record.get( 5 ).asString();
            int minutesLatency = record.get( 6 ).asInt();
            boolean isNew = record.get( 7 ).asBoolean();
            return new LdbcQuery7Result(
                    personId,
                    personFirstName,
                    personLastName,
                    likeCreationDate,
                    messageId,
                    messageContent,
                    minutesLatency,
                    isNew );
        }
    }

    public static class InteractiveQuery8 extends CypherListOperationHandler<LdbcQuery8,LdbcQuery8Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery8);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery8 operation) {
            return state.getQueryStore().getQuery8Map(operation);
        }

        @Override
        public LdbcQuery8Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            long commentCreationDate = record.get( 3 ).asLong();
            long commentId = record.get( 4 ).asLong();
            String commentContent = record.get( 5 ).asString();
            return new LdbcQuery8Result(
                    personId,
                    personFirstName,
                    personLastName,
                    commentCreationDate,
                    commentId,
                    commentContent );
        }
    }

    public static class InteractiveQuery9 extends CypherListOperationHandler<LdbcQuery9,LdbcQuery9Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery9);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery9 operation) {
            return state.getQueryStore().getQuery9Map(operation);
        }

        @Override
        public LdbcQuery9Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            long messageId = record.get( 3 ).asLong();
            String messageContent = record.get( 4 ).asString();
            long messageCreationDate = record.get( 5 ).asLong();
            return new LdbcQuery9Result(
                    personId,
                    personFirstName,
                    personLastName,
                    messageId,
                    messageContent,
                    messageCreationDate );
        }
    }

    public static class InteractiveQuery10 extends CypherListOperationHandler<LdbcQuery10,LdbcQuery10Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery10);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery10 operation) {
            return state.getQueryStore().getQuery10Map(operation);
        }

        @Override
        public LdbcQuery10Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            int commonInterestScore = record.get( 3 ).asInt();
            String personGender = record.get( 4 ).asString();
            String personCityName = record.get( 5 ).asString();
            return new LdbcQuery10Result(
                    personId,
                    personFirstName,
                    personLastName,
                    commonInterestScore,
                    personGender,
                    personCityName );
        }
    }

    public static class InteractiveQuery11 extends CypherListOperationHandler<LdbcQuery11,LdbcQuery11Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery11);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery11 operation) {
            return state.getQueryStore().getQuery11Map(operation);
        }

        @Override
        public LdbcQuery11Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            String organizationName = record.get( 3 ).asString();
            int organizationWorkFromYear = record.get( 4 ).asInt();
            return new LdbcQuery11Result(
                    personId,
                    personFirstName,
                    personLastName,
                    organizationName,
                    organizationWorkFromYear );
        }
    }

    public static class InteractiveQuery12 extends CypherListOperationHandler<LdbcQuery12,LdbcQuery12Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery12);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery12 operation) {
            return state.getQueryStore().getQuery12Map(operation);
        }

        @Override
        public LdbcQuery12Result toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String personFirstName = record.get( 1 ).asString();
            String personLastName = record.get( 2 ).asString();
            List<String> tagNames = new ArrayList<>();
            if ( !record.get( 3 ).isNull() )
            {
                tagNames = record.get( 3 ).asList( Value::asString );
            }
            int replyCount = record.get( 4 ).asInt();
            return new LdbcQuery12Result(
                    personId,
                    personFirstName,
                    personLastName,
                    tagNames,
                    replyCount );
        }
    }

    public static class InteractiveQuery13a extends CypherSingletonOperationHandler<LdbcQuery13a, LdbcQuery13Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery13a operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery13);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery13a operation) {
            return state.getQueryStore().getQuery13Map(operation);
        }

        @Override
        public LdbcQuery13Result toResult( Record record )
        {
            if(record != null)
            {
                return new LdbcQuery13Result( record.get( 0 ).asInt() );
            }
            else
            {
                return new LdbcQuery13Result(-1);
            }
        }
    }

    public static class InteractiveQuery13b extends CypherSingletonOperationHandler<LdbcQuery13b,LdbcQuery13Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery13b operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery13);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery13b operation) {
            return state.getQueryStore().getQuery13Map(operation);
        }

        @Override
        public LdbcQuery13Result toResult( Record record )
        {
            if(record != null)
            {
                return new LdbcQuery13Result( record.get( 0 ).asInt() );
            }
            else
            {
                return new LdbcQuery13Result(-1);
            }
        }
    }

    public static class InteractiveQuery14a extends CypherListOperationHandler<LdbcQuery14a, LdbcQuery14Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery14a operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery14);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery14a operation) {
            return state.getQueryStore().getQuery14Map(operation);
        }

        @Override
        public LdbcQuery14Result toResult( Record record ) throws ParseException
        {
            List<Long> personIdsInPath = new ArrayList<>();
            if ( !record.get( 0 ).isNull() )
            {
                personIdsInPath = record.get( 0 ).asList( Value::asLong );
            }
            long pathWeight = record.get( 1 ).asLong();
            return new LdbcQuery14Result(
                    personIdsInPath,
                    pathWeight );
        }
    }

    public static class InteractiveQuery14b extends CypherListOperationHandler<LdbcQuery14b, LdbcQuery14Result>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcQuery14b operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveComplexQuery14);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcQuery14b operation) {
            return state.getQueryStore().getQuery14Map(operation);
        }

        @Override
        public LdbcQuery14Result toResult( Record record ) throws ParseException
        {
            List<Long> personIdsInPath = new ArrayList<>();
            if ( !record.get( 0 ).isNull() )
            {
                personIdsInPath = record.get( 0 ).asList( Value::asLong );
            }
            long pathWeight = record.get( 1 ).asLong();
            return new LdbcQuery14Result(
                    personIdsInPath,
                    pathWeight );
        }
    }

    // Interactive short reads
    public static class ShortQuery1PersonProfile extends CypherSingletonOperationHandler<LdbcShortQuery1PersonProfile,LdbcShortQuery1PersonProfileResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery1);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery1PersonProfile operation) {
            return state.getQueryStore().getShortQuery1PersonProfileMap(operation);
        }

        @Override
        public LdbcShortQuery1PersonProfileResult toResult( Record record ) throws ParseException
        {
            if (record != null){
                String firstName = record.get( 0 ).asString();
                String lastName = record.get( 1 ).asString();
                long birthday = record.get( 2 ).asLong();
                String locationIP = record.get( 3 ).asString();
                String browserUsed = record.get( 4 ).asString();
                long cityId = record.get( 5 ).asLong();
                String gender = record.get( 6 ).asString();
                long creationDate = record.get( 7 ).asLong();
                return new LdbcShortQuery1PersonProfileResult(
                        firstName,
                        lastName,
                        birthday,
                        locationIP,
                        browserUsed,
                        cityId,
                        gender,
                        creationDate );
            }
            else
            {
                return null;
            }

        }
    }

    public static class ShortQuery2PersonPosts extends CypherListOperationHandler<LdbcShortQuery2PersonPosts,LdbcShortQuery2PersonPostsResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery2);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery2PersonPosts operation) {
            return state.getQueryStore().getShortQuery2PersonPostsMap(operation);
        }

        @Override
        public LdbcShortQuery2PersonPostsResult toResult( Record record ) throws ParseException
        {
            if (record != null){
                long messageId = record.get( 0 ).asLong();
                String messageContent = record.get( 1 ).asString();
                long messageCreationDate = record.get( 2 ).asLong();
                long originalPostId = record.get( 3 ).asLong();
                long originalPostAuthorId = record.get( 4 ).asLong();
                String originalPostAuthorFirstName = record.get( 5 ).asString();
                String originalPostAuthorLastName = record.get( 6 ).asString();
                return new LdbcShortQuery2PersonPostsResult(
                        messageId,
                        messageContent,
                        messageCreationDate,
                        originalPostId,
                        originalPostAuthorId,
                        originalPostAuthorFirstName,
                        originalPostAuthorLastName );
            }
            else
            {
                return null;
            }
        }
    }

    public static class ShortQuery3PersonFriends extends CypherListOperationHandler<LdbcShortQuery3PersonFriends,LdbcShortQuery3PersonFriendsResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery3);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery3PersonFriends operation) {
            return state.getQueryStore().getShortQuery3PersonFriendsMap(operation);
        }

        @Override
        public LdbcShortQuery3PersonFriendsResult toResult( Record record ) throws ParseException
        {
            long personId = record.get( 0 ).asLong();
            String firstName = record.get( 1 ).asString();
            String lastName = record.get( 2 ).asString();
            long friendshipCreationDate = record.get( 3 ).asLong();
            return new LdbcShortQuery3PersonFriendsResult(
                    personId,
                    firstName,
                    lastName,
                    friendshipCreationDate );
        }
    }

    public static class ShortQuery4MessageContent extends CypherSingletonOperationHandler<LdbcShortQuery4MessageContent,LdbcShortQuery4MessageContentResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery4);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery4MessageContent operation) {
            return state.getQueryStore().getShortQuery4MessageContentMap(operation);
        }

        @Override
        public LdbcShortQuery4MessageContentResult toResult( Record record ) throws ParseException
        {
            if (record != null){
            // Pay attention, the spec's and the implementation's parameter orders are different.
            long messageCreationDate = record.get( 0 ).asLong();
            String messageContent = record.get( 1 ).asString();
            return new LdbcShortQuery4MessageContentResult(
                    messageContent,
                    messageCreationDate );
            }
            else{
                return null;
            }

        }
    }

    public static class ShortQuery5MessageCreator extends CypherSingletonOperationHandler<LdbcShortQuery5MessageCreator,LdbcShortQuery5MessageCreatorResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery5);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery5MessageCreator operation) {
            return state.getQueryStore().getShortQuery5MessageCreatorMap(operation);
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult toResult( Record record )
        {
            if (record != null){
                long personId = record.get( 0 ).asLong();
                String firstName = record.get( 1 ).asString();
                String lastName = record.get( 2 ).asString();
                return new LdbcShortQuery5MessageCreatorResult(
                        personId,
                        firstName,
                        lastName );
            }
            else
            {
                return null;
            }

        }
    }

    public static class ShortQuery6MessageForum extends CypherSingletonOperationHandler<LdbcShortQuery6MessageForum,LdbcShortQuery6MessageForumResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery6);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery6MessageForum operation) {
            return state.getQueryStore().getShortQuery6MessageForumMap(operation);
        }

        @Override
        public LdbcShortQuery6MessageForumResult toResult( Record record )
        {
            if (record != null)
            {

                long forumId = record.get( 0 ).asLong();
                String forumTitle = record.get( 1 ).asString();
                long moderatorId = record.get( 2 ).asLong();
                String moderatorFirstName = record.get( 3 ).asString();
                String moderatorLastName = record.get( 4 ).asString();
                return new LdbcShortQuery6MessageForumResult(
                        forumId,
                        forumTitle,
                        moderatorId,
                        moderatorFirstName,
                        moderatorLastName );
            }
            else
            {
                return null;
            }
        }
    }

    public static class ShortQuery7MessageReplies extends CypherListOperationHandler<LdbcShortQuery7MessageReplies,LdbcShortQuery7MessageRepliesResult>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveShortQuery7);
        }

        @Override
        public Map<String, Object> getParameters(CypherDbConnectionState state, LdbcShortQuery7MessageReplies operation) {
            return state.getQueryStore().getShortQuery7MessageRepliesMap(operation);
        }

        @Override
        public LdbcShortQuery7MessageRepliesResult toResult( Record record ) throws ParseException
        {
            long commentId = record.get( 0 ).asLong();
            String commentContent = record.get( 1 ).asString();
            long commentCreationDate = record.get( 2 ).asLong();
            long replyAuthorId = record.get( 3 ).asLong();
            String replyAuthorFirstName = record.get( 4 ).asString();
            String replyAuthorLastName = record.get( 5 ).asString();
            boolean replyAuthorKnowsOriginalMessageAuthor = record.get( 6 ).asBoolean();
            return new LdbcShortQuery7MessageRepliesResult(
                    commentId,
                    commentContent,
                    commentCreationDate,
                    replyAuthorId,
                    replyAuthorFirstName,
                    replyAuthorLastName,
                    replyAuthorKnowsOriginalMessageAuthor );
        }
    }

    // Interactive inserts

    public static class Insert1AddPerson extends CypherUpdateOperationHandler<LdbcInsert1AddPerson>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert1AddPerson operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert1);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert1AddPerson operation )
        {
            final List<List<Long>> universities =
                    operation.getStudyAt().stream().map( u -> Arrays.asList( u.getOrganizationId(), (long) u.getYear() ) ).collect( Collectors.toList() );
            final List<List<Long>> companies =
                    operation.getWorkAt().stream().map( c -> Arrays.asList( c.getOrganizationId(), (long) c.getYear() ) ).collect( Collectors.toList() );

            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert1AddPerson.PERSON_ID, operation.getPersonId() )
                               .put( LdbcInsert1AddPerson.PERSON_FIRST_NAME, operation.getPersonFirstName() )
                               .put( LdbcInsert1AddPerson.PERSON_LAST_NAME, operation.getPersonLastName() )
                               .put( LdbcInsert1AddPerson.GENDER, operation.getGender() )
                               .put( LdbcInsert1AddPerson.BIRTHDAY, operation.getBirthday().getTime() )
                               .put( LdbcInsert1AddPerson.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcInsert1AddPerson.LOCATION_IP, operation.getLocationIp() )
                               .put( LdbcInsert1AddPerson.BROWSER_USED, operation.getBrowserUsed() )
                               .put( LdbcInsert1AddPerson.CITY_ID, operation.getCityId() )
                               .put( LdbcInsert1AddPerson.LANGUAGES, operation.getLanguages() )
                               .put( LdbcInsert1AddPerson.EMAILS, operation.getEmails() )
                               .put( LdbcInsert1AddPerson.TAG_IDS, operation.getTagIds() )
                               .put( LdbcInsert1AddPerson.STUDY_AT, universities )
                               .put( LdbcInsert1AddPerson.WORK_AT, companies )
                               .build();
        }
    }

    public static class Insert2AddPostLike extends CypherUpdateOperationHandler<LdbcInsert2AddPostLike>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert2AddPostLike operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert2);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert2AddPostLike operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert2AddPostLike.PERSON_ID, operation.getPersonId() )
                               .put( LdbcInsert2AddPostLike.POST_ID, operation.getPostId() )
                               .put( LdbcInsert2AddPostLike.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }

    public static class Insert3AddCommentLike extends CypherUpdateOperationHandler<LdbcInsert3AddCommentLike>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert3AddCommentLike operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert3);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert3AddCommentLike operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert3AddCommentLike.PERSON_ID, operation.getPersonId() )
                               .put( LdbcInsert3AddCommentLike.COMMENT_ID, operation.getCommentId() )
                               .put( LdbcInsert3AddCommentLike.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }

    public static class Insert4AddForum extends CypherUpdateOperationHandler<LdbcInsert4AddForum>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert4AddForum operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert4);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert4AddForum operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert4AddForum.FORUM_ID, operation.getForumId() )
                               .put( LdbcInsert4AddForum.FORUM_TITLE, operation.getForumTitle() )
                               .put( LdbcInsert4AddForum.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcInsert4AddForum.MODERATOR_PERSON_ID, operation.getModeratorPersonId() )
                               .put( LdbcInsert4AddForum.TAG_IDS, operation.getTagIds() )
                               .build();
        }
    }

    public static class Insert5AddForumMembership extends CypherUpdateOperationHandler<LdbcInsert5AddForumMembership>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert5AddForumMembership operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert5);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert5AddForumMembership operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert5AddForumMembership.FORUM_ID, operation.getForumId() )
                               .put( LdbcInsert5AddForumMembership.PERSON_ID, operation.getPersonId() )
                               .put( LdbcInsert5AddForumMembership.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }

    public static class Insert6AddPost extends CypherUpdateOperationHandler<LdbcInsert6AddPost>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert6AddPost operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert6);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert6AddPost operation )
        {
            final HashMap<String, Object> parameterMap = new HashMap<>();
            parameterMap.put(LdbcInsert6AddPost.POST_ID, operation.getPostId());
            parameterMap.put(LdbcInsert6AddPost.IMAGE_FILE, operation.getImageFile());
            parameterMap.put(LdbcInsert6AddPost.CREATION_DATE, operation.getCreationDate().getTime());
            parameterMap.put(LdbcInsert6AddPost.LOCATION_IP, operation.getLocationIp());
            parameterMap.put(LdbcInsert6AddPost.BROWSER_USED, operation.getBrowserUsed());
            parameterMap.put(LdbcInsert6AddPost.LANGUAGE, operation.getLanguage());
            parameterMap.put(LdbcInsert6AddPost.CONTENT, operation.getContent());
            parameterMap.put(LdbcInsert6AddPost.LENGTH, operation.getLength());
            parameterMap.put(LdbcInsert6AddPost.AUTHOR_PERSON_ID, operation.getAuthorPersonId());
            parameterMap.put(LdbcInsert6AddPost.FORUM_ID, operation.getForumId());
            parameterMap.put(LdbcInsert6AddPost.COUNTRY_ID, operation.getCountryId());
            parameterMap.put(LdbcInsert6AddPost.TAG_IDS, operation.getTagIds());
            return parameterMap;
        }
    }

    public static class Insert7AddComment extends CypherUpdateOperationHandler<LdbcInsert7AddComment>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert7AddComment operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert7);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert7AddComment operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert7AddComment.COMMENT_ID, operation.getCommentId() )
                               .put( LdbcInsert7AddComment.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcInsert7AddComment.LOCATION_IP, operation.getLocationIp() )
                               .put( LdbcInsert7AddComment.BROWSER_USED, operation.getBrowserUsed() )
                               .put( LdbcInsert7AddComment.CONTENT, operation.getContent() )
                               .put( LdbcInsert7AddComment.LENGTH, operation.getLength() )
                               .put( LdbcInsert7AddComment.AUTHOR_PERSON_ID, operation.getAuthorPersonId() )
                               .put( LdbcInsert7AddComment.COUNTRY_ID, operation.getCountryId() )
                               .put( LdbcInsert7AddComment.REPLY_TO_POST_ID, operation.getReplyToPostId() )
                               .put( LdbcInsert7AddComment.REPLY_TO_COMMENT_ID, operation.getReplyToCommentId() )
                               .put( LdbcInsert7AddComment.TAG_IDS, operation.getTagIds() )
                               .build();
        }
    }

    public static class Insert8AddFriendship extends CypherUpdateOperationHandler<LdbcInsert8AddFriendship>
    {
        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcInsert8AddFriendship operation) {
            return state.getQueryStore().getParameterizedQuery(QueryType.InteractiveInsert8);
        }

        @Override
        public Map<String, Object> getParameters( LdbcInsert8AddFriendship operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcInsert8AddFriendship.PERSON1_ID, operation.getPerson1Id() )
                               .put( LdbcInsert8AddFriendship.PERSON2_ID, operation.getPerson2Id() )
                               .put( LdbcInsert8AddFriendship.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }

    // Deletions
    public static class Delete1RemovePerson extends CypherUpdateOperationHandler<LdbcDelete1RemovePerson> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete1RemovePerson operation) {
            return state.getQueryStore().getDelete1(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete1RemovePerson operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete1RemovePerson.PERSON_ID, operation.getremovePersonIdD1() )
                    .build();
        }
    }

    public static class Delete2RemovePostLike extends CypherUpdateOperationHandler<LdbcDelete2RemovePostLike> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete2RemovePostLike operation) {
            return state.getQueryStore().getDelete2(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete2RemovePostLike operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete2RemovePostLike.PERSON_ID, operation.getremovePersonIdD2() )
                    .put( LdbcDelete2RemovePostLike.POST_ID, operation.getremovePostIdD2() )
                    .build();
        }
    }

    public static class Delete3RemoveCommentLike extends CypherUpdateOperationHandler<LdbcDelete3RemoveCommentLike> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete3RemoveCommentLike operation) {
            return state.getQueryStore().getDelete3(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete3RemoveCommentLike operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete3RemoveCommentLike.PERSON_ID, operation.getremovePersonIdD3() )
                    .put( LdbcDelete3RemoveCommentLike.COMMENT_ID, operation.getremoveCommentIdD3() )
                    .build();
        }
    }

    public static class Delete4RemoveForum extends CypherUpdateOperationHandler<LdbcDelete4RemoveForum> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete4RemoveForum operation) {
            return state.getQueryStore().getDelete4(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete4RemoveForum operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete4RemoveForum.FORUM_ID, operation.getremoveForumIdD4() )
                    .build();
        }
    }

    public static class Delete5RemoveForumMembership extends CypherUpdateOperationHandler<LdbcDelete5RemoveForumMembership> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete5RemoveForumMembership operation) {
            return state.getQueryStore().getDelete5(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete5RemoveForumMembership operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete5RemoveForumMembership.PERSON_ID, operation.getremovePersonIdD5() )
                    .put( LdbcDelete5RemoveForumMembership.FORUM_ID, operation.getremoveForumIdD5() )
                    .build();
        }
    }

    public static class Delete6RemovePostThread extends CypherUpdateOperationHandler<LdbcDelete6RemovePostThread> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete6RemovePostThread operation) {
            return state.getQueryStore().getDelete6(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete6RemovePostThread operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete6RemovePostThread.POST_ID, operation.getremovePostIdD6() )
                    .build();
        }
    }

    public static class Delete7RemoveCommentSubthread extends CypherUpdateOperationHandler<LdbcDelete7RemoveCommentSubthread> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete7RemoveCommentSubthread operation) {
            return state.getQueryStore().getDelete7(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete7RemoveCommentSubthread operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete7RemoveCommentSubthread.COMMENT_ID, operation.getremoveCommentIdD7() )
                    .build();
        }
    }

    public static class Delete8RemoveFriendship extends CypherUpdateOperationHandler<LdbcDelete8RemoveFriendship> {

        @Override
        public String getQueryString(CypherDbConnectionState state, LdbcDelete8RemoveFriendship operation) {
            return state.getQueryStore().getDelete8(operation);
        }

        @Override
        public Map<String, Object> getParameters(LdbcDelete8RemoveFriendship operation) {
            return ImmutableMap.<String, Object>builder()
                    .put( LdbcDelete8RemoveFriendship.PERSON1_ID, operation.getremovePerson1Id() )
                    .put( LdbcDelete8RemoveFriendship.PERSON2_ID, operation.getremovePerson2Id() )
                    .build();
        }
    }

}
