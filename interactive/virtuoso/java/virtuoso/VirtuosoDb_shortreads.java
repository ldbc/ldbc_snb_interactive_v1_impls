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
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

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
    protected void onCleanup()  {
    	try {
			virtuosoDbConnectionState.getDs().close();
			virtuosoDbConnectionState.stats();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
        private Random rand;
        private long [] timeQuery;
        private long timePersonView;
        private long timePostView;
        private long personViewCount;
        private long postViewCount;
        
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
            ds.setMaxPoolSize(Integer.parseInt(properties.get("tc")) + 16);
            ds.fill();
			queryDir = properties.get("queryDir");
			runSql = properties.get("run_sql").equals("true") ? true : false;
			printNames = properties.get("printQueryNames").equals("true") ? true : false;
			printStrings = properties.get("printQueryStrings").equals("true") ? true : false;
			printResults = properties.get("printQueryResults").equals("true") ? true : false;
			rand = new Random();
	        timeQuery = new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	        timePersonView = 0;
	        timePostView = 0;
	        personViewCount = 0;
	        postViewCount = 0;
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

		public Random getRand() {
			return rand;
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

		public void stats() {
			System.out.println("Query01: " + timeQuery[0]);
			System.out.println("Query02: " + timeQuery[1]);
			System.out.println("Query03: " + timeQuery[2]);
			System.out.println("Query07: " + timeQuery[6]);
			System.out.println("Query08: " + timeQuery[7]);
			System.out.println("Query09: " + timeQuery[8]);
			System.out.println("Query10: " + timeQuery[9]);
			System.out.println("Query11: " + timeQuery[10]);
			System.out.println("Query12: " + timeQuery[11]);
			System.out.println("PersonV: " + timePersonView + " / " + personViewCount + " = " + ((double)timePersonView) / personViewCount);
			System.out.println("PostV  : " + timePostView + " / " + postViewCount + " = " + ((double)timePostView) / postViewCount);
		}
		
		public void runViews(List<Long> candidatesForPersonView, List<Long> candidatesForPostView, int query) throws SQLException {
			long startTime = System.currentTimeMillis();
		    int probability = 35;

//		    System.out.println("Starttttt!!!!");
		    int index1 = 0;
		    int index2 = 0;
		    while (true) {
		    	
			    Connection conn = getConn();
			    CallableStatement stmt1 = conn.prepareCall("person_view(?)");
			    CallableStatement stmt2 = conn.prepareCall("post_view(?)");
		    	
		    	int candidatesForPersonViewSize = candidatesForPersonView.size();
		    	int candidatesForPostViewSize = candidatesForPostView.size();
//		    	System.out.println("@@@ " + candidatesForPersonViewSize + " " + candidatesForPostViewSize);
//		    	System.out.println("### " + index1 + " " + index2);		    	
		    	if (index1 >= candidatesForPersonViewSize && index2 >= candidatesForPostViewSize)
		    		break;
		    	
			    for (; index1 < candidatesForPersonViewSize; index1++) {
			    	long startTime1 = System.currentTimeMillis();
		            stmt1.setLong(1, candidatesForPersonView.get(index1));
				    boolean results = stmt1.execute();
				    int setNumber = 1;
				    
				    //Loop through the available result sets.
				    while (results) {
						ResultSet rs = stmt1.getResultSet();
						//Retrieve data from the result set.
						boolean first_row = true;
						boolean second_row = false;
						while (rs.next()) {
						    if (setNumber == 1) {
//								System.out.println("Firstname    : " + rs.getString(1));
//								System.out.println("Lastname     : " + rs.getString(2));
//								System.out.println("Gender       : " + rs.getString(3));
//								System.out.println("Birthday     : " + rs.getDate(4));
//								System.out.println("Creationdate : " + rs.getTimestamp(5));
//								System.out.println("Locationip   : " + rs.getInt(6));
//								System.out.println("BrowserUsed  : " + rs.getString(7));
//								System.out.println("PlaceID      : " + rs.getInt(8));
						    }
						    else if (setNumber == 2) {
						    	if (first_row || second_row)
						    		if (rand.nextInt(100) >= probability) {
						    			candidatesForPostView.add(rs.getLong(1));
								}
//								System.out.println("PostID           : " + rs.getInt(1));
//								System.out.println("PostContent      : " + rs.getString(2));
//								System.out.println("PostImageFile    : " + rs.getString(3));
//								System.out.println("PostCreationdate : " + rs.getTimestamp(4));
//								System.out.println("OrigPostID       : " + rs.getInt(5));
//								System.out.println("OrigPersonID     : " + rs.getInt(6));
//								System.out.println("OrigFirstName    : " + rs.getString(7));
//								System.out.println("OrigLastName     : " + rs.getString(8));
						    }
						    else if (setNumber == 3) {
						    	if (first_row)
						    		if (rand.nextInt(100) >= probability) {
						    			candidatesForPersonView.add(rs.getLong(1));
								}
//								System.out.println("FriendPersonID   : " + rs.getInt(1));
//								System.out.println("FriendFirstName  : " + rs.getString(2));
//								System.out.println("FriendLastName   : " + rs.getString(3));
//								System.out.println("Since            : " + rs.getTimestamp(4));
						    }
						    if (first_row) {
						    	first_row = false;
						    	second_row = true;
						    }
						    else
						    	second_row = false;
						}
						rs.close();
						setNumber++;
						//Check for next result set
						results = stmt1.getMoreResults();
				    }
				    long endTime1 = System.currentTimeMillis();
				    timePersonView += endTime1 - startTime1;
				}
			    
			    for (; index2 < candidatesForPostViewSize; index2++) {
				    long startTime2 = System.currentTimeMillis();
			    	stmt2.setLong(1, candidatesForPostView.get(index2));
			    	
				    boolean results = stmt2.execute();
				    int setNumber = 1;
				    
				    //Loop through the available result sets.
				    while (results) {
						ResultSet rs = stmt2.getResultSet();
						//Retrieve data from the result set.
						boolean first_row = true;
						boolean second_row = false;
						while (rs.next()) {
						    if (setNumber == 1) {
//								System.out.println("Content   : " + rs.getString(1));
//								System.out.println("Imagefile : " + rs.getString(2));
//								System.out.println("Timestamp : " + rs.getTimestamp(3));
						    }
						    else if (setNumber == 2) {
						    	if (first_row)
						    		if (rand.nextInt(100) >= probability) {
						    			candidatesForPersonView.add(rs.getLong(1));
								}
//								System.out.println("CreatorID : " + rs.getInt(1));
//								System.out.println("FirstName : " + rs.getString(2));
//								System.out.println("LastName  : " + rs.getString(3));
						    }
						    else if (setNumber == 3) {
						    	if (first_row)
						    		if (rand.nextInt(100) >= probability) {
						    			candidatesForPersonView.add(rs.getLong(3));
								}
//								System.out.println("ForumID : " + rs.getInt(1));
//								System.out.println("ForumTitle : " + rs.getString(2));
//								System.out.println("ModeratorID : " + rs.getInt(3));
//								System.out.println("ModeratorFirstName : " + rs.getString(4));
//								System.out.println("ModeratorLastName  : " + rs.getString(5));
						    }
						    else if (setNumber == 4) {
						    	if (first_row || second_row)
						    		if (rand.nextInt(100) >= probability) {
						    			candidatesForPostView.add(rs.getLong(1));
								}
						    	if (first_row)
						    		if (rand.nextInt(100) >= probability) {
						    			candidatesForPersonView.add(rs.getLong(3));
								}
//								System.out.println("OrigPostID      : " + rs.getInt(1));
//								System.out.println("OrigPostContent : " + rs.getString(2));
//								System.out.println("OrigAuthorID    : " + rs.getInt(3));
//								System.out.println("OrigAuthorFirst : " + rs.getString(4));
//								System.out.println("OrigAuthorLast  : " + rs.getString(5));
//								System.out.println("FriendOrNot     : " + rs.getInt(6));
						    }
						    if (first_row) {
						    	first_row = false;
						    	second_row = true;
						    }
						    else
						    	second_row = false;
						}
						rs.close();
						setNumber++;
						//Check for next result set
						results = stmt2.getMoreResults();
				    } 
				    long endTime2 = System.currentTimeMillis();
				    timePostView += endTime2 - startTime2;
			    }
			    probability += 5;
			    stmt1.close();
			    stmt2.close();
			    conn.close();
		    }
		    
		    personViewCount += candidatesForPersonView.size();
		    postViewCount += candidatesForPostView.size();
		    long endTime = System.currentTimeMillis();
		    addTimeQuery(endTime - startTime, query);
		}

		private void addTimeQuery(long l, int query) {
			timeQuery[query - 1] += l;
		}
		
    }

    public static class LdbcQuery1ToVirtuoso extends OperationHandler<LdbcQuery1> {
        static final List<LdbcQuery1Result> RESULT = new ArrayList<LdbcQuery1Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery1 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query1.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Name@", operation.firstName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Name%", operation.firstName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery1");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
        		
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long id;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						id = result.getLong(1);
					else
						id = Long.parseLong(result.getString(1).substring(47));
					String lastName = result.getString(2);
					int dist = result.getInt(3);
					long birthday = result.getDate(4).getTime();
					long creationDate = result.getTimestamp(5).getTime();
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
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(id);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 1);				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery1ToVirtuosoSparql extends OperationHandler<LdbcQuery1> {
        static final List<LdbcQuery1Result> RESULT = new ArrayList<LdbcQuery1Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery1 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query1.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Name%", operation.firstName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery1");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long id = Long.parseLong(result.getString(1).substring(47));
					String lastName = result.getString(2);
					int dist = result.getInt(3);
					long birthday = result.getDate(4).getTime();
					long creationDate = result.getTimestamp(5).getTime();
					String gender = result.getString(6);
					String browserUsed = result.getString(7);
					String ip = result.getString(8);
					Collection<String> emails =  result.getString(9) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(9).split(", ")));
					Collection<String> languages =  result.getString(10) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(10).split(", ")));
					String place = result.getString(11);
					List<List<Object>> universities = null;
					if (result.getString(12) != null) {
						List<String> items = new ArrayList<String>(Arrays.asList(result.getString(12).split(", ")));
						universities = new ArrayList<List<Object>>(items.size());
						for (int i = 0; i < items.size(); i++) {
							universities.set(i, new ArrayList<Object>(Arrays.asList(items.get(i).split(" "))));
						}
					}
					else
						universities = new ArrayList<List<Object>>();
					List<List<Object>> companies = null;
					if (result.getString(13) != null) {
						List<String> items = new ArrayList<String>(Arrays.asList(result.getString(13).split(", ")));
						companies = new ArrayList<List<Object>>(items.size());
						for (int i = 0; i < items.size(); i++) {
							companies.set(i, new ArrayList<Object>(Arrays.asList(items.get(i).split(" "))));
						}
					}
					else
						companies = new ArrayList<List<Object>>();
					LdbcQuery1Result tmp = new LdbcQuery1Result(id, lastName, dist, birthday, creationDate, gender, browserUsed, ip, emails, languages, place, universities, companies);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery2ToVirtuoso extends OperationHandler<LdbcQuery2> {
        static final List<LdbcQuery2Result> RESULT = new ArrayList<LdbcQuery2Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery2 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query2.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		}
        		Statement  stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery2");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
        		
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long id;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						id = result.getLong(1);
					else
						id = Long.parseLong(result.getString(1).substring(47));
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long postid;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						postid = result.getLong(4);
					else
						postid = Long.parseLong(result.getString(4).substring(47));
					String content = result.getString(5);
					long postdate = result.getTimestamp(6).getTime();
					LdbcQuery2Result tmp = new LdbcQuery2Result(id, firstName, lastName, postid, content, postdate);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(id);
					if (results_count == 1 || results_count == 2)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPostView.add(postid);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery2ToVirtuosoSparql extends OperationHandler<LdbcQuery2> {
        static final List<LdbcQuery2Result> RESULT = new ArrayList<LdbcQuery2Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery2 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query2.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery2");
        		
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long id = Long.parseLong(result.getString(1).substring(47));
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long postid = Long.parseLong(result.getString(4).substring(47));
					String content = result.getString(5);
					long postdate = result.getTimestamp(6).getTime();
					LdbcQuery2Result tmp = new LdbcQuery2Result(id, firstName, lastName, postid, content, postdate);
					//System.out.println(tmp.toString());
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
            return operation.buildResult(results_count, RESULT);
        }
    }    
    
    public static class LdbcQuery3ToVirtuoso extends OperationHandler<LdbcQuery3> {
        static final List<LdbcQuery3Result> RESULT = new ArrayList<LdbcQuery3Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery3 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query3.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Country1@", operation.countryXName());
        			queryString = queryString.replaceAll("@Country2@", operation.countryYName());
        			queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
        			queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationDays()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Country1%", operation.countryXName());
            		queryString = queryString.replaceAll("%Country2%", operation.countryYName());
            		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
            		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
        		}
        		Statement  stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery3");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long id;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						id = result.getLong(1);
					else
						id = Long.parseLong(result.getString(1).substring(47));
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long ct1 = result.getLong(4);
					long ct2 = result.getLong(5);
					long total = result.getLong(6);
					LdbcQuery3Result tmp = new LdbcQuery3Result(id, firstName, lastName, ct1, ct2, total);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(id);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 3);
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery3ToVirtuosoSparql extends OperationHandler<LdbcQuery3> {
        static final List<LdbcQuery3Result> RESULT = new ArrayList<LdbcQuery3Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery3 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query3.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Country1%", operation.countryXName());
        		queryString = queryString.replaceAll("%Country2%", operation.countryYName());
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
        		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery3");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					
					long id = Long.parseLong(result.getString(1).substring(47));
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long ct1 = result.getLong(4);
					long ct2 = result.getLong(5);
					long total = result.getLong(6);
					// TODO: Add results to the class LdbcQuery3Result when Alex modifies this class
					LdbcQuery3Result tmp = new LdbcQuery3Result(id, firstName, lastName, ct1, ct2, total);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery4ToVirtuoso extends OperationHandler<LdbcQuery4> {
        static final List<LdbcQuery4Result> RESULT = new ArrayList<LdbcQuery4Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery4 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query4.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
        			queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationDays()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
            		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery4");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery4Result tmp = new LdbcQuery4Result(tagName, tagCount);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
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
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery4ToVirtuosoSparql extends OperationHandler<LdbcQuery4> {
        static final List<LdbcQuery4Result> RESULT = new ArrayList<LdbcQuery4Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery4 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query4.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
        		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery4");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery4Result tmp = new LdbcQuery4Result(tagName, tagCount);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery5ToVirtuoso extends OperationHandler<LdbcQuery5> {
        static final List<LdbcQuery5Result> RESULT = new ArrayList<LdbcQuery5Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery5 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query5.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.minDate()));
        		}
        		else {
        			queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.minDate()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery5");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String forumTitle = result.getString(1);
					int postCount = result.getInt(2);
					LdbcQuery5Result tmp = new LdbcQuery5Result(forumTitle, postCount);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
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
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery5ToVirtuosoSparql extends OperationHandler<LdbcQuery5> {
        static final List<LdbcQuery5Result> RESULT = new ArrayList<LdbcQuery5Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery5 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query5.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.minDate()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery5");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					
					String forumTitle = result.getString(1);
					int postCount = result.getInt(2);
					LdbcQuery5Result tmp = new LdbcQuery5Result(forumTitle, postCount);
					System.out.println(tmp);
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery6ToVirtuoso extends OperationHandler<LdbcQuery6> {
        static final List<LdbcQuery6Result> RESULT = new ArrayList<LdbcQuery6Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery6 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query6.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Tag@", operation.tagName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Tag%", operation.tagName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery6");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery6Result tmp = new LdbcQuery6Result(tagName, tagCount);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery6ToVirtuosoSparql extends OperationHandler<LdbcQuery6> {
        static final List<LdbcQuery6Result> RESULT = new ArrayList<LdbcQuery6Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery6 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query6.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Tag%", operation.tagName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery6");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery6Result tmp = new LdbcQuery6Result(tagName, tagCount);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery7ToVirtuoso extends OperationHandler<LdbcQuery7> {
        static final List<LdbcQuery7Result> RESULT = new ArrayList<LdbcQuery7Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery7 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query7.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery7");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long likeCreationDate = result.getTimestamp(4).getTime();
					boolean isNew = result.getInt(5) == 1 ? true : false;
					long postId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						postId = result.getLong(6);
					else
						postId = Long.parseLong(result.getString(6).substring(47));
					String postContent = result.getString(7);
					int milliSecondDelay = result.getInt(8);
					LdbcQuery7Result tmp = new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, postId, postContent, milliSecondDelay, isNew);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(personId);
					if (results_count == 1 || results_count == 2)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPostView.add(postId);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 7);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery7ToVirtuosoSparql extends OperationHandler<LdbcQuery7> {
        static final List<LdbcQuery7Result> RESULT = new ArrayList<LdbcQuery7Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery7 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query7.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));	
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery7");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long likeCreationDate = result.getTimestamp(4).getTime();
					boolean isNew = result.getInt(5) == 1 ? true : false;
					long postId = Long.parseLong(result.getString(6).substring(47));
					String postContent = result.getString(7);
					int milliSecondDelay = result.getInt(8);
					LdbcQuery7Result tmp = new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, postId, postContent, milliSecondDelay, isNew);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery8ToVirtuoso extends OperationHandler<LdbcQuery8> {
        static final List<LdbcQuery8Result> RESULT = new ArrayList<LdbcQuery8Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery8 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query8.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));        			
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery8");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long replyCreationDate = result.getTimestamp(4).getTime();
					long replyId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						replyId = result.getLong(5);
					else
						replyId = Long.parseLong(result.getString(5).substring(47));
					String replyContent = result.getString(6);
					LdbcQuery8Result tmp = new LdbcQuery8Result(personId, personFirstName, personLastName, replyCreationDate, replyId, replyContent);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(personId);
					if (results_count == 1 || results_count == 2)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPostView.add(replyId);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 8);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery8ToVirtuosoSparql extends OperationHandler<LdbcQuery8> {
        static final List<LdbcQuery8Result> RESULT = new ArrayList<LdbcQuery8Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery8 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query8.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));	
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery8");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long replyCreationDate = result.getTimestamp(4).getTime();
					long replyId = Long.parseLong(result.getString(5).substring(47));
					String replyContent = result.getString(6);
					LdbcQuery8Result tmp = new LdbcQuery8Result(personId, personFirstName, personLastName, replyCreationDate, replyId, replyContent);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery9ToVirtuoso extends OperationHandler<LdbcQuery9> {
        static final List<LdbcQuery9Result> RESULT = new ArrayList<LdbcQuery9Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery9 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query9.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery9");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long postOrCommentId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						postOrCommentId = result.getLong(4);
					else
						postOrCommentId = Long.parseLong(result.getString(4).substring(47));
					String postOrCommentContent = result.getString(5);
					long postOrCommentCreationDate = result.getTimestamp(6).getTime();
					LdbcQuery9Result tmp = new LdbcQuery9Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(personId);
					if (results_count == 1 || results_count == 2)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPostView.add(postOrCommentId);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 9);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery9ToVirtuosoSparql extends OperationHandler<LdbcQuery9> {
        static final List<LdbcQuery9Result> RESULT = new ArrayList<LdbcQuery9Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery9 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query9.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery9");
        		
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;					
					long personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long postOrCommentId = Long.parseLong(result.getString(4).substring(47));
					String postOrCommentContent = result.getString(5);
					long postOrCommentCreationDate = result.getTimestamp(6).getTime();
					LdbcQuery9Result tmp = new LdbcQuery9Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }    
    
    public static class LdbcQuery10ToVirtuoso extends OperationHandler<LdbcQuery10> {
        static final List<LdbcQuery10Result> RESULT = new ArrayList<LdbcQuery10Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery10 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query10.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@HS0@", String.valueOf(operation.month()));
        			queryString = queryString.replaceAll("@HS1@", String.valueOf((operation.month()+1)%12));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%HS0%", String.valueOf(operation.month()));
               		queryString = queryString.replaceAll("%HS1%", String.valueOf((operation.month()+1)%12));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery10");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int commonInterestScore = result.getInt(3);
					long personId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						personId = result.getLong(4);
					else
						personId = Long.parseLong(result.getString(4).substring(47));
					String gender = result.getString(5);
					String personCityName = result.getString(6);
					LdbcQuery10Result tmp = new LdbcQuery10Result(personId, personFirstName, personLastName, commonInterestScore, gender, personCityName);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(personId);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 10);
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery10ToVirtuosoSparql extends OperationHandler<LdbcQuery10> {
        static final List<LdbcQuery10Result> RESULT = new ArrayList<LdbcQuery10Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery10 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query10.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%HS0%", String.valueOf(operation.month()));
           		queryString = queryString.replaceAll("%HS1%", String.valueOf((operation.month()+1)%12));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery10");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int commonInterestScore = result.getInt(3);
					long personId = Long.parseLong(result.getString(4).substring(47));
					String gender = result.getString(5);
					String personCityName = result.getString(6);
					LdbcQuery10Result tmp = new LdbcQuery10Result(personId, personFirstName, personLastName, commonInterestScore, gender, personCityName);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery11ToVirtuoso extends OperationHandler<LdbcQuery11> {
        static final List<LdbcQuery11Result> RESULT = new ArrayList<LdbcQuery11Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery11 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query11.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
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
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery11");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int organizationWorkFromYear = result.getInt(3);
					String organizationName = result.getString(4);
					long personId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						personId = result.getLong(5);
					else
						personId = Long.parseLong(result.getString(5).substring(47));;
					LdbcQuery11Result tmp = new LdbcQuery11Result(personId, personFirstName, personLastName, organizationName, organizationWorkFromYear);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(personId);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 11);
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }

    public static class LdbcQuery11ToVirtuosoSparql extends OperationHandler<LdbcQuery11> {
        static final List<LdbcQuery11Result> RESULT = new ArrayList<LdbcQuery11Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery11 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query11.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%Date0%", String.valueOf(operation.workFromYear()));
        		queryString = queryString.replaceAll("%Country%", operation.countryName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery11");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int organizationWorkFromYear = result.getInt(3);
					String organizationName = result.getString(4);
					long personId = Long.parseLong(result.getString(5).substring(47));;
					LdbcQuery11Result tmp = new LdbcQuery11Result(personId, personFirstName, personLastName, organizationName, organizationWorkFromYear);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery12ToVirtuoso extends OperationHandler<LdbcQuery12> {
    	static final List<LdbcQuery12Result> RESULT = new ArrayList<LdbcQuery12Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery12 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query12.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        			queryString = queryString.replaceAll("@TagType@", operation.tagClassName());
        		}
        		else {
            		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
            		queryString = queryString.replaceAll("%TagType%", operation.tagClassName());
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery12");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
        		List<Long> candidatesForPersonView = new ArrayList<Long>();
        		List<Long> candidatesForPostView = new ArrayList<Long>();
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					long personId;
					if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql())
						personId = result.getLong(1);
					else
						personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					String tagName = result.getString(4);
					int replyCount = result.getInt(5);
					LdbcQuery12Result tmp = new LdbcQuery12Result(personId, personFirstName, personLastName, null, replyCount);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
						System.out.println(tmp.toString());
					RESULT.add(tmp);
					if (results_count == 1)
						if (((VirtuosoDbConnectionState)dbConnectionState()).getRand().nextInt(100) >= 30)
							candidatesForPersonView.add(personId);
				}
				stmt.close();
				conn.close();
				((VirtuosoDbConnectionState)dbConnectionState()).runViews(candidatesForPersonView, candidatesForPostView, 12);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery12ToVirtuosoSparql extends OperationHandler<LdbcQuery12> {
    	static final List<LdbcQuery12Result> RESULT = new ArrayList<LdbcQuery12Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery12 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query12.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
        		queryString = queryString.replaceAll("%TagType%", operation.tagClassName());
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery12");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = Long.parseLong(result.getString(1).substring(47));;;
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					String tagName = result.getString(4);
					int replyCount = result.getInt(5);
					LdbcQuery12Result tmp = new LdbcQuery12Result(personId, personFirstName, personLastName, null, replyCount);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery13ToVirtuoso extends OperationHandler<LdbcQuery13> {
        static final List<LdbcQuery13Result> RESULT = new ArrayList<LdbcQuery13Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery13 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query13.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
        			queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
        		}
        		else {
        			queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
            		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery13");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					int shortestPathlength = result.getInt(1);
					LdbcQuery13Result tmp = new LdbcQuery13Result(shortestPathlength);
					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery13ToVirtuosoSparql extends OperationHandler<LdbcQuery13> {
        static final List<LdbcQuery13Result> RESULT = new ArrayList<LdbcQuery13Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery13 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query13.txt"));
        		queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
        		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery13");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					int shortestPathlenght = result.getInt(1);
					LdbcQuery13Result tmp = new LdbcQuery13Result(shortestPathlenght);
					//System.out.println(tmp.toString());
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
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery14ToVirtuoso extends OperationHandler<LdbcQuery14> {
        static final List<LdbcQuery14Result> RESULT = new ArrayList<LdbcQuery14Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery14 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File(((VirtuosoDbConnectionState)dbConnectionState()).getQueryDir(), "query14.txt"));
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isRunSql()) {
        			queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
        			queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
        		}
        		else {
            		queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
            		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
        		}
        		Statement stmt = conn.createStatement();
        		
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcQuery14");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(queryString);
        		
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) {
					results_count++;
					String path = result.getString(1);
					double weight = result.getDouble(2);
//					if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintResults())
//					System.out.println(path);
//					System.out.println(weight);
					// TODO: This should be fixed
//					RESULT.add(new LdbcQuery14Result(personId, personFirstName, personLastName, tagName, replyCount));
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery14ToVirtuosoSparql extends OperationHandler<LdbcQuery14> {
        static final List<LdbcQuery14Result> RESULT = new ArrayList<LdbcQuery14Result>();

        @Override
        protected OperationResultReport executeOperation(LdbcQuery14 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("/2d1/ldbc/ldbc_snb_interactive_vendors/interactive/virtuoso/queries/sparql/query14.txt"));
        		queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
        		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery14");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String path = result.getString(1);
					double weight = result.getDouble(2);
//					System.out.println(path);
//					System.out.println(weight);
					// TODO: This should be fixed
//					RESULT.add(new LdbcQuery14Result(personId, personFirstName, personLastName, tagName, replyCount));
				}
				stmt.close();conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcUpdate1AddPersonToVirtuoso extends OperationHandler<LdbcUpdate1AddPerson> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate1AddPerson operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate1");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate1AddPersonToVirtuosoSparql extends OperationHandler<LdbcUpdate1AddPerson> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate1AddPerson operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate1");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		DateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate2AddPostLikeToVirtuoso extends OperationHandler<LdbcUpdate2AddPostLike> {
        
        @Override
        protected OperationResultReport executeOperation(LdbcUpdate2AddPostLike operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
                	System.out.println("########### LdbcUpdate2");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.postId() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate2AddPostLike(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setLong(2, operation.postId());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate2AddPostLikeToVirtuosoSparql extends OperationHandler<LdbcUpdate2AddPostLike> {
        @Override
        protected OperationResultReport executeOperation(LdbcUpdate2AddPostLike operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
                	System.out.println("########### LdbcUpdate2");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.postId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		String postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.postId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[1];
        		triplets[0] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/likes> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPost> " + postUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate3AddCommentLikeToVirtuoso extends OperationHandler<LdbcUpdate3AddCommentLike> {
        @Override
        protected OperationResultReport executeOperation(LdbcUpdate3AddCommentLike operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate3");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.commentId() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate2AddPostLike(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setLong(2, operation.commentId());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate3AddCommentLikeToVirtuosoSparql extends OperationHandler<LdbcUpdate3AddCommentLike> {
        @Override
        protected OperationResultReport executeOperation(LdbcUpdate3AddCommentLike operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate3");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.personId() + " " + operation.commentId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		String commentUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.commentId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[1];
        		triplets[0] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/likes> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasComment> " + commentUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);

        }
    }
    
    public static class LdbcUpdate4AddForumToVirtuoso extends OperationHandler<LdbcUpdate4AddForum> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate4AddForum operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
           		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate4");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate4AddForumToVirtuosoSparql extends OperationHandler<LdbcUpdate4AddForum> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate4AddForum operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate4");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate5AddForumMembershipToVirtuoso extends OperationHandler<LdbcUpdate5AddForumMembership> {
        @Override
        protected OperationResultReport executeOperation(LdbcUpdate5AddForumMembership operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate5");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.forumId() + " " + operation.personId() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate5AddForumMembership(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.forumId());
        		cs.setLong(2, operation.personId());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate5AddForumMembershipToVirtuosoSparql extends OperationHandler<LdbcUpdate5AddForumMembership> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate5AddForumMembership operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate5");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.forumId() + " " + operation.personId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String memberUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[1];
        		triplets[0] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasMember> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson> " + memberUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/joinDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate6AddPostToVirtuoso extends OperationHandler<LdbcUpdate6AddPost> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate6AddPost operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate6");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }  
    
    public static class LdbcUpdate6AddPostToVirtuosoSparql extends OperationHandler<LdbcUpdate6AddPost> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate6AddPost operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate6");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate7AddCommentToVirtuoso extends OperationHandler<LdbcUpdate7AddComment> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate7AddComment operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn(); 
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate7");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    } 
    
    public static class LdbcUpdate7AddCommentToVirtuosoSparql extends OperationHandler<LdbcUpdate7AddComment> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate7AddComment operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate7");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings()) {
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
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    } 
    
    public static class LdbcUpdate8AddFriendshipToVirtuoso extends OperationHandler<LdbcUpdate8AddFriendship> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate8AddFriendship operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate8");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.person1Id() + " " + operation.person2Id() + " " + operation.creationDate());
        		String queryString = "{call LdbcUpdate8AddFriendship(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.person1Id());
        		cs.setLong(2, operation.person2Id());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate8AddFriendshipToVirtuosoSparql extends OperationHandler<LdbcUpdate8AddFriendship> {
        

        @Override
        protected OperationResultReport executeOperation(LdbcUpdate8AddFriendship operation) throws DbException {
    		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintNames())
        			System.out.println("########### LdbcUpdate8");
        		if (((VirtuosoDbConnectionState)dbConnectionState()).isPrintStrings())
        			System.out.println(operation.person1Id() + " " + operation.person2Id() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String person1Uri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.person1Id()) + ">";
        		String person2Uri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.person2Id()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[2];
        		triplets[0] = person1Uri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson> " + person2Uri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        		triplets[1] = person1Uri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows> " + person2Uri + " .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.execute();
        	    cs.close();
        	    conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
}