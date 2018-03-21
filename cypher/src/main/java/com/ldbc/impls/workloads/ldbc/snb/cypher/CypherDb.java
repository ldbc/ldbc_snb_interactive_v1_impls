package com.ldbc.impls.workloads.ldbc.snb.cypher;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.db.SnbDb;
import com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiQueryStore;

import java.io.IOException;

public abstract class CypherDb extends SnbDb<CypherBiQueryStore> {
	
	protected CypherDriverConnectionStore dbs;

	@Override
	protected void onClose() throws IOException {
		try {
			dbs.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected DbConnectionState getConnectionState() throws DbException {
		return dbs;
	}

}
