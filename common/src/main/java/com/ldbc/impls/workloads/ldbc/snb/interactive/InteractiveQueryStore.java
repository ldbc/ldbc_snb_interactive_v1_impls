package com.ldbc.impls.workloads.ldbc.snb.interactive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
import com.ldbc.impls.workloads.ldbc.snb.SnbQueryStore;

import java.util.List;

public abstract class InteractiveQueryStore extends SnbQueryStore<InteractiveQueryStore.InteractiveQuery> {

	public enum InteractiveQuery {
		Query1 ( 1, ImmutableList.of("Person", "Name")),
		Query2 ( 2, ImmutableList.of("Person", "Date0")),
		Query3 ( 3, ImmutableList.of("Person", "Country1", "Country2", "Date0", "Duration")),
		Query4 ( 4, ImmutableList.of("Person", "Date0", "Duration")),
		Query5 ( 5, ImmutableList.of("Person", "Date0")),
		Query6 ( 6, ImmutableList.of("Person", "Tag")),
		Query7 ( 7, ImmutableList.of("Person")),
		Query8 ( 8, ImmutableList.of("Person")),
		Query9 ( 9, ImmutableList.of("Person", "Date0")),
		Query10(10, ImmutableList.of("Person", "HS0", "HS1")),
		Query11(11, ImmutableList.of("Person", "Date0", "Country")),
		Query12(12, ImmutableList.of("Person", "TagType")),
		Query13(13, ImmutableList.of("Person1", "Person2")),
		Query14(14, ImmutableList.of("Person1", "Person2")),
//		ShortQuery1PersonProfile("shortquery1personprofile.sql", ImmutableList.of()),
//		ShortQuery2PersonPosts("shortquery2personposts.sql", ImmutableList.of()),
//		ShortQuery3PersonFriends("shortquery3personfriends.sql", ImmutableList.of()),
//		ShortQuery4MessageContent("shortquery4messagecontent.sql", ImmutableList.of()),
//		ShortQuery5MessageCreator("shortquery5messagecreator.sql", ImmutableList.of()),
//		ShortQuery6MessageForum("shortquery6messageforum.sql", ImmutableList.of()),
//		ShortQuery7MessageReplies("shortquery7messagereplies.sql", ImmutableList.of()),
//		Update1AddPerson("update1addperson.sql", ImmutableList.of()),
//		Update1AddPersonCompanies("update1addpersoncompanies.sql", ImmutableList.of()),
//		Update1AddPersonEmails("update1addpersonemails.sql", ImmutableList.of()),
//		Update1AddPersonLanguages("update1addpersonlanguages.sql", ImmutableList.of()),
//		Update1AddPersonTags("update1addpersontags.sql", ImmutableList.of()),
//		Update1AddPersonUniversities("update1addpersonuniversities.sql", ImmutableList.of()),
//		Update2AddPostLike("update2addpostlike.sql", ImmutableList.of()),
//		Update3AddCommentLike("update3addcommentlike.sql", ImmutableList.of()),
//		Update4AddForum("update4addforum.sql", ImmutableList.of()),
//		Update4AddForumTags("update4addforumtags.sql", ImmutableList.of()),
//		Update5AddForumMembership("update5addforummembership.sql", ImmutableList.of()),
//		Update6AddPost("update6addpost.sql", ImmutableList.of()),
//		Update6AddPostTags("update6addposttags.sql", ImmutableList.of()),
//		Update7AddComment("update7addcomment.sql", ImmutableList.of()),
//		Update7AddCommentTags("update7addcommenttags.sql", ImmutableList.of()),
//		Update8AddFriendship("update8addfriendship.sql", ImmutableList.of()),
		;

		InteractiveQuery(int number, List<String> parameters) {
			this.number = number;
			this.parameters = parameters;
		}

		private int number;
		private List<String> parameters;

		public int getNumber() {
			return number;
		}

		public List<String> getParameters() {
			return parameters;
		}
	};


	public InteractiveQueryStore(String path, String prefix, String postfix) throws DbException {
		for (InteractiveQuery interactiveQuery : InteractiveQuery.values()) {
			queries.put(interactiveQuery, loadQueryFromFile(path, prefix + interactiveQuery.number + postfix));
		}
	}

	public String getQuery1(LdbcQuery1 operation) {
		return prepare(InteractiveQuery.Query1, new ImmutableMap.Builder<String, String>()
				.put("Person", getConverter().convertId(operation.personId()))
				.put("Name",   getConverter().convertString(operation.firstName()))
				.build());
	}

	public String getQuery2(LdbcQuery2 operation) {
		return prepare(InteractiveQuery.Query2, new ImmutableMap.Builder<String, String>()
				.put("Person", getConverter().convertId(operation.personId()))
				.put("Date0",  getConverter().convertDateTime(operation.maxDate()))
				.build());
	}

	public String getQuery3(LdbcQuery3 operation) {
		return prepare(InteractiveQuery.Query3, new ImmutableMap.Builder<String, String>()
						.put("Person",   getConverter().convertId(operation.personId()))
						.put("Country1", getConverter().convertString(operation.countryXName()))
						.put("Country2", getConverter().convertString(operation.countryYName()))
						.put("Date0",    getConverter().convertDateTime(operation.startDate()))
						.put("Duration", getConverter().convertInteger(operation.durationDays()))
						.build());
	}

	public String getQuery4(LdbcQuery4 operation) {
		return prepare(InteractiveQuery.Query4, new ImmutableMap.Builder<String, String>()
						.put("Person",   getConverter().convertId(operation.personId()))
						.put("Date0",    getConverter().convertDateTime(operation.startDate()))
						.put("Duration", getConverter().convertInteger(operation.durationDays()))
						.build());
	}

	public String getQuery5(LdbcQuery5 operation) {
		return prepare(InteractiveQuery.Query5, new ImmutableMap.Builder<String, String>()
						.put("Person", getConverter().convertId(operation.personId()))
						.put("Date0",  getConverter().convertDateTime(operation.minDate()))
						.build());
	}

	public String getQuery6(LdbcQuery6 operation) {
		return prepare(InteractiveQuery.Query6, new ImmutableMap.Builder<String, String>()
						.put("Person", getConverter().convertId(operation.personId()))
						.put("Tag",    getConverter().convertString(operation.tagName()))
						.build());
	}

	public String getQuery7(LdbcQuery7 operation) {
		return prepare(InteractiveQuery.Query7, new ImmutableMap.Builder<String, String>()
						.put("Person", getConverter().convertId(operation.personId()))
						.build());
	}

	public String getQuery8(LdbcQuery8 operation) {
		return prepare(InteractiveQuery.Query8, new ImmutableMap.Builder<String, String>()
						.put("Person", getConverter().convertId(operation.personId()))
						.build());
	}

	public String getQuery9(LdbcQuery9 operation) {
		return prepare(InteractiveQuery.Query9, new ImmutableMap.Builder<String, String>()
						.put("Person", getConverter().convertId(operation.personId()))
						.put("Date0",  getConverter().convertDateTime(operation.maxDate()))
						.build());
	}

	public String getQuery10(LdbcQuery10 operation) {
		return prepare(InteractiveQuery.Query10, new ImmutableMap.Builder<String, String>()
						.put("Person", getConverter().convertId(operation.personId()))
						.put("HS0",    getConverter().convertInteger(operation.month()))
						.put("HS1",    getConverter().convertInteger(operation.month() % 12 + 1))
						.build());
	}

	public String getQuery11(LdbcQuery11 operation) {
		return prepare(InteractiveQuery.Query11, new ImmutableMap.Builder<String, String>()
						.put("Person",  getConverter().convertId(operation.personId()))
						.put("Date0",   getConverter().convertInteger(operation.workFromYear()))
						.put("Country", getConverter().convertString(operation.countryName()))
						.build());
	}

	public String getQuery12(LdbcQuery12 operation) {
		return prepare(InteractiveQuery.Query12, new ImmutableMap.Builder<String, String>()
						.put("Person",  getConverter().convertId(operation.personId()))
						.put("TagType", getConverter().convertString(operation.tagClassName()))
						.build());
	}

	public String getQuery13(LdbcQuery13 operation) {
		return prepare(InteractiveQuery.Query13, new ImmutableMap.Builder<String, String>()
						.put("Person1", getConverter().convertId(operation.person1Id()))
						.put("Person2", getConverter().convertId(operation.person2Id()))
						.build());
	}

	public String getQuery14(LdbcQuery14 operation) {
		return prepare(InteractiveQuery.Query14, new ImmutableMap.Builder<String, String>()
						.put("Person1", getConverter().convertId(operation.person1Id()))
						.put("Person2", getConverter().convertId(operation.person2Id()))
						.build());
	}

//	public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
//		return getSql(InteractiveQuery.ShortQuery1PersonProfile,
//				operation.personId());
//	}
//
//	public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
//		return getSql(InteractiveQuery.ShortQuery2PersonPosts,
//				operation.personId());
//	}
//
//	public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
//		return getSql(InteractiveQuery.ShortQuery3PersonFriends,
//				operation.personId());
//	}
//
//	public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
//		return getSql(InteractiveQuery.ShortQuery4MessageContent,
//				operation.messageId());
//	}
//
//	public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
//		return getSql(InteractiveQuery.ShortQuery5MessageCreator,
//				operation.messageId());
//	}
//
//	public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
//		return getSql(InteractiveQuery.ShortQuery6MessageForum,
//				operation.messageId());
//	}
//
//	public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
//		return getSql(InteractiveQuery.ShortQuery7MessageReplies,
//				operation.messageId());
//	}
//
//	public List<String> getUpdate1AddPerson(LdbcUpdate1AddPerson operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update1AddPerson,
//				operation.personId(),
//				operation.personFirstName(),
//				operation.personLastName(),
//				operation.gender(),
//				convertDate(operation.birthday()),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.cityId()));
//		for (int i = 0; i < operation.workAt().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonCompanies,
//					operation.personId(),
//					operation.workAt().get(i).organizationId(),
//					operation.workAt().get(i).year()));
//		}
//		for (int i = 0; i < operation.emails().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonEmails,
//					operation.personId(),
//					operation.emails().get(i)));
//		}
//		for (int i = 0; i < operation.languages().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonLanguages,
//					operation.personId(),
//					operation.languages().get(i)));
//		}
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonTags,
//					operation.personId(),
//					operation.tagIds().get(i)));
//		}
//		for (int i = 0; i < operation.studyAt().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update1AddPersonUniversities,
//					operation.personId(),
//					operation.studyAt().get(i).organizationId(),
//					operation.studyAt().get(i).year()));
//		}
//		return list;
//	}
//
//	public String getUpdate2AddPostLike(LdbcUpdate2AddPostLike operation) {
//		return getSql(InteractiveQuery.Update2AddPostLike,
//				operation.personId(),
//				operation.postId(),
//				convertDate(operation.creationDate()));
//	}
//
//	public String getUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation) {
//		return getSql(InteractiveQuery.Update3AddCommentLike,
//				operation.personId(),
//				operation.commentId(),
//				convertDate(operation.creationDate()));
//	}
//
//	public ArrayList<String> getUpdate4AddForum(LdbcUpdate4AddForum operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update4AddForum,
//				operation.forumId(),
//				operation.forumTitle(),
//				convertDate(operation.creationDate()),
//				operation.moderatorPersonId()));
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update4AddForumTags,
//					operation.forumId(),
//					operation.tagIds().get(i)));
//		}
//		return list;
//	}
//
//
//	public String getUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation) {
//		return getSql(InteractiveQuery.Update5AddForumMembership,
//				operation.forumId(),
//				operation.personId(),
//				convertDate(operation.joinDate()));
//	}
//
//	public List<String> getUpdate6AddPost(LdbcUpdate6AddPost operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update6AddPost,
//				operation.postId(),
//				operation.imageFile(),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.language(),
//				operation.content(),
//				operation.length(),
//				operation.authorPersonId(),
//				operation.forumId(),
//				operation.countryId()));
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update6AddPostTags,
//					operation.postId(),
//					operation.tagIds().get(i)));
//		}
//		return list;
//	}
//
//	public List<String> getUpdate7AddComment(LdbcUpdate7AddComment operation) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(getSql(InteractiveQuery.Update7AddComment,
//				operation.commentId(),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.content(),
//				operation.length(),
//				operation.authorPersonId(),
//				operation.countryId(),
//				operation.replyToPostId(),
//				operation.replyToCommentId()));
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			list.add(getSql(InteractiveQuery.Update7AddCommentTags,
//					operation.commentId(),
//					operation.tagIds().get(i)));
//		}
//		return list;
//	}
//
//	public String getUpdate8AddFriendship(LdbcUpdate8AddFriendship operation) {
//		return getSql(InteractiveQuery.Update8AddFriendship,
//				operation.person1Id(),
//				operation.person2Id(),
//				convertDate(operation.creationDate()));
//	}
//
//	public PreparedStatement getStmtQuery1(LdbcQuery1 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query1, con,
//				operation.personId(),
//				operation.firstName());
//	}
//
//	public PreparedStatement getStmtQuery2(LdbcQuery2 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query2, con,
//				operation.personId(),
//				convertDate(operation.maxDate()));
//	}
//
//	public PreparedStatement getStmtQuery3(LdbcQuery3 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query3, con,
//				operation.personId(),
//				operation.countryXName(),
//				operation.countryYName(),
//				convertDate(operation.startDate()),
//				operation.durationDays());
//	}
//
//
//	public PreparedStatement getStmtQuery4(LdbcQuery4 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query4, con,
//				operation.personId(),
//				convertDate(operation.startDate()),
//				operation.durationDays());
//	}
//
//	public PreparedStatement getStmtQuery5(LdbcQuery5 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query5, con,
//				operation.personId(),
//				convertDate(operation.minDate()));
//	}
//
//	public PreparedStatement getStmtQuery6(LdbcQuery6 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query6, con,
//				operation.personId(),
//				operation.tagName());
//	}
//
//	public PreparedStatement getStmtQuery7(LdbcQuery7 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query7, con,
//				operation.personId());
//	}
//
//	public PreparedStatement getStmtQuery8(LdbcQuery8 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query8, con,
//				operation.personId());
//	}
//
//	public PreparedStatement getStmtQuery9(LdbcQuery9 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query9, con,
//				operation.personId(),
//				convertDate(operation.maxDate()));
//	}
//
//	public PreparedStatement getStmtQuery10(LdbcQuery10 operation, Connection con) {
//		int nextMonth=operation.month()+1;
//		if(nextMonth==13) { nextMonth=1;}
//		return getStmt(InteractiveQuery.Query10, con,
//				operation.personId(),
//				operation.month(),
//				nextMonth);
//
//	}
//
//	public PreparedStatement getStmtQuery11(LdbcQuery11 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query11, con,
//				operation.personId(),
//				operation.workFromYear(),
//				operation.countryName());
//	}
//
//	public PreparedStatement getStmtQuery12(LdbcQuery12 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query12, con,
//				operation.personId(),
//				operation.tagClassName());
//	}
//
//	public PreparedStatement getStmtQuery13(LdbcQuery13 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query13, con,
//				operation.person1Id(),
//				operation.person2Id());
//	}
//
//	public PreparedStatement getStmtQuery14(LdbcQuery14 operation, Connection con) {
//		return getStmt(InteractiveQuery.Query14, con,
//				operation.person1Id(),
//				operation.person2Id());
//	}
//
//	public PreparedStatement getStmtShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery1PersonProfile, con,
//				operation.personId());
//	}
//
//	public PreparedStatement getStmtShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery2PersonPosts, con,
//				operation.personId());
//	}
//
//	public PreparedStatement getStmtShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery3PersonFriends, con,
//				operation.personId());
//	}
//
//	public PreparedStatement getStmtShortQuery4MessageContent(LdbcShortQuery4MessageContent operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery4MessageContent, con,
//				operation.messageId());
//	}
//
//	public PreparedStatement getStmtShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery5MessageCreator, con,
//				operation.messageId());
//	}
//
//	public PreparedStatement getStmtShortQuery6MessageForum(LdbcShortQuery6MessageForum operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery6MessageForum, con,
//				operation.messageId());
//	}
//
//	public PreparedStatement getStmtShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation, Connection con) {
//		return getStmt(InteractiveQuery.ShortQuery7MessageReplies, con,
//				operation.messageId());
//	}
//
//	public List<PreparedStatement> getStmtUpdate1AddPerson(LdbcUpdate1AddPerson operation, Connection con) throws SQLException {
//		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
//		list.add(getStmt(InteractiveQuery.Update1AddPerson, con,
//				operation.personId(),
//				operation.personFirstName(),
//				operation.personLastName(),
//				operation.gender(),
//				convertDate(operation.birthday()),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.cityId()));
//		list.get(0).addBatch();
//		PreparedStatement batchStmt = null;
//		for (int i = 0; i < operation.workAt().size(); i++) {
//			batchStmt = getStmtBatched(InteractiveQuery.Update1AddPersonCompanies, con,
//					operation.personId(),
//					operation.workAt().get(i).organizationId(),
//					operation.workAt().get(i).year());
//		}
//		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
//		for (int i = 0; i < operation.emails().size(); i++) {
//			batchStmt = getStmtBatched(InteractiveQuery.Update1AddPersonEmails, con,
//					operation.personId(),
//					operation.emails().get(i));
//		}
//		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
//		for (int i = 0; i < operation.languages().size(); i++) {
//			batchStmt = getStmtBatched(InteractiveQuery.Update1AddPersonLanguages, con,
//					operation.personId(),
//					operation.languages().get(i));
//		}
//		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			batchStmt = getStmtBatched(InteractiveQuery.Update1AddPersonTags, con,
//					operation.personId(),
//					operation.tagIds().get(i));
//		}
//		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
//		for (int i = 0; i < operation.studyAt().size(); i++) {
//			batchStmt = getStmtBatched(InteractiveQuery.Update1AddPersonUniversities, con,
//					operation.personId(),
//					operation.studyAt().get(i).organizationId(),
//					operation.studyAt().get(i).year());
//		}
//		if(batchStmt!=null) { list.add(batchStmt); batchStmt=null; };
//		return list;
//	}
//
//	public PreparedStatement getStmtUpdate2AddPostLike(LdbcUpdate2AddPostLike operation, Connection con) {
//		return getStmt(InteractiveQuery.Update2AddPostLike, con,
//				operation.personId(),
//				operation.postId(),
//				convertDate(operation.creationDate()));
//	}
//
//	public PreparedStatement getStmtUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation, Connection con) {
//		return getStmt(InteractiveQuery.Update3AddCommentLike, con,
//				operation.personId(),
//				operation.commentId(),
//				convertDate(operation.creationDate()));
//	}
//
//	public ArrayList<PreparedStatement> getStmtUpdate4AddForum(LdbcUpdate4AddForum operation, Connection con) throws SQLException {
//		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
//		list.add(getStmt(InteractiveQuery.Update4AddForum, con,
//				operation.forumId(),
//				operation.forumTitle(),
//				convertDate(operation.creationDate()),
//				operation.moderatorPersonId()));
//		list.get(0).addBatch();
//		PreparedStatement forumTags = null;
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			forumTags = getStmtBatched(InteractiveQuery.Update4AddForumTags, con,
//					operation.forumId(),
//					operation.tagIds().get(i));
//		}
//		if(forumTags!=null) { list.add(forumTags); };
//		return list;
//	}
//
//	public PreparedStatement getStmtUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation, Connection con) {
//		return getStmt(InteractiveQuery.Update5AddForumMembership, con,
//				operation.forumId(),
//				operation.personId(),
//				convertDate(operation.joinDate()));
//	}
//
//	public List<PreparedStatement> getStmtUpdate6AddPost(LdbcUpdate6AddPost operation, Connection con) throws SQLException {
//		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
//		list.add(getStmt(InteractiveQuery.Update6AddPost, con,
//				operation.postId(),
//				operation.imageFile(),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.language(),
//				operation.content(),
//				operation.length(),
//				operation.authorPersonId(),
//				operation.forumId(),
//				operation.countryId()));
//		list.get(0).addBatch();
//		PreparedStatement tags = null;
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			tags = getStmtBatched(InteractiveQuery.Update6AddPostTags, con,
//					operation.postId(),
//					operation.tagIds().get(i));
//		}
//		if(tags!=null) { list.add(tags); };
//		return list;
//	}
//
//
//	public List<PreparedStatement> getStmtUpdate7AddComment(LdbcUpdate7AddComment operation, Connection con) throws SQLException {
//		ArrayList<PreparedStatement> list = new ArrayList<PreparedStatement>();
//		list.add(getStmt(InteractiveQuery.Update7AddComment, con,
//				operation.commentId(),
//				convertDate(operation.creationDate()),
//				operation.locationIp(),
//				operation.browserUsed(),
//				operation.content(),
//				operation.length(),
//				operation.authorPersonId(),
//				operation.countryId(),
//				operation.replyToPostId(),
//				operation.replyToCommentId()));
//		list.get(0).addBatch();
//		PreparedStatement tags = null;
//		for (int i = 0; i < operation.tagIds().size(); i++) {
//			tags = getStmtBatched(InteractiveQuery.Update7AddCommentTags, con,
//					operation.commentId(),
//					operation.tagIds().get(i));
//		}
//		if(tags!=null) { list.add(tags); };
//		return list;
//	}
//
//	public PreparedStatement getStmtUpdate8AddFriendship(LdbcUpdate8AddFriendship operation, Connection con) {
//		return getStmt(InteractiveQuery.Update8AddFriendship, con,
//				operation.person1Id(),
//				operation.person2Id(),
//				convertDate(operation.creationDate()));
//	}

}
