package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.VirtuosoDb;

import java.util.Map;

public class VirtuosoInteractiveDb extends VirtuosoDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        this.registerOperationHandler(LdbcQuery1.class, SparqlDb.Query1.class);
        this.registerOperationHandler(LdbcQuery2.class, SparqlDb.Query2.class);
        this.registerOperationHandler(LdbcQuery3.class, VirtuosoDb.Query3.class);
        this.registerOperationHandler(LdbcQuery4.class, VirtuosoDb.Query4.class);
        this.registerOperationHandler(LdbcQuery5.class, SparqlDb.Query5.class);
        this.registerOperationHandler(LdbcQuery6.class, SparqlDb.Query6.class);
        this.registerOperationHandler(LdbcQuery7.class, VirtuosoDb.Query7.class);
        this.registerOperationHandler(LdbcQuery8.class, SparqlDb.Query8.class);
        this.registerOperationHandler(LdbcQuery9.class, SparqlDb.Query9.class);
        this.registerOperationHandler(LdbcQuery10.class, SparqlDb.Query10.class);
        this.registerOperationHandler(LdbcQuery11.class, SparqlDb.Query11.class);
        this.registerOperationHandler(LdbcQuery12.class, SparqlDb.Query12.class);
        
        SparqlInteractiveDbInitializer.registerShortQueries(this);
        
        this.registerOperationHandler(LdbcUpdate1AddPerson.class, VirtuosoDb.Update1AddPerson.class);
        this.registerOperationHandler(LdbcUpdate2AddPostLike.class, VirtuosoDb.Update2AddPostLike.class);
        this.registerOperationHandler(LdbcUpdate3AddCommentLike.class, VirtuosoDb.Update3AddCommentLike.class);
        this.registerOperationHandler(LdbcUpdate4AddForum.class, VirtuosoDb.Update4AddForum.class);
        this.registerOperationHandler(LdbcUpdate5AddForumMembership.class, VirtuosoDb.Update5AddForumMembership.class);
        this.registerOperationHandler(LdbcUpdate6AddPost.class, VirtuosoDb.Update6AddPost.class);
        this.registerOperationHandler(LdbcUpdate7AddComment.class, VirtuosoDb.Update7AddComment.class);
        this.registerOperationHandler(LdbcUpdate8AddFriendship.class, VirtuosoDb.Update8AddFriendship.class);
    }

}

