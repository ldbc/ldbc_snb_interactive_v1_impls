package org.ldbcouncil.snb.impls.workloads.dummydb;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.db.BaseDb;
import org.ldbcouncil.snb.impls.workloads.dummydb.operationhandlers.DummyListOperationHandler;
import org.ldbcouncil.snb.impls.workloads.dummydb.operationhandlers.DummySingletonOperationHandler;
import org.ldbcouncil.snb.impls.workloads.dummydb.operationhandlers.DummyUpdateOperationHandler;

import java.util.Arrays;
import java.util.Map;


public abstract class DummyDb extends BaseDb<DummyQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        try {
            dcs = new DummyConnectionState(properties, new DummyQueryStore("someDir"));
        } catch (ClassNotFoundException e) {
            throw new DbException(e);
        }
    }

    // Interactive complex reads

    public static class Query1 extends DummyListOperationHandler<LdbcQuery1, LdbcQuery1Result> {

        @Override
        public LdbcQuery1Result convertSingleResult() {
            return new LdbcQuery1Result(
                123456789l,
                "Somestring",
                7,
                889586070000l,
                889586070000l,
                "someString",
                "someString",
                "someString",
                Arrays.asList("some", "string"),
                Arrays.asList("some", "string"),
                "someString",
                Arrays.asList(new LdbcQuery1Result.Organization("string", 1, "string")),
                Arrays.asList(new LdbcQuery1Result.Organization("string", 1, "string"))
            );
        }

        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery1 query)
        {
            return "query";
        }
    }

    public static class Query2 extends DummyListOperationHandler<LdbcQuery2, LdbcQuery2Result> {

        @Override
        public LdbcQuery2Result convertSingleResult() {
            return new LdbcQuery2Result(
                    123456789l,
                    "someString",
                    "someString",
                    123456798l,
                    "someString",
                    889586070000l);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery2 query)
        {
            return "query";
        }
    }

    public static class Query3 extends DummyListOperationHandler<LdbcQuery3, LdbcQuery3Result> {

        @Override
        public LdbcQuery3Result convertSingleResult() {
            return new LdbcQuery3Result(
                    123456789l,
                    "someString",
                    "someString",
                    1,
                    2,
                    3);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery3 query)
        {
            return "query";
        }
    }

    public static class Query4 extends DummyListOperationHandler<LdbcQuery4, LdbcQuery4Result> {

        @Override
        public LdbcQuery4Result convertSingleResult() {
            return new LdbcQuery4Result(
                "someString",
                    1
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery4 query)
        {
            return "query";
        }
    }

    public static class Query5 extends DummyListOperationHandler<LdbcQuery5, LdbcQuery5Result> {

        @Override
        public LdbcQuery5Result convertSingleResult() {
            return new LdbcQuery5Result(
                "someString",
                    1
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery5 query)
        {
            return "query";
        }
    }

    public static class Query6 extends DummyListOperationHandler<LdbcQuery6, LdbcQuery6Result> {

        @Override
        public LdbcQuery6Result convertSingleResult() {
            return new LdbcQuery6Result(
                "someString",
                    1
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery6 query)
        {
            return "query";
        }
    }

    public static class Query7 extends DummyListOperationHandler<LdbcQuery7, LdbcQuery7Result> {

        @Override
        public LdbcQuery7Result convertSingleResult() {
            return new LdbcQuery7Result(
                    123456789l,
                    "someString",
                    "someString",
                    889586070000l,
                    123456789l,
                    "someString",
                    1,
                    true);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery7 query)
        {
            return "query";
        }
    }

    public static class Query8 extends DummyListOperationHandler<LdbcQuery8, LdbcQuery8Result> {

        @Override
        public LdbcQuery8Result convertSingleResult() {
            return new LdbcQuery8Result(
                    123456789l,
                    "someString",
                    "someString",
                    889586070000l,
                    123456789l,
                    "someString");
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery8 query)
        {
            return "query";
        }
    }

    public static class Query9 extends DummyListOperationHandler<LdbcQuery9, LdbcQuery9Result> {

        @Override
        public LdbcQuery9Result convertSingleResult() {
            return new LdbcQuery9Result(
                    123456789l,
                    "someString",
                    "someString",
                    123456798l,
                    "someString",
                    889586070000l);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery9 query)
        {
            return "query";
        }
    }

    public static class Query10 extends DummyListOperationHandler<LdbcQuery10, LdbcQuery10Result> {

        @Override
        public LdbcQuery10Result convertSingleResult() {
            return new LdbcQuery10Result(
                    123456789l,
                    "someString",
                    "someString",
                    1,
                    "someString",
                    "someString");
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery10 query)
        {
            return "query";
        }
    }

    public static class Query11 extends DummyListOperationHandler<LdbcQuery11, LdbcQuery11Result> {

        @Override
        public LdbcQuery11Result convertSingleResult() {
            return new LdbcQuery11Result(
                    123456789l,
                    "someString",
                    "someString",
                    "someString",
                    1);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery11 query)
        {
            return "query";
        }
    }

    public static class Query12 extends DummyListOperationHandler<LdbcQuery12, LdbcQuery12Result> {

        @Override
        public LdbcQuery12Result convertSingleResult() {
            return new LdbcQuery12Result(
                    123456789l,
                    "someString",
                    "someString",
                    Arrays.asList("some", "string"),
                    1);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery12 query)
        {
            return "query";
        }
    }

    public static class Query13 extends DummySingletonOperationHandler<LdbcQuery13, LdbcQuery13Result> {

        @Override
        public LdbcQuery13Result convertSingleResult() {
            return new LdbcQuery13Result(1);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery13 query)
        {
            return "query";
        }
    }

    public static class Query14 extends DummyListOperationHandler<LdbcQuery14, LdbcQuery14Result> {

        @Override
        public LdbcQuery14Result convertSingleResult() {
            return new LdbcQuery14Result(
                Arrays.asList(1l, 2l),
                123456798l
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcQuery14 query)
        {
            return "query";
        }
    }

    public static class ShortQuery1PersonProfile extends DummySingletonOperationHandler<LdbcShortQuery1PersonProfile, LdbcShortQuery1PersonProfileResult> {

        @Override
        public LdbcShortQuery1PersonProfileResult convertSingleResult() {
            return new LdbcShortQuery1PersonProfileResult(
                    "someString",
                    "someString",
                    889586070000l,
                    "someString",
                    "someString",
                    1l,
                    "someString",
                    889586070000l);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery1PersonProfile query)
        {
            return "query";
        }
    }

    public static class ShortQuery2PersonPosts extends DummyListOperationHandler<LdbcShortQuery2PersonPosts, LdbcShortQuery2PersonPostsResult> {

        @Override
        public LdbcShortQuery2PersonPostsResult convertSingleResult() {
            return new LdbcShortQuery2PersonPostsResult(
                    123456789l,
                    "someString",
                    889586070000l,
                    123456798l,
                    12l,
                    "someString",
                    "someString");
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery2PersonPosts query)
        {
            return "query";
        }
    }

    public static class ShortQuery3PersonFriends extends DummyListOperationHandler<LdbcShortQuery3PersonFriends, LdbcShortQuery3PersonFriendsResult> {

        @Override
        public LdbcShortQuery3PersonFriendsResult convertSingleResult() {
            return new LdbcShortQuery3PersonFriendsResult(
                    123456789l,
                    "someString",
                    "someString",
                    889586070000l);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery3PersonFriends query)
        {
            return "query";
        }
    }

    public static class ShortQuery4MessageContent extends DummySingletonOperationHandler<LdbcShortQuery4MessageContent, LdbcShortQuery4MessageContentResult> {

        @Override
        public LdbcShortQuery4MessageContentResult convertSingleResult() {
            return new LdbcShortQuery4MessageContentResult(
                    "someString",
                    889586070000l);
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery4MessageContent query)
        {
            return "query";
        }
    }

    public static class ShortQuery5MessageCreator extends DummySingletonOperationHandler<LdbcShortQuery5MessageCreator, LdbcShortQuery5MessageCreatorResult> {

        @Override
        public LdbcShortQuery5MessageCreatorResult convertSingleResult() {
            return new LdbcShortQuery5MessageCreatorResult(
                123456789l,
                "someString",
                "someString"
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery5MessageCreator query)
        {
            return "query";
        }
    }

    public static class ShortQuery6MessageForum extends DummySingletonOperationHandler<LdbcShortQuery6MessageForum, LdbcShortQuery6MessageForumResult> {

        @Override
        public LdbcShortQuery6MessageForumResult convertSingleResult() {
            return new LdbcShortQuery6MessageForumResult(
                123456789l,
                "someString",
                1l,
                "someString",
                "someString"
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery6MessageForum query)
        {
            return "query";
        }
    }

    public static class ShortQuery7MessageReplies extends DummyListOperationHandler<LdbcShortQuery7MessageReplies, LdbcShortQuery7MessageRepliesResult> {

        @Override
        public LdbcShortQuery7MessageRepliesResult convertSingleResult() {
            return new LdbcShortQuery7MessageRepliesResult(
                123456789l,
                "someString",
                889586070000l,
                123456798l,
                "someString",
                "someString",
                true
            );
        }
        @Override
        public String getQueryString(DummyConnectionState state, LdbcShortQuery7MessageReplies query)
        {
            return "query";
        }
    }

    public static class Insert1AddPerson extends DummyUpdateOperationHandler<LdbcInsert1AddPerson> {

    }

    public static class Insert2AddPostLike extends DummyUpdateOperationHandler<LdbcInsert2AddPostLike> {

    }

    public static class Insert3AddCommentLike extends DummyUpdateOperationHandler<LdbcInsert3AddCommentLike> {

    }

    public static class Insert4AddForum extends DummyUpdateOperationHandler<LdbcInsert4AddForum> {

    }

    public static class Insert5AddForumMembership extends DummyUpdateOperationHandler<LdbcInsert5AddForumMembership> {

    }

    public static class Insert6AddPost extends DummyUpdateOperationHandler<LdbcInsert6AddPost> {

    }

    public static class Insert7AddComment extends DummyUpdateOperationHandler<LdbcInsert7AddComment> {

    }

    public static class Insert8AddFriendship extends DummyUpdateOperationHandler<LdbcInsert8AddFriendship> {

    }

    // Deletions
    public static class Delete1RemovePerson extends DummyUpdateOperationHandler<LdbcDelete1RemovePerson> {

    }

    public static class Delete2RemovePostLike extends DummyUpdateOperationHandler<LdbcDelete2RemovePostLike> {

    }

    public static class Delete3RemoveCommentLike extends DummyUpdateOperationHandler<LdbcDelete3RemoveCommentLike> {

    }

    public static class Delete4RemoveForum extends DummyUpdateOperationHandler<LdbcDelete4RemoveForum> {

    }

    public static class Delete5RemoveForumMembership extends DummyUpdateOperationHandler<LdbcDelete5RemoveForumMembership> {

    }

    public static class Delete6RemovePostThread extends DummyUpdateOperationHandler<LdbcDelete6RemovePostThread> {

    }

    public static class Delete7RemoveCommentSubthread extends DummyUpdateOperationHandler<LdbcDelete7RemoveCommentSubthread> {

    }

    public static class Delete8RemoveFriendship extends DummyUpdateOperationHandler<LdbcDelete8RemoveFriendship> {

    }
}
