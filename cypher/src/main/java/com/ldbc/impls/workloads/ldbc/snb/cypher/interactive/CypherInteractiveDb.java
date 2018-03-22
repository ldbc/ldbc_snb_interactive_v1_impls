package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.CypherConverter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDriverConnectionState;

import java.util.Map;

public class CypherInteractiveDb extends CypherDb<CypherInteractiveQueryStore> {

	protected static final CypherConverter converter = new CypherConverter();

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		dbs = new CypherDriverConnectionState(properties, new CypherInteractiveQueryStore(properties.get("queryDir")));

		//registerOperationHandler()
	}

}
