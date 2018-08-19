package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;

import java.util.Map;

public class CypherInteractiveDb extends CypherDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);

        registerOperationHandler(LdbcQuery3.class, InteractiveQuery3.class);
    }

}
