package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3Result;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.converter.CypherConverter;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;
import org.neo4j.driver.v1.Record;

import java.util.Map;

public class CypherInteractiveDb extends CypherDb<InteractiveQueryStore> {

    protected static final CypherConverter converter = new CypherConverter();

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new CypherDbConnectionState(properties, new CypherInteractiveQueryStore(properties.get("queryDir")));

        registerOperationHandler(LdbcQuery3.class, InteractiveQuery3.class);
    }

    public static class InteractiveQuery3 extends CypherListOperationHandler<LdbcQuery3, LdbcQuery3Result, CypherInteractiveQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherInteractiveQueryStore> state, LdbcQuery3 operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcQuery3Result convertSingleResult(Record record) {
            long personId = record.get(0).asInt();
            String personFirstName = record.get(1).asString();
            String personLastName = record.get(2).asString();
            int xCount = record.get(3).asInt();
            int yCount = record.get(4).asInt();
            int count = record.get(5).asInt();
            return new LdbcQuery3Result(personId, personFirstName, personLastName, xCount, yCount, count);
        }

    }


}
