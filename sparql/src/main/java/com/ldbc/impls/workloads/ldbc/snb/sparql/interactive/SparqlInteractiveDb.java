package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;


import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3Result;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDriverConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlPoolingDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlBiQueryStore;
import org.openrdf.query.BindingSet;

import java.util.Map;

public class SparqlInteractiveDb extends SparqlDb {

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		dbs = new SparqlPoolingDbConnectionState(properties, new SparqlBiQueryStore(properties.get("queryDir")));

//		registerOperationHandler(LdbcQuery1.class, Query1.class);
//		registerOperationHandler(LdbcQuery2.class, Query2.class);
		registerOperationHandler(LdbcQuery3.class, Query3.class);
//		registerOperationHandler(LdbcQuery4.class, Query4.class);
//		registerOperationHandler(LdbcQuery5.class, Query5.class);
//		registerOperationHandler(LdbcQuery6.class, Query6.class);
//		registerOperationHandler(LdbcQuery7.class, Query7.class);
//		registerOperationHandler(LdbcQuery8.class, Query8.class);
//		registerOperationHandler(LdbcQuery9.class, Query9.class);
//		registerOperationHandler(LdbcQuery10.class, Query10.class);
//		registerOperationHandler(LdbcQuery11.class, Query11.class);
//		registerOperationHandler(LdbcQuery12.class, Query12.class);
//		registerOperationHandler(LdbcQuery13.class, Query13.class);
//		registerOperationHandler(LdbcQuery14.class, Query14.class);

//		registerOperationHandler(LdbcShortQuery1PersonProfile.class, LdbcShortQuery1.class);
//		registerOperationHandler(LdbcShortQuery2PersonPosts.class, LdbcShortQuery2.class);
//		registerOperationHandler(LdbcShortQuery3PersonFriends.class, LdbcShortQuery3.class);
//		registerOperationHandler(LdbcShortQuery4MessageContent.class, LdbcShortQuery4.class);
//		registerOperationHandler(LdbcShortQuery5MessageCreator.class, LdbcShortQuery5.class);
//		registerOperationHandler(LdbcShortQuery6MessageForum.class, LdbcShortQuery6.class);
//		registerOperationHandler(LdbcShortQuery7MessageReplies.class, LdbcShortQuery7.class);

//		registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcUpdate1AddPersonSparql.class);
//		registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcUpdate2AddPostLikeSparql.class);
//		registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcUpdate3AddCommentLikeSparql.class);
//		registerOperationHandler(LdbcUpdate4AddForum.class, LdbcUpdate4AddForumSparql.class);
//		registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcUpdate5AddForumMembershipSparql.class);
//		registerOperationHandler(LdbcUpdate6AddPost.class, LdbcUpdate6AddPostSparql.class);
//		registerOperationHandler(LdbcUpdate7AddComment.class, LdbcUpdate7AddCommentSparql.class);
//		registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcUpdate8AddFriendshipSparql.class);
	}

	public static class Query3 extends SparqlListOperationHandler<LdbcQuery3, LdbcQuery3Result, SparqlInteractiveQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionState<SparqlInteractiveQueryStore> state, LdbcQuery3 operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcQuery3Result convertSingleResult(BindingSet bs) {
			long personId          = convertLong   (bs, "personId"       );
			String personFirstName = convertString (bs, "personFirstName");
			String personLastName  = convertString (bs, "personLastName" );
			int countX             = convertInteger(bs, "countX"         );
			int countY             = convertInteger(bs, "countY"         );
			int count              = convertInteger(bs, "count"          );
			return new LdbcQuery3Result(personId, personFirstName, personLastName, countX, countY, count);
		}

	}


//	public static class Query1 implements OperationHandler<LdbcQuery1, SparqlDriverConnectionState> {
//		public void executeOperation(LdbcQuery1 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery1Result> RESULT = new ArrayList<LdbcQuery1Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query1.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Name@", operation.firstName());
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Name%", operation.firstName());
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery1");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long id;
//					if (state.isRunSql())
//						id = result.getLong(1);
//					else
//						id = Long.parseLong(result.getString(1).substring(47));
//					String lastName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					int dist = result.getInt(3);
//					long birthday = result.getLong(4);
//					long creationDate = result.getLong(5);
//					String gender = result.getString(6);
//					String browserUsed = result.getString(7);
//					String ip = result.getString(8);
//					Collection<String> emails =  result.getString(9) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(9).split(", ")));
//					Collection<String> languages =  result.getString(10) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(result.getString(10).split(", ")));
//					String place = new String(result.getString(11).getBytes("ISO-8859-1"));
//					ArrayList<List<Object>> universities = null;
//					if (result.getString(12) != null) {
//					        String ss = new String(result.getString(12).getBytes("ISO-8859-1"));
//						List<String> items = new ArrayList<String>(Arrays.asList(ss.split(", ")));
//						universities = new ArrayList<List<Object>>();
//						for (int i = 0; i < items.size(); i++) {
//							universities.add(new ArrayList<Object>(Arrays.asList(items.get(i).split(" "))));
//						}
//					}
//					else
//						universities = new ArrayList<List<Object>>();
//					ArrayList<List<Object>> companies = null;
//					if (result.getString(13) != null) {
//					        String ss = new String(result.getString(13).getBytes("ISO-8859-1"));
//						List<String> items = new ArrayList<String>(Arrays.asList(ss.split(", ")));
//						companies = new ArrayList<List<Object>>();
//						for (int i = 0; i < items.size(); i++) {
//							companies.add(new ArrayList<Object>(Arrays.asList(items.get(i).split(" "))));
//						}
//					}
//					else
//						companies = new ArrayList<List<Object>>();
//					LdbcQuery1Result tmp = new LdbcQuery1Result(id, lastName, dist, birthday, creationDate,
//							gender, browserUsed, ip, emails, languages, place, universities, companies);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//	public static class Query2 implements OperationHandler<LdbcQuery2, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery2 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery2Result> RESULT = new ArrayList<LdbcQuery2Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query2.txt"));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
//				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Date0@", sdf.format(operation.maxDate()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Date0%", sdf.format(operation.maxDate()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery2");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long id;
//					if (state.isRunSql())
//						id = result.getLong(1);
//					else
//						id = Long.parseLong(result.getString(1).substring(47));
//					String firstName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					String lastName = new String(result.getString(3).getBytes("ISO-8859-1"));
//					long postid;
//					if (state.isRunSql())
//						postid = result.getLong(4);
//					else
//						postid = Long.parseLong(result.getString(4).substring(47));
//					String content = new String(result.getString(5).getBytes("ISO-8859-1"));
//					long postdate = result.getLong(6);
//					LdbcQuery2Result tmp = new LdbcQuery2Result(id, firstName, lastName, postid, content, postdate);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//	public static class Query3 implements OperationHandler<LdbcQuery3, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery3 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery3Result> RESULT = new ArrayList<LdbcQuery3Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query3.txt"));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
//				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Country1@", operation.countryXName());
//					queryString = queryString.replaceAll("@Country2@", operation.countryYName());
//					queryString = queryString.replaceAll("@Date0@", sdf.format(operation.startDate()));
//					queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationDays()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Country1%", operation.countryXName());
//					queryString = queryString.replaceAll("%Country2%", operation.countryYName());
//					queryString = queryString.replaceAll("%Date0%", sdf.format(operation.startDate()));
//					queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery3");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long id;
//					if (state.isRunSql())
//						id = result.getLong(1);
//					else
//						id = Long.parseLong(result.getString(1).substring(47));
//					String firstName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					String lastName = new String(result.getString(3).getBytes("ISO-8859-1"));
//					long ct1 = result.getLong(4);
//					long ct2 = result.getLong(5);
//					long total = result.getLong(6);
//					LdbcQuery3Result tmp = new LdbcQuery3Result(id, firstName, lastName, ct1, ct2, total);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query4 implements OperationHandler<LdbcQuery4, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery4 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery4Result> RESULT = new ArrayList<LdbcQuery4Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query4.txt"));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
//				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Date0@", sdf.format(operation.startDate()));
//					queryString = queryString.replaceAll("@Duration@", String.valueOf(operation.durationDays()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Date0%", sdf.format(operation.startDate()));
//					queryString = queryString.replaceAll("%Duration%", String.valueOf(operation.durationDays()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery4");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					String tagName = new String(result.getString(1).getBytes("ISO-8859-1"));
//					int tagCount = result.getInt(2);
//					LdbcQuery4Result tmp = new LdbcQuery4Result(tagName, tagCount);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//	public static class Query5 implements OperationHandler<LdbcQuery5, SparqlDriverConnectionState> {
//
//
//		public void executeOperation(LdbcQuery5 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery5Result> RESULT = new ArrayList<LdbcQuery5Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query5.txt"));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
//				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Date0@", sdf.format(operation.minDate()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Date0%", sdf.format(operation.minDate()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery5");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					String forumTitle = new String(result.getString(1).getBytes("ISO-8859-1"));
//					int postCount = result.getInt(3);
//					LdbcQuery5Result tmp = new LdbcQuery5Result(forumTitle, postCount);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query6 implements OperationHandler<LdbcQuery6, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery6 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery6Result> RESULT = new ArrayList<LdbcQuery6Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query6.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Tag@", operation.tagName());
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Tag%", operation.tagName());
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery6");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					String tagName = new String(result.getString(1).getBytes("ISO-8859-1"));
//					int tagCount = result.getInt(2);
//					LdbcQuery6Result tmp = new LdbcQuery6Result(tagName, tagCount);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query7 implements OperationHandler<LdbcQuery7, SparqlDriverConnectionState> {
//
//
//		public void executeOperation(LdbcQuery7 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery7Result> RESULT = new ArrayList<LdbcQuery7Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query7.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery7");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long personId;
//					if (state.isRunSql())
//						personId = result.getLong(1);
//					else
//						personId = Long.parseLong(result.getString(1).substring(47));
//					String personFirstName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					String personLastName = new String(result.getString(3).getBytes("ISO-8859-1"));
//					long likeCreationDate = result.getLong(4);
//					boolean isNew = result.getInt(5) == 1 ? true : false;
//					long postId;
//					if (state.isRunSql())
//						postId = result.getLong(6);
//					else
//						postId = Long.parseLong(result.getString(6).substring(47));
//					String postContent = new String(result.getString(7).getBytes("ISO-8859-1"));;
//					int milliSecondDelay = result.getInt(8);
//					LdbcQuery7Result tmp = new LdbcQuery7Result(personId, personFirstName, personLastName, likeCreationDate, postId, postContent, milliSecondDelay, isNew);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query8 implements OperationHandler<LdbcQuery8, SparqlDriverConnectionState> {
//
//
//		public void executeOperation(LdbcQuery8 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery8Result> RESULT = new ArrayList<LdbcQuery8Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query8.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery8");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long personId;
//					if (state.isRunSql())
//						personId = result.getLong(1);
//					else
//						personId = Long.parseLong(result.getString(1).substring(47));
//					String personFirstName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					String personLastName = new String(result.getString(3).getBytes("ISO-8859-1"));
//					long replyCreationDate = result.getLong(4);
//					long replyId;
//					if (state.isRunSql())
//						replyId = result.getLong(5);
//					else
//						replyId = Long.parseLong(result.getString(5).substring(47));
//					String replyContent = new String(result.getString(6).getBytes("ISO-8859-1"));
//					LdbcQuery8Result tmp = new LdbcQuery8Result(personId, personFirstName, personLastName, replyCreationDate, replyId, replyContent);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//	public static class Query9 implements OperationHandler<LdbcQuery9, SparqlDriverConnectionState> {
//
//
//		public void executeOperation(LdbcQuery9 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery9Result> RESULT = new ArrayList<LdbcQuery9Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query9.txt"));
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
//				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Date0@", sdf.format(operation.maxDate()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Date0%", sdf.format(operation.maxDate()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery9");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long personId;
//					if (state.isRunSql())
//						personId = result.getLong(1);
//					else
//						personId = Long.parseLong(result.getString(1).substring(47));
//					String personFirstName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					String personLastName = new String(result.getString(3).getBytes("ISO-8859-1"));
//					long postOrCommentId;
//					if (state.isRunSql())
//						postOrCommentId = result.getLong(4);
//					else
//						postOrCommentId = Long.parseLong(result.getString(4).substring(47));
//					String postOrCommentContent = new String(result.getString(5).getBytes("ISO-8859-1"));
//					long postOrCommentCreationDate = result.getLong(6);
//					LdbcQuery9Result tmp = new LdbcQuery9Result(personId, personFirstName, personLastName, postOrCommentId, postOrCommentContent, postOrCommentCreationDate);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query10 implements OperationHandler<LdbcQuery10, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery10 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery10Result> RESULT = new ArrayList<LdbcQuery10Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query10.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@HS0@", String.valueOf(operation.month()));
//					int nextMonth = operation.month() + 1;
//					if (nextMonth == 13)
//					    nextMonth = 1;
//					queryString = queryString.replaceAll("@HS1@", String.valueOf(nextMonth));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%HS0%", String.valueOf(operation.month()));
//					int nextMonth = operation.month() + 1;
//					if (nextMonth == 13)
//					    nextMonth = 1;
//					queryString = queryString.replaceAll("%HS1%", String.valueOf(nextMonth));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery10");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) { results_count++;
//				String personFirstName = new String(result.getString(1).getBytes("ISO-8859-1"));
//				String personLastName = new String(result.getString(2).getBytes("ISO-8859-1"));
//				int commonInterestScore = result.getInt(3);
//				long personId;
//				if (state.isRunSql())
//					personId = result.getLong(4);
//				else
//					personId = Long.parseLong(result.getString(4).substring(47));
//				String gender = result.getString(5);
//				String personCityName = new String(result.getString(6).getBytes("ISO-8859-1"));
//				LdbcQuery10Result tmp = new LdbcQuery10Result(personId, personFirstName, personLastName, commonInterestScore, gender, personCityName);
//				if (state.isPrintResults())
//					System.out.println(tmp.toString());
//				RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query11 implements OperationHandler<LdbcQuery11, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery11 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery11Result> RESULT = new ArrayList<LdbcQuery11Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query11.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@Date0@", String.valueOf(operation.workFromYear()));
//					queryString = queryString.replaceAll("@Country@", operation.countryName());
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%Date0%", String.valueOf(operation.workFromYear()));
//					queryString = queryString.replaceAll("%Country%", operation.countryName());
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery11");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					String personFirstName = new String(result.getString(1).getBytes("ISO-8859-1"));
//					String personLastName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					int organizationWorkFromYear = result.getInt(3);
//					String organizationName = new String(result.getString(4).getBytes("ISO-8859-1"));
//					long personId;
//					if (state.isRunSql())
//						personId = result.getLong(5);
//					else
//						personId = Long.parseLong(result.getString(5).substring(47));;
//						LdbcQuery11Result tmp = new LdbcQuery11Result(personId, personFirstName, personLastName, organizationName, organizationWorkFromYear);
//						if (state.isPrintResults())
//							System.out.println(tmp.toString());
//						RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//	public static class Query12 implements OperationHandler<LdbcQuery12, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery12 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery12Result> RESULT = new ArrayList<LdbcQuery12Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query12.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person@", String.valueOf(operation.personId()));
//					queryString = queryString.replaceAll("@TagType@", operation.tagClassName());
//				}
//				else {
//					queryString = queryString.replaceAll("%Person%", String.format("%020d", operation.personId()));
//					queryString = queryString.replaceAll("%TagType%", operation.tagClassName());
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery12");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					long personId;
//					if (state.isRunSql())
//						personId = result.getLong(1);
//					else
//						personId = Long.parseLong(result.getString(1).substring(47));
//					String personFirstName = new String(result.getString(2).getBytes("ISO-8859-1"));
//					String personLastName = new String(result.getString(3).getBytes("ISO-8859-1"));
//					Collection<String> tagNames =  result.getString(4) == null ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(new String(result.getString(4).getBytes("ISO-8859-1")).split(", ")));
//					int replyCount = result.getInt(5);
//					LdbcQuery12Result tmp = new LdbcQuery12Result(personId, personFirstName, personLastName, tagNames, replyCount);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
//
//
//	public static class Query13 implements OperationHandler<LdbcQuery13, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery13 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery13Result> RESULT = new ArrayList<LdbcQuery13Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query13.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
//					queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
//					queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery13");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					int shortestPathlength = result.getInt(1);
//					LdbcQuery13Result tmp = new LdbcQuery13Result(shortestPathlength);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT.get(0), operation);
//		}
//	}
//
//	public static class Query14 implements OperationHandler<LdbcQuery14, SparqlDriverConnectionState> {
//
//		public void executeOperation(LdbcQuery14 operation, SparqlDriverConnectionState state, ResultReporter resultReporter) throws DbException {
//			Connection conn = state.getConn();
//			Statement stmt = null;
//			List<LdbcQuery14Result> RESULT = new ArrayList<LdbcQuery14Result>();
//			int results_count = 0; RESULT.clear();
//			try {
//				String queryString = file2string(new File(state.getQueryDir(), "query14.txt"));
//				if (state.isRunSql()) {
//					queryString = queryString.replaceAll("@Person1@", String.valueOf(operation.person1Id()));
//					queryString = queryString.replaceAll("@Person2@", String.valueOf(operation.person2Id()));
//				}
//				else {
//					queryString = queryString.replaceAll("%Person1%", String.format("%020d", operation.person1Id()));
//					queryString = queryString.replaceAll("%Person2%", String.format("%020d", operation.person2Id()));
//				}
//				stmt = conn.createStatement();
//
//				if (state.isPrintNames())
//					System.out.println("########### LdbcQuery14");
//				if (state.isPrintStrings())
//					System.out.println(queryString);
//
//				ResultSet result = stmt.executeQuery(queryString);
//				while (result.next()) {
//					results_count++;
//					Long [] ttt = null;
//					if (state.isRunSql()) {
//					    openlink.util.Vector o1 = (openlink.util.Vector)(result.getObject(1));
//					    ttt = new Long[o1.size()];
//					    for (int i = 0; i < o1.size(); i++) {
//						if (o1.elementAt(i) instanceof Long)
//						    ttt[i] = (Long)o1.elementAt(i);
//						else if (o1.elementAt(i) instanceof Integer)
//						    ttt[i] = new Long((Integer)o1.elementAt(i));
//						else if (o1.elementAt(i) instanceof Short)
//						    ttt[i] = new Long((Short)o1.elementAt(i));
//						else {
//						    System.out.println("Error in Q14");
//						}
//					    }
//					}
//					else {
//					    String path = result.getString(1);
//					    String [] parts = path.split("[)]");
//					    ttt = new Long[parts.length - 1];
//					    for (int j = 0; j < ttt.length; j++) {
//						ttt[j] = Long.parseLong(parts[j].split("-")[0].trim());
//					    }
//					}
//					double weight = result.getDouble(2);
//					LdbcQuery14Result tmp = new LdbcQuery14Result(new ArrayList<Long>(Arrays.asList(ttt)), weight);
//					if (state.isPrintResults())
//						System.out.println(tmp.toString());
//					RESULT.add(tmp);
//				}
//				stmt.close();conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try { stmt.close();conn.close(); } catch (SQLException e1) { }
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//			resultReporter.report(results_count, RESULT, operation);
//		}
//	}
	
}

