package com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate5AddForumMembership;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;

public class InteractiveQueryStore {
	public static enum QueryType {
		Query1("query1.txt", typeList(Long.class, String.class)),
		Query2("query2.txt", typeList(Long.class, Timestamp.class)),
		Query3("query3.txt", typeList(Long.class, String.class, String.class, Timestamp.class, Integer.class)),
		Query4("query4.txt", typeList(Long.class, Timestamp.class, Integer.class)),
		Query5("query5.txt", typeList(Long.class, Timestamp.class)),
		Query6("query6.txt", typeList(Long.class, String.class)),
		Query7("query7.txt", typeList(Long.class)),
		Query8("query8.txt", typeList(Long.class)),
		Query9("query9.txt", typeList(Long.class, Timestamp.class)),
		Query10("query10.txt", typeList(Long.class, Integer.class, Integer.class)),
		Query11("query11.txt", typeList(Long.class, Integer.class, String.class)),
		Query12("query12.txt", typeList(Long.class, String.class)),
		Query13("query13.txt", typeList(Long.class, Long.class)),
		Query14("query14.txt", typeList(Long.class, Long.class)),
		ShortQuery1PersonProfile("shortquery1personprofile.txt", typeList(Long.class)),
		ShortQuery2PersonPosts("shortquery2personposts.txt", typeList(Long.class)),
		ShortQuery3PersonFriends("shortquery3personfriends.txt", typeList(Long.class)),
		ShortQuery4MessageContent("shortquery4messagecontent.txt", typeList(Long.class)),
		ShortQuery5MessageCreator("shortquery5messagecreator.txt", typeList(Long.class)),
		ShortQuery6MessageForum("shortquery6messageforum.txt", typeList(Long.class)),
		ShortQuery7MessageReplies("shortquery7messagereplies.txt", typeList(Long.class)),
		Update1AddPerson("update1addperson.txt", typeList(Long.class, String.class, String.class, String.class, Timestamp.class, Timestamp.class, String.class, String.class, Long.class)),
		Update1AddPersonCompanies("update1addpersoncompanies.txt", typeList(Long.class, Long.class, Integer.class)),
		Update1AddPersonEmails("update1addpersonemails.txt", typeList(Long.class, String.class)),
		Update1AddPersonLanguages("update1addpersonlanguages.txt", typeList(Long.class, String.class)),
		Update1AddPersonTags("update1addpersontags.txt", typeList(Long.class, Long.class)),
		Update1AddPersonUniversities("update1addpersonuniversities.txt", typeList(Long.class, Long.class, Integer.class)),
		Update2AddPostLike("update2addpostlike.txt", typeList(Long.class, Long.class, Timestamp.class)),
		Update3AddCommentLike("update3addcommentlike.txt", typeList(Long.class, Long.class, Timestamp.class)),
		Update4AddForum("update4addforum.txt", typeList(Long.class, String.class, Timestamp.class, Long.class)),
		Update4AddForumTags("update4addforumtags.txt", typeList(Long.class, Long.class)),
		Update5AddForumMembership("update5addforummembership.txt", typeList(Long.class, Long.class, Timestamp.class)),
		Update6AddPost("update6addpost.txt", typeList(Long.class, String.class, Timestamp.class, String.class, String.class, String.class, String.class, Integer.class, Long.class, Long.class, Long.class)),
		Update6AddPostTags("update6addposttags.txt", typeList(Long.class, Long.class)),
		Update7AddComment("update7addcomment.txt", typeList(Long.class, Timestamp.class, String.class, String.class, String.class, Integer.class, Long.class, Long.class, Long.class, Long.class)),
		Update7AddCommentTags("update7addcommenttags.txt", typeList(Long.class, Long.class)),
		Update8AddFriendship("update8addfriendship.txt", typeList(Long.class, Long.class, Timestamp.class));
		
		QueryType(String file) {
			fileName = file;
		}
		
		QueryType(String file, List<Class<?>> types) {
			fileName = file;
			this.types = types;
		}
		
		static List<Class<?>> typeList(Class<?>... types) {
			ArrayList<Class<?>> typeList = new ArrayList<>();
			for (Class<?> cls : types) {
				typeList.add(cls);
			}
			return typeList;
		}
		
		
		String fileName;
		List<Class<?>> types;
	};
	
	
	private HashMap<QueryType, String> queries;
	private ConcurrentHashMap<ConQuery, PrepStmt> stmts;
	private static Pattern wildcardPattern = Pattern.compile("--([0-9]+)--");
	private static Pattern replacePattern = Pattern.compile("[']{0,1} *--[0-9]+-- *[']{0,1}");
	
	public InteractiveQueryStore(String path) throws DbException {
		queries = new HashMap<InteractiveQueryStore.QueryType, String>();
		stmts = new ConcurrentHashMap<>();
		
		for (QueryType queryType : QueryType.values()) {
			queries.put(queryType, loadQueryFromFile(path, queryType.fileName));
		}
	}
	
	private PreparedStatement getStmt(QueryType query, Connection con, boolean batch, Object[] values) {
		ConQuery cq = new ConQuery(query, con);
		if(!stmts.containsKey(cq)) {
			String queryStr = queries.get(query);
			ArrayList<Integer> replacements = new ArrayList<>();
			Matcher m = wildcardPattern.matcher(queryStr);
			while(m.find()) {
				replacements.add(Integer.parseInt(m.group(1)));
			}
			
			String prepareStr=replacePattern.matcher(queryStr).replaceAll("?");
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement(prepareStr);
				stmts.put(cq, new PrepStmt(stmt, replacements));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			return stmts.get(cq).instantiate(query, batch, values);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private PreparedStatement getStmtBatched(QueryType query, Connection con, Object... values) {
		return getStmt(query, con, true, values);
	}
	
	private PreparedStatement getStmt(QueryType query, Connection con, Object... values) {
		return getStmt(query, con, false, values);
	}
	
	private String getSql(QueryType query, Object... values) {
		String queryStr = queries.get(query);
		for (int i = 0; i < values.length; i++) {
			if(values[i] instanceof String) {
				values[i]=((String)values[i]).replaceAll("'", "''");
			}
			while(true) {
				String newQuery=queryStr.replace("--"+(i+1)+"--", values[i].toString());
				if(newQuery.equals(queryStr)) {
					break;
				} else {
					queryStr=newQuery;
				}
			}
		}
		
		return queryStr;
	}

	public String getQuery1(LdbcQuery1 operation) {
		return getSql(QueryType.Query1, 
				operation.personId(),
				operation.firstName());
	}

	public String getQuery2(LdbcQuery2 operation) {
		return getSql(QueryType.Query2, 
				operation.personId(),
				convertDate(operation.maxDate()));
	}

	public String getQuery3(LdbcQuery3 operation) {
		return getSql(QueryType.Query3, 
				operation.personId(), 
				operation.countryXName(), 
				operation.countryYName(), 
				convertDate(operation.startDate()), 
				operation.durationDays());
	}
	

	public String getQuery4(LdbcQuery4 operation) {
		return getSql(QueryType.Query4, 
				operation.personId(), 
				convertDate(operation.startDate()),
				operation.durationDays());
	}

	public String getQuery5(LdbcQuery5 operation) {
		return getSql(QueryType.Query5, 
				operation.personId(), 
				convertDate(operation.minDate()));
	}

	public String getQuery6(LdbcQuery6 operation) {
		return getSql(QueryType.Query6, 
				operation.personId(), 
				operation.tagName());
	}

	public String getQuery7(LdbcQuery7 operation) {
		return getSql(QueryType.Query7, 
				operation.personId());
	}
	
	public String getQuery8(LdbcQuery8 operation) {
		return getSql(QueryType.Query8, 
				operation.personId());
	}
	
	public String getQuery9(LdbcQuery9 operation) {
		return getSql(QueryType.Query9, 
				operation.personId(), 
				convertDate(operation.maxDate()));
	}

	public String getQuery10(LdbcQuery10 operation) {
		int nextMonth=operation.month()+1;
		if(nextMonth==13) { nextMonth=1;}
		return getSql(QueryType.Query10, 
				operation.personId(), 
				operation.month(),
				nextMonth);
		
	}

	public String getQuery11(LdbcQuery11 operation) {
		return getSql(QueryType.Query11, 
				operation.personId(), 
				operation.workFromYear(), 
				operation.countryName());
	}

	public String getQuery12(LdbcQuery12 operation) {
		return getSql(QueryType.Query12, 
				operation.personId(), 
				operation.tagClassName());
	}

	public String getQuery13(LdbcQuery13 operation) {
		return getSql(QueryType.Query13, 
				operation.person1Id(),
				operation.person2Id());
	}

	public String getQuery14(LdbcQuery14 operation) {
		return getSql(QueryType.Query14, 
				operation.person1Id(),
				operation.person2Id());
	}

	public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
		return getSql(QueryType.ShortQuery1PersonProfile, 
				operation.personId());
	}

	public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
		return getSql(QueryType.ShortQuery2PersonPosts, 
				operation.personId());
	}

	public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
		return getSql(QueryType.ShortQuery3PersonFriends, 
				operation.personId());
	}

	public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
		return getSql(QueryType.ShortQuery4MessageContent, 
				operation.messageId());
	}

	public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
		return getSql(QueryType.ShortQuery5MessageCreator, 
				operation.messageId());
	}

	public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
		return getSql(QueryType.ShortQuery6MessageForum, 
				operation.messageId());
	}

	public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
		return getSql(QueryType.ShortQuery7MessageReplies, 
				operation.messageId());
	}
	
	public List<String> getUpdate1AddPerson(LdbcUpdate1AddPerson operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getSql(QueryType.Update1AddPerson,
				operation.personId(),
				operation.personFirstName(),
				operation.personLastName(),
				operation.gender(),
				convertDate(operation.birthday()),
				convertDate(operation.creationDate()),
				operation.locationIp(),
				operation.browserUsed(),
				operation.cityId()));
		for (int i = 0; i < operation.workAt().size(); i++) {
			list.add(getSql(QueryType.Update1AddPersonCompanies,
					operation.personId(),
					operation.workAt().get(i).organizationId(),
					operation.workAt().get(i).year()));
		}
		for (int i = 0; i < operation.emails().size(); i++) {
			list.add(getSql(QueryType.Update1AddPersonEmails,
					operation.personId(),
					operation.emails().get(i)));
		}
		for (int i = 0; i < operation.languages().size(); i++) {
			list.add(getSql(QueryType.Update1AddPersonLanguages,
					operation.personId(),
					operation.languages().get(i)));
		}
		for (int i = 0; i < operation.tagIds().size(); i++) {
			list.add(getSql(QueryType.Update1AddPersonTags,
					operation.personId(),
					operation.tagIds().get(i)));
		}
		for (int i = 0; i < operation.studyAt().size(); i++) {
			list.add(getSql(QueryType.Update1AddPersonUniversities,
					operation.personId(),
					operation.studyAt().get(i).organizationId(),
					operation.studyAt().get(i).year()));
		}
		return list;
	}

	public String getUpdate2AddPostLike(LdbcUpdate2AddPostLike operation) {
		return getSql(QueryType.Update2AddPostLike,
				operation.personId(),
				operation.postId(),
				convertDate(operation.creationDate()));
	}

	public String getUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation) {
		return getSql(QueryType.Update3AddCommentLike,
				operation.personId(),
				operation.commentId(),
				convertDate(operation.creationDate()));
	}

	public ArrayList<String> getUpdate4AddForum(LdbcUpdate4AddForum operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getSql(QueryType.Update4AddForum,
				operation.forumId(),
				operation.forumTitle(),
				convertDate(operation.creationDate()),
				operation.moderatorPersonId()));
		for (int i = 0; i < operation.tagIds().size(); i++) {
			list.add(getSql(QueryType.Update4AddForumTags,
					operation.forumId(),
					operation.tagIds().get(i)));
		}
		return list;
	}
	

	public String getUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation) {
		return getSql(QueryType.Update5AddForumMembership,
				operation.forumId(),
				operation.personId(),
				convertDate(operation.joinDate()));
	}

	public List<String> getUpdate6AddPost(LdbcUpdate6AddPost operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getSql(QueryType.Update6AddPost,
				operation.postId(),
				operation.imageFile(),
				convertDate(operation.creationDate()),
				operation.locationIp(),
				operation.browserUsed(),
				operation.language(),
				operation.content(),
				operation.length(),
				operation.authorPersonId(),
				operation.forumId(),
				operation.countryId()));
		for (int i = 0; i < operation.tagIds().size(); i++) {
			list.add(getSql(QueryType.Update6AddPostTags,
					operation.postId(),
					operation.tagIds().get(i)));
		}
		return list;
	}

	public List<String> getUpdate7AddComment(LdbcUpdate7AddComment operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getSql(QueryType.Update7AddComment,
				operation.commentId(),
				convertDate(operation.creationDate()),
				operation.locationIp(),
				operation.browserUsed(),
				operation.content(),
				operation.length(),
				operation.authorPersonId(),
				operation.countryId(),
				operation.replyToPostId(),
				operation.replyToCommentId()));
		for (int i = 0; i < operation.tagIds().size(); i++) {
			list.add(getSql(QueryType.Update7AddCommentTags,
					operation.commentId(),
					operation.tagIds().get(i)));
		}
		return list;
	}

	public String getUpdate8AddFriendship(LdbcUpdate8AddFriendship operation) {
		return getSql(QueryType.Update8AddFriendship,
				operation.person1Id(),
				operation.person2Id(),
				convertDate(operation.creationDate()));
	}
	
	public PreparedStatement getStmtQuery1(LdbcQuery1 operation, Connection con) {
		return getStmt(QueryType.Query1, con,
				operation.personId(),
				operation.firstName());
	}

	public PreparedStatement getStmtQuery2(LdbcQuery2 operation, Connection con) {
		return getStmt(QueryType.Query2, con,
				operation.personId(),
				convertDate(operation.maxDate()));
	}

	public PreparedStatement getStmtQuery3(LdbcQuery3 operation, Connection con) {
		return getStmt(QueryType.Query3, con,
				operation.personId(), 
				operation.countryXName(), 
				operation.countryYName(), 
				convertDate(operation.startDate()), 
				operation.durationDays());
	}
	

	public PreparedStatement getStmtQuery4(LdbcQuery4 operation, Connection con) {
		return getStmt(QueryType.Query4, con,
				operation.personId(), 
				convertDate(operation.startDate()),
				operation.durationDays());
	}

	public PreparedStatement getStmtQuery5(LdbcQuery5 operation, Connection con) {
		return getStmt(QueryType.Query5, con,
				operation.personId(), 
				convertDate(operation.minDate()));
	}

	public PreparedStatement getStmtQuery6(LdbcQuery6 operation, Connection con) {
		return getStmt(QueryType.Query6, con,
				operation.personId(), 
				operation.tagName());
	}

	public PreparedStatement getStmtQuery7(LdbcQuery7 operation, Connection con) {
		return getStmt(QueryType.Query7, con,
				operation.personId());
	}
	
	public PreparedStatement getStmtQuery8(LdbcQuery8 operation, Connection con) {
		return getStmt(QueryType.Query8, con,
				operation.personId());
	}
	
	public PreparedStatement getStmtQuery9(LdbcQuery9 operation, Connection con) {
		return getStmt(QueryType.Query9, con,
				operation.personId(), 
				convertDate(operation.maxDate()));
	}

	public PreparedStatement getStmtQuery10(LdbcQuery10 operation, Connection con) {
		int nextMonth=operation.month()+1;
		if(nextMonth==13) { nextMonth=1;}
		return getStmt(QueryType.Query10, con,
				operation.personId(), 
				operation.month(),
				nextMonth);
		
	}

	public PreparedStatement getStmtQuery11(LdbcQuery11 operation, Connection con) {
		return getStmt(QueryType.Query11, con,
				operation.personId(), 
				operation.workFromYear(), 
				operation.countryName());
	}

	public PreparedStatement getStmtQuery12(LdbcQuery12 operation, Connection con) {
		return getStmt(QueryType.Query12, con,
				operation.personId(), 
				operation.tagClassName());
	}

	public PreparedStatement getStmtQuery13(LdbcQuery13 operation, Connection con) {
		return getStmt(QueryType.Query13, con,
				operation.person1Id(),
				operation.person2Id());
	}

	public PreparedStatement getStmtQuery14(LdbcQuery14 operation, Connection con) {
		return getStmt(QueryType.Query14, con,
				operation.person1Id(),
				operation.person2Id());
	}

	public PreparedStatement getStmtShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation, Connection con) {
		return getStmt(QueryType.ShortQuery1PersonProfile, con,
				operation.personId());
	}

	public PreparedStatement getStmtShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation, Connection con) {
		return getStmt(QueryType.ShortQuery2PersonPosts, con,
				operation.personId());
	}

	public PreparedStatement getStmtShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation, Connection con) {
		return getStmt(QueryType.ShortQuery3PersonFriends, con,
				operation.personId());
	}

	public PreparedStatement getStmtShortQuery4MessageContent(LdbcShortQuery4MessageContent operation, Connection con) {
		return getStmt(QueryType.ShortQuery4MessageContent, con,
				operation.messageId());
	}

	public PreparedStatement getStmtShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation, Connection con) {
		return getStmt(QueryType.ShortQuery5MessageCreator, con,
				operation.messageId());
	}

	public PreparedStatement getStmtShortQuery6MessageForum(LdbcShortQuery6MessageForum operation, Connection con) {
		return getStmt(QueryType.ShortQuery6MessageForum, con,
				operation.messageId());
	}

	public PreparedStatement getStmtShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation, Connection con) {
		return getStmt(QueryType.ShortQuery7MessageReplies, con,
				operation.messageId());
	}
	
	public List<PreparedStatement> getStmtUpdate1AddPerson(LdbcUpdate1AddPerson operation, Connection con) throws SQLException {
		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
		list.add(getStmt(QueryType.Update1AddPerson, con,
				operation.personId(),
				operation.personFirstName(),
				operation.personLastName(),
				operation.gender(),
				convertDate(operation.birthday()),
				convertDate(operation.creationDate()),
				operation.locationIp(),
				operation.browserUsed(),
				operation.cityId()));
		list.get(0).addBatch();
		PreparedStatement batchStmt = null;
		for (int i = 0; i < operation.workAt().size(); i++) {
			batchStmt = getStmtBatched(QueryType.Update1AddPersonCompanies, con,
					operation.personId(),
					operation.workAt().get(i).organizationId(),
					operation.workAt().get(i).year());
		}
		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
		for (int i = 0; i < operation.emails().size(); i++) {
			batchStmt = getStmtBatched(QueryType.Update1AddPersonEmails, con,
					operation.personId(),
					operation.emails().get(i));
		}
		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
		for (int i = 0; i < operation.languages().size(); i++) {
			batchStmt = getStmtBatched(QueryType.Update1AddPersonLanguages, con,
					operation.personId(),
					operation.languages().get(i));
		}
		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
		for (int i = 0; i < operation.tagIds().size(); i++) {
			batchStmt = getStmtBatched(QueryType.Update1AddPersonTags, con,
					operation.personId(),
					operation.tagIds().get(i));
		}
		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
		for (int i = 0; i < operation.studyAt().size(); i++) {
			batchStmt = getStmtBatched(QueryType.Update1AddPersonUniversities, con,
					operation.personId(),
					operation.studyAt().get(i).organizationId(),
					operation.studyAt().get(i).year());
		}
		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
		return list;
	}
	
	public PreparedStatement getStmtUpdate2AddPostLike(LdbcUpdate2AddPostLike operation, Connection con) {
		return getStmt(QueryType.Update2AddPostLike, con,
				operation.personId(),
				operation.postId(),
				convertDate(operation.creationDate()));
	}

	public PreparedStatement getStmtUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation, Connection con) {
		return getStmt(QueryType.Update3AddCommentLike, con,
				operation.personId(),
				operation.commentId(),
				convertDate(operation.creationDate()));
	}
	
	public ArrayList<PreparedStatement> getStmtUpdate4AddForum(LdbcUpdate4AddForum operation, Connection con) throws SQLException {
		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
		list.add(getStmt(QueryType.Update4AddForum, con,
				operation.forumId(),
				operation.forumTitle(),
				convertDate(operation.creationDate()),
				operation.moderatorPersonId()));
		list.get(0).addBatch();
		PreparedStatement forumTags = null;
		for (int i = 0; i < operation.tagIds().size(); i++) {
			forumTags = getStmtBatched(QueryType.Update4AddForumTags, con,
					operation.forumId(),
					operation.tagIds().get(i));
		}
		if(forumTags!=null) { list.add(forumTags); };
		return list;
	}

	public PreparedStatement getStmtUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation, Connection con) {
		return getStmt(QueryType.Update5AddForumMembership, con,
				operation.forumId(),
				operation.personId(),
				convertDate(operation.joinDate()));
	}
	
	public List<PreparedStatement> getStmtUpdate6AddPost(LdbcUpdate6AddPost operation, Connection con) throws SQLException {
		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
		list.add(getStmt(QueryType.Update6AddPost, con,
				operation.postId(),
				operation.imageFile(),
				convertDate(operation.creationDate()),
				operation.locationIp(),
				operation.browserUsed(),
				operation.language(),
				operation.content(),
				operation.length(),
				operation.authorPersonId(),
				operation.forumId(),
				operation.countryId()));
		list.get(0).addBatch();
		PreparedStatement tags = null;
		for (int i = 0; i < operation.tagIds().size(); i++) {
			tags = getStmtBatched(QueryType.Update6AddPostTags, con,
					operation.postId(),
					operation.tagIds().get(i));
		}
		if(tags!=null) { list.add(tags); };
		return list;
	}


	public List<PreparedStatement> getStmtUpdate7AddComment(LdbcUpdate7AddComment operation, Connection con) throws SQLException {
		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
		list.add(getStmt(QueryType.Update7AddComment, con,
				operation.commentId(),
				convertDate(operation.creationDate()),
				operation.locationIp(),
				operation.browserUsed(),
				operation.content(),
				operation.length(),
				operation.authorPersonId(),
				operation.countryId(),
				operation.replyToPostId(),
				operation.replyToCommentId()));
		list.get(0).addBatch();
		PreparedStatement tags = null;
		for (int i = 0; i < operation.tagIds().size(); i++) {
			tags = getStmtBatched(QueryType.Update7AddCommentTags, con,
					operation.commentId(),
					operation.tagIds().get(i));
		}
		if(tags!=null) { list.add(tags); };
		return list;
	}
	
	public PreparedStatement getStmtUpdate8AddFriendship(LdbcUpdate8AddFriendship operation, Connection con) {
		return getStmt(QueryType.Update8AddFriendship, con,
				operation.person1Id(),
				operation.person2Id(),
				convertDate(operation.creationDate()));
	}
	
	private static class SqlDate {
		String dateCStr;
		String dateStr;
		
		public SqlDate(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			new Timestamp(date.getTime());
			
			dateCStr = sdf.format(date).replace("+00:00", "");
			dateStr = "'"+dateCStr+"'::timestamp";
		}
		
		@Override
		public String toString() {
			return dateStr;
		}
		
	}
	
	private SqlDate convertDate(Date date) {
		return new SqlDate(date);
	}
	
	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}
	
	private static class ConQuery {
		QueryType queryType;
		Connection con;
		
		public ConQuery(QueryType queryType, Connection con) {
			this.queryType = queryType;
			this.con = con;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((con == null) ? 0 : con.hashCode());
			result = prime * result + ((queryType == null) ? 0 : queryType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ConQuery other = (ConQuery) obj;
			if (con == null) {
				if (other.con != null)
					return false;
			} else if (!con.toString().equals(other.con.toString()))
				return false;
			if (queryType != other.queryType)
				return false;
			return true;
		}
	}
	
	private static class PrepStmt {
		PreparedStatement stmt;
		ArrayList<Integer> replacements;
		
		public PrepStmt(PreparedStatement stmt, ArrayList<Integer> replacements) {
			this.stmt = stmt;
			this.replacements = replacements;
		}
		
		PreparedStatement instantiate(QueryType t, boolean batched, Object... values) throws SQLException {
			for (int i = 0; i < replacements.size(); i++) {
				int value=replacements.get(i)-1;
				Class<?> type=t.types.get(value);
				if(type==Long.class) {
					stmt.setLong(i+1, (Long)values[value]);
				} else if(type==String.class) {
					stmt.setString(i+1, (String) values[value]);
				} else if(type==Integer.class) {
					stmt.setInt(i+1, (Integer) values[value]);
				} else if(type==Timestamp.class) {
					stmt.setString(i+1, ((SqlDate) values[value]).dateCStr);
				} else {
					throw new RuntimeException("Unsupported type: "+t.types.get(value)+" in Query: "+t);					
				}
			}
			
			if(batched) {
				stmt.addBatch();
			}
			return stmt;
		}
	}
	
	@Override
	public String toString() {
		return "Query store has "+stmts.size()+" prepared statements";
	}
}
