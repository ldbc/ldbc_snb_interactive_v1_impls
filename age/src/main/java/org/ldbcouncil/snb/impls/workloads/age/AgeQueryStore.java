package org.ldbcouncil.snb.impls.workloads.age;

import com.google.common.collect.ImmutableMap;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.*;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Loads AGE SQL query files and provides parameter maps for all 29 LDBC operation types.
 *
 * Key differences from the base QueryStore:
 * - Dates are epoch milliseconds (long), not ISO strings
 * - endDate for IC3/IC4 is computed from startDate + durationDays
 * - $graphName is replaced at load time with the configured graph name
 * - String values are Cypher-escaped via AgeConverter
 */
public class AgeQueryStore extends QueryStore {

    private final String graphName;

    public AgeQueryStore(String path, String graphName) throws DbException {
        super(path, ".sql");
        this.graphName = graphName;
    }

    @Override
    protected Converter getConverter() {
        return new AgeConverter();
    }

    @Override
    protected String loadQueryFromFile(String path, String filename) throws DbException {
        final String filePath = path + File.separator + filename;
        try {
            String query = new String(Files.readAllBytes(Paths.get(filePath)));
            return query.replace("$graphName", graphName);
        } catch (IOException e) {
            // Not all query types are required (IC13, IC14 have no files)
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // IC queries — override to provide epoch-ms dates and computed endDate
    // -------------------------------------------------------------------------

    @Override
    public Map<String, Object> getQuery1Map(LdbcQuery1 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery1.PERSON_ID, Long.toString(operation.getPersonIdQ1()))
                .put(LdbcQuery1.FIRST_NAME, getConverter().convertString(operation.getFirstName()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery2Map(LdbcQuery2 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery2.PERSON_ID, Long.toString(operation.getPersonIdQ2()))
                .put(LdbcQuery2.MAX_DATE, Long.toString(operation.getMaxDate().getTime()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery3Map(LdbcQuery3 operation) {
        long endDate = operation.getStartDate().getTime() + (long) operation.getDurationDays() * 86400000L;
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery3.PERSON_ID, Long.toString(operation.getPersonIdQ3()))
                .put(LdbcQuery3.COUNTRY_X_NAME, getConverter().convertString(operation.getCountryXName()))
                .put(LdbcQuery3.COUNTRY_Y_NAME, getConverter().convertString(operation.getCountryYName()))
                .put(LdbcQuery3.START_DATE, Long.toString(operation.getStartDate().getTime()))
                .put("endDate", Long.toString(endDate))
                .build();
    }

    @Override
    public Map<String, Object> getQuery4Map(LdbcQuery4 operation) {
        long endDate = operation.getStartDate().getTime() + (long) operation.getDurationDays() * 86400000L;
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery4.PERSON_ID, Long.toString(operation.getPersonIdQ4()))
                .put(LdbcQuery4.START_DATE, Long.toString(operation.getStartDate().getTime()))
                .put("endDate", Long.toString(endDate))
                .build();
    }

    @Override
    public Map<String, Object> getQuery5Map(LdbcQuery5 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery5.PERSON_ID, Long.toString(operation.getPersonIdQ5()))
                .put(LdbcQuery5.MIN_DATE, Long.toString(operation.getMinDate().getTime()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery6Map(LdbcQuery6 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery6.PERSON_ID, Long.toString(operation.getPersonIdQ6()))
                .put(LdbcQuery6.TAG_NAME, getConverter().convertString(operation.getTagName()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery7Map(LdbcQuery7 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery7.PERSON_ID, Long.toString(operation.getPersonIdQ7()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery8Map(LdbcQuery8 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery8.PERSON_ID, Long.toString(operation.getPersonIdQ8()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery9Map(LdbcQuery9 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery9.PERSON_ID, Long.toString(operation.getPersonIdQ9()))
                .put(LdbcQuery9.MAX_DATE, Long.toString(operation.getMaxDate().getTime()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery10Map(LdbcQuery10 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery10.PERSON_ID, Long.toString(operation.getPersonIdQ10()))
                .put(LdbcQuery10.MONTH, Integer.toString(operation.getMonth()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery11Map(LdbcQuery11 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery11.PERSON_ID, Long.toString(operation.getPersonIdQ11()))
                .put(LdbcQuery11.COUNTRY_NAME, getConverter().convertString(operation.getCountryName()))
                .put(LdbcQuery11.WORK_FROM_YEAR, Integer.toString(operation.getWorkFromYear()))
                .build();
    }

    @Override
    public Map<String, Object> getQuery12Map(LdbcQuery12 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery12.PERSON_ID, Long.toString(operation.getPersonIdQ12()))
                .put(LdbcQuery12.TAG_CLASS_NAME, getConverter().convertString(operation.getTagClassName()))
                .build();
    }

    // -------------------------------------------------------------------------
    // IS queries
    // -------------------------------------------------------------------------

    @Override
    public Map<String, Object> getShortQuery1PersonProfileMap(LdbcShortQuery1PersonProfile operation) {
        return ImmutableMap.of(LdbcShortQuery1PersonProfile.PERSON_ID,
                Long.toString(operation.getPersonIdSQ1()));
    }

    @Override
    public Map<String, Object> getShortQuery2PersonPostsMap(LdbcShortQuery2PersonPosts operation) {
        return ImmutableMap.of(LdbcShortQuery2PersonPosts.PERSON_ID,
                Long.toString(operation.getPersonIdSQ2()));
    }

    @Override
    public Map<String, Object> getShortQuery3PersonFriendsMap(LdbcShortQuery3PersonFriends operation) {
        return ImmutableMap.of(LdbcShortQuery3PersonFriends.PERSON_ID,
                Long.toString(operation.getPersonIdSQ3()));
    }

    @Override
    public Map<String, Object> getShortQuery4MessageContentMap(LdbcShortQuery4MessageContent operation) {
        return ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID,
                Long.toString(operation.getMessageIdContent()));
    }

    @Override
    public Map<String, Object> getShortQuery5MessageCreatorMap(LdbcShortQuery5MessageCreator operation) {
        return ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID,
                Long.toString(operation.getMessageIdCreator()));
    }

    @Override
    public Map<String, Object> getShortQuery6MessageForumMap(LdbcShortQuery6MessageForum operation) {
        return ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID,
                Long.toString(operation.getMessageForumId()));
    }

    @Override
    public Map<String, Object> getShortQuery7MessageRepliesMap(LdbcShortQuery7MessageReplies operation) {
        return ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID,
                Long.toString(operation.getMessageRepliesId()));
    }

    // -------------------------------------------------------------------------
    // IU queries — override base class map methods with AGE-specific values
    // -------------------------------------------------------------------------

    @Override
    public Map<String, Object> getUpdate1SingleMap(LdbcUpdate1AddPerson operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate1AddPerson.PERSON_ID, Long.toString(operation.getPersonId()))
                .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.getPersonFirstName()))
                .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.getPersonLastName()))
                .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.getGender()))
                .put(LdbcUpdate1AddPerson.BIRTHDAY, Long.toString(operation.getBirthday().getTime()))
                .put(LdbcUpdate1AddPerson.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                .put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                .put(LdbcUpdate1AddPerson.CITY_ID, Long.toString(operation.getCityId()))
                .put(LdbcUpdate1AddPerson.LANGUAGES, getConverter().convertStringList(operation.getLanguages()))
                .put(LdbcUpdate1AddPerson.EMAILS, getConverter().convertStringList(operation.getEmails()))
                .put(LdbcUpdate1AddPerson.TAG_IDS, AgeConverter.convertTagIds(operation.getTagIds()))
                .put(LdbcUpdate1AddPerson.STUDY_AT, new AgeConverter().convertOrganisations(operation.getStudyAt()))
                .put(LdbcUpdate1AddPerson.WORK_AT, new AgeConverter().convertOrganisations(operation.getWorkAt()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate2Map(LdbcUpdate2AddPostLike operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate2AddPostLike.PERSON_ID, Long.toString(operation.getPersonId()))
                .put(LdbcUpdate2AddPostLike.POST_ID, Long.toString(operation.getPostId()))
                .put(LdbcUpdate2AddPostLike.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate3Map(LdbcUpdate3AddCommentLike operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate3AddCommentLike.PERSON_ID, Long.toString(operation.getPersonId()))
                .put(LdbcUpdate3AddCommentLike.COMMENT_ID, Long.toString(operation.getCommentId()))
                .put(LdbcUpdate3AddCommentLike.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate4SingleMap(LdbcUpdate4AddForum operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate4AddForum.FORUM_ID, Long.toString(operation.getForumId()))
                .put(LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.getForumTitle()))
                .put(LdbcUpdate4AddForum.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .put(LdbcUpdate4AddForum.MODERATOR_PERSON_ID, Long.toString(operation.getModeratorPersonId()))
                .put(LdbcUpdate4AddForum.TAG_IDS, AgeConverter.convertTagIds(operation.getTagIds()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate5Map(LdbcUpdate5AddForumMembership operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate5AddForumMembership.FORUM_ID, Long.toString(operation.getForumId()))
                .put(LdbcUpdate5AddForumMembership.PERSON_ID, Long.toString(operation.getPersonId()))
                .put(LdbcUpdate5AddForumMembership.JOIN_DATE, Long.toString(operation.getJoinDate().getTime()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate6SingleMap(LdbcUpdate6AddPost operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate6AddPost.POST_ID, Long.toString(operation.getPostId()))
                .put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(
                        operation.getImageFile() == null ? "" : operation.getImageFile()))
                .put(LdbcUpdate6AddPost.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                .put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                .put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.getLanguage()))
                .put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(
                        operation.getContent() == null ? "" : operation.getContent()))
                .put(LdbcUpdate6AddPost.LENGTH, Integer.toString(operation.getLength()))
                .put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, Long.toString(operation.getAuthorPersonId()))
                .put(LdbcUpdate6AddPost.FORUM_ID, Long.toString(operation.getForumId()))
                .put(LdbcUpdate6AddPost.COUNTRY_ID, Long.toString(operation.getCountryId()))
                .put(LdbcUpdate6AddPost.TAG_IDS, AgeConverter.convertTagIds(operation.getTagIds()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate7SingleMap(LdbcUpdate7AddComment operation) {
        // Resolve the target message id: replyToPostId is -1 when replying to a comment and vice versa
        long replyToId = operation.getReplyToPostId() != -1
                ? operation.getReplyToPostId()
                : operation.getReplyToCommentId();
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate7AddComment.COMMENT_ID, Long.toString(operation.getCommentId()))
                .put(LdbcUpdate7AddComment.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                .put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                .put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.getContent()))
                .put(LdbcUpdate7AddComment.LENGTH, Integer.toString(operation.getLength()))
                .put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, Long.toString(operation.getAuthorPersonId()))
                .put(LdbcUpdate7AddComment.COUNTRY_ID, Long.toString(operation.getCountryId()))
                .put("replyToId", Long.toString(replyToId))
                .put(LdbcUpdate7AddComment.TAG_IDS, AgeConverter.convertTagIds(operation.getTagIds()))
                .build();
    }

    @Override
    public Map<String, Object> getUpdate8Map(LdbcUpdate8AddFriendship operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcUpdate8AddFriendship.PERSON1_ID, Long.toString(operation.getPerson1Id()))
                .put(LdbcUpdate8AddFriendship.PERSON2_ID, Long.toString(operation.getPerson2Id()))
                .put(LdbcUpdate8AddFriendship.CREATION_DATE, Long.toString(operation.getCreationDate().getTime()))
                .build();
    }
}
