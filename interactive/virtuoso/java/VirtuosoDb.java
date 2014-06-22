package com.ldbc.driver.workloads.ldbc.socnet.interactive.db;


import com.ldbc.driver.*;
import com.ldbc.driver.workloads.ldbc.socnet.interactive.*;
import com.ldbc.driver.workloads.ldbc.socnet.interactive.LdbcUpdate1AddPerson.Organization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class VirtuosoDb extends Db {
    private VirtuosoDbConnectionState virtuosoDbConnectionState;
    public static FileWriter logger;
    
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
			virtuosoDbConnectionState = new VirtuosoDbConnectionState();
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
        registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonToVirtuoso.class);
        registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeToVirtuoso.class);
        registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeToVirtuoso.class);
        registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumToVirtuoso.class);
        registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipToVirtuoso.class);
        registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostToVirtuoso.class);
        registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentToVirtuoso.class);
        registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipToVirtuoso.class);
        
//        registerOperationHandler(LdbcQuery1.class, LdbcQuery1ToVirtuosoSparql.class);
//    	registerOperationHandler(LdbcQuery2.class, LdbcQuery2ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery3.class, LdbcQuery3ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery4.class, LdbcQuery4ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery5.class, LdbcQuery5ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery6.class, LdbcQuery6ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery7.class, LdbcQuery7ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery8.class, LdbcQuery8ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery9.class, LdbcQuery9ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery10.class, LdbcQuery10ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery11.class, LdbcQuery11ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery12.class, LdbcQuery12ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery13.class, LdbcQuery13ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcQuery14.class, LdbcQuery14ToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentToVirtuosoSparql.class);
//        registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipToVirtuosoSparql.class);
    }

    @Override
    protected void onCleanup()  {
    	try {
			virtuosoDbConnectionState.getConn().close();
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
    	private Connection conn;
        private String endPoint;   
        
		VirtuosoDbConnectionState() throws ClassNotFoundException, SQLException {
			super();
			Class.forName("virtuoso.jdbc4.Driver");
			// TODO: This should not be fixed
	        endPoint = "jdbc:virtuoso://localhost:1207/charset=UTF-8";
			conn = DriverManager.getConnection(endPoint, "dba", "dba");;
			
		}

		public Connection getConn() {
			return conn;
		}
		
    }

    public static class LdbcQuery1ToVirtuoso extends OperationHandler<LdbcQuery1> {
        static final List<LdbcQuery1Result> RESULT = new ArrayList<LdbcQuery1Result>();

        @Override
        protected OperationResult executeOperation(LdbcQuery1 operation) throws DbException {
//        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	String endPoint1 = "jdbc:virtuoso://localhost:1207/charset=UTF-8";
			Connection conn1 = null;
			try {
				conn1 = DriverManager.getConnection(endPoint1, "dba", "dba");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query1.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Name@", operation.firstName());
        		System.out.println("@@@@@@@@@@@@@" + String.valueOf(operation.personId()) + " - " + operation.firstName());
        		Statement stmt = conn1.createStatement();
        		System.out.println("########### LdbcQuery1");
//        		System.out.println(queryString);
		   
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long id = result.getLong(1);
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
					Collection<String> universities =  result.getString(12) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(12).split(", ")));
					Collection<String> companies = result.getString(13) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(13).split(", ")));
					LdbcQuery1Result tmp = new LdbcQuery1Result(id, lastName, dist, birthday, creationDate,
							gender, browserUsed, ip, emails, languages, place, universities, companies);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				conn1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(results_count, RESULT);
        }
    }
    
    public static class LdbcQuery1ToVirtuosoSparql extends OperationHandler<LdbcQuery1> {
        static final List<LdbcQuery1Result> RESULT = new ArrayList<LdbcQuery1Result>();

        @Override
        protected OperationResult executeOperation(LdbcQuery1 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query1.txt"));
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
					Collection<String> universities =  result.getString(12) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(12).split(", ")));
					Collection<String> companies = result.getString(13) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(13).split(", ")));
					LdbcQuery1Result tmp = new LdbcQuery1Result(id, lastName, dist, birthday, creationDate, gender, browserUsed, ip, emails, languages, place, universities, companies);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery2 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query2.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		System.out.println("@@@@@@@@@@@@@" + String.valueOf(operation.personId()) + " - " + operation.maxDate());
        		Statement  stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery2");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long id = result.getLong(1);
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long postid = result.getLong(4);;
					String content = result.getString(5);
					long postdate = result.getTimestamp(6).getTime();
					LdbcQuery2Result tmp = new LdbcQuery2Result(id, firstName, lastName, postid, content, postdate);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery2 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query2.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
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
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery3 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query3.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Country1@", operation.countryX());
        		queryString = queryString.replaceAll("@Country2@", operation.countryY());
        		queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
        		queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationMillis()));
        		System.out.println("@@@@@@@@@@@@@" + String.valueOf(operation.personId()) + " - " + operation.countryX() + " - " + operation.countryY() + " - " + operation.startDate() + " - " + String.valueOf(operation.durationMillis()));
        		Statement  stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery3");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long id = result.getLong(1);
					String firstName = result.getString(2);
					String lastName = result.getString(3);
					long ct1 = result.getLong(4);
					long ct2 = result.getLong(5);
					long total = result.getLong(6);
					// TODO: Add results to the class LdbcQuery3Result when Alex modifies this class
					LdbcQuery3Result tmp = new LdbcQuery3Result(id, firstName, lastName, ct1, ct2);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery3 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query3.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
        		queryString = queryString.replaceAll("%Country1%", operation.countryX());
        		queryString = queryString.replaceAll("%Country2%", operation.countryY());
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.startDate()));
        		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationMillis()));
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
					LdbcQuery3Result tmp = new LdbcQuery3Result(id, firstName, lastName, ct1, ct2);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery4 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query4.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.minDate()));
        		queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationMillis()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery4");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery4Result tmp = new LdbcQuery4Result(tagName, tagCount);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery4 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query4.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
        		queryString = queryString.replaceAll("%Date0%", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.minDate()));
        		queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationMillis()));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery4");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery4Result tmp = new LdbcQuery4Result(tagName, tagCount);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery5 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query5.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.minDate()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery5");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String forumTitle = result.getString(1);
					int postCount = result.getInt(2);
					LdbcQuery5Result tmp = new LdbcQuery5Result(forumTitle, postCount);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery5 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query5.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
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
        protected OperationResult executeOperation(LdbcQuery6 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query6.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Tag@", operation.tagName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery6");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery6Result tmp = new LdbcQuery6Result(tagName, tagCount);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery6 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query6.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
        		queryString = queryString.replaceAll("%Tag%", operation.tagName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery6");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String tagName = result.getString(1);
					int tagCount = result.getInt(2);
					LdbcQuery6Result tmp = new LdbcQuery6Result(tagName, tagCount);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery7 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query7.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));	
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery7");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = result.getLong(1);
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					Date likeCreationDate = result.getTimestamp(4);
					boolean isNew = result.getInt(5) == 1 ? true : false;
					long postId = result.getLong(6);
					String postContent = result.getString(7);
					long milliSecondDelay = result.getLong(8);
					LdbcQuery7Result tmp = new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, isNew, postId, postContent, milliSecondDelay);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery7 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query7.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));	
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 178237431944471926l));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery7");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = Long.parseLong(result.getString(1).substring(47));
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					Date likeCreationDate = result.getTimestamp(4);
					boolean isNew = result.getInt(5) == 1 ? true : false;
					long postId = Long.parseLong(result.getString(6).substring(47));
					String postContent = result.getString(7);
					long milliSecondDelay = result.getLong(8);
					LdbcQuery7Result tmp = new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, isNew, postId, postContent, milliSecondDelay);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery8 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query8.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));	
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery8");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = result.getLong(1);
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long replyCreationDate = result.getTimestamp(4).getTime();
					long replyId = result.getLong(5);
					String replyContent = result.getString(6);
					LdbcQuery8Result tmp = new LdbcQuery8Result(personId, personFirstName, personLastName, replyCreationDate, replyId, replyContent);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery8 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query8.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));	
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
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
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery9 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query9.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Date0@", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss S").format(operation.maxDate()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery9");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = result.getLong(1);
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					long postOrCommentId = result.getLong(4);
					String postOrCommentContent = result.getString(5);
					long postOrCommentCreationDate = result.getTimestamp(6).getTime();
					LdbcQuery9Result tmp = new LdbcQuery9Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery9 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query9.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
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
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery10 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query10.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@HS0@", String.valueOf(operation.month1()));
           		queryString = queryString.replaceAll("@HS1@", String.valueOf(operation.month2()));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery10");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					double commonInterestScore = result.getDouble(3);
					long personId = result.getLong(4);
					String gender = result.getString(5);
					String personCityName = result.getString(6);
					LdbcQuery10Result tmp = new LdbcQuery10Result(personFirstName, personLastName, personId, commonInterestScore, gender, personCityName);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery10 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query10.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
        		queryString = queryString.replaceAll("%HS0%", String.valueOf(operation.month1()));
           		queryString = queryString.replaceAll("%HS1%", String.valueOf(operation.month2()));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery10");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					double commonInterestScore = result.getDouble(3);
					long personId = Long.parseLong(result.getString(4).substring(47));
					String gender = result.getString(5);
					String personCityName = result.getString(6);
					LdbcQuery10Result tmp = new LdbcQuery10Result(personFirstName, personLastName, personId, commonInterestScore, gender, personCityName);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery11 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query11.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@Date0@", String.valueOf(operation.workFromYear()));
        		queryString = queryString.replaceAll("@Country@", operation.countryName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery11");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					String personFirstName = result.getString(1);
					String personLastName = result.getString(2);
					int organizationWorkFromYear = result.getInt(3);
					String organizationName = result.getString(4);
					long personId = result.getLong(5);
					LdbcQuery11Result tmp = new LdbcQuery11Result(personId, personFirstName, personLastName, organizationName, organizationWorkFromYear);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery11 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query11.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
        		queryString = queryString.replaceAll("%Date0%", String.valueOf(operation.workFromYear()));
        		queryString = queryString.replaceAll("%Country%", operation.countryName());
//        		queryString = queryString.replaceAll("%Country%", "United_States");
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
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery12 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query12.txt"));
        		queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
        		queryString = queryString.replaceAll("@TagType@", operation.tagClassName());
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery12");
//        		System.out.println(queryString);
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					long personId = result.getLong(1);
					String personFirstName = result.getString(2);
					String personLastName = result.getString(3);
					String tagName = result.getString(4);
					int replyCount = result.getInt(5);
					LdbcQuery12Result tmp = new LdbcQuery12Result(personId, personFirstName, personLastName, null, replyCount);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery12 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query12.txt"));
        		queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//        		queryString = queryString.replaceAll("%Person%", String.format("%020d", 263437488460719218l));
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
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery13 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query13.txt"));
//        		queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.personId1()));
        		queryString = queryString.replaceAll("@Person1@", String.valueOf(263437488460719218l));
//        		queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.personId2()));
        		queryString = queryString.replaceAll("@Person2@", String.valueOf(245644091791353387l));
        		Statement stmt = conn.createStatement();
//        		System.out.println(queryString);
        		System.out.println("########### LdbcQuery13");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					int shortestPathLength = result.getInt(1);
					LdbcQuery13Result tmp = new LdbcQuery13Result(shortestPathLength);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery13 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query13.txt"));
//        		queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.personId1()));
        		queryString = queryString.replaceAll("%Person1%", String.format("%020d", 263437488460719218l));
//        		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.personId2()));
        		queryString = queryString.replaceAll("%Person2%", String.format("%020d", 245644091791353387l));
        		Statement stmt = conn.createStatement();
        		System.out.println("########### LdbcQuery13");
		    
				ResultSet result = stmt.executeQuery(queryString);
				while (result.next()) { results_count++;
					int shortestPathLength = result.getInt(1);
					LdbcQuery13Result tmp = new LdbcQuery13Result(shortestPathLength);
					System.out.println(tmp.toString());
					RESULT.add(tmp);
				}
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
        protected OperationResult executeOperation(LdbcQuery14 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sql/query14.txt"));
        		queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
//        		queryString = queryString.replaceAll("@Person1@", String.valueOf(263437488460719218l));
        		queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
//        		queryString = queryString.replaceAll("@Person2@", String.valueOf(245644091791353387l));
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
//					RESULT.add(new LdbcQuery12Result(personId, personFirstName, personLastName, tagName, replyCount));
				}
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
        protected OperationResult executeOperation(LdbcQuery14 operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	int results_count = 0;
        	try {
        		String queryString = file2string(new File("workloads/ldbc/socnet/interactive/virtuoso_queries/sparql/query14.txt"));
        		queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
//        		queryString = queryString.replaceAll("%Person1%", String.format("%020d", 263437488460719218l));
        		queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
//        		queryString = queryString.replaceAll("%Person2%", String.format("%020d", 245644091791353387l));
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
//					RESULT.add(new LdbcQuery12Result(personId, personFirstName, personLastName, tagName, replyCount));
				}
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
        protected OperationResult executeOperation(LdbcUpdate1AddPerson operation) throws DbException {
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		System.out.println("########### LdbcUpdate1");
//            	System.out.println("################################################ LdbcUpdate1AddPerson");
//            	System.out.println(operation.personId() + " " + operation.personFirstName() + " " + operation.personLastName());
//            	System.out.println(operation.gender());
//            	System.out.println(operation.birthday());
//            	System.out.println(operation.creationDate());
//            	System.out.println(operation.locationIp());
//            	System.out.println(operation.browserUsed());
//            	System.out.println(operation.cityId());
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
        	    cs.setArray(10, conn.createArrayOf("varchar", operation.languages()));
        	    cs.setArray(11, conn.createArrayOf("varchar", operation.emails()));
        	    Long tagIds1 [] = new Long[operation.tagIds().length];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(12, conn.createArrayOf("int", tagIds1));
        	    Long universityIds [] = new Long[operation.studyAt().length];
        	    Integer universityYears [] = new Integer[operation.studyAt().length];
        	    i=0;
        	    for(Organization temp:operation.studyAt()){
        	        universityIds[i] = temp.organizationId();
        	        universityYears[i++] = temp.year();
        	    }
        	    cs.setArray(13, conn.createArrayOf("int", universityIds));
        	    cs.setArray(14, conn.createArrayOf("int", universityYears));
        	    Long companyIds [] = new Long[operation.workAt().length];
        	    Integer companyYears [] = new Integer[operation.workAt().length];
        	    i=0;
        	    for(Organization temp:operation.workAt()){
        	        companyIds[i] = temp.organizationId();
        	        companyYears[i++] = temp.year();
        	    }
        	    cs.setArray(15, conn.createArrayOf("int", companyIds));
        	    cs.setArray(16, conn.createArrayOf("int", companyYears));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate1AddPersonToVirtuosoSparql extends OperationHandler<LdbcUpdate1AddPerson> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate1AddPerson operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate1");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate1AddPerson");
//            	System.out.println(operation.personId() + " " + operation.personFirstName() + " " + operation.personLastName());
//            	System.out.println(operation.gender());
//            	System.out.println(operation.birthday());
//            	System.out.println(operation.creationDate());
//            	System.out.println(operation.locationIp());
//            	System.out.println(operation.browserUsed());
//            	System.out.println(operation.cityId());
//            	System.out.println("[");
//            	for (String lan : operation.languages()) {
//					System.out.println(lan);
//				}
//            	System.out.println("]");
//            	System.out.println("[");
//            	for (String email : operation.emails()) {
//					System.out.println(email);
//				}
//            	System.out.println("]");
//            	System.out.println("[");
//            	for (long tag : operation.tagIds()) {
//					System.out.println(tag);
//				}
//            	System.out.println("]");
//            	System.out.println("[");
//            	for (Organization tag : operation.studyAt()) {
//					System.out.println(tag.organizationId() + " - " + tag.year());
//				}
//            	System.out.println("]");
//            	System.out.println("[");
//            	for (Organization tag : operation.workAt()) {
//					System.out.println(tag.organizationId() + " - " + tag.year());
//				}
//            	System.out.println("]");
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		DateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
        		String triplets [] = new String[9 + operation.languages().length + operation.emails().length + operation.tagIds().length + operation.studyAt().length + operation.workAt().length];
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
        		for (int k = 0; k < operation.languages().length; k++, j++)
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/speaks> \"" + operation.languages()[k] + "\" .";
        		for (int k = 0; k < operation.emails().length; k++, j++)
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/email> \"" + operation.emails()[k] + "\" .";
        		for (int k = 0; k < operation.tagIds().length; k++, j++)
        			//TODO: hasInterest -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasInterest> \"" + operation.tagIds()[k] + "\" .";
           		for (int k = 0; k < operation.studyAt().length; k++, j++)
        			//TODO: studyAt -> organisationId vs <http://dbpedia.org/resource/Rutherford_University>
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/studyAt> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasOrganisation> \"" + operation.studyAt()[k].organizationId() + "\"; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/classYear> \"" + operation.studyAt()[k].year() + "\"] .";
           		for (int k = 0; k < operation.workAt().length; k++, j++)
        			//TODO: workAt -> organisationId vs <http://dbpedia.org/resource/Hong_Kong_Air_International_Ltd>
        			triplets[j] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/workAt> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasOrganisation> \"" + operation.workAt()[k].organizationId() + "\"; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/workFrom> \"" + operation.workAt()[k].year() + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate2AddPostLikeToVirtuoso extends OperationHandler<LdbcUpdate2AddPostLike> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate2AddPostLike operation) throws DbException {
        	System.out.println("########### LdbcUpdate2");
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		String queryString = "{call LdbcUpdate2AddPostLike(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setLong(2, operation.postId());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate2AddPostLikeToVirtuosoSparql extends OperationHandler<LdbcUpdate2AddPostLike> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate2AddPostLike operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate2");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate2AddPostLike");
//            	System.out.println(operation.personId() + " " + operation.postId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		String postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.postId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[1];
        		triplets[0] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/likes> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPost> " + postUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate3AddCommentLikeToVirtuoso extends OperationHandler<LdbcUpdate3AddCommentLike> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate3AddCommentLike operation) throws DbException {
        	System.out.println("########### LdbcUpdate3");
//        	System.out.println("################################################ LdbcUpdate3AddCommentLike");
//        	System.out.println(operation.personId() + " " + operation.commentId());
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		String queryString = "{call LdbcUpdate2AddPostLike(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.personId());
        	    cs.setLong(2, operation.commentId());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate3AddCommentLikeToVirtuosoSparql extends OperationHandler<LdbcUpdate3AddCommentLike> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate3AddCommentLike operation) throws DbException {        	
        	try {
            	System.out.println("########### LdbcUpdate3");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate3AddCommentLike");
//            	System.out.println(operation.personId() + " " + operation.commentId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String personUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		String commentUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.commentId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[1];
        		triplets[0] = personUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/likes> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasComment> " + commentUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);

        }
    }
    
    public static class LdbcUpdate4AddForumToVirtuoso extends OperationHandler<LdbcUpdate4AddForum> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate4AddForum operation) throws DbException {
        	System.out.println("########### LdbcUpdate4");
        	System.out.println("################################################ LdbcUpdate4AddForum");
        	System.out.println(operation.forumId() + " " + operation.forumTitle());
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		String queryString = "LdbcUpdate4AddForum(?, ?, ?, ?, ?)";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.forumId());
        	    cs.setString(2, operation.forumTitle());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.setLong(4, operation.moderatorPersonId());
        	    Long tagIds1 [] = new Long[operation.tagIds().length];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(5, conn.createArrayOf("int", tagIds1));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate4AddForumToVirtuosoSparql extends OperationHandler<LdbcUpdate4AddForum> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate4AddForum operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate4");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate4AddForum");
//            	System.out.println(operation.forumId() + " " + operation.forumTitle());
//            	System.out.println(operation.creationDate() + " " + operation.moderatorPersonId());
//            	System.out.println("[");
//            	for (long tag : operation.tagIds()) {
//					System.out.println(tag);
//				}
//            	System.out.println("]");
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String moderatorUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.moderatorPersonId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[4 + operation.tagIds().length];
        		triplets[0] = forumUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Forum> .";
        		triplets[1] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/title> \"" + operation.forumTitle() + "\" .";
        		triplets[2] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        		triplets[3] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasModerator> " + moderatorUri + " .";
        		for (int k = 0; k < operation.tagIds().length; k++)
        			//TODO: hasTag -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
        			triplets[4 + k] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasTag> \"" + operation.tagIds()[k] + "\" .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate5AddForumMembershipToVirtuoso extends OperationHandler<LdbcUpdate5AddForumMembership> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate5AddForumMembership operation) throws DbException {
        	System.out.println("########### LdbcUpdate5");
//        	System.out.println("################################################ LdbcUpdate5AddForumMembership");
//        	System.out.println(operation.forumId() + " " + operation.personId());
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		String queryString = "{call LdbcUpdate5AddForumMembership(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.forumId());
        		cs.setLong(2, operation.personId());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate5AddForumMembershipToVirtuosoSparql extends OperationHandler<LdbcUpdate5AddForumMembership> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate5AddForumMembership operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate5");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate5AddForumMembership");
//            	System.out.println(operation.forumId() + " " + operation.personId() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String memberUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.personId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[1];
        		triplets[0] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasMember> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson> " + memberUri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/joinDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate6AddPostToVirtuoso extends OperationHandler<LdbcUpdate6AddPost> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate6AddPost operation) throws DbException {
        	System.out.println("########### LdbcUpdate6");
//        	System.out.println("################################################ LdbcUpdate6AddPost");
//        	System.out.println(operation.postId() + " " + operation.browserUsed());
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
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
        	    Long tagIds1 [] = new Long[operation.tagIds().length];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(12, conn.createArrayOf("int", tagIds1));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }  
    
    public static class LdbcUpdate6AddPostToVirtuosoSparql extends OperationHandler<LdbcUpdate6AddPost> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate6AddPost operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate6");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate6AddPost");
//            	System.out.println(operation.postId() + " " + operation.imageFile());
//            	System.out.println(operation.creationDate() + " " + operation.locationIp());
//            	System.out.println(operation.browserUsed() + " " + operation.language());
//            	System.out.println(operation.content());
//            	System.out.println(operation.length() + " " + operation.authorPersonId());
//            	System.out.println(operation.forumId() + " " + operation.countryId());
//            	System.out.println("[");
//            	for (long tag : operation.tagIds()) {
//					System.out.println(tag);
//				}
//            	System.out.println("]");
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.postId()) + ">";
        		String forumUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/forum" + String.format("%020d", operation.forumId()) + ">";
        		String authorUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.authorPersonId()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		if (operation.imageFile().equals("")) {
        			String triplets [] = new String[10 + operation.tagIds().length];
        			triplets[0] = postUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Post> .";
        			triplets[1] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/locationIP> \"" + operation.locationIp() + "\" .";
        			triplets[2] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        			triplets[3] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/browserUsed> \"" + operation.browserUsed() + "\" .";
        			triplets[4] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/language> \"" + operation.language() + "\" .";
        			triplets[5] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/content> \"" + operation.content() + "\" .";
        			triplets[6] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/length> \"" + operation.length() + "\" .";
        			triplets[7] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator> " + authorUri + " .";
        			//TODO: countryId() -> long vs <http://dbpedia.org/resource/Sweden>
        			triplets[8] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/isLocatedIn> \"" + operation.countryId() + "\" .";
        			triplets[9] = forumUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/containerOf> " + postUri + " .";
        			for (int k = 0; k < operation.tagIds().length; k++)
        				//TODO: hasTag -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
        				triplets[10 + k] = postUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasTag> \"" + operation.tagIds()[k] + "\" .";
        			cs.setArray(1, conn.createArrayOf("varchar", triplets));
        			cs.executeQuery();
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
        			cs.executeQuery();
        		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate7AddCommentToVirtuoso extends OperationHandler<LdbcUpdate7AddComment> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate7AddComment operation) throws DbException {
        	System.out.println("########### LdbcUpdate7");
//        	System.out.println("################################################ LdbcUpdate7AddComment");
//        	System.out.println(operation.commentId() + " " + operation.browserUsed() + " " + operation.replyToPostId() + " " + operation.replyToCommentId());
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn(); 
        	try {
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
        	    Long tagIds1 [] = new Long[operation.tagIds().length];
        	    int i=0;
        	    for(long temp:operation.tagIds()){
        	        tagIds1[i++] = temp;
        	    }
        	    cs.setArray(11, conn.createArrayOf("int", tagIds1));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    } 
    
    public static class LdbcUpdate7AddCommentToVirtuosoSparql extends OperationHandler<LdbcUpdate7AddComment> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate7AddComment operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate7");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate7AddComment");
//            	System.out.println(operation.commentId());
//            	System.out.println(operation.creationDate() + " " + operation.locationIp());
//            	System.out.println(operation.browserUsed());
//            	System.out.println(operation.content());
//            	System.out.println(operation.length() + " " + operation.authorPersonId());
//            	System.out.println(operation.countryId());
//            	System.out.println(operation.replyToPostId() + " " + operation.replyToCommentId());
//            	System.out.println("[");
//            	for (long tag : operation.tagIds()) {
//					System.out.println(tag);
//				}
//            	System.out.println("]");
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String commentUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.commentId()) + ">";
        		String authorUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.authorPersonId()) + ">";
        		String postUri = null;
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[9 + operation.tagIds().length];
        		triplets[0] = commentUri + " a <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/Comment> .";
        		triplets[1] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/locationIP> \"" + operation.locationIp() + "\" .";
        		triplets[2] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\" .";
        		triplets[3] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/browserUsed> \"" + operation.browserUsed() + "\" .";
       			triplets[4] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/content> \"" + operation.content() + "\" .";
       			triplets[5] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/length> \"" + operation.length() + "\" .";
       			triplets[6] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasCreator> " + authorUri + " .";
       			//TODO: countryId() -> long vs <http://dbpedia.org/resource/Sweden>
       			triplets[7] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/isLocatedIn> \"" + operation.countryId() + "\" .";
       			if (operation.replyToPostId() == -1)
       				postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/comm" + String.format("%020d", operation.replyToCommentId()) + ">";
       			else
       				postUri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/post" + String.format("%020d", operation.replyToPostId()) + ">";
       			triplets[8] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/replyOf> " + postUri + " .";
        		for (int k = 0; k < operation.tagIds().length; k++)
        			//TODO: hasTag -> tagId vs <http://dbpedia.org/resource/Pablo_Picasso>
       				triplets[9 + k] = commentUri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasTag> \"" + operation.tagIds()[k] + "\" .";
       			cs.setArray(1, conn.createArrayOf("varchar", triplets));
       			cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    } 
    
    public static class LdbcUpdate8AddFriendshipToVirtuoso extends OperationHandler<LdbcUpdate8AddFriendship> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate8AddFriendship operation) throws DbException {
        	System.out.println("########### LdbcUpdate8");
//        	System.out.println("################################################ LdbcUpdate8AddFriendship");
//        	System.out.println(operation.person1Id() + " " + operation.person2Id());
        	Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
        	try {
        		String queryString = "{call LdbcUpdate8AddFriendship(?, ?, ?)}";
        		CallableStatement cs = conn.prepareCall(queryString);
        		cs.setLong(1, operation.person1Id());
        		cs.setLong(2, operation.person2Id());
        		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        	    cs.setString(3, df.format(operation.creationDate()));
        	    cs.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
    
    public static class LdbcUpdate8AddFriendshipToVirtuosoSparql extends OperationHandler<LdbcUpdate8AddFriendship> {
        

        @Override
        protected OperationResult executeOperation(LdbcUpdate8AddFriendship operation) throws DbException {
        	try {
        		System.out.println("########### LdbcUpdate8");
        		Connection conn = ((VirtuosoDbConnectionState)dbConnectionState()).getConn();
//            	System.out.println("################################################ LdbcUpdate8AddFriendship");
//            	System.out.println(operation.person1Id() + " " + operation.person2Id() + " " + operation.creationDate());
        		String queryString = "LdbcUpdateSparql(?)";
        		PreparedStatement cs = conn.prepareStatement(queryString);
        		String person1Uri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.person1Id()) + ">";
        		String person2Uri = "<http://www.ldbc.eu/ldbc_socialnet/1.0/data/pers" + String.format("%020d", operation.person2Id()) + ">";
        		DateFormat df1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        		String triplets [] = new String[2];
        		triplets[0] = person1Uri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows> [ <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/hasPerson> " + person2Uri + "; <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/creationDate> \"" + df1.format(operation.creationDate()) + "\"] .";
        		triplets[1] = person1Uri + " <http://www.ldbc.eu/ldbc_socialnet/1.0/vocabulary/knows> " + person2Uri + " .";
        	    cs.setArray(1, conn.createArrayOf("varchar", triplets));
        	    cs.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return operation.buildResult(0, null);
        }
    }
}