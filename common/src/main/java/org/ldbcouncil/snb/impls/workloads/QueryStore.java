package org.ldbcouncil.snb.impls.workloads;
/**
 * QueryStore.java
 * 
 * This class stores functions to query definition files and to retrieve the following used in operation handlers:
 * - Query definition strings
 * - Parameter map (Map<String, Object>), with as default String objects as values
 * - Prepared queries (using string substitution)
 * 
 * Implementations can extend this class and override functions to change e.g.
 * - ParameterPrefix ()
 * - ParameterPostfix (file extension of query files)
 * - Parameter map with different type stored than a string.
 */
import com.google.common.collect.ImmutableMap;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class QueryStore {

    /**
     * Converter used for converting result objects. 
     * @return
     */
    protected Converter getConverter() { return new Converter(); }

    /**
     * Parameter prefix used in query definitions. Defaults to '$'
     * @return
     */
    protected String getParameterPrefix() { return "$"; }

    /**
     * The file extension for query files. 
     * @return
     */
    protected String getParameterPostfix() { return ""; }

    /**
     * Stores the loaded queries.
     */
    protected Map<QueryType, String> queries = new HashMap<>();

    /**
     * Load a query file
     * @param path Path to the file
     * @param filename The name of the query file
     * @return Loaded query definition
     * @throws DbException
     */
    protected String loadQueryFromFile(String path, String filename) throws DbException {
        final String filePath = path + File.separator + filename;
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.printf("Unable to load query from file: %s", filePath);
            return null;
        }
    }

    /**
     * Prepares the parameterized query using the parameter map
     * @param queryType Type of query to prepare (QueryType)
     * @param parameterSubstitutions The parameter map containing the subsitute values
     * @return Prepared query string
     */
    protected String prepare(QueryType queryType, Map<String, Object> parameterSubstitutions) {
        String querySpecification = queries.get(queryType);
        for (String parameter : parameterSubstitutions.keySet()) {
            querySpecification = querySpecification.replace(
                    getParameterPrefix() + parameter + getParameterPostfix(),
                    (String) parameterSubstitutions.get(parameter)
            );
        }
        return querySpecification;
    }

    /**
     * 
     * @param queryType
     * @return
     */
    public String getParameterizedQuery(QueryType queryType) {
        return queries.get(queryType);
    }

    /**
     * Create QueryStore
     * @param path: Path to the directory containing query files
     * @param postfix The extension of the query files
     * @throws DbException
     */
    public QueryStore(String path, String postfix) throws DbException {
        for (QueryType queryType : QueryType.values()) {
            queries.put(queryType, loadQueryFromFile(path, queryType.getName() + postfix));
        }
    }

    /**
     * Get prepared Query1 string
     * @param operation LdbcQuery1 operation containing parameter values
     * @return Prepared Query1 string
     */
    public String getQuery1(LdbcQuery1 operation) {
        return prepare(QueryType.InteractiveComplexQuery1, getQuery1Map(operation));
    }

    /**
     * Get Query1 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery1 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery1Map(LdbcQuery1 operation) {
        return new ImmutableMap.Builder<String, Object>()
                .put(LdbcQuery1.PERSON_ID, getConverter().convertId(operation.getPersonIdQ1()))
                .put(LdbcQuery1.FIRST_NAME, getConverter().convertString(operation.getFirstName()))
                .build();
    }

    /**
     * Get prepared Query2 string
     * @param operation LdbcQuery2 operation containing parameter values
     * @return Prepared Query2 string
     */
    public String getQuery2(LdbcQuery2 operation) {
        return prepare(QueryType.InteractiveComplexQuery2, getQuery2Map(operation));
    }

    /**
     * Get Query2 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery2 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery2Map(LdbcQuery2 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery2.PERSON_ID, getConverter().convertId(operation.getPersonIdQ2()))
        .put(LdbcQuery2.MAX_DATE, getConverter().convertDate(operation.getMaxDate()))
        .build();
    }

    /**
     * Get prepared Query3 string
     * @param operation LdbcQuery3 operation containing parameter values
     * @return Prepared Query3 string
     */
    public String getQuery3(LdbcQuery3 operation) {
        return prepare(QueryType.InteractiveComplexQuery3, getQuery3Map(operation));
    }

    /**
     * Get Query3 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery3 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery3Map(LdbcQuery3 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery3.PERSON_ID, getConverter().convertId(operation.getPersonIdQ3()))
        .put(LdbcQuery3.COUNTRY_X_NAME, getConverter().convertString(operation.getCountryXName()))
        .put(LdbcQuery3.COUNTRY_Y_NAME, getConverter().convertString(operation.getCountryYName()))
        .put(LdbcQuery3.START_DATE, getConverter().convertDate(operation.getStartDate()))
        .put(LdbcQuery3.DURATION_DAYS, getConverter().convertInteger(operation.getDurationDays()))
        .build();
    }

    /**
     * Get prepared Query4 string
     * @param operation LdbcQuery4 operation containing parameter values
     * @return Prepared Query4 string
     */
    public String getQuery4(LdbcQuery4 operation) {
        return prepare(QueryType.InteractiveComplexQuery4, getQuery4Map(operation));
    }

    /**
     * Get Query4 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery4 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery4Map(LdbcQuery4 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery4.PERSON_ID, getConverter().convertId(operation.getPersonIdQ4()))
        .put(LdbcQuery4.START_DATE, getConverter().convertDate(operation.getStartDate()))
        .put(LdbcQuery4.DURATION_DAYS, getConverter().convertInteger(operation.getDurationDays()))
        .build();
    }

    /**
     * Get prepared Query5 string
     * @param operation LdbcQuery5 operation containing parameter values
     * @return Prepared Query5 string
     */
    public String getQuery5(LdbcQuery5 operation) {
        return prepare(QueryType.InteractiveComplexQuery5, getQuery5Map(operation));
    }

    /**
     * Get Query5 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery5 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery5Map(LdbcQuery5 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery5.PERSON_ID, getConverter().convertId(operation.getPersonIdQ5()))
        .put(LdbcQuery5.MIN_DATE, getConverter().convertDate(operation.getMinDate()))
        .build();
    }

    /**
     * Get prepared Query6 string
     * @param operation LdbcQuery6 operation containing parameter values
     * @return Prepared Query6 string
     */
    public String getQuery6(LdbcQuery6 operation) {
        return prepare(QueryType.InteractiveComplexQuery6, getQuery6Map(operation));
    }

    /**
     * Get Query6 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery6 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery6Map(LdbcQuery6 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery6.PERSON_ID, getConverter().convertId(operation.getPersonIdQ6()))
        .put(LdbcQuery6.TAG_NAME, getConverter().convertString(operation.getTagName()))
        .build();
    }

    /**
     * Get prepared Query7 string
     * @param operation LdbcQuery7 operation containing parameter values
     * @return Prepared Query7 string
     */
    public String getQuery7(LdbcQuery7 operation) {
        return prepare(QueryType.InteractiveComplexQuery7, getQuery7Map(operation));
    }

    /**
     * Get Query7 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery7 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery7Map(LdbcQuery7 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery7.PERSON_ID, getConverter().convertId(operation.getPersonIdQ7()))
        .build();
    }

    /**
     * Get prepared Query8 string
     * @param operation LdbcQuery8 operation containing parameter values
     * @return Prepared Query8 string
     */
    public String getQuery8(LdbcQuery8 operation) {
        return prepare(QueryType.InteractiveComplexQuery8, getQuery8Map(operation));
    }

    /**
     * Get Query8 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery8 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery8Map(LdbcQuery8 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery8.PERSON_ID, getConverter().convertId(operation.getPersonIdQ8()))
        .build();
    }

    /**
     * Get prepared Query9 string
     * @param operation LdbcQuery9 operation containing parameter values
     * @return Prepared Query9 string
     */
    public String getQuery9(LdbcQuery9 operation) {
        return prepare(QueryType.InteractiveComplexQuery9, getQuery9Map(operation));
    }

    /**
     * Get Query9 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery9 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery9Map(LdbcQuery9 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery9.PERSON_ID, getConverter().convertId(operation.getPersonIdQ9()))
        .put(LdbcQuery9.MAX_DATE, getConverter().convertDate(operation.getMaxDate()))
        .build();
    }

    /**
     * Get prepared Query10 string
     * @param operation LdbcQuery10 operation containing parameter values
     * @return Prepared Query10 string
     */
    public String getQuery10(LdbcQuery10 operation) {
        return prepare(QueryType.InteractiveComplexQuery10, getQuery10Map(operation));
    }

    /**
     * Get Query10 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery10 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery10Map(LdbcQuery10 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery10.PERSON_ID, getConverter().convertId(operation.getPersonIdQ10()))
        .put(LdbcQuery10.MONTH, getConverter().convertInteger(operation.getMonth()))
        .build();
    }

    /**
     * Get prepared Query11 string
     * @param operation LdbcQuery11 operation containing parameter values
     * @return Prepared Query11 string
     */
    public String getQuery11(LdbcQuery11 operation) {
        return prepare(QueryType.InteractiveComplexQuery11, getQuery11Map(operation));
    }

    /**
     * Get Query11 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery11 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery11Map(LdbcQuery11 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery11.PERSON_ID, getConverter().convertId(operation.getPersonIdQ11()))
        .put(LdbcQuery11.COUNTRY_NAME, getConverter().convertString(operation.getCountryName()))
        .put(LdbcQuery11.WORK_FROM_YEAR, getConverter().convertInteger(operation.getWorkFromYear()))
        .build();
    }

    /**
     * Get prepared Query12 string
     * @param operation LdbcQuery12 operation containing parameter values
     * @return Prepared Query12 string
     */
    public String getQuery12(LdbcQuery12 operation) {
        return prepare(QueryType.InteractiveComplexQuery12, getQuery12Map(operation));
    }

    /**
     * Get Query12 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery12 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery12Map(LdbcQuery12 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery12.PERSON_ID, getConverter().convertId(operation.getPersonIdQ12()))
        .put(LdbcQuery12.TAG_CLASS_NAME, getConverter().convertString(operation.getTagClassName()))
        .build();
    }

    /**
     * Get prepared Query13 string
     * @param operation LdbcQuery13 operation containing parameter values
     * @return Prepared Query13 string
     */
    public String getQuery13(LdbcQuery13 operation) {
        return prepare(QueryType.InteractiveComplexQuery13, getQuery13Map(operation));
    }

    /**
     * Get Query13 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery13 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery13Map (LdbcQuery13 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery13.PERSON1_ID, getConverter().convertId(operation.getPerson1IdQ13StartNode()))
        .put(LdbcQuery13.PERSON2_ID, getConverter().convertId(operation.getPerson2IdQ13EndNode()))
        .build();
    }

    /**
     * Get prepared Query14 string
     * @param operation LdbcQuery14 operation containing parameter values
     * @return Prepared Query14 string
     */
    public String getQuery14(LdbcQuery14 operation) {
        return prepare(QueryType.InteractiveComplexQuery14, getQuery14Map(operation));
    }

    /**
     * Get Query14 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcQuery14 operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getQuery14Map (LdbcQuery14 operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcQuery14.PERSON1_ID, getConverter().convertId(operation.getPerson1IdQ14StartNode()))
        .put(LdbcQuery14.PERSON2_ID, getConverter().convertId(operation.getPerson2IdQ14EndNode()))
        .build();
    }

    /**
     * Get prepared LdbcShortQuery1PersonProfile string
     * @param operation LdbcShortQuery1PersonProfile operation containing parameter values
     * @return Prepared LdbcShortQuery1PersonProfile string
     */
    public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
        return prepare(
                QueryType.InteractiveShortQuery1,getShortQuery1PersonProfileMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery1PersonProfile Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery1PersonProfile operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery1PersonProfileMap(LdbcShortQuery1PersonProfile operation) {
        return ImmutableMap.of(LdbcShortQuery1PersonProfile.PERSON_ID, getConverter().convertId(operation.getPersonIdSQ1()));
    }

    /**
     * Get prepared LdbcShortQuery2PersonPosts string
     * @param operation LdbcShortQuery2PersonPosts operation containing parameter values
     * @return Prepared LdbcShortQuery2PersonPosts string
     */
    public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
        return prepare(
                QueryType.InteractiveShortQuery2, getShortQuery2PersonPostsMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery2PersonPosts Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery2PersonPosts operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery2PersonPostsMap(LdbcShortQuery2PersonPosts operation) {
        return ImmutableMap.of(LdbcShortQuery2PersonPosts.PERSON_ID, getConverter().convertId(operation.getPersonIdSQ2()));
    }

    /**
     * Get prepared LdbcShortQuery3PersonFriends string
     * @param operation LdbcShortQuery3PersonFriends operation containing parameter values
     * @return Prepared LdbcShortQuery3PersonFriends string
     */
    public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
        return prepare(
                QueryType.InteractiveShortQuery3, getShortQuery3PersonFriendsMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery3PersonFriends Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery3PersonFriends operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery3PersonFriendsMap(LdbcShortQuery3PersonFriends operation) {
        return ImmutableMap.of(LdbcShortQuery3PersonFriends.PERSON_ID, getConverter().convertId(operation.getPersonIdSQ3()));
    }

    /**
     * Get prepared LdbcShortQuery4MessageContent string
     * @param operation LdbcShortQuery4MessageContent operation containing parameter values
     * @return Prepared LdbcShortQuery4MessageContent string
     */
    public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
        return prepare(
                QueryType.InteractiveShortQuery4, getShortQuery4MessageContentMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery4MessageContent Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery4MessageContent operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery4MessageContentMap(LdbcShortQuery4MessageContent operation) {
        return ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID, getConverter().convertId(operation.getMessageIdContent()));
    }

    /**
     * Get prepared LdbcShortQuery5MessageCreator string
     * @param operation LdbcShortQuery5MessageCreator operation containing parameter values
     * @return Prepared LdbcShortQuery5MessageCreator string
     */
    public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
        return prepare(
                QueryType.InteractiveShortQuery5, getShortQuery5MessageCreatorMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery5MessageCreator Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery5MessageCreator operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery5MessageCreatorMap(LdbcShortQuery5MessageCreator operation) {
        return ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID, getConverter().convertId(operation.getMessageIdCreator()));
    }

    /**
     * Get prepared LdbcShortQuery6MessageForum string
     * @param operation LdbcShortQuery6MessageForum operation containing parameter values
     * @return Prepared LdbcShortQuery6MessageForum string
     */
    public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
        return prepare(
                QueryType.InteractiveShortQuery6, getShortQuery6MessageForumMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery6MessageForum Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery6MessageForum operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery6MessageForumMap(LdbcShortQuery6MessageForum operation) {
        return ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID, getConverter().convertId(operation.getMessageForumId()));
    }

    /**
     * Get prepared LdbcShortQuery7MessageReplies string
     * @param operation LdbcShortQuery7MessageReplies operation containing parameter values
     * @return Prepared LdbcShortQuery7MessageReplies string
     */
    public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
        return prepare(
                QueryType.InteractiveShortQuery7, getShortQuery7MessageRepliesMap(operation)
        );
    }

    /**
     * Get LdbcShortQuery7MessageReplies Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcShortQuery7MessageReplies operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getShortQuery7MessageRepliesMap(LdbcShortQuery7MessageReplies operation) {
        return ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID, getConverter().convertId(operation.getMessageRepliesId()));
    }

    
    /**
     * Get prepared UpdateQuery1 string
     * This is used for single query updates.
     * @param operation UpdateQuery1 operation containing parameter values
     * @return Prepared UpdateQuery1 string
     */
    public String getUpdate1Single(LdbcUpdate1AddPerson operation) {
        return prepare(
                QueryType.InteractiveUpdate1, getUpdate1SingleMap(operation)
        );
    }

    /**
     * Get UpdateQuery1 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate1AddPerson operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate1SingleMap(LdbcUpdate1AddPerson operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()))
        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.getPersonFirstName()))
        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.getPersonLastName()))
        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.getGender()))
        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDate(operation.getBirthday()))
        .put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
        .put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
        .put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
        .put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertId(operation.getCityId()))
        .put(LdbcUpdate1AddPerson.WORK_AT, getConverter().convertOrganisations(operation.getWorkAt()))
        .put(LdbcUpdate1AddPerson.STUDY_AT, getConverter().convertOrganisations(operation.getStudyAt()))
        .put(LdbcUpdate1AddPerson.EMAILS, getConverter().convertStringList(operation.getEmails()))
        .put(LdbcUpdate1AddPerson.LANGUAGES, getConverter().convertStringList(operation.getLanguages()))
        .put(LdbcUpdate1AddPerson.TAG_IDS, getConverter().convertLongList(operation.getTagIds()))
        .build();
    }

    /**
     * Get prepared UpdateQuery2 string
     * @param operation LdbcUpdate2AddPostLike operation containing parameter values
     * @return Prepared UpdateQuery2 string
     */
    public String getUpdate2(LdbcUpdate2AddPostLike operation) {
        return prepare(
                QueryType.InteractiveUpdate2, getUpdate2Map(operation)
        );
    }

    /**
     * Get UpdateQuery2 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate2AddPostLike operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate2Map(LdbcUpdate2AddPostLike operation) {
        return ImmutableMap.of(
            LdbcUpdate2AddPostLike.PERSON_ID, getConverter().convertId(operation.getPersonId()),
            LdbcUpdate2AddPostLike.POST_ID, getConverter().convertId(operation.getPostId()),
            LdbcUpdate2AddPostLike.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate())
        );
    }

    /**
     * Get prepared UpdateQuery3 string
     * @param operation LdbcUpdate3AddCommentLike operation containing parameter values
     * @return Prepared UpdateQuery3 string
     */
    public String getUpdate3(LdbcUpdate3AddCommentLike operation) {
        return prepare(
                QueryType.InteractiveUpdate3, getUpdate3Map(operation)
        );
    }

    /**
     * Get UpdateQuery3 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate3AddCommentLike operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate3Map(LdbcUpdate3AddCommentLike operation) {
        return ImmutableMap.of(
            LdbcUpdate3AddCommentLike.PERSON_ID, getConverter().convertId(operation.getPersonId()),
            LdbcUpdate3AddCommentLike.COMMENT_ID, getConverter().convertId(operation.getCommentId()),
            LdbcUpdate3AddCommentLike.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate())
        );
    }

    /**
     * Get prepared UpdateQuery4 string
     * This is used for single query updates.
     * @param operation LdbcUpdate4AddForum operation containing parameter values
     * @return Prepared UpdateQuery4 string
     */
    public String getUpdate4Single(LdbcUpdate4AddForum operation) {
        return prepare(
                QueryType.InteractiveUpdate4, getUpdate4SingleMap(operation)
        );
    }

    /**
     * Get UpdateQuery4 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate4AddForum operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate4SingleMap(LdbcUpdate4AddForum operation) {
        return ImmutableMap.of(
            LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.getForumId()),
            LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.getForumTitle()),
            LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()),
            LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.getModeratorPersonId()),
            LdbcUpdate4AddForum.TAG_IDS, getConverter().convertLongList(operation.getTagIds())
        );
    }

    /**
     * Get prepared UpdateQuery5 string
     * @param operation LdbcUpdate5AddForumMembership operation containing parameter values
     * @return Prepared UpdateQuery5 string
     */
    public String getUpdate5(LdbcUpdate5AddForumMembership operation) {
        return prepare(
                QueryType.InteractiveUpdate5, getUpdate5Map(operation)
        );
    }

    /**
     * Get UpdateQuery5 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate5AddForumMembership operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate5Map(LdbcUpdate5AddForumMembership operation) {
        return ImmutableMap.of(
            LdbcUpdate5AddForumMembership.FORUM_ID, getConverter().convertId(operation.getForumId()),
            LdbcUpdate5AddForumMembership.PERSON_ID, getConverter().convertId(operation.getPersonId()),
            LdbcUpdate5AddForumMembership.JOIN_DATE, getConverter().convertDateTime(operation.getJoinDate())
        );
    }
    
    /**
     * Get prepared UpdateQuery6 string
     * This is used for single query updates.
     * @param operation LdbcUpdate6AddPost operation containing parameter values
     * @return Prepared UpdateQuery6 string
     */
    public String getUpdate6Single(LdbcUpdate6AddPost operation) {
        return prepare(
                QueryType.InteractiveUpdate6, getUpdate6SingleMap(operation)
        );
    }

    /**
     * Get UpdateQuery6 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate6AddPost operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate6SingleMap(LdbcUpdate6AddPost operation) {
        return new ImmutableMap.Builder<String, Object>()
            .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.getPostId()))
            .put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.getImageFile()))
            .put(LdbcUpdate6AddPost.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
            .put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
            .put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
            .put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.getLanguage()))
            .put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.getContent()))
            .put(LdbcUpdate6AddPost.LENGTH, getConverter().convertInteger(operation.getLength()))
            .put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
            .put(LdbcUpdate6AddPost.FORUM_ID, getConverter().convertId(operation.getForumId()))
            .put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertId(operation.getCountryId()))
            .put(LdbcUpdate6AddPost.TAG_IDS, getConverter().convertLongList(operation.getTagIds()))
            .build();
    }

    /**
     * Get prepared UpdateQuery7 string
     * This is used for single query updates.
     * @param operation LdbcUpdate7AddComment operation containing parameter values
     * @return Prepared UpdateQuery7 string
     */
    public String getUpdate7Single(LdbcUpdate7AddComment operation) {
        return prepare(
                QueryType.InteractiveUpdate7, getUpdate7SingleMap(operation)
        );
    }

    /**
     * Get UpdateQuery7 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate7AddComment operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate7SingleMap(LdbcUpdate7AddComment operation) {
        return new ImmutableMap.Builder<String, Object>()
        .put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()))
        .put(LdbcUpdate7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
        .put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
        .put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
        .put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.getContent()))
        .put(LdbcUpdate7AddComment.LENGTH, getConverter().convertInteger(operation.getLength()))
        .put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
        .put(LdbcUpdate7AddComment.COUNTRY_ID, getConverter().convertId(operation.getCountryId()))
        .put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.getReplyToPostId()))
        .put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.getReplyToCommentId()))
        .put(LdbcUpdate7AddComment.TAG_IDS, getConverter().convertLongList(operation.getTagIds()))
        .build();
    }

    /**
     * Get prepared UpdateQuery8 string
     * @param operation LdbcUpdate8AddFriendship operation containing parameter values
     * @return Prepared UpdateQuery8 string
     */
    public String getUpdate8(LdbcUpdate8AddFriendship operation) {
        return prepare(
                QueryType.InteractiveUpdate8, getUpdate8Map(operation)
        );
    }

    /**
     * Get UpdateQuery8 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcUpdate8AddFriendship operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getUpdate8Map(LdbcUpdate8AddFriendship operation) {
        return ImmutableMap.of(
            LdbcUpdate8AddFriendship.PERSON1_ID, getConverter().convertId(operation.getPerson1Id()),
            LdbcUpdate8AddFriendship.PERSON2_ID, getConverter().convertId(operation.getPerson2Id()),
            LdbcUpdate8AddFriendship.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate())
        );
    }

    /**
     * Get prepared UpdateQuery1 strings.
     * This is used for system requiring multiple updates.
     * @param operation UpdateQuery1 operation containing parameter values
     * @return List of prepared UpdateQuery1 strings
     */
    public List<String> getUpdate1Multiple(LdbcUpdate1AddPerson operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate1AddPerson,
                new ImmutableMap.Builder<String, Object>()
                        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()))
                        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.getPersonFirstName()))
                        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.getPersonLastName()))
                        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.getGender()))
                        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDate(operation.getBirthday()))
                        .put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
                        .put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                        .put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                        .put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertId(operation.getCityId()))
                        .build()
        ));

        for (LdbcUpdate1AddPerson.Organization organization : operation.getWorkAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonCompanies,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                            "organizationId", getConverter().convertId(organization.getOrganizationId()),
                            "worksFromYear", getConverter().convertInteger(organization.getYear())
                    )
            ));
        }
        for (String email : operation.getEmails()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonEmails,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                            "email", getConverter().convertString(email)
                    )
            ));
        }
        for (String language : operation.getLanguages()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonLanguages,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                            "language", getConverter().convertString(language)
                    )
            ));
        }

        for (long tagId : operation.getTagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonTags,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        for (LdbcUpdate1AddPerson.Organization organization : operation.getStudyAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonUniversities,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                            "organizationId", getConverter().convertId(organization.getOrganizationId()),
                            "studiesFromYear", getConverter().convertInteger(organization.getYear())
                    )
            ));
        }
        return list;
    }

    /**
     * Get prepared UpdateQuery4 strings.
     * This is used for system requiring multiple updates.
     * @param operation UpdateQuery4 operation containing parameter values
     * @return List of prepared UpdateQuery4 strings
     */
    public List<String> getUpdate4Multiple(LdbcUpdate4AddForum operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate4AddForum,
                ImmutableMap.of(
                        LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.getForumId()),
                        LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.getForumTitle()),
                        LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()),
                        LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.getModeratorPersonId())
                )
        ));

        for (long tagId : operation.getTagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate4AddForumTags,
                    ImmutableMap.of(
                            LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.getForumId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    /**
     * Get prepared UpdateQuery6 string
     * This is used for system requiring multiple updates.
     * @param operation UpdateQuery6 operation containing parameter values
     * @return List of prepared UpdateQuery6 strings
     */
    public List<String> getUpdate6Multiple(LdbcUpdate6AddPost operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate6AddPost,
                new ImmutableMap.Builder<String, Object>()
                        .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.getPostId()))
                        .put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.getImageFile()))
                        .put(LdbcUpdate6AddPost.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
                        .put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                        .put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                        .put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.getLanguage()))
                        .put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.getContent()))
                        .put(LdbcUpdate6AddPost.LENGTH, getConverter().convertInteger(operation.getLength()))
                        .put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
                        .put(LdbcUpdate6AddPost.FORUM_ID, getConverter().convertId(operation.getForumId()))
                        .put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertId(operation.getCountryId()))
                        .build()
                )
        );
        for (long tagId : operation.getTagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate6AddPostTags,
                    ImmutableMap.of(
                            LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.getPostId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    /**
     * Get prepared UpdateQuery7 string
     * This is used for system requiring multiple updates.
     * @param operation UpdateQuery7 operation containing parameter values
     * @return List of prepared UpdateQuery7 strings
     */
    public List<String> getUpdate7Multiple(LdbcUpdate7AddComment operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate7AddComment,
                new ImmutableMap.Builder<String, Object>()
                        .put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()))
                        .put(LdbcUpdate7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
                        .put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                        .put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                        .put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.getContent()))
                        .put(LdbcUpdate7AddComment.LENGTH, getConverter().convertInteger(operation.getLength()))
                        .put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
                        .put(LdbcUpdate7AddComment.COUNTRY_ID, getConverter().convertId(operation.getCountryId()))
                        .put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.getReplyToPostId()))
                        .put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.getReplyToCommentId()))
                        .build()
        ));
        for (long tagId : operation.getTagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate7AddCommentTags,
                    ImmutableMap.of(
                            LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    // Deletions
    /**
     * Get prepared DeleteQuery1 string
     * @param operation LdbcDelete1RemovePerson operation containing parameter values
     * @return Prepared DeleteQuery1 string
     */
    public String getDelete1(LdbcDelete1RemovePerson operation) {
        return prepare(
                QueryType.InteractiveDelete1, getDelete1Map(operation)
        );
    }

    /**
     * Get DeleteQuery1 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete1RemovePerson operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete1Map(LdbcDelete1RemovePerson operation) {
        return ImmutableMap.of(
            LdbcDelete1RemovePerson.PERSON_ID, getConverter().convertId(operation.getremovePersonIdD1())
        );
    }

    /**
     * Get prepared DeleteQuery2 string
     * @param operation LdbcDelete2RemovePostLike operation containing parameter values
     * @return Prepared DeleteQuery2 string
     */
    public String getDelete2(LdbcDelete2RemovePostLike operation) {
        return prepare(
                QueryType.InteractiveDelete2, getDelete2Map(operation)
        );
    }

    /**
     * Get DeleteQuery2 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete2RemovePostLike operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete2Map(LdbcDelete2RemovePostLike operation) {
        return ImmutableMap.of(
            LdbcDelete2RemovePostLike.PERSON_ID, getConverter().convertId(operation.getremovePersonIdD2()),
            LdbcDelete2RemovePostLike.POST_ID, getConverter().convertId(operation.getremovePostIdD2())
        );
    }

    /**
     * Get prepared DeleteQuery3 string
     * @param operation LdbcDelete3RemoveCommentLike operation containing parameter values
     * @return Prepared DeleteQuery3 string
     */
    public String getDelete3(LdbcDelete3RemoveCommentLike operation) {
        return prepare(
                QueryType.InteractiveDelete3, getDelete3Map(operation)
        );
    }

    /**
     * Get DeleteQuery3 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete3RemoveCommentLike operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete3Map(LdbcDelete3RemoveCommentLike operation) {
        return ImmutableMap.of(
            LdbcDelete3RemoveCommentLike.PERSON_ID, getConverter().convertId(operation.getremovePersonIdD3()),
            LdbcDelete3RemoveCommentLike.COMMENT_ID, getConverter().convertId(operation.getremoveCommentIdD3())
        );
    }

    /**
     * Get prepared DeleteQuery4 string
     * @param operation LdbcDelete4RemoveForum operation containing parameter values
     * @return Prepared DeleteQuery4 string
     */
    public String getDelete4(LdbcDelete4RemoveForum operation) {
        return prepare(
                QueryType.InteractiveDelete4, getDelete4Map(operation)
        );
    }

    /**
     * Get DeleteQuery4 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete4RemoveForum operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete4Map(LdbcDelete4RemoveForum operation) {
        return ImmutableMap.of(
            LdbcDelete4RemoveForum.FORUM_ID, getConverter().convertId(operation.getremoveForumIdD4())
        );
    }

    /**
     * Get prepared DeleteQuery5 string
     * @param operation LdbcDelete5RemoveForumMembership operation containing parameter values
     * @return Prepared DeleteQuery5 string
     */
    public String getDelete5(LdbcDelete5RemoveForumMembership operation) {
        return prepare(
                QueryType.InteractiveDelete5, getDelete5Map(operation)
        );
    }

    /**
     * Get DeleteQuery5 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete5RemoveForumMembership operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete5Map(LdbcDelete5RemoveForumMembership operation) {
        return ImmutableMap.of(
            LdbcDelete5RemoveForumMembership.FORUM_ID, getConverter().convertId(operation.getremoveForumIdD5()),
            LdbcDelete5RemoveForumMembership.PERSON_ID, getConverter().convertId(operation.getremovePersonIdD5())
        );
    }

    /**
     * Get prepared DeleteQuery6 string
     * @param operation LdbcDelete6RemovePostThread operation containing parameter values
     * @return Prepared DeleteQuery6 string
     */
    public String getDelete6(LdbcDelete6RemovePostThread operation) {
        return prepare(
                QueryType.InteractiveDelete6, getDelete6Map(operation)
        );
    }

    /**
     * Get DeleteQuery6 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete6RemovePostThread operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete6Map(LdbcDelete6RemovePostThread operation) {
        return ImmutableMap.of(
            LdbcDelete6RemovePostThread.POST_ID, getConverter().convertId(operation.getremovePostIdD6())
        );
    }

    /**
     * Get prepared DeleteQuery7 string
     * @param operation LdbcDelete7RemoveCommentSubthread operation containing parameter values
     * @return Prepared DeleteQuery7 string
     */
    public String getDelete7(LdbcDelete7RemoveCommentSubthread operation) {
        return prepare(
                QueryType.InteractiveDelete7, getDelete7Map(operation)
        );
    }

    /**
     * Get DeleteQuery7 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete7RemoveCommentSubthread operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete7Map(LdbcDelete7RemoveCommentSubthread operation) {
        return ImmutableMap.of(
            LdbcDelete7RemoveCommentSubthread.COMMENT_ID, getConverter().convertId(operation.getremoveCommentIdD7())
        );
    }

    /**
     * Get prepared DeleteQuery8 string
     * @param operation LdbcDelete8RemoveFriendship operation containing parameter values
     * @return Prepared DeleteQuery8 string
     */
    public String getDelete8(LdbcDelete8RemoveFriendship operation) {
        return prepare(
                QueryType.InteractiveDelete8, getDelete8Map(operation)
        );
    }

    /**
     * Get DeleteQuery8 Map. This map contain the name of the parameter and the value as string.
     * @param operation LdbcDelete8RemoveFriendship operation containing parameter values
     * @return Map with parameters and values as string.
     */
    public Map<String, Object> getDelete8Map(LdbcDelete8RemoveFriendship operation) {
        return ImmutableMap.of(
            LdbcDelete8RemoveFriendship.PERSON1_ID, getConverter().convertId(operation.getremovePerson1Id()),
            LdbcDelete8RemoveFriendship.PERSON2_ID, getConverter().convertId(operation.getremovePerson2Id())
        );
    }

}
