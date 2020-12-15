package com.ldbc.impls.workloads.ldbc.snb;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
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
        InteractiveComplexQuery3DurationAsFunction ("interactive-complex-3-duration-as-function" ),
        InteractiveComplexQuery4DurationAsFunction ("interactive-complex-4-duration-as-function" ),
        InteractiveComplexQuery7WithSecond("interactive-complex-7-with-second"),

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
        InteractiveUpdate1AddPerson            ("interactive-update-1-add-person"),
        InteractiveUpdate1AddPersonCompanies   ("interactive-update-1-add-person-companies"),
        InteractiveUpdate1AddPersonEmails      ("interactive-update-1-add-person-emails"),
        InteractiveUpdate1AddPersonLanguages   ("interactive-update-1-add-person-languages"),
        InteractiveUpdate1AddPersonTags        ("interactive-update-1-add-person-tags"),
        InteractiveUpdate1AddPersonUniversities("interactive-update-1-add-person-universities"),
        InteractiveUpdate4AddForum             ("interactive-update-4-add-forum"),
        InteractiveUpdate4AddForumTags         ("interactive-update-4-add-forum-tags"),
        InteractiveUpdate6AddPost              ("interactive-update-6-add-post"),
        InteractiveUpdate6AddPostTags          ("interactive-update-6-add-post-tags"),
        InteractiveUpdate7AddComment           ("interactive-update-7-add-comment"),
        InteractiveUpdate7AddCommentTags       ("interactive-update-7-add-comment-tags"),

        // interactive updates (additional queries for system that insert content/imageFile as separated operation)
        InteractiveUpdate6AddPostContent       ("interactive-update-6-add-post-content"),
        InteractiveUpdate6AddPostImageFile     ("interactive-update-6-add-post-imagefile"),

        // interactive deletes
        InteractiveDelete1("interactive-delete-1"),
        InteractiveDelete2("interactive-delete-2"),
        InteractiveDelete3("interactive-delete-3"),
        InteractiveDelete4("interactive-delete-4"),
        InteractiveDelete5("interactive-delete-5"),
        InteractiveDelete6("interactive-delete-6"),
        InteractiveDelete7("interactive-delete-7"),
        InteractiveDelete8("interactive-delete-8"),

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

    public String getQuery3DurationAsFunction(LdbcQuery3 operation) {
        return prepare(QueryType.InteractiveComplexQuery3DurationAsFunction, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("countryXName", getConverter().convertString(operation.countryXName()))
                .put("countryYName", getConverter().convertString(operation.countryYName()))
                .put("startDate", getConverter().convertDateTime(operation.startDate()))
                .put("durationDays", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery4DurationAsFunction(LdbcQuery4 operation) {
        return prepare(QueryType.InteractiveComplexQuery4DurationAsFunction, new ImmutableMap.Builder<String, String>()
                .put("personId", getConverter().convertId(operation.personId()))
                .put("startDate", getConverter().convertDateTime(operation.startDate()))
                .put("durationDays", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery7WithSecond(LdbcQuery7 operation) {
        return prepare(QueryType.InteractiveComplexQuery7WithSecond, new ImmutableMap.Builder<String, String>()
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
                        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()))
                        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.personFirstName()))
                        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.personLastName()))
                        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.gender()))
                        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDate(operation.birthday()))
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
                        LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.forumId()),
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
                        .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.postId()))
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
                        .put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.commentId()))
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
                QueryType.InteractiveUpdate1AddPerson,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()))
                        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.personFirstName()))
                        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.personLastName()))
                        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.gender()))
                        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDate(operation.birthday()))
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
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()),
                            "organizationId", getConverter().convertId(organization.organizationId()),
                            "worksFromYear", getConverter().convertInteger(organization.year())
                    )
            ));
        }
        for (String email : operation.emails()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonEmails,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()),
                            "email", getConverter().convertString(email)
                    )
            ));
        }
        for (String language : operation.languages()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonLanguages,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()),
                            "language", getConverter().convertString(language)
                    )
            ));
        }

        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonTags,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        for (LdbcUpdate1AddPerson.Organization organization : operation.studyAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonUniversities,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()),
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
                QueryType.InteractiveUpdate4AddForum,
                ImmutableMap.of(
                        LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.forumId()),
                        LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.forumTitle()),
                        LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()),
                        LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.moderatorPersonId())
                )
        ));

        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate4AddForumTags,
                    ImmutableMap.of(
                            LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.forumId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    public List<String> getUpdate6Multiple(LdbcUpdate6AddPost operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate6AddPost,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.postId()))
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
                            LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.postId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    public List<String> getUpdate6MultipleSeparatedContent(LdbcUpdate6AddPost operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate6AddPost,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.postId()))
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
                            LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.postId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        // Check the query, and you will see why convertId needed instead of convertIdForInsertion
        list.add(prepare(QueryType.InteractiveUpdate6AddPostContent,
                ImmutableMap.of(
                        LdbcUpdate6AddPost.POST_ID, getConverter().convertId(operation.postId()),
                        LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.content()))
                )
        );
        list.add(prepare(QueryType.InteractiveUpdate6AddPostImageFile,
                ImmutableMap.of(
                        LdbcUpdate6AddPost.POST_ID, getConverter().convertId(operation.postId()),
                        LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.imageFile()))
                )
        );

        return list;
    }

    public List<String> getUpdate7Multiple(LdbcUpdate7AddComment operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate7AddComment,
                new ImmutableMap.Builder<String, String>()
                        .put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.commentId()))
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
                            LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.commentId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    // Interactive deletes

    public String getDelete1(LdbcDelete1RemovePerson operation) {
        return prepare(
                QueryType.InteractiveDelete1,
                ImmutableMap.of(
                        LdbcDelete1RemovePerson.PERSON_ID, getConverter().convertId(operation.personId())
                )
        );
    }

    public String getDelete2(LdbcDelete2RemovePostLike operation) {
        return prepare(
                QueryType.InteractiveDelete2,
                ImmutableMap.of(
                        LdbcDelete2RemovePostLike.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcDelete2RemovePostLike.POST_ID, getConverter().convertId(operation.postId())
                )
        );
    }

    public String getDelete3(LdbcDelete3RemoveCommentLike operation) {
        return prepare(
                QueryType.InteractiveDelete3,
                ImmutableMap.of(
                        LdbcDelete3RemoveCommentLike.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcDelete3RemoveCommentLike.COMMENT_ID, getConverter().convertId(operation.personId())
                )
        );
    }

    public String getDelete4(LdbcDelete4RemoveForum operation) {
        return prepare(
                QueryType.InteractiveDelete4,
                ImmutableMap.of(
                        LdbcDelete4RemoveForum.FORUM_ID, getConverter().convertId(operation.forumId())
                )
        );
    }

    public String getDelete5(LdbcDelete5RemoveForumMembership operation) {
        return prepare(
                QueryType.InteractiveDelete5,
                ImmutableMap.of(
                        LdbcDelete5RemoveForumMembership.FORUM_ID, getConverter().convertId(operation.forumId()),
                        LdbcDelete5RemoveForumMembership.PERSON_ID, getConverter().convertId(operation.personId())
                )
        );
    }

    public String getDelete6(LdbcDelete6RemovePost operation) {
        return prepare(
                QueryType.InteractiveDelete6,
                ImmutableMap.of(
                        LdbcDelete6RemovePost.POST_ID, getConverter().convertId(operation.postId())
                )
        );
    }

    public String getDelete7(LdbcDelete7RemoveComment operation) {
        return prepare(
                QueryType.InteractiveDelete7,
                ImmutableMap.of(
                        LdbcDelete7RemoveComment.COMMENT_ID, getConverter().convertId(operation.commentId())
                )
        );
    }

    public String getDelete8(LdbcDelete8RemoveFriendship operation) {
        return prepare(
                QueryType.InteractiveDelete8,
                ImmutableMap.of(
                        LdbcDelete8RemoveFriendship.PERSON1_ID, getConverter().convertId(operation.person1Id()),
                        LdbcDelete8RemoveFriendship.PERSON2_ID, getConverter().convertId(operation.person2Id())
                )
        );
    }

    // BI queries

    public String getQuery1(LdbcSnbBiQuery1PostingSummary operation) {
        return prepare(QueryType.BiQuery1, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery1PostingSummary.DATETIME, getConverter().convertDateTime(operation.datetime()))
                .build());
    }

    public String getQuery2(LdbcSnbBiQuery2TagEvolution operation) {
        return prepare(QueryType.BiQuery2, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery2TagEvolution.YEAR, getConverter().convertInteger(operation.year()))
                .put(LdbcSnbBiQuery2TagEvolution.MONTH, getConverter().convertInteger(operation.month()))
                .put(LdbcSnbBiQuery2TagEvolution.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .build());
    }

    public String getQuery3(LdbcSnbBiQuery3PopularCountryTopics operation) {
        return prepare(QueryType.BiQuery3, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery3PopularCountryTopics.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .put(LdbcSnbBiQuery3PopularCountryTopics.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery4(LdbcSnbBiQuery4TopCountryPosters operation) {
        return prepare(QueryType.BiQuery4, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery4TopCountryPosters.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery5(LdbcSnbBiQuery5ActivePosters operation) {
        return prepare(QueryType.BiQuery5, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery5ActivePosters.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery6(LdbcSnbBiQuery6AuthoritativeUsers operation) {
        return prepare(QueryType.BiQuery6, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery6AuthoritativeUsers.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery7(LdbcSnbBiQuery7RelatedTopics operation) {
        return prepare(QueryType.BiQuery7, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery7RelatedTopics.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery8(LdbcSnbBiQuery8TagPerson operation) {
        return prepare(QueryType.BiQuery8, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery8TagPerson.TAG, getConverter().convertString(operation.tag()))
                .put(LdbcSnbBiQuery8TagPerson.DATE, getConverter().convertDateTime(operation.date()))
                .build());
    }

    public String getQuery9(LdbcSnbBiQuery9TopThreadInitiators operation) {
        return prepare(QueryType.BiQuery9, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery9TopThreadInitiators.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery9TopThreadInitiators.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery10(LdbcSnbBiQuery10ExpertsInSocialCircle operation) {
        return prepare(QueryType.BiQuery10, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery10ExpertsInSocialCircle.PERSON_ID, getConverter().convertId(operation.personId()))
                .put(LdbcSnbBiQuery10ExpertsInSocialCircle.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery10ExpertsInSocialCircle.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .put(LdbcSnbBiQuery10ExpertsInSocialCircle.MIN_PATH_DISTANCE, getConverter().convertInteger(operation.minPathDistance()))
                .put(LdbcSnbBiQuery10ExpertsInSocialCircle.MAX_PATH_DISTANCE, getConverter().convertInteger(operation.maxPathDistance()))
                .build());
    }

    public String getQuery11(LdbcSnbBiQuery11FriendshipTriangles operation) {
        return prepare(QueryType.BiQuery11, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery11FriendshipTriangles.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery11FriendshipTriangles.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .build());
    }

    public String getQuery12(LdbcSnbBiQuery12PersonPostCounts operation) {
        return prepare(QueryType.BiQuery12, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery12PersonPostCounts.DATE, getConverter().convertDateTime(operation.date()))
                .put(LdbcSnbBiQuery12PersonPostCounts.LENGTH_THRESHOLD, getConverter().convertInteger(operation.lengthThreshold()))
                .put(LdbcSnbBiQuery12PersonPostCounts.LANGUAGES, getConverter().convertStringList(operation.languages()))
                .build());
    }

    public String getQuery13(LdbcSnbBiQuery13Zombies operation) {
        return prepare(QueryType.BiQuery13, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery13Zombies.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery13Zombies.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery14(LdbcSnbBiQuery14InternationalDialog operation) {
        return prepare(QueryType.BiQuery14, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery14InternationalDialog.COUNTRY1, getConverter().convertString(operation.country1()))
                .put(LdbcSnbBiQuery14InternationalDialog.COUNTRY2, getConverter().convertString(operation.country2()))
                .build());
    }

    public String getQuery15(LdbcSnbBiQuery15WeightedPaths operation) {
        return prepare(QueryType.BiQuery15, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery15WeightedPaths.PERSON1_ID, getConverter().convertId(operation.person1Id()))
                .put(LdbcSnbBiQuery15WeightedPaths.PERSON2_ID, getConverter().convertId(operation.person2Id()))
                .put(LdbcSnbBiQuery15WeightedPaths.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery15WeightedPaths.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery16(LdbcSnbBiQuery16FakeNewsDetection operation) {
        return prepare(QueryType.BiQuery16, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery16FakeNewsDetection.TAG_A, getConverter().convertString(operation.tagA()))
                .put(LdbcSnbBiQuery16FakeNewsDetection.DATE_A, getConverter().convertDateTime(operation.dateA()))
                .put(LdbcSnbBiQuery16FakeNewsDetection.TAG_B, getConverter().convertString(operation.tagB()))
                .put(LdbcSnbBiQuery16FakeNewsDetection.DATE_B, getConverter().convertDateTime(operation.dateB()))
                .put(LdbcSnbBiQuery16FakeNewsDetection.MAX_KNOWS_LIMIT, getConverter().convertInteger(operation.maxKnowsLimit()))
                .put(LdbcSnbBiQuery16FakeNewsDetection.LIMIT, getConverter().convertInteger(operation.limit()))
                .build());
    }

    public String getQuery17(LdbcSnbBiQuery17InformationPropagationAnalysis operation) {
        return prepare(QueryType.BiQuery17, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery17InformationPropagationAnalysis.TAG, getConverter().convertString(operation.tag()))
                .put(LdbcSnbBiQuery17InformationPropagationAnalysis.DELTA, getConverter().convertInteger(operation.delta()))
                .put(LdbcSnbBiQuery17InformationPropagationAnalysis.LIMIT, getConverter().convertInteger(operation.limit()))
                .build());
    }

    public String getQuery18(LdbcSnbBiQuery18FriendRecommendation operation) {
        return prepare(QueryType.BiQuery18, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery18FriendRecommendation.PERSON1_ID, getConverter().convertId(operation.person1Id()))
                .put(LdbcSnbBiQuery18FriendRecommendation.TAG, getConverter().convertString(operation.tag()))
                .put(LdbcSnbBiQuery18FriendRecommendation.LIMIT, getConverter().convertInteger(operation.limit()))
                .build());
    }

    public String getQuery19(LdbcSnbBiQuery19InteractionPathBetweenCities operation) {
        return prepare(QueryType.BiQuery19, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery19InteractionPathBetweenCities.CITY1_ID, getConverter().convertId(operation.city1Id()))
                .put(LdbcSnbBiQuery19InteractionPathBetweenCities.CITY2_ID, getConverter().convertId(operation.city2Id()))
                .build());
    }

    public String getQuery20(LdbcSnbBiQuery20Recruitment operation) {
        return prepare(QueryType.BiQuery20, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery20Recruitment.COMPANY, getConverter().convertString(operation.company()))
                .put(LdbcSnbBiQuery20Recruitment.PERSON2_ID, getConverter().convertId(operation.person2Id()))
                .build());
    }

}
