package com.ldbc.impls.workloads.ldbc.snb.interactive;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
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
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

import java.util.ArrayList;
import java.util.List;

public abstract class InteractiveQueryStore extends QueryStore<InteractiveQueryStore.InteractiveQuery> {

    public enum InteractiveQuery {
        Query1("query1"),
        Query2("query2"),
        Query3("query3"),
        Query4("query4"),
        Query5("query5"),
        Query6("query6"),
        Query7("query7"),
        Query8("query8"),
        Query9("query9"),
        Query10("query10"),
        Query11("query11"),
        Query12("query12"),
        Query13("query13"),
        Query14("query14"),

        ShortQuery1PersonProfile("shortquery1personprofile"),
        ShortQuery2PersonPosts("shortquery2personposts"),
        ShortQuery3PersonFriends("shortquery3personfriends"),
        ShortQuery4MessageContent("shortquery4messagecontent"),
        ShortQuery5MessageCreator("shortquery5messagecreator"),
        ShortQuery6MessageForum("shortquery6messageforum"),
        ShortQuery7MessageReplies("shortquery7messagereplies"),

        Update1AddPerson("update1addperson"),
        Update1AddPersonCompanies("update1addpersoncompanies"),
        Update1AddPersonEmails("update1addpersonemails"),
        Update1AddPersonLanguages("update1addpersonlanguages"),
        Update1AddPersonTags("update1addpersontags"),
        Update1AddPersonUniversities("update1addpersonuniversities"),

        Update2AddPostLike("update2addpostlike"),

        Update3AddCommentLike("update3addcommentlike"),

        Update4AddForum("update4addforum"),
        Update4AddForumTags("update4addforumtags"),

        Update5AddForumMembership("update5addforummembership"),

        Update6AddPost("update6addpost"),
        Update6AddPostTags("update6addposttags"),

        Update7AddComment("update7addcomment"),
        Update7AddCommentTags("update7addcommenttags"),

        Update8AddFriendship("update8addfriendship"),
        ;

        private String name;

        InteractiveQuery(String name) {
            this.name = name;
        }

    }

    public InteractiveQueryStore(String path, String postfix) throws DbException {
        for (InteractiveQuery interactiveQuery : InteractiveQuery.values()) {
            queries.put(interactiveQuery, loadQueryFromFile(path, interactiveQuery.name + postfix));
        }
    }

    // query getters
    public String getQuery1(LdbcQuery1 operation) {
        return prepare(InteractiveQuery.Query1, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Name", getConverter().convertString(operation.firstName()))
                .build());
    }

    public String getQuery2(LdbcQuery2 operation) {
        return prepare(InteractiveQuery.Query2, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.maxDate()))
                .build());
    }

    public String getQuery3(LdbcQuery3 operation) {
        return prepare(InteractiveQuery.Query3, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Country1", getConverter().convertString(operation.countryXName()))
                .put("Country2", getConverter().convertString(operation.countryYName()))
                .put("Date0", getConverter().convertDateTime(operation.startDate()))
                .put("Duration", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery4(LdbcQuery4 operation) {
        return prepare(InteractiveQuery.Query4, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.startDate()))
                .put("Duration", getConverter().convertInteger(operation.durationDays()))
                .build());
    }

    public String getQuery5(LdbcQuery5 operation) {
        return prepare(InteractiveQuery.Query5, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.minDate()))
                .build());
    }

    public String getQuery6(LdbcQuery6 operation) {
        return prepare(InteractiveQuery.Query6, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Tag", getConverter().convertString(operation.tagName()))
                .build());
    }

    public String getQuery7(LdbcQuery7 operation) {
        return prepare(InteractiveQuery.Query7, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .build());
    }

    public String getQuery8(LdbcQuery8 operation) {
        return prepare(InteractiveQuery.Query8, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .build());
    }

    public String getQuery9(LdbcQuery9 operation) {
        return prepare(InteractiveQuery.Query9, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertDateTime(operation.maxDate()))
                .build());
    }

    public String getQuery10(LdbcQuery10 operation) {
        return prepare(InteractiveQuery.Query10, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("HS0", getConverter().convertInteger(operation.month()))
                .put("HS1", getConverter().convertInteger(operation.month() % 12 + 1))
                .build());
    }

    public String getQuery11(LdbcQuery11 operation) {
        return prepare(InteractiveQuery.Query11, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("Date0", getConverter().convertInteger(operation.workFromYear()))
                .put("Country", getConverter().convertString(operation.countryName()))
                .build());
    }

    public String getQuery12(LdbcQuery12 operation) {
        return prepare(InteractiveQuery.Query12, new ImmutableMap.Builder<String, String>()
                .put("Person", getConverter().convertId(operation.personId()))
                .put("TagType", getConverter().convertString(operation.tagClassName()))
                .build());
    }

    public String getQuery13(LdbcQuery13 operation) {
        return prepare(InteractiveQuery.Query13, new ImmutableMap.Builder<String, String>()
                .put("Person1", getConverter().convertId(operation.person1Id()))
                .put("Person2", getConverter().convertId(operation.person2Id()))
                .build());
    }

    public String getQuery14(LdbcQuery14 operation) {
        return prepare(InteractiveQuery.Query14, new ImmutableMap.Builder<String, String>()
                .put("Person1", getConverter().convertId(operation.person1Id()))
                .put("Person2", getConverter().convertId(operation.person2Id()))
                .build());
    }

    public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
        return prepare(
                InteractiveQuery.ShortQuery1PersonProfile,
                ImmutableMap.of(LdbcShortQuery1PersonProfile.PERSON_ID, getConverter().convertId(operation.personId()))
        );
    }

    public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
        return prepare(
                InteractiveQuery.ShortQuery2PersonPosts,
                ImmutableMap.of(LdbcShortQuery2PersonPosts.PERSON_ID, getConverter().convertId(operation.personId()))
        );
    }

    public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
        return prepare(
                InteractiveQuery.ShortQuery3PersonFriends,
                ImmutableMap.of(LdbcShortQuery3PersonFriends.PERSON_ID, getConverter().convertId(operation.personId()))
        );
    }

    public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
        return prepare(
                InteractiveQuery.ShortQuery4MessageContent,
                ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
        return prepare(
                InteractiveQuery.ShortQuery5MessageCreator,
                ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
        return prepare(
                InteractiveQuery.ShortQuery6MessageForum,
                ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
        return prepare(
                InteractiveQuery.ShortQuery7MessageReplies,
                ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID, getConverter().convertId(operation.messageId()))
        );
    }

    public List<String> getUpdate1AddPerson(LdbcUpdate1AddPerson operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                InteractiveQuery.Update1AddPerson,
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
                    InteractiveQuery.Update1AddPersonCompanies,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "organizationId", getConverter().convertId(organization.organizationId()),
                            "worksFromYear", getConverter().convertInteger(organization.year())
                    )
            ));
        }
        for (String email : operation.emails()) {
            list.add(prepare(
                    InteractiveQuery.Update1AddPersonEmails,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "email", getConverter().convertString(email)
                    )
            ));
        }
        for (String language : operation.languages()) {
            list.add(prepare(
                    InteractiveQuery.Update1AddPersonLanguages,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "language", getConverter().convertString(language)
                    )
            ));
        }

        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    InteractiveQuery.Update1AddPersonTags,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        for (LdbcUpdate1AddPerson.Organization organization : operation.studyAt()) {
            list.add(prepare(
                    InteractiveQuery.Update1AddPersonUniversities,
                    ImmutableMap.of(
                            LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertId(operation.personId()),
                            "organizationId", getConverter().convertId(organization.organizationId()),
                            "studiesFromYear", getConverter().convertInteger(organization.year())
                    )
            ));
        }
        return list;
    }

    public String getUpdate2AddPostLike(LdbcUpdate2AddPostLike operation) {
        return prepare(
                InteractiveQuery.Update2AddPostLike,
                ImmutableMap.of(
                        LdbcUpdate2AddPostLike.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcUpdate2AddPostLike.POST_ID, getConverter().convertId(operation.postId()),
                        LdbcUpdate2AddPostLike.CREATION_DATE, getConverter().convertDateTime(operation.creationDate())
                )
        );
    }

    public String getUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation) {
        return prepare(
                InteractiveQuery.Update3AddCommentLike,
                ImmutableMap.of(
                        LdbcUpdate3AddCommentLike.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcUpdate3AddCommentLike.COMMENT_ID, getConverter().convertId(operation.commentId()),
                        LdbcUpdate3AddCommentLike.CREATION_DATE, getConverter().convertDateTime(operation.creationDate())
                )
        );
    }

    public List<String> getUpdate4AddForum(LdbcUpdate4AddForum operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                InteractiveQuery.Update4AddForum,
                ImmutableMap.of(
                        LdbcUpdate4AddForum.FORUM_ID, getConverter().convertId(operation.forumId()),
                        LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.forumTitle()),
                        LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()),
                        LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.moderatorPersonId())
                )
        ));

        for (long tagId : operation.tagIds()) {
            list.add(prepare(
                    InteractiveQuery.Update4AddForumTags,
                    ImmutableMap.of(
                            LdbcUpdate4AddForum.FORUM_ID, getConverter().convertId(operation.forumId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }


    public String getUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation) {
        return prepare(
                InteractiveQuery.Update5AddForumMembership,
                ImmutableMap.of(
                        LdbcUpdate5AddForumMembership.FORUM_ID, getConverter().convertId(operation.forumId()),
                        LdbcUpdate5AddForumMembership.PERSON_ID, getConverter().convertId(operation.personId()),
                        LdbcUpdate5AddForumMembership.JOIN_DATE, getConverter().convertDateTime(operation.joinDate())
                )
        );
    }

    public List<String> getUpdate6AddPost(LdbcUpdate6AddPost operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(InteractiveQuery.Update6AddPost,
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
                    InteractiveQuery.Update6AddPostTags,
                    ImmutableMap.of(
                            LdbcUpdate6AddPost.POST_ID, getConverter().convertId(operation.postId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    public List<String> getUpdate7AddComment(LdbcUpdate7AddComment operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(InteractiveQuery.Update7AddComment,
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
                    InteractiveQuery.Update7AddCommentTags,
                    ImmutableMap.of(
                            LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertId(operation.commentId()),
                            "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        return list;
    }

    public String getUpdate8AddFriendship(LdbcUpdate8AddFriendship operation) {
        return prepare(
                InteractiveQuery.Update8AddFriendship,
                ImmutableMap.of(
                        LdbcUpdate8AddFriendship.PERSON1_ID, getConverter().convertId(operation.person1Id()),
                        LdbcUpdate8AddFriendship.PERSON2_ID, getConverter().convertId(operation.person2Id()),
                        LdbcUpdate8AddFriendship.CREATION_DATE, getConverter().convertDateTime(operation.creationDate())
                )
        );
    }

}
