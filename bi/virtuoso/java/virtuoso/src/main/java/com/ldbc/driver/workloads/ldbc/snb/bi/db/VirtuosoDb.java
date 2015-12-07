package com.ldbc.driver.workloads.ldbc.snb.bi.db;


import com.ldbc.driver.*;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;

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
			//ds.setCharset("UTF-8");
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
					queryString = queryString.replaceAll("@Name1@", operation.countries().get(0));
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
					queryString = queryString.replaceAll("@Type@", String.valueOf(operation.tagClass()));
					queryString = queryString.replaceAll("@Country@", String.valueOf(operation.country()));
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
					queryString = queryString.replaceAll("@Country@", String.valueOf(operation.country()));
					queryString = queryString.replaceAll("@Limit@", String.valueOf(operation.limit()));
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
}

