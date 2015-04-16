package com.ldbc.driver.workloads.ldbc.snb.interactive.db;


import com.ldbc.driver.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson.Organization;

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
    protected void onInit(Map<String, String> properties) throws DbException {
    	try {
			virtuosoDbConnectionState = new VirtuosoDbConnectionState(properties);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		registerOperationHandler(LdbcQuery1.class, LdbcQuery1ToVirtuoso.class);
		registerOperationHandler(LdbcQuery2.class, LdbcQuery2ToVirtuoso.class);
		registerOperationHandler(LdbcQuery3.class, LdbcQuery3ToVirtuoso.class);
		registerOperationHandler(LdbcQuery4.class, LdbcQuery4ToVirtuoso.class);
		registerOperationHandler(LdbcQuery5.class, LdbcQuery5ToVirtuoso.class);
		registerOperationHandler(LdbcQuery6.class, LdbcQuery6ToVirtuoso.class);
		registerOperationHandler(LdbcQuery7.class, LdbcQuery7ToVirtuoso.class);
		registerOperationHandler(LdbcQuery8.class, LdbcQuery8ToVirtuoso.class);
		registerOperationHandler(LdbcQuery9.class, LdbcQuery9ToVirtuoso.class);
		registerOperationHandler(LdbcQuery10.class, LdbcQuery10ToVirtuoso.class);
		registerOperationHandler(LdbcQuery11.class, LdbcQuery11ToVirtuoso.class);
        registerOperationHandler(LdbcQuery12.class, LdbcQuery12ToVirtuoso.class);
        registerOperationHandler(LdbcQuery13.class, LdbcQuery13ToVirtuoso.class);
        registerOperationHandler(LdbcQuery14.class, LdbcQuery14ToVirtuoso.class);
        
        registerOperationHandler(LdbcShortQuery1PersonProfile.class, LdbcShortQuery1ToVirtuoso.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, LdbcShortQuery2ToVirtuoso.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, LdbcShortQuery3ToVirtuoso.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, LdbcShortQuery4ToVirtuoso.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, LdbcShortQuery5ToVirtuoso.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, LdbcShortQuery6ToVirtuoso.class);
        registerOperationHandler(LdbcShortQuery7MessageReplies.class, LdbcShortQuery7ToVirtuoso.class);
	        
	    if (properties.get("run_sql").equals("true")) {
	        registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentToVirtuoso.class);
	        registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipToVirtuoso.class);
    	}
	    else {
	        registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentToVirtuosoSparql.class);
	        registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipToVirtuosoSparql.class);
    	}
    }

    @Override
    protected void onClose()   {
	System.out.println("ON CLOSE()");
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
                return ds.getPooledConnection().getConnection();
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
	
	@Override
	    public void close() throws IOException {
	    // TODO Auto-generated method stub
	    
	}
		
    }

    public static class LdbcQuery1ToVirtuoso implements OperationHandler<LdbcQuery1, VirtuosoDbConnectionState> {
        @Override
        public void executeOperation(LdbcQuery1 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery1Result> RESULT = new ArrayList<LdbcQuery1Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query1.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Name@", operation.firstName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Name%", operation.firstName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery1");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
        		
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long id;
					if (state.isRunSql())
						id = result.getLong(1);
					else
						id = Long.parseLong(result.getString(1).substring(47));
					String lastName = result.getString(2);
					int dist = result.getInt(3);
					long birthday = result.getLong(4);
					long creationDate = result.getLong(5);
					String gender = result.getString(6);
					String browserUsed = result.getString(7);
					String ip = result.getString(8);
					Collection<String> emails =  result.getString(9) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(9).split(", ")));
					Collection<String> languages =  result.getString(10) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(10).split(", ")));
					String place = result.getString(11);
					ArrayList<List<Object>> universities = null;
					if (result.getString(12) != null) {
						List<String> items = new ArrayList<String>(Arrays.asList(result.getString(12).split(", ")));
						universities = new ArrayList<List<Object>>();
						for (int i = 0; i < items.size(); i++) {
							universities.add(new ArrayList<Object>(Arrays.asList(items.get(i).split(" "))));
						}
					}
					else
						universities = new ArrayList<List<Object>>();
					ArrayList<List<Object>> companies = null;
					if (result.getString(13) != null) {
						List<String> items = new ArrayList<String>(Arrays.asList(result.getString(13).split(", ")));
						companies = new ArrayList<List<Object>>();
						for (int i = 0; i < items.size(); i++) {
							companies.add(new ArrayList<Object>(Arrays.asList(items.get(i).split(" "))));
						}
					}
					else
						companies = new ArrayList<List<Object>>();
					LdbcQuery1Result tmp = new LdbcQuery1Result(id, lastName, dist, birthday, creationDate,
							gender, browserUsed, ip, emails, languages, place, universities, companies);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcQuery2ToVirtuoso implements OperationHandler<LdbcQuery2, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery2 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery2Result> RESULT = new ArrayList<LdbcQuery2Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query2.txt"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
				queryString = queryString.replaceAll("@Date0@", sdf.format(operation.maxDate()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
			queryString = queryString.replaceAll("%Date0%", sdf.format(operation.maxDate()));
        		}
        		Statement  stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery2");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long id;
					if (state.isRunSql())
						id = result.getLong(1);
					else
						id = Long.parseLong(result.getString(1).substring(47));
					if (id == 0)
					    System.out.println("@@@@@@@@@@@@@@@@@@@@@@ - " + id);
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long postid;
					if (state.isRunSql())
						postid = result.getLong(4);
					else
						postid = Long.parseLong(result.getString(4).substring(47));
					if (postid == 0)
					    System.out.println("@@@@@@@@@@@@@@@@@@@@@@ - " + postid);
					String content = result.getString(5);
					long postdate = result.getLong(6);
					LdbcQuery2Result tmp = new LdbcQuery2Result(id, firstName, lastName, postid, content, postdate);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcQuery3ToVirtuoso implements OperationHandler<LdbcQuery3, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery3 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery3Result> RESULT = new ArrayList<LdbcQuery3Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query3.txt"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Country1@", operation.countryXName());
        			queryString = queryString.replaceAll("@Country2@", operation.countryYName());
				queryString = queryString.replaceAll("@Date0@", sdf.format(operation.startDate()));
        			queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationDays()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Country1%", operation.countryXName());
            		queryString = queryString.replaceAll("%Country2%", operation.countryYName());
			queryString = queryString.replaceAll("%Date0%", sdf.format(operation.startDate()));
            		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
        		}
        		Statement  stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery3");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long id;
					if (state.isRunSql())
						id = result.getLong(1);
					else
						id = Long.parseLong(result.getString(1).substring(47));
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long ct1 = result.getLong(4);
					long ct2 = result.getLong(5);
					long total = result.getLong(6);
					LdbcQuery3Result tmp = new LdbcQuery3Result(id, firstName, lastName, ct1, ct2, total);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    
    public static class LdbcQuery4ToVirtuoso implements OperationHandler<LdbcQuery4, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery4 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery4Result> RESULT = new ArrayList<LdbcQuery4Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query4.txt"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
				queryString = queryString.replaceAll("@Date0@", sdf.format(operation.startDate()));
        			queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationDays()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
			queryString = queryString.replaceAll("%Date0%", sdf.format(operation.startDate()));
            		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery4");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery4Result tmp = new LdbcQuery4Result(tagName, tagCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcQuery5ToVirtuoso implements OperationHandler<LdbcQuery5, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery5 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery5Result> RESULT = new ArrayList<LdbcQuery5Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query5.txt"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
				queryString = queryString.replaceAll("@Date0@", sdf.format(operation.minDate()));
        		}
        		else {
        			queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
			queryString = queryString.replaceAll("%Date0%", sdf.format(operation.minDate()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery5");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String forumTitle = result.getString(1);
					int postCount = result.getInt(3);
					LdbcQuery5Result tmp = new LdbcQuery5Result(forumTitle, postCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    

    public static class LdbcQuery6ToVirtuoso implements OperationHandler<LdbcQuery6, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery6 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery6Result> RESULT = new ArrayList<LdbcQuery6Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query6.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Tag@", operation.tagName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Tag%", operation.tagName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery6");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery6Result tmp = new LdbcQuery6Result(tagName, tagCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    

    public static class LdbcQuery7ToVirtuoso implements OperationHandler<LdbcQuery7, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery7 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery7Result> RESULT = new ArrayList<LdbcQuery7Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query7.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery7");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (state.isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long likeCreationDate = result.getLong(4);
					boolean isNew = result.getInt(5) == 1 ? true : false;
					long postId;
					if (state.isRunSql())
						postId = result.getLong(6);
					else
						postId = Long.parseLong(result.getString(6).substring(47));
					String postContent = result.getString(7);
					int milliSecondDelay = result.getInt(8);
					LdbcQuery7Result tmp = new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, postId, postContent, milliSecondDelay, isNew);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }


    public static class LdbcQuery8ToVirtuoso implements OperationHandler<LdbcQuery8, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery8 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery8Result> RESULT = new ArrayList<LdbcQuery8Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query8.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));        			
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery8");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (state.isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long replyCreationDate = result.getLong(4);
					long replyId;
					if (state.isRunSql())
						replyId = result.getLong(5);
					else
						replyId = Long.parseLong(result.getString(5).substring(47));
					// TODO
					String replyContent = result.getString(6);
					//String replyContent = null;
					LdbcQuery8Result tmp = new LdbcQuery8Result(personId, personFirstName, personLastName, replyCreationDate, replyId, replyContent);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    public static class LdbcQuery9ToVirtuoso implements OperationHandler<LdbcQuery9, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery9 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery9Result> RESULT = new ArrayList<LdbcQuery9Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query9.txt"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
				queryString = queryString.replaceAll("@Date0@", sdf.format(operation.maxDate()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
			queryString = queryString.replaceAll("%Date0%", sdf.format(operation.maxDate()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery9");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (state.isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long postOrCommentId;
					if (state.isRunSql())
						postOrCommentId = result.getLong(4);
					else
						postOrCommentId = Long.parseLong(result.getString(4).substring(47));
					String postOrCommentContent = result.getString(5);
					long postOrCommentCreationDate = result.getLong(6);
					//System.out.println("### " + result.getTimestamp(6));
					//System.out.println("### " + result.getString(6));
					//System.out.println("### " + Timestamp.valueOf(result.getString(6)).getTime());
					//System.out.println("### " + Timestamp.valueOf(result.getString(6)).getTimezoneOffset());
					// create time zone object 
					TimeZone tzone = TimeZone.getTimeZone("UTC");
					// set time zone to default
					tzone.setDefault(tzone);
					//System.out.println("@@@ " + result.getTimestamp(6));
					//System.out.println("@@@ " + result.getString(6));
					//System.out.println("@@@ " + Timestamp.valueOf(result.getString(6)).getTime());
					//System.out.println("@@@ " + Timestamp.valueOf(result.getString(6)).getTimezoneOffset());
					LdbcQuery9Result tmp = new LdbcQuery9Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    
    public static class LdbcQuery10ToVirtuoso implements OperationHandler<LdbcQuery10, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery10 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery10Result> RESULT = new ArrayList<LdbcQuery10Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query10.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@HS0@", String.valueOf(operation.month()));
        			queryString = queryString.replaceAll("@HS1@", String.valueOf((operation.month() + 1) % 12));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%HS0%", String.valueOf(operation.month()));
               		queryString = queryString.replaceAll("%HS1%", String.valueOf((operation.month() + 1) % 12));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery10");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int commonInterestScore = result.getInt(3);
					long personId;
					if (state.isRunSql())
						personId = result.getLong(4);
					else
						personId = Long.parseLong(result.getString(4).substring(47));
					String gender = result.getString(5);
					String personCityName = result.getString(6);
					LdbcQuery10Result tmp = new LdbcQuery10Result(personId, personFirstName, personLastName, commonInterestScore, gender, personCityName);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    
    public static class LdbcQuery11ToVirtuoso implements OperationHandler<LdbcQuery11, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery11 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery11Result> RESULT = new ArrayList<LdbcQuery11Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query11.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Date0@", String.valueOf(operation.workFromYear()));
        			queryString = queryString.replaceAll("@Country@", operation.countryName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Date0%", String.valueOf(operation.workFromYear()));
            		queryString = queryString.replaceAll("%Country%", operation.countryName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery11");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int organizationWorkFromYear = result.getInt(3);
					String organizationName = result.getString(4);
					long personId;
					if (state.isRunSql())
						personId = result.getLong(5);
					else
						personId = Long.parseLong(result.getString(5).substring(47));;
					LdbcQuery11Result tmp = new LdbcQuery11Result(personId, personFirstName, personLastName, organizationName, organizationWorkFromYear);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    public static class LdbcQuery12ToVirtuoso implements OperationHandler<LdbcQuery12, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery12 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery12Result> RESULT = new ArrayList<LdbcQuery12Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query12.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@TagType@", operation.tagClassName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%TagType%", operation.tagClassName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery12");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (state.isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					Collection<String> tagNames =  result.getString(4) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(4).split(", ")));
					int replyCount = result.getInt(5);
					LdbcQuery12Result tmp = new LdbcQuery12Result(personId, personFirstName, personLastName, tagNames, replyCount);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    
    public static class LdbcQuery13ToVirtuoso implements OperationHandler<LdbcQuery13, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery13 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery13Result> RESULT = new ArrayList<LdbcQuery13Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query13.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
        			queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
        		}
        		else {
        			queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
            		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery13");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					int shortestPathlength = result.getInt(1);
					LdbcQuery13Result tmp = new LdbcQuery13Result(shortestPathlength);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT.get(0), operation);
        }
    }
    
    public static class LdbcQuery14ToVirtuoso implements OperationHandler<LdbcQuery14, VirtuosoDbConnectionState> {

        @Override
        public void executeOperation(LdbcQuery14 operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	Connection conn = state.getConn();
		List<LdbcQuery14Result> RESULT = new ArrayList<LdbcQuery14Result>();
        	int results_count = 0; RESULT.clear();
        	try {
        		String queryString = file2string(new File(state.getQueryDir(), "query14.txt"));
        		if (state.isRunSql()) {
        			queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
        			queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
            		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcQuery14");
        		if (state.isPrintStrings())
        			System.out.println(queryString);
        		
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					openlink.util.Vector o1 = (openlink.util.Vector)(result.getObject(1));
					Long [] ttt = new Long[o1.size()];
					for (int i = 0; i < o1.size(); i++) {
					    if (o1.elementAt(i) instanceof Long)
						ttt[i] = (Long)o1.elementAt(i);
					    else if (o1.elementAt(i) instanceof Integer)
						ttt[i] = new Long((Integer)o1.elementAt(i));
					}
					double weight = result.getDouble(2);
					LdbcQuery14Result tmp = new LdbcQuery14Result(new ArrayList<Long>(Arrays.asList(ttt)), weight);
					if (state.isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcShortQuery1ToVirtuoso implements OperationHandler<LdbcShortQuery1PersonProfile, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery1PersonProfile operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	LdbcShortQuery1PersonProfileResult RESULT = null;
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("person_view_1(?)");
        		stmt1.setLong(1, operation.personId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery1");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery1 (" + operation.personId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					String firstName = rs.getString(1);
					String lastName = rs.getString(2);
					String gender = rs.getString(3);
					long birthday = rs.getLong(4);
					long creationDate = rs.getLong(5);
					String locationIp = rs.getString(6);
					String browserUsed = rs.getString(7);
					long cityId = rs.getLong(8);
					RESULT = new LdbcShortQuery1PersonProfileResult(firstName, lastName, birthday, locationIp, browserUsed, cityId, gender, creationDate);
					if (state.isPrintResults())
					    System.out.println(RESULT.toString());
        			}
        		}
			stmt1.close();conn.close();
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcShortQuery2ToVirtuoso implements OperationHandler<LdbcShortQuery2PersonPosts, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery2PersonPosts operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	List<LdbcShortQuery2PersonPostsResult> RESULT = new ArrayList<LdbcShortQuery2PersonPostsResult>();
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("person_view_2(?)");
        		stmt1.setLong(1, operation.personId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery2");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery2 (" + operation.personId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					long postId = rs.getLong(1);
					String postContent = rs.getString(2);
					if (postContent == null || postContent.length() == 0)
					    postContent = rs.getString(3);
    					long postCreationTime = rs.getLong(4);
    					long origPostId = rs.getLong(5);
    					long origPersonId = rs.getLong(6);
    					String origFirstName = rs.getString(7);
					String origLastName = rs.getString(8);
					if (postId == 0)
					    System.out.println("1@@@@@@@@@@@@@@@@@@@@@@ - " + postId);
					if (origPostId == 0)
					    System.out.println("2@@@@@@@@@@@@@@@@@@@@@@ - " + origPostId);
					if (origPersonId == 0)
					    System.out.println("3@@@@@@@@@@@@@@@@@@@@@@ - " + origPersonId);
    					LdbcShortQuery2PersonPostsResult tmp = new LdbcShortQuery2PersonPostsResult(postId, postContent, postCreationTime, origPostId, origPersonId, origFirstName, origLastName);
    					if (state.isPrintResults())
    						System.out.println(tmp.toString());
    					RESULT.add(tmp);
        			}
        		}
				stmt1.close();conn.close();
			} catch (SQLException e) {
		    System.out.println("Err: LdbcShortQuery2 (" + operation.personId() + ")");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
		    System.out.println("Err: LdbcShortQuery2 (" + operation.personId() + ")");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcShortQuery3ToVirtuoso implements OperationHandler<LdbcShortQuery3PersonFriends, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery3PersonFriends operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	List<LdbcShortQuery3PersonFriendsResult> RESULT = new ArrayList<LdbcShortQuery3PersonFriendsResult>();
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("person_view_3(?)");
        		stmt1.setLong(1, operation.personId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery3");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery3 (" + operation.personId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					long personId = rs.getLong(1);
    					String firstName = rs.getString(2);
    					String lastName = rs.getString(3);
    					long since = rs.getLong(4);
					if (personId == 0)
					    System.out.println("4@@@@@@@@@@@@@@@@@@@@@@ - " + personId);
    					LdbcShortQuery3PersonFriendsResult tmp = new LdbcShortQuery3PersonFriendsResult(personId, firstName, lastName, since);
    					if (state.isPrintResults())
    						System.out.println(tmp.toString());
    					RESULT.add(tmp);
        			}
        		}
				stmt1.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    public static class LdbcShortQuery4ToVirtuoso implements OperationHandler<LdbcShortQuery4MessageContent, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery4MessageContent operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	LdbcShortQuery4MessageContentResult RESULT = null;
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("post_view_1(?)");
        		stmt1.setLong(1, operation.messageId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery4");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery4 (" + operation.messageId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					String messageContent = rs.getString(1);
					if (messageContent == null || messageContent.length() == 0)
					    messageContent = rs.getString(2);
    					long creationDate = rs.getLong(3);
    					RESULT = new LdbcShortQuery4MessageContentResult(messageContent, creationDate);
    					if (state.isPrintResults())
    						System.out.println(RESULT.toString());
        			}
        		}
				stmt1.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
		    System.out.println("Err: LdbcShortQuery4 (" + operation.messageId() + ")");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
		    System.out.println("Err: LdbcShortQuery4 (" + operation.messageId() + ")");
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    public static class LdbcShortQuery5ToVirtuoso implements OperationHandler<LdbcShortQuery5MessageCreator, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery5MessageCreator operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	LdbcShortQuery5MessageCreatorResult RESULT = null;
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("post_view_2(?)");
        		stmt1.setLong(1, operation.messageId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery5");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery5 (" + operation.messageId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					long personId = rs.getLong(1);
    					String firstName = rs.getString(2);
    					String lastName = rs.getString(3);
					if (personId == 0)
					    System.out.println("5@@@@@@@@@@@@@@@@@@@@@@ - " + personId);
    					RESULT = new LdbcShortQuery5MessageCreatorResult(personId, firstName, lastName);
    					if (state.isPrintResults())
    						System.out.println(RESULT.toString());
        			}
        		}
				stmt1.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }

    public static class LdbcShortQuery6ToVirtuoso implements OperationHandler<LdbcShortQuery6MessageForum, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery6MessageForum operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	LdbcShortQuery6MessageForumResult RESULT = null;
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("post_view_3(?)");
        		stmt1.setLong(1, operation.messageId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery6");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery6 (" + operation.messageId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					long forumId = rs.getLong(1);
    					String forumTitle = rs.getString(2);
    					long moderatorId = rs.getLong(3);
    					String moderatorFirstName = rs.getString(4);
    					String moderatorLastName = rs.getString(5);
					if (moderatorId == 0)
					    System.out.println("6@@@@@@@@@@@@@@@@@@@@@@ - " + moderatorId);
    					RESULT = new LdbcShortQuery6MessageForumResult(forumId, forumTitle, moderatorId, moderatorFirstName, moderatorLastName);
    					if (state.isPrintResults())
    						System.out.println(RESULT.toString());
        			}
        		}
				stmt1.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcShortQuery7ToVirtuoso implements OperationHandler<LdbcShortQuery7MessageReplies, VirtuosoDbConnectionState> {   
        @Override
        public void executeOperation(LdbcShortQuery7MessageReplies operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	List<LdbcShortQuery7MessageRepliesResult> RESULT = new ArrayList<LdbcShortQuery7MessageRepliesResult>();
        	int results_count = 0;
        	try {
		    Connection conn = state.getConn();
        		CallableStatement stmt1 = conn.prepareCall("post_view_4(?)");
        		stmt1.setLong(1, operation.messageId());
        		
        		if (state.isPrintNames())
        			System.out.println("########### LdbcShortQuery7");
        		if (state.isPrintStrings())
        			System.out.println("LdbcShortQuery7 (" + operation.messageId() + ")");
        		
        		boolean results = stmt1.execute();
        		if (results) {
        			ResultSet rs = stmt1.getResultSet();
        			while (rs.next()) {
    					results_count++;
    					long commentId = rs.getLong(1);
    					String commentContent = rs.getString(2);
					long creationDate = rs.getLong(3);
    					long personId = rs.getLong(4);
    					String firstName = rs.getString(5);
    					String lastName = rs.getString(6);
    					int knows = rs.getInt(7);
					boolean knows_b = (knows == 1) ? true : false;
					if (commentId == 0)
					    System.out.println("7@@@@@@@@@@@@@@@@@@@@@@ - " + commentId);
					if (personId == 0)
					    System.out.println("8@@@@@@@@@@@@@@@@@@@@@@ - " + personId);
    					LdbcShortQuery7MessageRepliesResult tmp = new LdbcShortQuery7MessageRepliesResult(commentId, commentContent, creationDate, personId, firstName, lastName, knows_b);
    					if (state.isPrintResults())
    						System.out.println(tmp.toString());
    					RESULT.add(tmp);
        			}
        		}
				stmt1.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(results_count, RESULT, operation);
        }
    }
    
    public static class LdbcUpdate1AddPersonToVirtuoso implements OperationHandler<LdbcUpdate1AddPerson, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate1AddPerson operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
		       	Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate1");
        		if (state.isPrintStrings()) {
	            	System.out.println(operation.personId() + " " + operation.personFirstName() + " " + operation.personLastName());
	            	System.out.println(operation.gender());
	            	System.out.println(operation.birthday());
	            	System.out.println(operation.creationDate());
	            	System.out.println(operation.locationIp());
	            	System.out.println(operation.browserUsed());
	            	System.out.println(operation.cityId());
	            	System.out.println("[");
	            	for (String lan : operation.languages()) {
						System.out.println(lan);
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (String email : operation.emails()) {
						System.out.println(email);
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (Organization tag : operation.studyAt()) {
						System.out.println(tag.organizationId() + " - " + tag.year());
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (Organization tag : operation.workAt()) {
						System.out.println(tag.organizationId() + " - " + tag.year());
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdate1AddPerson(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setString(2, operation.personFirstName());
        	    cs.setString(3, operation.personLastName());
        	    cs.setString(4, operation.gender());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
        	    cs.setString(5, df.format(operation.birthday()));
		    cs.setString(6, df.format(operation.creationDate()));
        	    cs.setString(7, operation.locationIp());
        	    cs.setString(8, operation.browserUsed());
        	    cs.setLong(9, operation.cityId());
        	    cs.setArray(10, conn.createArrayOf("varchar", operation.languages().toArray(new String[operation.languages().size()])));
        	    cs.setArray(11, conn.createArrayOf("varchar", operation.emails().toArray(new String[operation.emails().size()])));
        	    Long tagIds1 [] = new Long[operation.tagIds().size()];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(12, conn.createArrayOf("int", tagIds1));
        	    Long universityIds [] = new Long[operation.studyAt().size()];
        	    Integer universityYears [] = new Integer[operation.studyAt().size()];
        	    i=0;
        	    for(Organization temp:operation.studyAt()){
        	        universityIds[i] = temp.organizationId();
        	        universityYears[i++] = temp.year();
        	    }
        	    cs.setArray(13, conn.createArrayOf("int", universityIds));
        	    cs.setArray(14, conn.createArrayOf("int", universityYears));
        	    Long companyIds [] = new Long[operation.workAt().size()];
        	    Integer companyYears [] = new Integer[operation.workAt().size()];
        	    i=0;
        	    for(Organization temp:operation.workAt()){
        	        companyIds[i] = temp.organizationId();
        	        companyYears[i++] = temp.year();
        	    }
        	    cs.setArray(15, conn.createArrayOf("int", companyIds));
        	    cs.setArray(16, conn.createArrayOf("int", companyYears));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate1AddPersonToVirtuosoSparql implements OperationHandler<LdbcUpdate1AddPerson, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate1AddPerson operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
		   	Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate1");
        		if (state.isPrintStrings()) {
	            	System.out.println(operation.personId() + " " + operation.personFirstName() + " " + operation.personLastName());
	            	System.out.println(operation.gender());
	            	System.out.println(operation.birthday());
	            	System.out.println(operation.creationDate());
	            	System.out.println(operation.locationIp());
	            	System.out.println(operation.browserUsed());
	            	System.out.println(operation.cityId());
	            	System.out.println("[");
	            	for (String lan : operation.languages()) {
						System.out.println(lan);
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (String email : operation.emails()) {
						System.out.println(email);
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (Organization tag : operation.studyAt()) {
						System.out.println(tag.organizationId() + " - " + tag.year());
					}
	            	System.out.println("]");
	            	System.out.println("[");
	            	for (Organization tag : operation.workAt()) {
						System.out.println(tag.organizationId() + " - " + tag.year());
					}
	            	System.out.println("]");
        		}        		
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
        		DateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
			df2.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[9 + operation.languages().size() + operation.emails().size() + operation.tagIds().size() + operation.studyAt().size() + operation.workAt().size()];
        		triplets[0] = personUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Person> .";
        		triplets[1] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/firstName> \"" + operation.personFirstName() + "\" .";
        		triplets[2] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/lastName> \"" + operation.personLastName() + "\" .";
        		triplets[3] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/gender> \"" + operation.gender() + "\" .";
        		triplets[4] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/birthday> \"" + df2.format(operation.birthday()) + "\" .";
        		triplets[5] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        		triplets[6] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/locationIP> \"" + operation.locationIp() + "\" .";
        		triplets[7] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/browserUsed> \"" + operation.browserUsed() + "\" .";
        		// TODO: isLocatedIn -> cityId vs <http://dbpedia.org/resource/Afghanistan>
        		triplets[8] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/isLocatedIn> \"" + operation.cityId() + "\" .";
        		int j = 9;
        		for (int k = 0; k < operation.languages().size(); k++, j++)
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/speaks> \"" + operation.languages().get(k) + "\" .";
        		for (int k = 0; k < operation.emails().size(); k++, j++)
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/email> \"" + operation.emails().get(k) + "\" .";
        		for (int k = 0; k < operation.tagIds().size(); k++, j++)
        			//TODO: hasInterest -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasInterest> \"" + operation.tagIds().get(k) + "\" .";
           		for (int k = 0; k < operation.studyAt().size(); k++, j++)
        			//TODO: studyAt -> organisationId vs <http://dbpedia.org/resource/Rutherford_University>
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/studyAt> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasOrganisation> \"" + operation.studyAt().get(k).organizationId() + "\"; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/classYear> \"" + operation.studyAt().get(k).year() + "\"] .";
           		for (int k = 0; k < operation.workAt().size(); k++, j++)
        			//TODO: workAt -> organisationId vs <http://dbpedia.org/resource/Hong_Kong_Air_International_Ltd>
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/workAt> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasOrganisation> \"" + operation.workAt().get(k).organizationId() + "\"; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/workFrom> \"" + operation.workAt().get(k).year() + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate2AddPostLikeToVirtuoso implements OperationHandler<LdbcUpdate2AddPostLike, VirtuosoDbConnectionState> {
        
        @Override
        public void executeOperation(LdbcUpdate2AddPostLike operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
                	System.out.println("########### LdbcUpdate2");
        		if (state.isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.postId() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate2AddPostLike(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setLong(2, operation.postId());
		    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
		    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		    cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate2AddPostLikeToVirtuosoSparql implements OperationHandler<LdbcUpdate2AddPostLike, VirtuosoDbConnectionState> {
        @Override
        public void executeOperation(LdbcUpdate2AddPostLike operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
                	System.out.println("########### LdbcUpdate2");
        		if (state.isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.postId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		String postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.postId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[1];
        		triplets[0] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/likes> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPost> " + postUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate3AddCommentLikeToVirtuoso implements OperationHandler<LdbcUpdate3AddCommentLike, VirtuosoDbConnectionState> {
        @Override
        public void executeOperation(LdbcUpdate3AddCommentLike operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate3");
        		if (state.isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.commentId() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate2AddPostLike(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setLong(2, operation.commentId());
		    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
		    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		    cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate3AddCommentLikeToVirtuosoSparql implements OperationHandler<LdbcUpdate3AddCommentLike, VirtuosoDbConnectionState> {
        @Override
        public void executeOperation(LdbcUpdate3AddCommentLike operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate3");
        		if (state.isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.commentId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		String commentUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.commentId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[1];
        		triplets[0] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/likes> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasComment> " + commentUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);

        }
    }
    
    public static class LdbcUpdate4AddForumToVirtuoso implements OperationHandler<LdbcUpdate4AddForum, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate4AddForum operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
           		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate4");
        		if (state.isPrintStrings()) {
	            	System.out.println(operation.forumId() + " " + operation.forumTitle());
	            	System.out.println(operation.creationDate() + " " + operation.moderatorPersonId());
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdate4AddForum(?, ?, ?, ?, ?)";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.forumId());
        	    cs.setString(2, operation.forumTitle());
		    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
		    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		    cs.setString(3, df.format(operation.creationDate()));
        	    cs.setLong(4, operation.moderatorPersonId());
        	    Long tagIds1 [] = new Long[operation.tagIds().size()];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(5, conn.createArrayOf("int", tagIds1));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate4AddForumToVirtuosoSparql implements OperationHandler<LdbcUpdate4AddForum, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate4AddForum operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate4");
        		if (state.isPrintStrings()) {
	            	System.out.println(operation.forumId() + " " + operation.forumTitle());
	            	System.out.println(operation.creationDate() + " " + operation.moderatorPersonId());
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String moderatorUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.moderatorPersonId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[4 + operation.tagIds().size()];
        		triplets[0] = forumUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Forum> .";
        		triplets[1] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/title> \"" + operation.forumTitle() + "\" .";
        		triplets[2] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        		triplets[3] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasModerator> " + moderatorUri + " .";
        		for (int k = 0; k < operation.tagIds().size(); k++)
        			//TODO: hasTag -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
        			triplets[4 + k] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasTag> \"" + operation.tagIds().get(k) + "\" .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate5AddForumMembershipToVirtuoso implements OperationHandler<LdbcUpdate5AddForumMembership, VirtuosoDbConnectionState> {
        @Override
        public void executeOperation(LdbcUpdate5AddForumMembership operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate5");
        		if (state.isPrintStrings())
        			System.out.println(operation.forumId() + " " + operation.personId() + " " + operation.joinDate());
        		String queryString = "{call LdbcUpdate5AddForumMembership(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.forumId());
        		cs.setLong(2, operation.personId());
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			cs.setString(3, df.format(operation.joinDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate5AddForumMembershipToVirtuosoSparql implements OperationHandler<LdbcUpdate5AddForumMembership, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate5AddForumMembership operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate5");
        		if (state.isPrintStrings())
        			System.out.println(operation.forumId() + " " + operation.personId() + " " + operation.joinDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String memberUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[1];
        		triplets[0] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasMember> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson> " + memberUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/joinDate> \"" + df1.format(operation.joinDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate6AddPostToVirtuoso implements OperationHandler<LdbcUpdate6AddPost, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate6AddPost operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate6");
        		if (state.isPrintStrings()) {
	            	System.out.println(operation.postId() + " " + operation.imageFile());
	            	System.out.println(operation.creationDate() + " " + operation.locationIp());
	            	System.out.println(operation.browserUsed() + " " + operation.language());
	            	System.out.println(operation.content());
	            	System.out.println(operation.length() + " " + operation.authorPersonId());
	            	System.out.println(operation.forumId() + " " + operation.countryId());
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdate6AddPost(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.postId());
        	    cs.setString(2, operation.imageFile());
		    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
		    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		    cs.setString(3, df.format(operation.creationDate()));
        	    cs.setString(4, operation.locationIp());
        	    cs.setString(5, operation.browserUsed());
        	    cs.setString(6, operation.language());
        	    cs.setString(7, operation.content());
        	    cs.setInt(8, operation.length());
        	    cs.setLong(9, operation.authorPersonId());
        	    cs.setLong(10, operation.forumId());
        	    cs.setLong(11, operation.countryId());
        	    Long tagIds1 [] = new Long[operation.tagIds().size()];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(12, conn.createArrayOf("int", tagIds1));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }  
    
    public static class LdbcUpdate6AddPostToVirtuosoSparql implements OperationHandler<LdbcUpdate6AddPost, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate6AddPost operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
    		        Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate6");
        		if (state.isPrintStrings()) {
	            	System.out.println(operation.postId() + " " + operation.imageFile());
	            	System.out.println(operation.creationDate() + " " + operation.locationIp());
	            	System.out.println(operation.browserUsed() + " " + operation.language());
	            	System.out.println(operation.content());
	            	System.out.println(operation.length() + " " + operation.authorPersonId());
	            	System.out.println(operation.forumId() + " " + operation.countryId());
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.postId()) + ">";
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String authorUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.authorPersonId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		if (operation.imageFile().equals("")) {
        			String triplets [] = new String[10 + operation.tagIds().size()];
        			triplets[0] = postUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Post> .";
        			triplets[1] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/locationIP> \"" + operation.locationIp() + "\" .";
        			triplets[2] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        			triplets[3] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/browserUsed> \"" + operation.browserUsed() + "\" .";
        			triplets[4] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/language> \"" + operation.language() + "\" .";
        			triplets[5] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/content> \"" + operation.content() + "\" .";
        			triplets[6] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/size()> \"" + operation.length() + "\" .";
        			triplets[7] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator> " + authorUri + " .";
        			//TODO: countryId() -> long vs <http://dbpedia.org/resource/Sweden>
        			triplets[8] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/isLocatedIn> \"" + operation.countryId() + "\" .";
        			triplets[9] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/containerOf> " + postUri + " .";
        			for (int k = 0; k < operation.tagIds().size(); k++)
        				//TODO: hasTag -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
        				triplets[10 + k] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasTag> \"" + operation.tagIds().get(k) + "\" .";
        			cs.setArray(1, conn.createArrayOf("varchar", triplets));
        			cs.execute();
        		}
        		else {
        			String triplets [] = new String[8];
        			triplets[0] = postUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Post> .";
        			triplets[1] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/locationIP> \"" + operation.locationIp() + "\" .";
        			triplets[2] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        			triplets[3] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/browserUsed> \"" + operation.browserUsed() + "\" .";
        			triplets[4] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/imageFile> \"" + operation.imageFile() + "\" .";
        			triplets[5] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator> " + authorUri + " .";
        			//TODO: countryId() -> long vs <http://dbpedia.org/resource/Sweden>
        			triplets[6] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/isLocatedIn> \"" + operation.countryId() + "\" .";
        			triplets[7] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/containerOf> " + postUri + " .";
        			cs.setArray(1, conn.createArrayOf("varchar", triplets));
        			cs.execute();
        		}
        		cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate7AddCommentToVirtuoso implements OperationHandler<LdbcUpdate7AddComment, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate7AddComment operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn(); 
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate7");
        		if (state.isPrintStrings()) {
	            	System.out.println("################################################ LdbcUpdate7AddComment");
	            	System.out.println(operation.commentId());
	            	System.out.println(operation.creationDate() + " " + operation.locationIp());
	            	System.out.println(operation.browserUsed());
	            	System.out.println(operation.content());
	            	System.out.println(operation.length() + " " + operation.authorPersonId());
	            	System.out.println(operation.countryId());
	            	System.out.println(operation.replyToPostId() + " " + operation.replyToCommentId());
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdate7AddComment(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.commentId());
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			cs.setString(2, df.format(operation.creationDate()));
        	    cs.setString(3, operation.locationIp());
        	    cs.setString(4, operation.browserUsed());
        	    cs.setString(5, operation.content());
        	    cs.setInt(6, operation.length());
        	    cs.setLong(7, operation.authorPersonId());
        	    cs.setLong(8, operation.countryId());
        	    cs.setLong(9, operation.replyToPostId());
        	    cs.setLong(10, operation.replyToCommentId());
        	    Long tagIds1 [] = new Long[operation.tagIds().size()];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(11, conn.createArrayOf("int", tagIds1));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    } 
    
    public static class LdbcUpdate7AddCommentToVirtuosoSparql implements OperationHandler<LdbcUpdate7AddComment, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate7AddComment operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate7");
        		if (state.isPrintStrings()) {
	            	System.out.println("################################################ LdbcUpdate7AddComment");
	            	System.out.println(operation.commentId());
	            	System.out.println(operation.creationDate() + " " + operation.locationIp());
	            	System.out.println(operation.browserUsed());
	            	System.out.println(operation.content());
	            	System.out.println(operation.length() + " " + operation.authorPersonId());
	            	System.out.println(operation.countryId());
	            	System.out.println(operation.replyToPostId() + " " + operation.replyToCommentId());
	            	System.out.println("[");
	            	for (long tag : operation.tagIds()) {
						System.out.println(tag);
					}
	            	System.out.println("]");
        		}
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String commentUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.commentId()) + ">";
        		String authorUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.authorPersonId()) + ">";
        		String postUri = null;
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[9 + operation.tagIds().size()];
        		triplets[0] = commentUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Comment> .";
        		triplets[1] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/locationIP> \"" + operation.locationIp() + "\" .";
        		triplets[2] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        		triplets[3] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/browserUsed> \"" + operation.browserUsed() + "\" .";
       			triplets[4] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/content> \"" + operation.content() + "\" .";
       			triplets[5] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/size()> \"" + operation.length() + "\" .";
       			triplets[6] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator> " + authorUri + " .";
       			//TODO: countryId() -> long vs <http://dbpedia.org/resource/Sweden>
       			triplets[7] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/isLocatedIn> \"" + operation.countryId() + "\" .";
       			if (operation.replyToPostId() == -1)
       				postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.replyToCommentId()) + ">";
       			else
       				postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.replyToPostId()) + ">";
       			triplets[8] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/replyOf> " + postUri + " .";
        		for (int k = 0; k < operation.tagIds().size(); k++)
        			//TODO: hasTag -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
       				triplets[9 + k] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasTag> \"" + operation.tagIds().get(k) + "\" .";
       			cs.setArray(1, conn.createArrayOf("varchar", triplets));
       			cs.execute();
       			cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    } 
    
    public static class LdbcUpdate8AddFriendshipToVirtuoso implements OperationHandler<LdbcUpdate8AddFriendship, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate8AddFriendship operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate8");
        		if (state.isPrintStrings())
        			System.out.println(operation.person1Id() + " " + operation.person2Id() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate8AddFriendship(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.person1Id());
        		cs.setLong(2, operation.person2Id());
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
    
    public static class LdbcUpdate8AddFriendshipToVirtuosoSparql implements OperationHandler<LdbcUpdate8AddFriendship, VirtuosoDbConnectionState> {
        

        @Override
        public void executeOperation(LdbcUpdate8AddFriendship operation, VirtuosoDbConnectionState state, ResultReporter resultReporter) throws DbException {
        	try {
			Connection conn = state.getConn();
        		if (state.isPrintNames())
        			System.out.println("########### LdbcUpdate8");
        		if (state.isPrintStrings())
        			System.out.println(operation.person1Id() + " " + operation.person2Id() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String person1Uri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.person1Id()) + ">";
        		String person2Uri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.person2Id()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS'+00:00'");
			df1.setTimeZone(TimeZone.getTimeZone("GMT"));
        		String triplets [] = new String[2];
        		triplets[0] = person1Uri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson> " + person2Uri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        		triplets[1] = person1Uri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows> " + person2Uri + " .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
        }
    }
}

