package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
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
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.StardogDb;

import java.util.Map;

public class StardogInteractiveDb extends StardogDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcQuery1.class, SparqlDb.Query1.class);
        registerOperationHandler(LdbcQuery2.class, SparqlDb.Query2.class);
        registerOperationHandler(LdbcQuery3.class, SparqlDb.Query3.class);
        registerOperationHandler(LdbcQuery4.class, SparqlDb.Query4.class);
        registerOperationHandler(LdbcQuery5.class, SparqlDb.Query5.class);
        registerOperationHandler(LdbcQuery6.class, SparqlDb.Query6.class);
        registerOperationHandler(LdbcQuery7.class, SparqlDb.Query7.class);
        registerOperationHandler(LdbcQuery8.class, SparqlDb.Query8.class);
        registerOperationHandler(LdbcQuery9.class, SparqlDb.Query9.class);
        registerOperationHandler(LdbcQuery10.class, SparqlDb.Query10.class);
        registerOperationHandler(LdbcQuery11.class, SparqlDb.Query11.class);
        registerOperationHandler(LdbcQuery12.class, SparqlDb.Query12.class);
        // query 13 and query 14 are not implemented

        registerOperationHandler(LdbcShortQuery1PersonProfile.class, SparqlDb.ShortQuery1PersonProfile.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, SparqlDb.ShortQuery2PersonPosts.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, SparqlDb.ShortQuery3PersonFriends.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, SparqlDb.ShortQuery4MessageContent.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, SparqlDb.ShortQuery5MessageCreator.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, SparqlDb.ShortQuery6MessageForum.class);
        registerOperationHandler(LdbcShortQuery7MessageReplies.class, SparqlDb.ShortQuery7MessageReplies.class);

        registerOperationHandler(LdbcUpdate1AddPerson.class, SparqlDb.Update1AddPerson.class);
        registerOperationHandler(LdbcUpdate2AddPostLike.class, SparqlDb.Update2AddPostLike.class);
        registerOperationHandler(LdbcUpdate3AddCommentLike.class, SparqlDb.Update3AddCommentLike.class);
        registerOperationHandler(LdbcUpdate4AddForum.class, SparqlDb.Update4AddForum.class);
        registerOperationHandler(LdbcUpdate5AddForumMembership.class, SparqlDb.Update5AddForumMembership.class);
        registerOperationHandler(LdbcUpdate6AddPost.class, SparqlDb.Update6AddPost.class);
        registerOperationHandler(LdbcUpdate7AddComment.class, SparqlDb.Update7AddComment.class);
        registerOperationHandler(LdbcUpdate8AddFriendship.class, SparqlDb.Update8AddFriendship.class);
    }

}

