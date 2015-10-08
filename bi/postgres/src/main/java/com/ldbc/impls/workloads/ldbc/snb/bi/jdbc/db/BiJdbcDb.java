package com.ldbc.impls.workloads.ldbc.snb.bi.jdbc.db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPersonResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummaryResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPostersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePostersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForumsResult;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcOperationHandler;

public class BiJdbcDb extends Db {

	JdbcDbConnectionStore<BiQueryStore> dbs;

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		try {
			dbs = new JdbcDbConnectionStore<BiQueryStore>(properties, new BiQueryStore(properties.get("queryDir")));
		} catch (ClassNotFoundException | SQLException e) {
			throw new DbException(e);
		}
		registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, BiQuery1.class);
		registerOperationHandler(LdbcSnbBiQuery4PopularCountryTopics.class, BiQuery4.class);
		registerOperationHandler(LdbcSnbBiQuery5TopCountryPosters.class, BiQuery5.class);
		registerOperationHandler(LdbcSnbBiQuery6ActivePosters.class, BiQuery6.class);
		registerOperationHandler(LdbcSnbBiQuery7AuthoritativeUsers.class, BiQuery7.class);
		registerOperationHandler(LdbcSnbBiQuery8RelatedTopics.class, BiQuery8.class);
		registerOperationHandler(LdbcSnbBiQuery9RelatedForums.class, BiQuery9.class);
		registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, BiQuery10.class);
		//registerOperationHandler(LdbcSnbBiQuery2TopTags.class, BiQuery2.class);
		//registerOperationHandler(LdbcSnbBiQuery3TagEvolution.class, BiQuery3.class);
	}

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
	
	public static class BiQuery1 extends JdbcOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery1PostingSummary operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(ResultSet result) throws SQLException {
			int year = result.getInt(1);
			boolean isComment = result.getBoolean(2);
			int size = 1; //HACK!! (int)result.getLong(3);
			int count = (int) result.getLong(4);
			int avgLen = result.getInt(5);
			int total = result.getInt(6);
			double pct = result.getDouble(7);
			
			return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, pct);
		}
		
	}

//	public static class BiQuery2 extends JDBCOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult, LdbcSnbBiQueryStore> {
//
//		@Override
//		public String getQueryString(JDBCDbConnectionState<LdbcSnbBiQueryStore> state, LdbcSnbBiQuery2TopTags operation) {
//			return state.getQueryStore().getQuery2(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery2TopTagsResult convertSingleResult(ResultSet result) throws SQLException {
//			String country = result.getString(1);
//			int month = result.getInt(2);
//			String gender = result.getString(3);
//			int ageGroup = result.getInt(4);
//			String tag = result.getString(5);
//			int count = result.getInt(6);
//			return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
//		}
//		
//	}
	
//	public static class BiQuery3 extends JdbcOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult, BiQueryStore> {
//
//		@Override
//		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery3TagEvolution operation) {
//			if(operation.limit()!=100) {
//				throw new RuntimeException("Bad stuff "+ operation.limit()); // HACK!!
//			}
//			return state.getQueryStore().getQuery3(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(ResultSet result) throws SQLException {
//			String tagName = result.getString(1);
//			int countA = result.getInt(2);
//			int countB = result.getInt(3);
//			int diffCount = result.getInt(4);
//			return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
//		}
//		
//	}
	
	public static class BiQuery4 extends JdbcOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery4PopularCountryTopics operation) {
			return state.getQueryStore().getQuery4(operation);
		}

		@Override
		public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			long forumId = result.getLong(1);
			String title = result.getString(2);
			long creationDate = result.getDate(3).getTime();
			long moderator = result.getLong(4);
			int count = result.getInt(5);
			return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
		}
		
	}
	
	public static class BiQuery5 extends JdbcOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery5TopCountryPosters operation) {
			return state.getQueryStore().getQuery5(operation);
		}

		@Override
		public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String firstName = result.getString(2);
			String lastName = result.getString(3);
			long creationDate = result.getDate(4).getTime();
			int count = result.getInt(5);
			return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
		}
		
	}
	
	public static class BiQuery6 extends JdbcOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int postCount = result.getInt(2);
			int replyCount = result.getInt(3);
			int likeCount = result.getInt(4);
			int score = result.getInt(5);
			return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
		}
		
	}

	public static class BiQuery7 extends JdbcOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
			return state.getQueryStore().getQuery7(operation);
		}

		@Override
		public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int score = result.getInt(2);
			return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
		}
		
	}
	
	public static class BiQuery8 extends JdbcOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery8RelatedTopics operation) {
			return state.getQueryStore().getQuery8(operation);
		}

		@Override
		public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			String tag = result.getString(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
		}
		
	}
	
	public static class BiQuery9 extends JdbcOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery9RelatedForums operation) {
			return state.getQueryStore().getQuery9(operation);
		}

		@Override
		public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(ResultSet result) throws SQLException {
			String forumTitle = result.getString(1);
			int sumA = result.getInt(2);
			int sumB = result.getInt(3);
			return new LdbcSnbBiQuery9RelatedForumsResult(forumTitle, sumA, sumB);
		}
		
	}
	
	public static class BiQuery10 extends JdbcOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery10TagPerson operation) {
			return state.getQueryStore().getQuery10(operation);
		}

		@Override
		public LdbcSnbBiQuery10TagPersonResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int score = result.getInt(2);
			return new LdbcSnbBiQuery10TagPersonResult(personId, score);
		}
		
	}
}
