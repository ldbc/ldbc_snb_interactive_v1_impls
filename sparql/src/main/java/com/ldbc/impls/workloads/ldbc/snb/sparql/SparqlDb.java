package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.SnbDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlBiQueryStore;

import java.io.IOException;

public abstract class SparqlDb extends SnbDb<SparqlBiQueryStore> {
	
	protected SparqlDriverConnectionStore dbs;

	@Override
	protected void onClose() {
		try {
			dbs.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected DbConnectionState getConnectionState()  {
		return dbs;
	}

}
