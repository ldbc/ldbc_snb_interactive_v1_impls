package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;

public class SparqlInteractiveDbInitializer {

    static void registerComplexQueries(SparqlDb db) throws DbException {
        db.registerOperationHandler(LdbcQuery1.class, SparqlDb.Query1.class);
        db.registerOperationHandler(LdbcQuery2.class, SparqlDb.Query2.class);
        db.registerOperationHandler(LdbcQuery3.class, SparqlDb.Query3.class);
        db.registerOperationHandler(LdbcQuery4.class, SparqlDb.Query4.class);
        db.registerOperationHandler(LdbcQuery5.class, SparqlDb.Query5.class);
        db.registerOperationHandler(LdbcQuery6.class, SparqlDb.Query6.class);
        db.registerOperationHandler(LdbcQuery7.class, SparqlDb.Query7.class);
        db.registerOperationHandler(LdbcQuery8.class, SparqlDb.Query8.class);
        db.registerOperationHandler(LdbcQuery9.class, SparqlDb.Query9.class);
        db.registerOperationHandler(LdbcQuery10.class, SparqlDb.Query10.class);
        db.registerOperationHandler(LdbcQuery11.class, SparqlDb.Query11.class);
        db.registerOperationHandler(LdbcQuery12.class, SparqlDb.Query12.class);
    }

    static void registerShortQueries(SparqlDb db) throws DbException {
        db.registerOperationHandler(LdbcShortQuery1PersonProfile.class, SparqlDb.ShortQuery1PersonProfile.class);
        db.registerOperationHandler(LdbcShortQuery2PersonPosts.class, SparqlDb.ShortQuery2PersonPosts.class);
        db.registerOperationHandler(LdbcShortQuery3PersonFriends.class, SparqlDb.ShortQuery3PersonFriends.class);
        db.registerOperationHandler(LdbcShortQuery4MessageContent.class, SparqlDb.ShortQuery4MessageContent.class);
        db.registerOperationHandler(LdbcShortQuery5MessageCreator.class, SparqlDb.ShortQuery5MessageCreator.class);
        db.registerOperationHandler(LdbcShortQuery6MessageForum.class, SparqlDb.ShortQuery6MessageForum.class);
        db.registerOperationHandler(LdbcShortQuery7MessageReplies.class, SparqlDb.ShortQuery7MessageReplies.class);
    }

    static void registerUpdateQueries(SparqlDb db) throws DbException {
        db.registerOperationHandler(LdbcUpdate1AddPerson.class, SparqlDb.Update1AddPerson.class);
        db.registerOperationHandler(LdbcUpdate2AddPostLike.class, SparqlDb.Update2AddPostLike.class);
        db.registerOperationHandler(LdbcUpdate3AddCommentLike.class, SparqlDb.Update3AddCommentLike.class);
        db.registerOperationHandler(LdbcUpdate4AddForum.class, SparqlDb.Update4AddForum.class);
        db.registerOperationHandler(LdbcUpdate5AddForumMembership.class, SparqlDb.Update5AddForumMembership.class);
        db.registerOperationHandler(LdbcUpdate6AddPost.class, SparqlDb.Update6AddPost.class);
        db.registerOperationHandler(LdbcUpdate7AddComment.class, SparqlDb.Update7AddComment.class);
        db.registerOperationHandler(LdbcUpdate8AddFriendship.class, SparqlDb.Update8AddFriendship.class);
    }

    static void registerQueries(SparqlDb db) throws DbException {
        registerComplexQueries(db);
        registerShortQueries(db);
        registerUpdateQueries(db);
    }
}
