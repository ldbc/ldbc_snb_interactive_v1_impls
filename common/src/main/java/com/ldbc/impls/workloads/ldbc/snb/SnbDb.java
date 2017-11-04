package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

import java.io.IOException;

public abstract class SnbDb<TBiQueryStore extends BiQueryStore> extends Db {
	
	protected DriverConnectionStore<TBiQueryStore> dbs;

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
