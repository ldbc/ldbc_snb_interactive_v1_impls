package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.db.SnbDb;

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public abstract class JdbcDb<DbQueryStore> extends SnbDb {
	
	protected JdbcDbConnectionStore<DbQueryStore> dbs;

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
	
	protected static long stringTimestampToEpoch(ResultSet r, int column) throws SQLException {
		return r.getTimestamp(column, Calendar.getInstance(TimeZone.getTimeZone("GMT"))).getTime();
	}
	
	protected static Iterable<String> arrayToStringArray(ResultSet r, int column) throws SQLException {
		Array value = r.getArray(column);
		if(value==null) {
			return new ArrayList<>();
		} else {
			String[] strs=(String[])value.getArray();
			ArrayList<String> array = new ArrayList<>();
			for (int i = 0; i < strs.length; i++) {
				array.add(strs[i]);
			}
			return array;
		}
	}
	
	protected static Iterable<List<Object>> arrayToObjectArray(ResultSet r, int column) throws SQLException {
		Array value = r.getArray(column);
		if(value==null) {
			return new ArrayList<>();
		} else {
			Object[][] strs=(Object[][])value.getArray();
			ArrayList<List<Object>> array = new ArrayList<>();
			for (int i = 0; i < strs.length; i++) {
				array.add(new ArrayList(Arrays.asList(strs[i])));
			}
			return array;
		}
	}
}
