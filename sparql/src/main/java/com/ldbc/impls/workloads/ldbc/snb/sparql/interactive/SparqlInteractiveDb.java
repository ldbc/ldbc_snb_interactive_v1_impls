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
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;

import java.util.Map;

public class SparqlInteractiveDb extends SparqlDb {

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
//        registerOperationHandler(LdbcQuery13.class, Query13.class); // cannot be implemented in standard SPARQL
//        registerOperationHandler(LdbcQuery14.class, Query14.class); // cannot be implemented in standard SPARQL

//		registerOperationHandler(LdbcShortQuery1PersonProfile.class, LdbcShortQuery1.class);
//		registerOperationHandler(LdbcShortQuery2PersonPosts.class, LdbcShortQuery2.class);
//		registerOperationHandler(LdbcShortQuery3PersonFriends.class, LdbcShortQuery3.class);
//		registerOperationHandler(LdbcShortQuery4MessageContent.class, LdbcShortQuery4.class);
//		registerOperationHandler(LdbcShortQuery5MessageCreator.class, LdbcShortQuery5.class);
//		registerOperationHandler(LdbcShortQuery6MessageForum.class, LdbcShortQuery6.class);
//		registerOperationHandler(LdbcShortQuery7MessageReplies.class, LdbcShortQuery7.class);

//		registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonSparql.class);
//		registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeSparql.class);
//		registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeSparql.class);
//		registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumSparql.class);
//		registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipSparql.class);
//		registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostSparql.class);
//		registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentSparql.class);
//		registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipSparql.class);
    }

}

