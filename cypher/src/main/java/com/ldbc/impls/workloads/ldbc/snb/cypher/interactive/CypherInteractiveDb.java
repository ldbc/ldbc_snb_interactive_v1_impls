package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;

import java.util.Map;

public class CypherInteractiveDb extends CypherDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcQuery1.class, InteractiveQuery1.class);
        registerOperationHandler(LdbcQuery2.class, InteractiveQuery2.class);
        registerOperationHandler(LdbcQuery3.class, InteractiveQuery3.class);
        registerOperationHandler(LdbcQuery4.class, InteractiveQuery4.class);
        registerOperationHandler(LdbcQuery5.class, InteractiveQuery5.class);
        registerOperationHandler(LdbcQuery6.class, InteractiveQuery6.class);
        registerOperationHandler(LdbcQuery7.class, InteractiveQuery7.class);
        registerOperationHandler(LdbcQuery8.class, InteractiveQuery8.class);
        registerOperationHandler(LdbcQuery9.class, InteractiveQuery9.class);
        registerOperationHandler(LdbcQuery10.class, InteractiveQuery10.class);
        registerOperationHandler(LdbcQuery11.class, InteractiveQuery11.class);
        registerOperationHandler(LdbcQuery12.class, InteractiveQuery12.class);
        registerOperationHandler(LdbcQuery13.class, InteractiveQuery13.class);
        registerOperationHandler(LdbcQuery14.class, InteractiveQuery14.class);

        registerOperationHandler(LdbcShortQuery1PersonProfile.class, ShortQuery1PersonProfile.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, ShortQuery2PersonPosts.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, ShortQuery3PersonFriends.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, ShortQuery4MessageContent.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, ShortQuery5MessageCreator.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, ShortQuery6MessageForum.class);
        registerOperationHandler(LdbcShortQuery7MessageReplies.class, ShortQuery7MessageReplies.class);

        registerOperationHandler(LdbcUpdate1AddPerson.class, Update1AddPerson.class);
    }

}
