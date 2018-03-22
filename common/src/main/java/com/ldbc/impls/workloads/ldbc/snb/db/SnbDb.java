package com.ldbc.impls.workloads.ldbc.snb.db;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.DriverConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.SnbQueryStore;

import java.io.IOException;

public abstract class SnbDb<TSnbQueryStore extends SnbQueryStore> extends Db {
	
	protected DriverConnectionState<TSnbQueryStore> dbs;

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
