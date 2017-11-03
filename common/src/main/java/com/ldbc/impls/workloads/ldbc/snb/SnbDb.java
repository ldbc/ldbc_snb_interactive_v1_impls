package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

public abstract class SnbDb<TBiQueryStore extends BiQueryStore> extends Db {
	
	protected DriverConnectionStore<TBiQueryStore> dbs;

	@Override
	protected final void onClose() throws IOException {
		try {
			dbs.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected final DbConnectionState getConnectionState() throws DbException {
		return dbs;
	}
	
	protected static long timestampToTimestamp(ResultSet r, int column) throws SQLException {
		return r.getTimestamp(column, Calendar.getInstance(TimeZone.getTimeZone("GMT"))).getTime();
	}

}
