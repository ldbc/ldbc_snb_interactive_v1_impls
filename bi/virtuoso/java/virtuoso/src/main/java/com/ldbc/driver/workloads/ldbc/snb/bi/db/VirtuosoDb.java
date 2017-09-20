package com.ldbc.driver.workloads.ldbc.snb.bi.db;


import com.ldbc.driver.*;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;
import java.sql.Timestamp;
import openlink.util.Vector;

import virtuoso.jdbc4.VirtuosoConnectionPoolDataSource;

public class VirtuosoDb extends Db {
	private VirtuosoDbConnectionState virtuosoDbConnectionState;

	public static  String file2string(File file) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();

			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				else {
					sb.append(line);
					sb.append("\n");
				}
			}
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("Error openening or reading file: " + file.getAbsolutePath(), e);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		try {
			virtuosoDbConnectionState = new VirtuosoDbConnectionState(properties);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, LdbcSnbBiQuery1PostingSummaryToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery2TopTags.class, LdbcSnbBiQuery2TopTagsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery3TagEvolution.class, LdbcSnbBiQuery3TagEvolutionToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery4PopularCountryTopics.class, LdbcSnbBiQuery4PopularCountryTopicsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery5TopCountryPosters.class, LdbcSnbBiQuery5TopCountryPostersToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery6ActivePosters.class, LdbcSnbBiQuery6ActivePostersToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery7AuthoritativeUsers.class, LdbcSnbBiQuery7AuthoritativeUsersToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery8RelatedTopics.class, LdbcSnbBiQuery8RelatedTopicsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery9RelatedForums.class, LdbcSnbBiQuery9RelatedForumsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, LdbcSnbBiQuery10TagPersonToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery11UnrelatedReplies.class, LdbcSnbBiQuery11UnrelatedRepliesToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery12TrendingPosts.class, LdbcSnbBiQuery12TrendingPostsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery13PopularMonthlyTags.class, LdbcSnbBiQuery13PopularMonthlyTagsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery14TopThreadInitiators.class, LdbcSnbBiQuery14TopThreadInitiatorsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery15SocialNormals.class, LdbcSnbBiQuery15SocialNormalsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, LdbcSnbBiQuery16ExpertsInSocialCircleToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, LdbcSnbBiQuery17FriendshipTrianglesToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, LdbcSnbBiQuery18PersonPostCountsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, LdbcSnbBiQuery19StrangerInteractionToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery20HighLevelTopics.class, LdbcSnbBiQuery20HighLevelTopicsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery21Zombies.class, LdbcSnbBiQuery21ZombiesToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery22InternationalDialog.class, LdbcSnbBiQuery22InternationalDialogToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery23HolidayDestinations.class, LdbcSnbBiQuery23HolidayDestinationsToVirtuoso.class);
		registerOperationHandler(LdbcSnbBiQuery24MessagesByTopic.class, LdbcSnbBiQuery24MessagesByTopicToVirtuoso.class);
	}

	@Override
	protected void onClose()   {
		try {
			virtuosoDbConnectionState.getDs().close();
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected DbConnectionState getConnectionState() throws DbException {
		return virtuosoDbConnectionState;
	}

	public class VirtuosoDbConnectionState extends DbConnectionState {
		private VirtuosoConnectionPoolDataSource ds;
		//    	private Connection conn;
		//        private String endPoint;
		private String queryDir;
		private boolean runSql;
		private boolean runCluster;
		private boolean printNames;
		private boolean printStrings;
		private boolean printResults;

		VirtuosoDbConnectionState(Map<String, String> properties) throws ClassNotFoundException, SQLException {
			super();
			//			Class.forName("virtuoso.jdbc4.Driver");
			//	        endPoint = properties.get("endpoint");
			//			conn = DriverManager.getConnection(endPoint, properties.get("user"), properties.get("password"));;
			ds = new VirtuosoConnectionPoolDataSource();
			ds.setDataSourceName("MyPool");
			ds.setServerName(properties.get("endpoint"));
			ds.setUser(properties.get("user"));
			ds.setPassword(properties.get("password"));
			ds.setMinPoolSize(1);
			//ds.setMaxPoolSize(Integer.parseInt(properties.get("tc")) * 2);
			ds.setMaxPoolSize(64);
			runCluster = properties.get("run_cluster").equals("true") ? true : false;
			if (runCluster)
				ds.setRoundrobin(true);
			ds.setCharset("UTF-8");
			ds.fill();
			queryDir = properties.get("queryDir");
			runSql = properties.get("run_sql").equals("true") ? true : false;
			printNames = properties.get("printQueryNames").equals("true") ? true : false;
			printStrings = properties.get("printQueryStrings").equals("true") ? true : false;
			printResults = properties.get("printQueryResults").equals("true") ? true : false;

		}

		public Connection getConn() {
			try {
				Connection tmp = ds.getPooledConnection().getConnection();
				tmp.setTransactionIsolation(2);
				return tmp;
			} catch (SQLException e) {
				// TODO Auto-generated catch block                                                                
				e.printStackTrace();
			}
			return null;
			//			return conn;
		}

		public String getQueryDir() {
			return queryDir;
		}

		public boolean isRunSql() {
			return runSql;
		}

		public boolean isPrintNames() {
			return printNames;
		}

		public boolean isPrintStrings() {
			return printStrings;
		}

		public boolean isPrintResults() {
			return printResults;
		}

		public VirtuosoConnectionPoolDataSource getDs() {
			return ds;
		}

		public void close() throws IOException {
			// TODO Auto-generated method stub

		}

	}

	public static class LdbcSnbBiQuery1PostingSummaryToVirtuoso implements OperationHandler<LdbcSnbBiQuery1PostingSummary, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery1PostingSummary operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery1PostingSummaryResult> RESULT = new ArrayList<LdbcSnbBiQuery1PostingSummaryResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query1.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Date@", String.valueOf(operation.date()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery1PostingSummary");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					int year = 0;
					if (state.isRunSql())
						year = result.getInt(1);
					else
						;
					boolean isComment = result.getInt(2) == 0 ? false : true;
					int messageLengthCategory = result.getInt(3);
					long messageCount = result.getLong(4);
					long messageLengthMean = result.getLong(5);
					long messageLengthSum = result.getLong(6);
					float percentOfTotalMessageCount = result.getFloat(7);
					LdbcSnbBiQuery1PostingSummaryResult tmp = new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, messageLengthCategory, messageCount, messageLengthMean, messageLengthSum, percentOfTotalMessageCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}

	public static class LdbcSnbBiQuery2TopTagsToVirtuoso implements OperationHandler<LdbcSnbBiQuery2TopTags, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery2TopTags operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery2TopTagsResult> RESULT = new ArrayList<LdbcSnbBiQuery2TopTagsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query2.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Date1@", String.valueOf(operation.dateA()));
					queryString = queryString.replaceAll("@Date2@", String.valueOf(operation.dateB()));
					String listString = "'" + operation.countries().get(0) + "'";
					for (int j = 1; j < operation.countries().size(); j++)
					{
					    listString += ", '" + operation.countries().get(j) + "'";
					}
					queryString = queryString.replaceAll("@Names@", listString);
					queryString = queryString.replaceAll("@Name2@", operation.countries().get(1));
					queryString = queryString.replaceAll("@EndDate@", String.valueOf(operation.endOfSimulationTime()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
					queryString = queryString.replaceAll("@MessageThreshold@", String.valueOf(operation.messageThreshold()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery2TopTags");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String country = result.getString(1);
					int month= result.getInt(2);
					String gender = result.getString(3);
					int ageGroup = result.getInt(4);
					String tag = result.getString(5);
					int count = result.getInt(6);
					LdbcSnbBiQuery2TopTagsResult tmp = new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery3TagEvolutionToVirtuoso implements OperationHandler<LdbcSnbBiQuery3TagEvolution, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery3TagEvolution operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery3TagEvolutionResult> RESULT = new ArrayList<LdbcSnbBiQuery3TagEvolutionResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query3.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Date1@", String.valueOf(operation.range1Start()));
					queryString = queryString.replaceAll("@Date2@", String.valueOf(operation.range1End()));
					queryString = queryString.replaceAll("@Date3@", String.valueOf(operation.range2Start()));
					queryString = queryString.replaceAll("@Date4@", String.valueOf(operation.range2End()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery3TagEvolution");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String tag = result.getString(1);
					int countA = result.getInt(2);
					int countB = result.getInt(3);
					int difference = result.getInt(4);
					LdbcSnbBiQuery3TagEvolutionResult tmp = new LdbcSnbBiQuery3TagEvolutionResult(tag, countA, countB, difference);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery4PopularCountryTopicsToVirtuoso implements OperationHandler<LdbcSnbBiQuery4PopularCountryTopics, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery4PopularCountryTopics operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery4PopularCountryTopicsResult> RESULT = new ArrayList<LdbcSnbBiQuery4PopularCountryTopicsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query4.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Type@", operation.tagClass());
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery4PopularCountryTopics");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long forumId = result.getLong(1);
					String forumTitle = result.getString(2);
					long forumCreationDate = result.getLong(3);
					long moderatorId = result.getLong(4);
					int count = result.getInt(5);
					LdbcSnbBiQuery4PopularCountryTopicsResult tmp = new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, forumTitle, forumCreationDate, moderatorId, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery5TopCountryPostersToVirtuoso implements OperationHandler<LdbcSnbBiQuery5TopCountryPosters, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery5TopCountryPosters operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery5TopCountryPostersResult> RESULT = new ArrayList<LdbcSnbBiQuery5TopCountryPostersResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query5.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
					queryString = queryString.replaceAll("@Limit1@", String.valueOf(operation.popularForumLimit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery5TopCountryPostersResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId = result.getLong(1);
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long creationDate = result.getLong(4);
					int count = result.getInt(5);
					LdbcSnbBiQuery5TopCountryPostersResult tmp = new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count); 
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery6ActivePostersToVirtuoso implements OperationHandler<LdbcSnbBiQuery6ActivePosters, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery6ActivePosters operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery6ActivePostersResult> RESULT = new ArrayList<LdbcSnbBiQuery6ActivePostersResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query6.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Tag@", operation.tag());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery6ActivePostersResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId = result.getLong(1);
					int postCount = result.getInt(2);
					int replyCount = result.getInt(3);
					int likeCount = result.getInt(4);
					int score = result.getInt(5);
					LdbcSnbBiQuery6ActivePostersResult tmp = new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score); 
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery7AuthoritativeUsersToVirtuoso implements OperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery7AuthoritativeUsers operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery7AuthoritativeUsersResult> RESULT = new ArrayList<LdbcSnbBiQuery7AuthoritativeUsersResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query7.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Tag@", operation.tag());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery7AuthoritativeUsersResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId = result.getLong(1);
					int score = result.getInt(2);
					LdbcSnbBiQuery7AuthoritativeUsersResult tmp = new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score); 
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery8RelatedTopicsToVirtuoso implements OperationHandler<LdbcSnbBiQuery8RelatedTopics, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery8RelatedTopics operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery8RelatedTopicsResult> RESULT = new ArrayList<LdbcSnbBiQuery8RelatedTopicsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query8.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Tag@", operation.tag());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery8RelatedTopicsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String tag = result.getString(1);
					int count = result.getInt(2);
					LdbcSnbBiQuery8RelatedTopicsResult tmp = new LdbcSnbBiQuery8RelatedTopicsResult(tag, count); 
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery9RelatedForumsToVirtuoso implements OperationHandler<LdbcSnbBiQuery9RelatedForums, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery9RelatedForums operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery9RelatedForumsResult> RESULT = new ArrayList<LdbcSnbBiQuery9RelatedForumsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query9.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@TagClass1@", operation.tagClassA());
					queryString = queryString.replaceAll("@TagClass2@", operation.tagClassB());
					queryString = queryString.replaceAll("@Threshold@", String.valueOf(operation.threshold()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery9RelatedForumsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long forumId = result.getLong(1);
				    int sumA = result.getInt(2);
				    int sumB = result.getInt(3);
					LdbcSnbBiQuery9RelatedForumsResult tmp = new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB); 
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery10TagPersonToVirtuoso implements OperationHandler<LdbcSnbBiQuery10TagPerson, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery10TagPerson operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery10TagPersonResult> RESULT = new ArrayList<LdbcSnbBiQuery10TagPersonResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query10.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Tag@", operation.tag());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery10TagPersonResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long personId = result.getLong(1);
				    int score = result.getInt(2);
				    // TODO: This query shold be changed
				    int friendsScore = 0;
					LdbcSnbBiQuery10TagPersonResult tmp = new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery11UnrelatedRepliesToVirtuoso implements OperationHandler<LdbcSnbBiQuery11UnrelatedReplies, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery11UnrelatedReplies operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery11UnrelatedRepliesResult> RESULT = new ArrayList<LdbcSnbBiQuery11UnrelatedRepliesResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query11.txt"));
				if (state.isRunSql()) {
					//TODO: This query should be updated
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery11UnrelatedRepliesResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId = result.getLong(1);
				    String tag = result.getString(2);
				    int likeCount = result.getInt(3);
				    //TODO: This query should be changed
				    int replyCount = 0;
					LdbcSnbBiQuery11UnrelatedRepliesResult tmp = new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tag, likeCount, replyCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery12TrendingPostsToVirtuoso implements OperationHandler<LdbcSnbBiQuery12TrendingPosts, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery12TrendingPosts operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery12TrendingPostsResult> RESULT = new ArrayList<LdbcSnbBiQuery12TrendingPostsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query12.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Count@", String.valueOf(operation.likeCount()));
					queryString = queryString.replaceAll("@Date@", String.valueOf(operation.date()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery12TrendingPostsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long messageId = result.getLong(1);
				    String firstName = result.getString(2);
				    String lastName = result.getString(3);
				    long creationDate = result.getLong(4);
				    int likeCount = result.getInt(5);
				   	LdbcSnbBiQuery12TrendingPostsResult tmp = new LdbcSnbBiQuery12TrendingPostsResult(messageId, firstName, lastName, creationDate, likeCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery13PopularMonthlyTagsToVirtuoso implements OperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery13PopularMonthlyTags operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery13PopularMonthlyTagsResult> RESULT = new ArrayList<LdbcSnbBiQuery13PopularMonthlyTagsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query13.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery13PopularMonthlyTagsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    int year = result.getInt(1);
				    int month = result.getInt(2);
				    //TODO: This query should be updated
				    List<TagPopularity> tagPopularities = new ArrayList<TagPopularity>();
				   	LdbcSnbBiQuery13PopularMonthlyTagsResult tmp = new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tagPopularities);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery14TopThreadInitiatorsToVirtuoso implements OperationHandler<LdbcSnbBiQuery14TopThreadInitiators, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery14TopThreadInitiators operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			//TODO: Server lost
			List<LdbcSnbBiQuery14TopThreadInitiatorsResult> RESULT = new ArrayList<LdbcSnbBiQuery14TopThreadInitiatorsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query14.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Date1@", String.valueOf(operation.beginDate()));
					queryString = queryString.replaceAll("@Date2@", String.valueOf(operation.endDate()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery14TopThreadInitiatorsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long personId = result.getLong(1);
				    String firstName = result.getString(2);
				    String lastName = result.getString(3);
				    int count = result.getInt(4);
				    int threadCount = result.getInt(5);				    				    
				   	LdbcSnbBiQuery14TopThreadInitiatorsResult tmp = new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery15SocialNormalsToVirtuoso implements OperationHandler<LdbcSnbBiQuery15SocialNormals, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery15SocialNormals operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery15SocialNormalsResult> RESULT = new ArrayList<LdbcSnbBiQuery15SocialNormalsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query15.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery15SocialNormalsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long personId = result.getLong(1);
				    int count = result.getInt(2);
				   	LdbcSnbBiQuery15SocialNormalsResult tmp = new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery16ExpertsInSocialCircleToVirtuoso implements OperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery16ExpertsInSocialCircle operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery16ExpertsInSocialCircleResult> RESULT = new ArrayList<LdbcSnbBiQuery16ExpertsInSocialCircleResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query16.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@TagClass@", operation.tagClass());
					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.person()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery16ExpertsInSocialCircleResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long personId = result.getLong(1);
				    String tag = result.getString(2);
				    int count = result.getInt(3);				    
				   	LdbcSnbBiQuery16ExpertsInSocialCircleResult tmp = new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery17FriendshipTrianglesToVirtuoso implements OperationHandler<LdbcSnbBiQuery17FriendshipTriangles, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery17FriendshipTriangles operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery17FriendshipTrianglesResult> RESULT = new ArrayList<LdbcSnbBiQuery17FriendshipTrianglesResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query17.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Country@", operation.country());
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery17FriendshipTrianglesResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    int count = result.getInt(1);				    
				   	LdbcSnbBiQuery17FriendshipTrianglesResult tmp = new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO: This doesn't work
			//resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery18PersonPostCountsToVirtuoso implements OperationHandler<LdbcSnbBiQuery18PersonPostCounts, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery18PersonPostCounts operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery18PersonPostCountsResult> RESULT = new ArrayList<LdbcSnbBiQuery18PersonPostCountsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query18.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Date@", String.valueOf(operation.date()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery18PersonPostCountsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    int messageCount = result.getInt(1);
				    int personCount = result.getInt(2);				    
				   	LdbcSnbBiQuery18PersonPostCountsResult tmp = new LdbcSnbBiQuery18PersonPostCountsResult(messageCount, personCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery19StrangerInteractionToVirtuoso implements OperationHandler<LdbcSnbBiQuery19StrangerInteraction, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery19StrangerInteraction operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery19StrangerInteractionResult> RESULT = new ArrayList<LdbcSnbBiQuery19StrangerInteractionResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query19.txt"));
				if (state.isRunSql()) {
					//TODO: This query should be changed
					queryString = queryString.replaceAll("@Date@", String.valueOf(operation.date()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery19StrangerInteractionResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    long personId = result.getLong(1);
				    //TODO: This query should be changed.
				    int strangerCount = 0;
				    int count = result.getInt(2);
				   	LdbcSnbBiQuery19StrangerInteractionResult tmp = new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery20HighLevelTopicsToVirtuoso implements OperationHandler<LdbcSnbBiQuery20HighLevelTopics, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery20HighLevelTopics operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery20HighLevelTopicsResult> RESULT = new ArrayList<LdbcSnbBiQuery20HighLevelTopicsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query20.txt"));
				if (state.isRunSql()) {
					//TODO: This query should be changed
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery20HighLevelTopicsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    String tagClass = result.getString(1);
				    int count = result.getInt(2);
				   	LdbcSnbBiQuery20HighLevelTopicsResult tmp = new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery21ZombiesToVirtuoso implements OperationHandler<LdbcSnbBiQuery21Zombies, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery21Zombies operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery21ZombiesResult> RESULT = new ArrayList<LdbcSnbBiQuery21ZombiesResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query21.txt"));
				if (state.isRunSql()) {
					//TODO: This query should be changed
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery21ZombiesResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					//TODO: This should be fixed
					long personId = 0;
					int zombieCount = 0;
					int realCount = 0;
					int score = 0;				    
				   	LdbcSnbBiQuery21ZombiesResult tmp = new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery22InternationalDialogToVirtuoso implements OperationHandler<LdbcSnbBiQuery22InternationalDialog, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery22InternationalDialog operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery22InternationalDialogResult> RESULT = new ArrayList<LdbcSnbBiQuery22InternationalDialogResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query22.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@CountryA@", operation.countryX());
					queryString = queryString.replaceAll("@CountryB@", operation.countryY());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery22InternationalDialogResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					// TODO: This query should be fixed
				    long personId1 = 0;
				    long personId2 = 0;
				    int score = 0;
				   	LdbcSnbBiQuery22InternationalDialogResult tmp = new LdbcSnbBiQuery22InternationalDialogResult(personId1, personId2, score);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery23HolidayDestinationsToVirtuoso implements OperationHandler<LdbcSnbBiQuery23HolidayDestinations, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery23HolidayDestinations operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery23HolidayDestinationsResult> RESULT = new ArrayList<LdbcSnbBiQuery23HolidayDestinationsResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query23.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@Country@", operation.country());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery23HolidayDestinationsResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    String place = result.getString(1);
				    int month = result.getInt(2);
				    int count = result.getInt(3);
				   	LdbcSnbBiQuery23HolidayDestinationsResult tmp = new LdbcSnbBiQuery23HolidayDestinationsResult(place, month, count);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
	
	public static class LdbcSnbBiQuery24MessagesByTopicToVirtuoso implements OperationHandler<LdbcSnbBiQuery24MessagesByTopic, VirtuosoDbConnectionState> {
		public void executeOperation(LdbcSnbBiQuery24MessagesByTopic operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
			Connection conn = state.getConn();
			Statement stmt = null;
			List<LdbcSnbBiQuery24MessagesByTopicResult> RESULT = new ArrayList<LdbcSnbBiQuery24MessagesByTopicResult>();
			int results_count = 0; RESULT.clear();
			try {
				String queryString = file2string(new File(state.getQueryDir(), "query24.txt"));
				if (state.isRunSql()) {
					queryString = queryString.replaceAll("@TagClass@", operation.tagClass());
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
				}
				else {

				}
				stmt = conn.createStatement();

				if (state.isPrintNames())
					System.out.println("########### LdbcSnbBiQuery24MessagesByTopicResult");
				if (state.isPrintStrings())
					System.out.println(queryString);

				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
				    int year = result.getInt(1);
				    int month = result.getInt(2);
				    String continent = result.getString(3);
				    int messageCount = result.getInt(4);
				    int likeCount = result.getInt(5);
				   	LdbcSnbBiQuery24MessagesByTopicResult tmp = new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try { stmt.close();conn.close(); } catch (SQLException e1) { }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultReporter.report(results_count, RESULT, operation);
		}
	}
}


