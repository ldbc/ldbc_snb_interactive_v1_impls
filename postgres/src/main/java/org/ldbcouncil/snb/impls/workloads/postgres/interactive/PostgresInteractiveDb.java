package org.ldbcouncil.snb.impls.workloads.postgres.interactive;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.control.LoggingService;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.postgres.PostgresDb;

import java.util.Map;

public class PostgresInteractiveDb extends PostgresDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcQuery1.class, Query1.class);
        registerOperationHandler(LdbcQuery2.class, Query2.class);
        registerOperationHandler(LdbcQuery3a.class, Query3a.class);
        registerOperationHandler(LdbcQuery3b.class, Query3b.class);
        registerOperationHandler(LdbcQuery4.class, Query4.class);
        registerOperationHandler(LdbcQuery5.class, Query5.class);
        registerOperationHandler(LdbcQuery6.class, Query6.class);
        registerOperationHandler(LdbcQuery7.class, Query7.class);
        registerOperationHandler(LdbcQuery8.class, Query8.class);
        registerOperationHandler(LdbcQuery9.class, Query9.class);
        registerOperationHandler(LdbcQuery10.class, Query10.class);
        registerOperationHandler(LdbcQuery11.class, Query11.class);
        registerOperationHandler(LdbcQuery12.class, Query12.class);
        registerOperationHandler(LdbcQuery13a.class, Query13a.class);
        registerOperationHandler(LdbcQuery13b.class, Query13b.class);
        registerOperationHandler(LdbcQuery14a.class, Query14a.class);
        registerOperationHandler(LdbcQuery14b.class, Query14b.class);

        registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
        registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);

        registerOperationHandler(LdbcInsert1AddPerson.class, Insert1AddPerson.class);
        registerOperationHandler(LdbcInsert2AddPostLike.class, Insert2AddPostLike.class);
        registerOperationHandler(LdbcInsert3AddCommentLike.class, Insert3AddCommentLike.class);
        registerOperationHandler(LdbcInsert4AddForum.class, Insert4AddForum.class);
        registerOperationHandler(LdbcInsert5AddForumMembership.class, Insert5AddForumMembership.class);
        registerOperationHandler(LdbcInsert6AddPost.class, Insert6AddPost.class);
        registerOperationHandler(LdbcInsert7AddComment.class, Insert7AddComment.class);
        registerOperationHandler(LdbcInsert8AddFriendship.class, Insert8AddFriendship.class);

        registerOperationHandler(LdbcDelete1RemovePerson.class, Delete1RemovePerson.class);
        registerOperationHandler(LdbcDelete2RemovePostLike.class, Delete2RemovePostLike.class);
        registerOperationHandler(LdbcDelete3RemoveCommentLike.class, Delete3RemoveCommentLike.class);
        registerOperationHandler(LdbcDelete4RemoveForum.class, Delete4RemoveForum.class);
        registerOperationHandler(LdbcDelete5RemoveForumMembership.class, Delete5RemoveForumMembership.class);
        registerOperationHandler(LdbcDelete6RemovePostThread.class, Delete6RemovePostThread.class);
        registerOperationHandler(LdbcDelete7RemoveCommentSubthread.class,Delete7RemoveCommentSubthread.class);
        registerOperationHandler(LdbcDelete8RemoveFriendship.class, Delete8RemoveFriendship.class);
    }

}
