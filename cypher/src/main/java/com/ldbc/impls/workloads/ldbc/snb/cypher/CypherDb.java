package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.SnbQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.db.SnbDb;

import java.io.IOException;

public abstract class CypherDb<DbQueryStore extends SnbQueryStore> extends SnbDb<DbQueryStore> {

	@Override
	protected void onClose() {
		try {
			dbs.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected DbConnectionState getConnectionState() {
		return dbs;
	}

}
