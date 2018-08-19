package com.ldbc.impls.workloads.ldbc.snb;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedReplies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircle;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteraction;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21Zombies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialog;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinations;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopic;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPaths;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate5AddForumMembership;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Superclass of query stores.
 *
 * Note: we deliberately do not use the {@link com.ldbc.driver.Operation#parameterMap()} method, because
 *
 * <ol>
 *   <li>some formats necessitate to handle ids differently from simple longs</li>
 *   <li>we would like to avoid any costs for type checking (e.g. using instanceof for each value)</li>
 * </ol>
 */
public abstract class QueryStore {

    protected Converter getConverter() { return new Converter(); }

    protected String getParameterPrefix() { return "$"; }

    protected String getParameterPostfix() { return ""; }

    protected Map<QueryType, String> queries = new HashMap<>();

    protected String loadQueryFromFile(String path, String filename) throws DbException {
        final String filePath = path + File.separator + filename;
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            //throw new DbException("Could not load query: " + filePath);
            return "";
        }
    }

    protected String prepare(QueryType queryType, Map<String, String> parameterSubstitutions) {
        String querySpecification = queries.get(queryType);
        for (String parameter : parameterSubstitutions.keySet()) {
            querySpecification = querySpecification.replace(
                    getParameterPrefix() + parameter + getParameterPostfix(),
                    parameterSubstitutions.get(parameter)
            );
        }
        return querySpecification;
    }

    public enum QueryType {

        // interactive complex queries
        InteractiveComplexQuery1 ("interactive-complex-1" ),
        InteractiveComplexQuery2 ("interactive-complex-2" ),
        InteractiveComplexQuery3 ("interactive-complex-3" ),
        InteractiveComplexQuery4 ("interactive-complex-4" ),
        InteractiveComplexQuery5 ("interactive-complex-5" ),
        InteractiveComplexQuery6 ("interactive-complex-6" ),
        InteractiveComplexQuery7 ("interactive-complex-7" ),
        InteractiveComplexQuery8 ("interactive-complex-8" ),
        InteractiveComplexQuery9 ("interactive-complex-9" ),
        InteractiveComplexQuery10("interactive-complex-10"),
        InteractiveComplexQuery11("interactive-complex-11"),
        InteractiveComplexQuery12("interactive-complex-12"),
        InteractiveComplexQuery13("interactive-complex-13"),
        InteractiveComplexQuery14("interactive-complex-14"),

        // interactive short queries
        InteractiveShortQuery1("interactive-short-1"),
        InteractiveShortQuery2("interactive-short-2"),
        InteractiveShortQuery3("interactive-short-3"),
        InteractiveShortQuery4("interactive-short-4"),
        InteractiveShortQuery5("interactive-short-5"),
        InteractiveShortQuery6("interactive-short-6"),
        InteractiveShortQuery7("interactive-short-7"),

        // interactive updates (single queries)
        InteractiveUpdate1("interactive-update-1"),
        InteractiveUpdate2("interactive-update-2"),
        InteractiveUpdate3("interactive-update-3"),
        InteractiveUpdate4("interactive-update-4"),
        InteractiveUpdate5("interactive-update-5"),
        InteractiveUpdate6("interactive-update-6"),
        InteractiveUpdate7("interactive-update-7"),
        InteractiveUpdate8("interactive-update-8"),

        // interactive updates (additional queries for systems that perform them as multiple queries)
        InteractiveUpdate1AddPersonCompanies   ("interactive-update-1-add-person-companies"),
        InteractiveUpdate1AddPersonEmails      ("interactive-update-1-add-person-emails"),
        InteractiveUpdate1AddPersonLanguages   ("interactive-update-1-add-person-languages"),
        InteractiveUpdate1AddPersonTags        ("interactive-update-1-add-person-tags"),
        InteractiveUpdate1AddPersonUniversities("interactive-update-1-add-person-universities"),
        InteractiveUpdate4AddForumTags         ("interactive-update-4-add-forum-tags"),
        InteractiveUpdate6AddPostTags          ("interactive-update-6-add-post-tags"),
        InteractiveUpdate7AddCommentTags       ("interactive-update-7-add-comment-tags"),

        // BI
        BiQuery1 ("bi-1" ),
        BiQuery2 ("bi-2" ),
        BiQuery3 ("bi-3" ),
        BiQuery4 ("bi-4" ),
        BiQuery5 ("bi-5" ),
        BiQuery6 ("bi-6" ),
        BiQuery7 ("bi-7" ),
        BiQuery8 ("bi-8" ),
        BiQuery9 ("bi-9" ),
        BiQuery10("bi-10"),
        BiQuery11("bi-11"),
        BiQuery12("bi-12"),
        BiQuery13("bi-13"),
        BiQuery14("bi-14"),
        BiQuery15("bi-15"),
        BiQuery16("bi-16"),
        BiQuery17("bi-17"),
        BiQuery18("bi-18"),
        BiQuery19("bi-19"),
        BiQuery20("bi-20"),
        BiQuery21("bi-21"),
        BiQuery22("bi-22"),
        BiQuery23("bi-23"),
        BiQuery24("bi-24"),
        BiQuery25("bi-25"),
        ;

        private String name;

        QueryType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public QueryStore(String path, String postfix) throws DbException {
        for (QueryType queryType : QueryType.values()) {
            queries.put(queryType, loadQueryFromFile(path, queryType.getName() + postfix));
        }
    }

    // Interactive complex reads

    public String getQuery1(LdbcQuery1 operation) {
        return prepare(QueryType.InteractiveComplexQuery1, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("firstName", getConverter().convertString(operation.firstName()))
                .build());
    }

    public String getQuery2(LdbcQuery2 operation) {
        return prepare(QueryType.InteractiveComplexQuery2, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("maxDate", getConverter().convertDateTime(operation.maxDate()))
                .build());
    }

    public String getQuery3(LdbcQuery3 operation) {
        return prepare(QueryType.InteractiveComplexQuery3, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("countryXName", getConverter().convertString(operation.countryXName()))
                .put("countryYName", getConverter().convertString(operation.countryYName()))
                .put("startDate", getConverter().convertDateTime(operation.startDate()))
                .put("durationDays", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery4(LdbcQuery4 operation) {
        return prepare(QueryType.InteractiveComplexQuery4, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("startDate", getConverter().convertDateTime(operation.startDate()))
                .put("durationDays", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery5(LdbcQuery5 operation) {
        return prepare(QueryType.InteractiveComplexQuery5, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("minDate", getConverter().convertDateTime(operation.minDate()))
                .build());
    }

    public String getQuery6(LdbcQuery6 operation) {
        return prepare(QueryType.InteractiveComplexQuery6, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("tagName", getConverter().convertString(operation.tagName()))
                .build());
    }

    public String getQuery7(LdbcQuery7 operation) {
        return prepare(QueryType.InteractiveComplexQuery7, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .build());
    }

    public String getQuery8(LdbcQuery8 operation) {
        return prepare(QueryType.InteractiveComplexQuery8, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .build());
    }

    public String getQuery9(LdbcQuery9 operation) {
        return prepare(QueryType.InteractiveComplexQuery9, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("maxDate", getConverter().convertDateTime(operation.maxDate()))
                .build());
    }

    public String getQuery10(LdbcQuery10 operation) {
        return prepare(QueryType.InteractiveComplexQuery10, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("month", getConverter().convertInteger(operation.month()))
                .put("nextMonth", getConverter().convertInteger(operation.month() % 12 + 1))
                .build());
    }

    public String getQuery11(LdbcQuery11 operation) {
        return prepare(QueryType.InteractiveComplexQuery11, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("countryName", getConverter().convertString(operation.countryName()))
                .put("workFromYear", getConverter().convertInteger(operation.workFromYear()))
                .build());
    }

    public String getQuery12(LdbcQuery12 operation) {
        return prepare(QueryType.InteractiveComplexQuery12, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("tagClassName", getConverter().convertString(operation.tagClassName()))
                .build());
    }

    public String getQuery13(LdbcQuery13 operation) {
        return prepare(QueryType.InteractiveComplexQuery13, new ImmutableMap.Builder<String, String>()
                .put("person1Id", getConverter().convertId(operation.person1Id()))
                .put("person2Id", getConverter().convertId(operation.person2Id()))
                .build());
    }

    public String getQuery14(LdbcQuery14 operation) {
        return prepare(QueryType.InteractiveComplexQuery14, new ImmutableMap.Builder<String, String>()
                .put("person1Id", getConverter().convertId(operation.person1Id()))
                .put("person2Id", getConverter().convertId(operation.person2Id()))
                .build());
    }

    // Interactive short reads

    public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
        return prepare(
                QueryType.InteractiveShortQuery1,
                ImmutableMap.of(LdbcShortQuery1PersonProfile.PERSON_ID, getConverter().convertId(operation.personId()))
        );
    }

    public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
        return prepare(
                QueryType.InteractiveShortQuery2,
                ImmutableMap.of(LdbcShortQuery2PersonPosts.PERSON_ID, getConverter().convertId(operation.personId()))
        );
    }

    public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
        return prepare(
                QueryType.InteractiveShortQuery3,
                ImmutableMap.of(LdbcShortQuery3PersonFriends.PERSON_ID, getConverter().convertId(operation.personId()))
        );
    }

    public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
        return prepare(
                QueryType.InteractiveShortQuery4,
                ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
        return prepare(
                QueryType.InteractiveShortQuery5,
                ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
        return prepare(
                QueryType.InteractiveShortQuery6,
                ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
        return prepare(
                QueryType.InteractiveShortQuery7,
                ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    // Interactive updates

    // Interactive updates, formulated as a single query each

    public String getUpdate1Single(LdbcUpdate1AddPerson operation) {
        return prepare(
                QueryType.InteractiveUpdate1,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()))
                        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.personFirstName()))
                        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.personLastName()))
                        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.gender()))
                        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDateTime(operation.birthday()))
                        .put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
                        .put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.locationIp()))
                        .put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
                        .put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertId(operation.cityId()))
                        .put(LdbcUpdate1AddPerson.WORK_AT, getConverter().convertOrganisations(operation.workAt()))
                        .put(LdbcUpdate1AddPerson.STUDY_AT, getConverter().convertOrganisations(operation.studyAt()))
                        .put(LdbcUpdate1AddPerson.EMAILS, getConverter().convertStringList(operation.emails()))
                        .put(LdbcUpdate1AddPerson.LANGUAGES, getConverter().convertStringList(operation.languages()))
                        .put(LdbcUpdate1AddPerson.TAG_IDS, getConverter().convertLongList(operation.tagIds()))
                        .build()
        );
    }

    public String getUpdate2(LdbcUpdate2AddPostLike operation) {
        return prepare(
                QueryType.InteractiveUpdate2,
                ImmutableMap.of(
                        LdbcUpdate2AddPostLike.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcUpdate2AddPostLike.POST_ID, getConverter().convertId(operation.postId()),
                        LdbcUpdate2AddPostLike.CREATION_DATE, getConverter().convertDateTime(operation.creationDate())
                )
        );
    }

    public String getUpdate3(LdbcUpdate3AddCommentLike operation) {
        return prepare(
                QueryType.InteractiveUpdate3,
                ImmutableMap.of(
                        LdbcUpdate3AddCommentLike.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcUpdate3AddCommentLike.COMMENT_ID, getConverter().convertId(operation.commentId()),
                        LdbcUpdate3AddCommentLike.CREATION_DATE, getConverter().convertDateTime(operation.creationDate())
                )
        );
    }

    public String getUpdate4Single(LdbcUpdate4AddForum operation) {
        return prepare(
                QueryType.InteractiveUpdate4,
                ImmutableMap.of(
                        LdbcUpdate4AddForum.FORUM_ID, getConverter().convertId(operation.forumId()),
                        LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.forumTitle()),
                        LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()),
                        LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.moderatorPersonId()),
                        LdbcUpdate4AddForum.TAG_IDS, getConverter().convertLongList(operation.tagIds())
                )
        );
    }


    public String getUpdate5(LdbcUpdate5AddForumMembership operation) {
        return prepare(
                QueryType.InteractiveUpdate5,
                ImmutableMap.of(
                        LdbcUpdate5AddForumMembership.FORUM_ID, getConverter().convertId(operation.forumId()),
                        LdbcUpdate5AddForumMembership.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcUpdate5AddForumMembership.JOIN_DATE, getConverter().convertDateTime(operation.joinDate())
                )
        );
    }

    public String getUpdate6Single(LdbcUpdate6AddPost operation) {
        return prepare(
                QueryType.InteractiveUpdate6,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertId(operation.postId()))
                        .put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.imageFile()))
                        .put(LdbcUpdate6AddPost.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
                        .put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.locationIp()))
                        .put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
                        .put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.language()))
                        .put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.content()))
                        .put(LdbcUpdate6AddPost.LENGTH, getConverter().convertInteger(operation.length()))
                        .put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, getConverter().convertId(operation.authorPersonId()))
                        .put(LdbcUpdate6AddPost.FORUM_ID, getConverter().convertId(operation.forumId()))
                        .put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertId(operation.countryId()))
                        .put(LdbcUpdate6AddPost.TAG_IDS, getConverter().convertLongList(operation.tagIds()))
                        .build()
        );
    }

    public String getUpdate7Single(LdbcUpdate7AddComment operation) {
        return prepare(
                QueryType.InteractiveUpdate7,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertId(operation.commentId()))
                        .put(LdbcUpdate7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
                        .put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.locationIp()))
                        .put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
                        .put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.content()))
                        .put(LdbcUpdate7AddComment.LENGTH, getConverter().convertInteger(operation.length()))
                        .put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.authorPersonId()))
                        .put(LdbcUpdate7AddComment.COUNTRY_ID, getConverter().convertId(operation.countryId()))
                        .put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.replyToPostId()))
                        .put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.replyToCommentId()))
                        .put(LdbcUpdate7AddComment.TAG_IDS, getConverter().convertLongList(operation.tagIds()))
                        .build()
        );
    }

    public String getUpdate8(LdbcUpdate8AddFriendship operation) {
        return prepare(
                QueryType.InteractiveUpdate8,
                ImmutableMap.of(
                        LdbcUpdate8AddFriendship.PERSON1_ID, getConverter().convertId(operation.person1Id()),
                        LdbcUpdate8AddFriendship.PERSON2_ID, getConverter().convertId(operation.person2Id()),
                        LdbcUpdate8AddFriendship.CREATION_DATE, getConverter().convertDateTime(operation.creationDate())
                )
        );
    }

    // Interactive updates requiring multiple queries (for some systems)

    public List<String> getUpdate1Multiple(LdbcUpdate1AddPerson operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate1,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()))
                        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.personFirstName()))
                        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.personLastName()))
                        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.gender()))
                        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDateTime(operation.birthday()))
                        .put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
                        .put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.locationIp()))
                        .put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
                        .put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertId(operation.cityId()))
                        .build()
        ));

        for (LdbcUpdate1AddPerson.Organization organization : operation.workAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonCompanies,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "organizationId", getConverter().convertId(organization.organizationId()),
                            "worksFromYear", getConverter().convertInteger(organization.year())
                    )
            ));
        }
        for (String email : operation.emails()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonEmails,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "email", getConverter().convertString(email)
                    )
            ));
        }
        for (String language : operation.languages()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonLanguages,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "language", getConverter().convertString(language)
                    )
            ));
        }

        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonTags,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        for (LdbcUpdate1AddPerson.Organization organization : operation.studyAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonUniversities,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "organizationId", getConverter().convertId(organization.organizationId()),
                            "studiesFromYear", getConverter().convertInteger(organization.year())
                    )
            ));
        }
        return list;
    }

    public List<String> getUpdate4Multiple(LdbcUpdate4AddForum operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate4,
                ImmutableMap.of(
                        LdbcUpdate4AddForum.FORUM_ID, getConverter().convertId(operation.forumId()),
                        LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.forumTitle()),
                        LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()),
                        LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.moderatorPersonId())
                )
        ));

        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate4AddForumTags,
                    ImmutableMap.of(
                            LdbcUpdate4AddForum.FORUM_ID, getConverter().convertId(operation.forumId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    public List<String> getUpdate6Multiple(LdbcUpdate6AddPost operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate6,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertId(operation.postId()))
                        .put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.imageFile()))
                        .put(LdbcUpdate6AddPost.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
                        .put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.locationIp()))
                        .put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
                        .put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.language()))
                        .put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.content()))
                        .put(LdbcUpdate6AddPost.LENGTH, getConverter().convertInteger(operation.length()))
                        .put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, getConverter().convertId(operation.authorPersonId()))
                        .put(LdbcUpdate6AddPost.FORUM_ID, getConverter().convertId(operation.forumId()))
                        .put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertId(operation.countryId()))
                        .build()
                )
        );
        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate6AddPostTags,
                    ImmutableMap.of(
                            LdbcUpdate6AddPost.POST_ID, getConverter().convertId(operation.postId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    public List<String> getUpdate7Multiple(LdbcUpdate7AddComment operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate7,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertId(operation.commentId()))
                        .put(LdbcUpdate7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
                        .put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.locationIp()))
                        .put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
                        .put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.content()))
                        .put(LdbcUpdate7AddComment.LENGTH, getConverter().convertInteger(operation.length()))
                        .put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.authorPersonId()))
                        .put(LdbcUpdate7AddComment.COUNTRY_ID, getConverter().convertId(operation.countryId()))
                        .put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.replyToPostId()))
                        .put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.replyToCommentId()))
                        .build()
        ));
        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate7AddCommentTags,
                    ImmutableMap.of(
                            LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertId(operation.commentId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    // BI queries

    public String getQuery1(LdbcSnbBiQuery1PostingSummary operation) {
        return prepare(QueryType.BiQuery1, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery1PostingSummary.DATE, getConverter().convertDateTime(operation.date()))
                .build());
    }

    public String getQuery2(LdbcSnbBiQuery2TopTags operation) {
        return prepare(QueryType.BiQuery2, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery2TopTags.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery2TopTags.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .put(LdbcSnbBiQuery2TopTags.COUNTRY1, getConverter().convertString(operation.country1()))
                .put(LdbcSnbBiQuery2TopTags.COUNTRY2, getConverter().convertString(operation.country2()))
                .build());
    }

    public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
        return prepare(QueryType.BiQuery3, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery3TagEvolution.YEAR, getConverter().convertInteger(operation.year()))
                .put(LdbcSnbBiQuery3TagEvolution.MONTH, getConverter().convertInteger(operation.month()))
                .build());
    }

    public String getQuery4(LdbcSnbBiQuery4PopularCountryTopics operation) {
        return prepare(QueryType.BiQuery4, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery4PopularCountryTopics.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .put(LdbcSnbBiQuery4PopularCountryTopics.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery5(LdbcSnbBiQuery5TopCountryPosters operation) {
        return prepare(QueryType.BiQuery5, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery5TopCountryPosters.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery6(LdbcSnbBiQuery6ActivePosters operation) {
        return prepare(QueryType.BiQuery6, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery6ActivePosters.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery7(LdbcSnbBiQuery7AuthoritativeUsers operation) {
        return prepare(QueryType.BiQuery7, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery7AuthoritativeUsers.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery8(LdbcSnbBiQuery8RelatedTopics operation) {
        return prepare(QueryType.BiQuery8, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery8RelatedTopics.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery9(LdbcSnbBiQuery9RelatedForums operation) {
        return prepare(QueryType.BiQuery9, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery9RelatedForums.TAG_CLASS1, getConverter().convertString(operation.tagClass1()))
                .put(LdbcSnbBiQuery9RelatedForums.TAG_CLASS2, getConverter().convertString(operation.tagClass2()))
                .put(LdbcSnbBiQuery9RelatedForums.THRESHOLD, getConverter().convertInteger(operation.threshold()))
                .build());
    }

    public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
        return prepare(QueryType.BiQuery10, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery10TagPerson.TAG, getConverter().convertString(operation.tag()))
                .put(LdbcSnbBiQuery10TagPerson.DATE, getConverter().convertDateTime(operation.date()))
                .build());
    }

    public String getQuery11(LdbcSnbBiQuery11UnrelatedReplies operation) {
        return prepare(QueryType.BiQuery11, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery11UnrelatedReplies.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery11UnrelatedReplies.BLACKLIST, getConverter().convertBlacklist(operation.blacklist()))
                .build());
    }

    public String getQuery12(LdbcSnbBiQuery12TrendingPosts operation) {
        return prepare(QueryType.BiQuery12, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery12TrendingPosts.DATE, getConverter().convertDateTime(operation.date()))
                .put(LdbcSnbBiQuery12TrendingPosts.LIKE_THRESHOLD, getConverter().convertInteger(operation.likeThreshold()))
                .build());
    }

    public String getQuery13(LdbcSnbBiQuery13PopularMonthlyTags operation) {
        return prepare(QueryType.BiQuery13, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery13PopularMonthlyTags.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery14(LdbcSnbBiQuery14TopThreadInitiators operation) {
        return prepare(QueryType.BiQuery14, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery14TopThreadInitiators.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery14TopThreadInitiators.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery15(LdbcSnbBiQuery15SocialNormals operation) {
        return prepare(QueryType.BiQuery15, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery15SocialNormals.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery16(LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
        return prepare(QueryType.BiQuery16, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.PERSON_ID, getConverter().convertId(operation.personId()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.MIN_PATH_DISTANCE, getConverter().convertInteger(operation.minPathDistance()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.MAX_PATH_DISTANCE, getConverter().convertInteger(operation.maxPathDistance()))
                .build());
    }

    public String getQuery17(LdbcSnbBiQuery17FriendshipTriangles operation) {
        return prepare(QueryType.BiQuery17, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery17FriendshipTriangles.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery18(LdbcSnbBiQuery18PersonPostCounts operation) {
        return prepare(QueryType.BiQuery18, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery18PersonPostCounts.DATE, getConverter().convertDateTime(operation.date()))
                .put(LdbcSnbBiQuery18PersonPostCounts.LENGTH_THRESHOLD, getConverter().convertInteger(operation.lengthThreshold()))
                .put(LdbcSnbBiQuery18PersonPostCounts.LANGUAGES, getConverter().convertStringList(operation.languages()))
                .build());
    }

    public String getQuery19(LdbcSnbBiQuery19StrangerInteraction operation) {
        return prepare(QueryType.BiQuery19, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery19StrangerInteraction.DATE, getConverter().convertDate(operation.date()))
                .put(LdbcSnbBiQuery19StrangerInteraction.TAG_CLASS1, getConverter().convertString(operation.tagClass1()))
                .put(LdbcSnbBiQuery19StrangerInteraction.TAG_CLASS2, getConverter().convertString(operation.tagClass2()))
                .build());
    }

    public String getQuery20(LdbcSnbBiQuery20HighLevelTopics operation) {
        return prepare(QueryType.BiQuery20, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery20HighLevelTopics.TAG_CLASSES, getConverter().convertStringList(operation.tagClasses()))
                .build());
    }

    public String getQuery21(LdbcSnbBiQuery21Zombies operation) {
        return prepare(QueryType.BiQuery21, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery21Zombies.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery21Zombies.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery22(LdbcSnbBiQuery22InternationalDialog operation) {
        return prepare(QueryType.BiQuery22, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery22InternationalDialog.COUNTRY1, getConverter().convertString(operation.country1()))
                .put(LdbcSnbBiQuery22InternationalDialog.COUNTRY2, getConverter().convertString(operation.country2()))
                .build());
    }

    public String getQuery23(LdbcSnbBiQuery23HolidayDestinations operation) {
        return prepare(QueryType.BiQuery23, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery23HolidayDestinations.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery24(LdbcSnbBiQuery24MessagesByTopic operation) {
        return prepare(QueryType.BiQuery24, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery24MessagesByTopic.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .build());
    }

    public String getQuery25(LdbcSnbBiQuery25WeightedPaths operation) {
        return prepare(QueryType.BiQuery25, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery25WeightedPaths.PERSON1_ID, getConverter().convertId(operation.person1Id()))
                .put(LdbcSnbBiQuery25WeightedPaths.PERSON2_ID, getConverter().convertId(operation.person2Id()))
                .put(LdbcSnbBiQuery25WeightedPaths.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery25WeightedPaths.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }


}
