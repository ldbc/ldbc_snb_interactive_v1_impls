package org.ldbcouncil.impls.workloads.ldbc.snb.postgres.interactive;

import org.ldbcouncil.driver.DbException;
import org.ldbcouncil.driver.control.LoggingService;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate5AddForumMembership;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import org.ldbcouncil.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import org.ldbcouncil.impls.workloads.ldbc.snb.postgres.PostgresDb;

import java.util.Map;

public class PostgresInteractiveDb extends PostgresDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcQuery1.class, Query1.class);
        registerOperationHandler(LdbcQuery2.class, Query2.class);
        registerOperationHandler(LdbcQuery3.class, Query3.class);
        registerOperationHandler(LdbcQuery4.class, Query4.class);
        registerOperationHandler(LdbcQuery5.class, Query5.class);
        registerOperationHandler(LdbcQuery6.class, Query6.class);
        registerOperationHandler(LdbcQuery7.class, Query7.class);
        registerOperationHandler(LdbcQuery8.class, Query8.class);
        registerOperationHandler(LdbcQuery9.class, Query9.class);
        registerOperationHandler(LdbcQuery10.class, Query10.class);
        registerOperationHandler(LdbcQuery11.class, Query11.class);
        registerOperationHandler(LdbcQuery12.class, Query12.class);
        registerOperationHandler(LdbcQuery13.class, Query13.class);
        registerOperationHandler(LdbcQuery14.class, Query14.class);

        registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
        registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);

        registerOperationHandler(LdbcUpdate1AddPerson.class, Update1AddPerson.class);
        registerOperationHandler(LdbcUpdate2AddPostLike.class, Update2AddPostLike.class);
        registerOperationHandler(LdbcUpdate3AddCommentLike.class, Update3AddCommentLike.class);
        registerOperationHandler(LdbcUpdate4AddForum.class, Update4AddForum.class);
        registerOperationHandler(LdbcUpdate5AddForumMembership.class, Update5AddForumMembership.class);
        registerOperationHandler(LdbcUpdate6AddPost.class, Update6AddPost.class);
        registerOperationHandler(LdbcUpdate7AddComment.class, Update7AddComment.class);
        registerOperationHandler(LdbcUpdate8AddFriendship.class, Update8AddFriendship.class);
    }

}
