package org.ldbcouncil.snb.impls.workloads.cypher;

import com.google.common.collect.ImmutableMap;
import org.ldbcouncil.snb.driver.Db;
import org.ldbcouncil.snb.driver.DbConnectionState;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery1;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery10;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery10Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery11;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery11Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery12;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery12Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery13Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery14;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery14Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery1Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery2;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery2Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery3;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery3Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery4;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery4Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery5;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery5Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery6;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery6Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery7;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery7Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery8;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery8Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery9;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery9Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery1PersonProfile;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery1PersonProfileResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery2PersonPosts;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery2PersonPostsResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery3PersonFriends;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery3PersonFriendsResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery4MessageContent;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery4MessageContentResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery5MessageCreator;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery5MessageCreatorResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery6MessageForum;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery6MessageForumResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery7MessageReplies;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery7MessageRepliesResult;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate2AddPostLike;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate3AddCommentLike;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate4AddForum;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate5AddForumMembership;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate6AddPost;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate7AddComment;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate8AddFriendship;
import org.ldbcouncil.snb.impls.workloads.cypher.converter.CypherConverter;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherIC13OperationHandler;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherSingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers.CypherUpdateOperationHandler;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;

public class CypherDb extends Db
{

    Driver driver;
    CypherQueryStore queryStore;

    static protected Date addDays( Date startDate, int days )
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( Calendar.DATE, days );
        return cal.getTime();
    }

    static protected Date addMonths( Date startDate, int months )
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( Calendar.MONTH, months );
        return cal.getTime();
    }

    @Override
    protected void onInit( Map<String, String> properties, LoggingService loggingService ) throws DbException
    {
        final String endpointURI = properties.get( "endpoint" );
        final String username = properties.get( "user" );
        final String password = properties.get( "password" );

        driver = GraphDatabase.driver( endpointURI, AuthTokens.basic( username, password ) );
        queryStore = new CypherQueryStore( properties.get( "queryDir" ) );
    }

    @Override
    protected void onClose() throws IOException
    {
        driver.close();
    }

    @Override
    protected DbConnectionState getConnectionState() throws DbException
    {
        return new CypherDbConnectionState( driver, queryStore );
    }

    // Interactive complex reads

    public static class InteractiveQuery1 extends CypherListOperationHandler<LdbcQuery1,LdbcQuery1Result>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-complex-1";
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
        public String getQueryFile()
        {
            return "interactive-complex-2";
        }

        @Override
        public Map<String, Object> getParameters( LdbcQuery2 operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcQuery2.PERSON_ID, operation.getPersonIdQ2() )
                               .put( LdbcQuery2.MAX_DATE, operation.getMaxDate().getTime() )
                               .put( LdbcQuery2.LIMIT, operation.getLimit() )
                               .build();
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

    public static class InteractiveQuery3 extends CypherListOperationHandler<LdbcQuery3,LdbcQuery3Result>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-complex-3";
        }

        @Override
        public Map<String, Object> getParameters( LdbcQuery3 operation )
        {
            final Date endDate = addDays( operation.getStartDate(), operation.getDurationDays() );
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcQuery3.PERSON_ID, operation.getPersonIdQ3() )
                               .put( LdbcQuery3.COUNTRY_X_NAME, operation.getCountryXName() )
                               .put( LdbcQuery3.COUNTRY_Y_NAME, operation.getCountryYName() )
                               .put( LdbcQuery3.START_DATE, operation.getStartDate().getTime() )
                               .put( "endDate", endDate.getTime() )
                               .put( LdbcQuery3.LIMIT, operation.getLimit() )
                               .build();
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
        public String getQueryFile()
        {
            return "interactive-complex-4";
        }

        @Override
        public Map<String, Object> getParameters( LdbcQuery4 operation )
        {
            final Date endDate = addDays( operation.getStartDate(), operation.getDurationDays() );
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcQuery4.PERSON_ID, operation.getPersonIdQ4() )
                               .put( LdbcQuery4.START_DATE, operation.getStartDate().getTime() )
                               .put( "endDate", endDate.getTime() )
                               .put( LdbcQuery4.LIMIT, operation.getLimit() )
                               .build();
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
        public String getQueryFile()
        {
            return "interactive-complex-5";
        }

        @Override
        public Map<String, Object> getParameters( LdbcQuery5 operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcQuery5.PERSON_ID, operation.getPersonIdQ5() )
                               .put( LdbcQuery5.MIN_DATE, operation.getMinDate().getTime() )
                               .put( LdbcQuery5.LIMIT, operation.getLimit() )
                               .build();
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
        public String getQueryFile()
        {
            return "interactive-complex-6";
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
        public String getQueryFile()
        {
            return "interactive-complex-7";
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
        public String getQueryFile()
        {
            return "interactive-complex-8";
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
        public String getQueryFile()
        {
            return "interactive-complex-9";
        }

        @Override
        public Map<String, Object> getParameters( LdbcQuery9 operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcQuery9.PERSON_ID, operation.getPersonIdQ9() )
                               .put( LdbcQuery9.MAX_DATE, operation.getMaxDate().getTime() )
                               .put( LdbcQuery9.LIMIT, operation.getLimit() )
                               .build();
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
        public String getQueryFile()
        {
            return "interactive-complex-10";
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
        public String getQueryFile()
        {
            return "interactive-complex-11";
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
        public String getQueryFile()
        {
            return "interactive-complex-12";
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

    public static class InteractiveQuery13 extends CypherIC13OperationHandler
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-complex-13";
        }

        @Override
        public LdbcQuery13Result toResult( Record record )
        {
            return new LdbcQuery13Result( record.get( 0 ).asInt() );
        }
    }

    public static class InteractiveQuery14 extends CypherListOperationHandler<LdbcQuery14,LdbcQuery14Result>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-complex-14";
        }

        @Override
        public LdbcQuery14Result toResult( Record record ) throws ParseException
        {
            List<Long> personIdsInPath = new ArrayList<>();
            if ( !record.get( 0 ).isNull() )
            {
                personIdsInPath = record.get( 0 ).asList( Value::asLong );
            }
            double pathWeight = record.get( 1 ).asDouble();
            return new LdbcQuery14Result(
                    personIdsInPath,
                    pathWeight );
        }
    }

    // Interactive short reads

    public static class ShortQuery1PersonProfile extends CypherSingletonOperationHandler<LdbcShortQuery1PersonProfile,LdbcShortQuery1PersonProfileResult>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-short-1";
        }

        @Override
        public LdbcShortQuery1PersonProfileResult toResult( Record record ) throws ParseException
        {
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
    }

    public static class ShortQuery2PersonPosts extends CypherListOperationHandler<LdbcShortQuery2PersonPosts,LdbcShortQuery2PersonPostsResult>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-short-2";
        }

        @Override
        public LdbcShortQuery2PersonPostsResult toResult( Record record ) throws ParseException
        {
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
    }

    public static class ShortQuery3PersonFriends extends CypherListOperationHandler<LdbcShortQuery3PersonFriends,LdbcShortQuery3PersonFriendsResult>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-short-3";
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
        public String getQueryFile()
        {
            return "interactive-short-4";
        }

        @Override
        public LdbcShortQuery4MessageContentResult toResult( Record record ) throws ParseException
        {
            // Pay attention, the spec's and the implementation's parameter orders are different.
            long messageCreationDate = record.get( 0 ).asLong();
            String messageContent = record.get( 1 ).asString();
            return new LdbcShortQuery4MessageContentResult(
                    messageContent,
                    messageCreationDate );
        }
    }

    public static class ShortQuery5MessageCreator extends CypherSingletonOperationHandler<LdbcShortQuery5MessageCreator,LdbcShortQuery5MessageCreatorResult>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-short-5";
        }

        @Override
        public LdbcShortQuery5MessageCreatorResult toResult( Record record )
        {
            long personId = record.get( 0 ).asLong();
            String firstName = record.get( 1 ).asString();
            String lastName = record.get( 2 ).asString();
            return new LdbcShortQuery5MessageCreatorResult(
                    personId,
                    firstName,
                    lastName );
        }
    }

    public static class ShortQuery6MessageForum extends CypherSingletonOperationHandler<LdbcShortQuery6MessageForum,LdbcShortQuery6MessageForumResult>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-short-6";
        }

        @Override
        public LdbcShortQuery6MessageForumResult toResult( Record record )
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
    }

    public static class ShortQuery7MessageReplies extends CypherListOperationHandler<LdbcShortQuery7MessageReplies,LdbcShortQuery7MessageRepliesResult>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-short-7";
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

    public static class Update1AddPerson extends CypherUpdateOperationHandler<LdbcUpdate1AddPerson>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-1";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate1AddPerson operation )
        {
            final List<List<Long>> universities =
                    operation.getStudyAt().stream().map( u -> Arrays.asList( u.getOrganizationId(), (long) u.getYear() ) ).collect( Collectors.toList() );
            final List<List<Long>> companies =
                    operation.getWorkAt().stream().map( c -> Arrays.asList( c.getOrganizationId(), (long) c.getYear() ) ).collect( Collectors.toList() );

            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate1AddPerson.PERSON_ID, operation.getPersonId() )
                               .put( LdbcUpdate1AddPerson.PERSON_FIRST_NAME, operation.getPersonFirstName() )
                               .put( LdbcUpdate1AddPerson.PERSON_LAST_NAME, operation.getPersonLastName() )
                               .put( LdbcUpdate1AddPerson.GENDER, operation.getGender() )
                               .put( LdbcUpdate1AddPerson.BIRTHDAY, operation.getBirthday().getTime() )
                               .put( LdbcUpdate1AddPerson.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcUpdate1AddPerson.LOCATION_IP, operation.getLocationIp() )
                               .put( LdbcUpdate1AddPerson.BROWSER_USED, operation.getBrowserUsed() )
                               .put( LdbcUpdate1AddPerson.CITY_ID, operation.getCityId() )
                               .put( LdbcUpdate1AddPerson.LANGUAGES, operation.getLanguages() )
                               .put( LdbcUpdate1AddPerson.EMAILS, operation.getEmails() )
                               .put( LdbcUpdate1AddPerson.TAG_IDS, operation.getTagIds() )
                               .put( LdbcUpdate1AddPerson.STUDY_AT, universities )
                               .put( LdbcUpdate1AddPerson.WORK_AT, companies )
                               .build();
        }
    }

    public static class Update2AddPostLike extends CypherUpdateOperationHandler<LdbcUpdate2AddPostLike>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-2";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate2AddPostLike operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate2AddPostLike.PERSON_ID, operation.getPersonId() )
                               .put( LdbcUpdate2AddPostLike.POST_ID, operation.getPostId() )
                               .put( LdbcUpdate2AddPostLike.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }

    public static class Update3AddCommentLike extends CypherUpdateOperationHandler<LdbcUpdate3AddCommentLike>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-3";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate3AddCommentLike operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate3AddCommentLike.PERSON_ID, operation.getPersonId() )
                               .put( LdbcUpdate3AddCommentLike.COMMENT_ID, operation.getCommentId() )
                               .put( LdbcUpdate3AddCommentLike.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }

    public static class Update4AddForum extends CypherUpdateOperationHandler<LdbcUpdate4AddForum>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-4";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate4AddForum operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate4AddForum.FORUM_ID, operation.getForumId() )
                               .put( LdbcUpdate4AddForum.FORUM_TITLE, operation.getForumTitle() )
                               .put( LdbcUpdate4AddForum.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcUpdate4AddForum.MODERATOR_PERSON_ID, operation.getModeratorPersonId() )
                               .put( LdbcUpdate4AddForum.TAG_IDS, operation.getTagIds() )
                               .build();
        }
    }

    public static class Update5AddForumMembership extends CypherUpdateOperationHandler<LdbcUpdate5AddForumMembership>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-5";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate5AddForumMembership operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate5AddForumMembership.FORUM_ID, operation.getForumId() )
                               .put( LdbcUpdate5AddForumMembership.PERSON_ID, operation.getPersonId() )
                               .put( LdbcUpdate5AddForumMembership.JOIN_DATE, operation.getJoinDate().getTime() )
                               .build();
        }
    }

    public static class Update6AddPost extends CypherUpdateOperationHandler<LdbcUpdate6AddPost>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-6";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate6AddPost operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate6AddPost.POST_ID, operation.getPostId() )
                               .put( LdbcUpdate6AddPost.IMAGE_FILE, operation.getImageFile() )
                               .put( LdbcUpdate6AddPost.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcUpdate6AddPost.LOCATION_IP, operation.getLocationIp() )
                               .put( LdbcUpdate6AddPost.BROWSER_USED, operation.getBrowserUsed() )
                               .put( LdbcUpdate6AddPost.LANGUAGE, operation.getLanguage() )
                               .put( LdbcUpdate6AddPost.CONTENT, operation.getContent() )
                               .put( LdbcUpdate6AddPost.LENGTH, operation.getLength() )
                               .put( LdbcUpdate6AddPost.AUTHOR_PERSON_ID, operation.getAuthorPersonId() )
                               .put( LdbcUpdate6AddPost.FORUM_ID, operation.getForumId() )
                               .put( LdbcUpdate6AddPost.COUNTRY_ID, operation.getCountryId() )
                               .put( LdbcUpdate6AddPost.TAG_IDS, operation.getTagIds() )
                               .build();
        }
    }

    public static class Update7AddComment extends CypherUpdateOperationHandler<LdbcUpdate7AddComment>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-7";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate7AddComment operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate7AddComment.COMMENT_ID, operation.getCommentId() )
                               .put( LdbcUpdate7AddComment.CREATION_DATE, operation.getCreationDate().getTime() )
                               .put( LdbcUpdate7AddComment.LOCATION_IP, operation.getLocationIp() )
                               .put( LdbcUpdate7AddComment.BROWSER_USED, operation.getBrowserUsed() )
                               .put( LdbcUpdate7AddComment.CONTENT, operation.getContent() )
                               .put( LdbcUpdate7AddComment.LENGTH, operation.getLength() )
                               .put( LdbcUpdate7AddComment.AUTHOR_PERSON_ID, operation.getAuthorPersonId() )
                               .put( LdbcUpdate7AddComment.COUNTRY_ID, operation.getCountryId() )
                               .put( LdbcUpdate7AddComment.REPLY_TO_POST_ID, operation.getReplyToPostId() )
                               .put( LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, operation.getReplyToCommentId() )
                               .put( LdbcUpdate7AddComment.TAG_IDS, operation.getTagIds() )
                               .build();
        }
    }

    public static class Update8AddFriendship extends CypherUpdateOperationHandler<LdbcUpdate8AddFriendship>
    {

        @Override
        public String getQueryFile()
        {
            return "interactive-update-8";
        }

        @Override
        public Map<String, Object> getParameters( LdbcUpdate8AddFriendship operation )
        {
            return ImmutableMap.<String, Object>builder()
                               .put( LdbcUpdate8AddFriendship.PERSON1_ID, operation.getPerson1Id() )
                               .put( LdbcUpdate8AddFriendship.PERSON2_ID, operation.getPerson2Id() )
                               .put( LdbcUpdate8AddFriendship.CREATION_DATE, operation.getCreationDate().getTime() )
                               .build();
        }
    }
}