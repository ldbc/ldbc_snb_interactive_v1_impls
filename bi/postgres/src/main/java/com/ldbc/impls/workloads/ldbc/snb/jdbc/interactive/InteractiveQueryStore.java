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
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson.Organization;
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
		Update1AddPerson("update1addperson.txt"),
		Update1AddPersonCompanies("update1addpersoncompanies.txt"),
		Update1AddPersonEmails("update1addpersonemails.txt"),
		Update1AddPersonLanguages("update1addpersonlanguages.txt"),
		Update1AddPersonTags("update1addpersontags.txt"),
		Update1AddPersonUniversities("update1addpersonuniversities.txt"),
		Update2AddPostLike("update2addpostlike.txt", typeList(Long.class, Long.class, Timestamp.class)),
		Update3AddCommentLike("update3addcommentlike.txt", typeList(Long.class, Long.class, Timestamp.class)),
		Update4AddForum("update4addforum.txt"),
		Update4AddForumTags("update4addforumtags.txt"),
		Update5AddForumMembership("update5addforummembership.txt", typeList(Long.class, Long.class, Timestamp.class)),
		Update6AddPost("update6addpost.txt"),
		Update6AddPostTags("update6addposttags.txt"),
		Update7AddComment("update7addcomment.txt"),
		Update7AddCommentTags("update7addcommenttags.txt"),
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
	
	private PreparedStatement getStmt(QueryType query, Connection con, Object... values) {
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
			return stmts.get(cq).instantiate(query, values);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getSql(QueryType query, Object... values) {
		String queryStr = queries.get(query);
		for (int i = 0; i < values.length; i++) {
			queryStr=queryStr.replaceAll("--"+(i+1)+"--", values[i]+"");
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
	
	public List<String> getUpdate1AddPerson(LdbcUpdate1AddPerson operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(queries.get(QueryType.Update1AddPerson)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.personFirstName())
				.replace("--3--", operation.personLastName())
				.replace("--4--", operation.gender())
				.replace("--5--", convertDate(operation.birthday()).toString())
				.replace("--6--", convertDate(operation.creationDate()).toString())
				.replace("--7--", operation.locationIp())
				.replace("--8--", operation.browserUsed())
				.replace("--9--", operation.cityId()+""));
		if(operation.workAt().size()>0) {
			list.add(queries.get(QueryType.Update1AddPersonCompanies)
					.replace("--1--", converToValueListOrganization(operation.personId(), operation.workAt())));
		}
		if(operation.emails().size()>0) {
			list.add(queries.get(QueryType.Update1AddPersonEmails)
					.replace("--1--", converToValueListString(operation.personId(), operation.emails())));
		}
		if(operation.languages().size()>0) {
			list.add(queries.get(QueryType.Update1AddPersonLanguages)
					.replace("--1--", converToValueListString(operation.personId(), operation.languages())));
		}
		if(operation.tagIds().size()>0) {
			list.add(queries.get(QueryType.Update1AddPersonTags)
					.replace("--1--", converToValueList(operation.personId(), operation.tagIds())));
		}
		if(operation.studyAt().size()>0) {
			list.add(queries.get(QueryType.Update1AddPersonUniversities)
					.replace("--1--", converToValueListOrganization(operation.personId(), operation.studyAt())));
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
		list.add(queries.get(QueryType.Update4AddForum)
				.replace("--1--", operation.forumId()+"")
				.replace("--2--", operation.forumTitle())
				.replace("--3--", convertDate(operation.creationDate()).toString())
				.replace("--4--", operation.moderatorPersonId()+""));
		if(operation.tagIds().size()>0) {
			list.add(queries.get(QueryType.Update4AddForumTags)
					.replace("--1--", converToValueList(operation.forumId(), operation.tagIds())));
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
		list.add(queries.get(QueryType.Update6AddPost)
				.replace("--1--", operation.postId()+"")
				.replace("--2--", operation.imageFile())
				.replace("--3--", convertDate(operation.creationDate()).toString())
				.replace("--4--", operation.locationIp())
				.replace("--5--", operation.browserUsed())
				.replace("--6--", operation.language())
				.replace("--7--", operation.content().replace("'", "''"))
				.replace("--8--", operation.length()+"")
				.replace("--9--", operation.authorPersonId()+"")
				.replace("--10--", operation.forumId()+"")
				.replace("--11--", operation.countryId()+""));
		if(operation.tagIds().size()>0) {
			list.add(queries.get(QueryType.Update6AddPostTags)
					.replace("--1--", converToValueList(operation.postId(), operation.tagIds())));
		}
		return list;
	}

	public List<String> getUpdate7AddComment(LdbcUpdate7AddComment operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(queries.get(QueryType.Update7AddComment)
				.replace("--1--", operation.commentId()+"")
				.replace("--2--", convertDate(operation.creationDate()).toString())
				.replace("--3--", operation.locationIp())
				.replace("--4--", operation.browserUsed())
				.replace("--5--", operation.content().replace("'", "''"))
				.replace("--6--", operation.length()+"")
				.replace("--7--", operation.authorPersonId()+"")
				.replace("--8--", operation.countryId()+"")
				.replace("--9--", operation.replyToPostId()+"")
				.replace("--10--", operation.replyToCommentId()+""));
		if(operation.tagIds().size()>0) {
			list.add(queries.get(QueryType.Update7AddCommentTags)
					.replace("--1--", converToValueList(operation.commentId(), operation.tagIds())));
		}
		return list;
	}

	public String getUpdate8AddFriendship(LdbcUpdate8AddFriendship operation) {
		return getSql(QueryType.Update8AddFriendship,
				operation.person1Id(),
				operation.person2Id(),
				convertDate(operation.creationDate()));
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

	public PreparedStatement getStmtUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation, Connection con) {
		return getStmt(QueryType.Update5AddForumMembership, con,
				operation.forumId(),
				operation.personId(),
				convertDate(operation.joinDate()));
	}

	public PreparedStatement getStmtUpdate8AddFriendship(LdbcUpdate8AddFriendship operation, Connection con) {
		return getStmt(QueryType.Update8AddFriendship, con,
				operation.person1Id(),
				operation.person2Id(),
				convertDate(operation.creationDate()));
	}

	private String converToValueList(long id, List<Long> values) {
		String res = "";
		for (int i = 0; i < values.size(); i++) {
			if(i>0) {
				res+=",";
			}
			res+=" ("+id+"," + values.get(i)+") ";
		}
		return res;
	}
	
	private String converToValueListString(long id, List<String> values) {
		String res = "";
		for (int i = 0; i < values.size(); i++) {
			if(i>0) {
				res+=",";
			}
			res+=" ("+id+",'" + values.get(i)+"') ";
		}
		return res;
	}
	
	private String converToValueListOrganization(long id, List<Organization> values) {
		String res = "";
		for (int i = 0; i < values.size(); i++) {
			if(i>0) {
				res+=",";
			}
			res+=" ("+id+"," + values.get(i).organizationId() +"," + values.get(i).year() +") ";
		}
		return res;
	}
	
	private static class SqlDate {
		String dateCStr;
		String dateStr;
		long timestamp;
		
		public SqlDate(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			new Timestamp(date.getTime());
			
			dateCStr = sdf.format(date).replace("+00:00", "");
			dateStr = "'"+dateCStr+"'::timestamp";
			timestamp = date.getTime();
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
	
	private class ConQuery {
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
			result = prime * result + getOuterType().hashCode();
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
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (con == null) {
				if (other.con != null)
					return false;
			} else if (!con.equals(other.con))
				return false;
			if (queryType != other.queryType)
				return false;
			return true;
		}

		private InteractiveQueryStore getOuterType() {
			return InteractiveQueryStore.this;
		}
	}
	
	private class PrepStmt {
		PreparedStatement stmt;
		ArrayList<Integer> replacements;
		
		public PrepStmt(PreparedStatement stmt, ArrayList<Integer> replacements) {
			this.stmt = stmt;
			this.replacements = replacements;
		}
		
		PreparedStatement instantiate(QueryType t, Object... values) throws SQLException {
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
//			if(t==QueryType.Query14) {
//				System.out.println(stmt.toString());
//				System.out.println(stmt.getParameterMetaData().toString());
//			}
			return stmt;
		}
	}
}
