package org.ldbcouncil.snb.impls.workloads.cypher;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.google.common.collect.ImmutableMap;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.*;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.cypher.converter.CypherConverter;

public class CypherQueryStore extends QueryStore
{
    public CypherQueryStore( String path )  throws DbException
    {
        super(path, ".cypher");
    }

    protected Converter getConverter() {
        return new CypherConverter();
    }

    static protected Date addDays( Date startDate, int days )
    {
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
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

    /**
     * The maps are overriden here to return maps with Java types
     * instead of strings. This speeds up querying the Cypher instance
     * as it reuses the query plans.
     */

    // Complex queries

    @Override
    public Map<String, Object> getQuery1Map(LdbcQuery1 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery1.PERSON_ID, operation.getPersonIdQ1())
                .put(LdbcQuery1.FIRST_NAME, operation.getFirstName())
                .build();
    }

    @Override
    public Map<String, Object> getQuery2Map(LdbcQuery2 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery2.PERSON_ID, operation.getPersonIdQ2() )
        .put(LdbcQuery2.MAX_DATE, operation.getMaxDate().getTime() )
        .put( LdbcQuery2.LIMIT, operation.getLimit() )
        .build();
    }

    @Override
    public Map<String, Object> getQuery3Map(LdbcQuery3 operation) {
        final Date endDate = addDays( operation.getStartDate(), operation.getDurationDays() );
        return new ImmutableMap.Builder<String, Object>()
        .put( LdbcQuery3.PERSON_ID, operation.getPersonIdQ3() )
        .put( LdbcQuery3.COUNTRY_X_NAME, operation.getCountryXName() )
        .put( LdbcQuery3.COUNTRY_Y_NAME, operation.getCountryYName()) 
        .put( LdbcQuery3.START_DATE, operation.getStartDate().getTime() )
        .put( "endDate", endDate.getTime() )
        .put( LdbcQuery3.LIMIT, operation.getLimit() )
        .build();
    }

    @Override
    public Map<String, Object> getQuery4Map(LdbcQuery4 operation) {
        final Date endDate = addDays( operation.getStartDate(), operation.getDurationDays() );
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery4.PERSON_ID, operation.getPersonIdQ4())
        .put(LdbcQuery4.START_DATE, operation.getStartDate().getTime())
        .put( "endDate", endDate.getTime() )
        .put( LdbcQuery4.LIMIT, operation.getLimit() )
        .build();
    }

    @Override
    public Map<String, Object> getQuery5Map(LdbcQuery5 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery5.PERSON_ID, operation.getPersonIdQ5())
        .put(LdbcQuery5.MIN_DATE, operation.getMinDate().getTime() )
        .put( LdbcQuery5.LIMIT, operation.getLimit() )
        .build();
    }

    @Override
    public Map<String, Object> getQuery6Map(LdbcQuery6 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery6.PERSON_ID, operation.getPersonIdQ6())
        .put(LdbcQuery6.TAG_NAME, operation.getTagName())
        .build();
    }

    @Override
    public Map<String, Object> getQuery7Map(LdbcQuery7 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery7.PERSON_ID, operation.getPersonIdQ7())
        .build();
    }

    @Override
    public Map<String, Object> getQuery8Map(LdbcQuery8 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery8.PERSON_ID, operation.getPersonIdQ8())
        .build();
    }

    @Override
    public Map<String, Object> getQuery9Map(LdbcQuery9 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery9.PERSON_ID, operation.getPersonIdQ9())
        .put(LdbcQuery9.MAX_DATE, operation.getMaxDate().getTime())
        .put( LdbcQuery9.LIMIT, operation.getLimit() )
        .build();
    }

    @Override
    public Map<String, Object> getQuery10Map(LdbcQuery10 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery10.PERSON_ID, operation.getPersonIdQ10())
        .put(LdbcQuery10.MONTH, operation.getMonth())
        .build();
    }

    @Override
    public Map<String, Object> getQuery11Map(LdbcQuery11 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery11.PERSON_ID, operation.getPersonIdQ11())
        .put(LdbcQuery11.COUNTRY_NAME, operation.getCountryName())
        .put(LdbcQuery11.WORK_FROM_YEAR, operation.getWorkFromYear())
        .build();
    }

    @Override
    public Map<String, Object> getQuery12Map(LdbcQuery12 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery12.PERSON_ID, operation.getPersonIdQ12())
        .put(LdbcQuery12.TAG_CLASS_NAME, operation.getTagClassName())
        .build();
    }

    @Override
    public Map<String, Object> getQuery13Map (LdbcQuery13 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery13.PERSON1_ID, operation.getPerson1IdQ13StartNode())
        .put(LdbcQuery13.PERSON2_ID, operation.getPerson2IdQ13EndNode())
        .build();
    }

    @Override
    public Map<String, Object> getQuery14Map (LdbcQuery14 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery14.PERSON1_ID, operation.getPerson1IdQ14StartNode())
        .put(LdbcQuery14.PERSON2_ID, operation.getPerson2IdQ14EndNode())
        .build();
    }

    // Short queries

    @Override
    public Map<String, Object> getShortQuery1PersonProfileMap(LdbcShortQuery1PersonProfile operation) {
        return ImmutableMap.of(LdbcShortQuery1PersonProfile.PERSON_ID, operation.getPersonIdSQ1());
    }

    @Override
    public Map<String, Object> getShortQuery2PersonPostsMap(LdbcShortQuery2PersonPosts operation) {
        return ImmutableMap.of(LdbcShortQuery2PersonPosts.PERSON_ID, operation.getPersonIdSQ2());
    }

    @Override
    public Map<String, Object> getShortQuery3PersonFriendsMap(LdbcShortQuery3PersonFriends operation) {
        return ImmutableMap.of(LdbcShortQuery3PersonFriends.PERSON_ID, operation.getPersonIdSQ3());
    }

    @Override
    public Map<String, Object> getShortQuery4MessageContentMap(LdbcShortQuery4MessageContent operation) {
        return ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID, operation.getMessageIdContent());
    }

    @Override
    public Map<String, Object> getShortQuery5MessageCreatorMap(LdbcShortQuery5MessageCreator operation) {
        return ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID, operation.getMessageIdCreator());
    }

    @Override
    public Map<String, Object> getShortQuery6MessageForumMap(LdbcShortQuery6MessageForum operation) {
        return ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID, operation.getMessageForumId());
    }

    @Override
    public Map<String, Object> getShortQuery7MessageRepliesMap(LdbcShortQuery7MessageReplies operation) {
        return ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID, operation.getMessageRepliesId());
    }
}
