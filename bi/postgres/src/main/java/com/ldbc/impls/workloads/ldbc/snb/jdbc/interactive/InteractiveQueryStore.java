package com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

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
		Query1("query1.txt"),
		Query2("query2.txt"),
		Query3("query3.txt"),
		Query4("query4.txt"),
		Query5("query5.txt"),
		Query6("query6.txt"),
		Query7("query7.txt"),
		Query8("query8.txt"),
		Query9("query9.txt"),
		Query10("query10.txt"),
		Query11("query11.txt"),
		Query12("query12.txt"),
		Query13("query13.txt"),
		Query14("query14.txt"),
		ShortQuery1PersonProfile("shortquery1personprofile.txt"),
		ShortQuery2PersonPosts("shortquery2personposts.txt"),
		ShortQuery3PersonFriends("shortquery3personfriends.txt"),
		ShortQuery4MessageContent("shortquery4messagecontent.txt"),
		ShortQuery5MessageCreator("shortquery5messagecreator.txt"),
		ShortQuery6MessageForum("shortquery6messageforum.txt"),
		ShortQuery7MessageReplies("shortquery7messagereplies.txt"),
		Update1AddPerson("update1addperson.txt"),
		Update1AddPersonCompanies("update1addpersoncompanies.txt"),
		Update1AddPersonEmails("update1addpersonemails.txt"),
		Update1AddPersonLanguages("update1addpersonlanguages.txt"),
		Update1AddPersonTags("update1addpersontags.txt"),
		Update1AddPersonUniversities("update1addpersonuniversities.txt"),
		Update2AddPostLike("update2addpostlike.txt"),
		Update3AddCommentLike("update3addcommentlike.txt"),
		Update4AddForum("update4addforum.txt"),
		Update4AddForumTags("update4addforumtags.txt"),
		Update5AddForumMembership("update5addforummembership.txt"),
		Update6AddPost("update6addpost.txt"),
		Update6AddPostTags("update6addposttags.txt"),
		Update7AddComment("update7addcomment.txt"),
		Update7AddCommentTags("update7addcommenttags.txt"),
		Update8AddFriendship("update8addfriendship.txt");
		
		QueryType(String file) {
			fileName = file;
		}
		
		String fileName;
	};
	
	
	private HashMap<QueryType, String> queries;
	
	public InteractiveQueryStore(String path) throws DbException {
		queries = new HashMap<InteractiveQueryStore.QueryType, String>();
		for (QueryType queryType : QueryType.values()) {
			queries.put(queryType, loadQueryFromFile(path, queryType.fileName));
		}
	}

	public String getQuery1(LdbcQuery1 operation) {
		return queries.get(QueryType.Query1)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.firstName());
	}

	public String getQuery2(LdbcQuery2 operation) {
		return queries.get(QueryType.Query2)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", convertDate(operation.maxDate()));
	}

	public String getQuery3(LdbcQuery3 operation) {
		return queries.get(QueryType.Query3)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.countryXName())
				.replace("--3--", operation.countryYName())
				.replace("--4--", convertDate(operation.startDate()))
				.replace("--5--", operation.durationDays()+"");
	}

	public String getQuery4(LdbcQuery4 operation) {
		return queries.get(QueryType.Query4)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", convertDate(operation.startDate()))
				.replace("--3--", operation.durationDays()+"");
	}

	public String getQuery5(LdbcQuery5 operation) {
		return queries.get(QueryType.Query5)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", convertDate(operation.minDate()));
	}

	public String getQuery6(LdbcQuery6 operation) {
		return queries.get(QueryType.Query6)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.tagName());
	}

	public String getQuery7(LdbcQuery7 operation) {
		return queries.get(QueryType.Query7)
				.replace("--1--", operation.personId()+"");
	}

	public String getQuery8(LdbcQuery8 operation) {
		return queries.get(QueryType.Query8)
				.replace("--1--", operation.personId()+"");
	}
	
	public String getQuery9(LdbcQuery9 operation) {
		return queries.get(QueryType.Query9)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", convertDate(operation.maxDate()));
	}

	public String getQuery10(LdbcQuery10 operation) {
		return queries.get(QueryType.Query10)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", ((operation.month()+1)%12)+"");
	}

	public String getQuery11(LdbcQuery11 operation) {
		return queries.get(QueryType.Query11)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.workFromYear()+"")
				.replace("--3--", operation.countryName());
	}

	public String getQuery12(LdbcQuery12 operation) {
		return queries.get(QueryType.Query12)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.tagClassName());
	}

	public String getQuery13(LdbcQuery13 operation) {
		return queries.get(QueryType.Query13)
				.replace("--1--", operation.person1Id()+"")
				.replace("--2--", operation.person2Id()+"");
	}

	public String getQuery14(LdbcQuery14 operation) {
		return queries.get(QueryType.Query14)
				.replace("--1--", operation.person1Id()+"")
				.replace("--2--", operation.person2Id()+"");
	}

	public String getShortQuery1PersonProfile(LdbcShortQuery1PersonProfile operation) {
		return queries.get(QueryType.ShortQuery1PersonProfile)
				.replace("--1--", operation.personId()+"");
	}

	public String getShortQuery2PersonPosts(LdbcShortQuery2PersonPosts operation) {
		return queries.get(QueryType.ShortQuery2PersonPosts)
				.replace("--1--", operation.personId()+"");
	}

	public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
		return queries.get(QueryType.ShortQuery3PersonFriends)
				.replace("--1--", operation.personId()+"");
	}

	public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
		return queries.get(QueryType.ShortQuery4MessageContent)
				.replace("--1--", operation.messageId()+"");
	}

	public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
		return queries.get(QueryType.ShortQuery5MessageCreator)
				.replace("--1--", operation.messageId()+"");
	}

	public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
		return queries.get(QueryType.ShortQuery6MessageForum)
				.replace("--1--", operation.messageId()+"");
	}

	public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
		return queries.get(QueryType.ShortQuery7MessageReplies)
				.replace("--1--", operation.messageId()+"");
	}

	public List<String> getUpdate1AddPerson(LdbcUpdate1AddPerson operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(queries.get(QueryType.Update1AddPerson)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.personFirstName())
				.replace("--3--", operation.personLastName())
				.replace("--4--", operation.gender())
				.replace("--5--", convertDate(operation.birthday()))
				.replace("--6--", convertDate(operation.creationDate()))
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
		return queries.get(QueryType.Update2AddPostLike)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.postId()+"")
				.replace("--3--", convertDate(operation.creationDate()));
	}

	public String getUpdate3AddCommentLike(LdbcUpdate3AddCommentLike operation) {
		return queries.get(QueryType.Update3AddCommentLike)
				.replace("--1--", operation.personId()+"")
				.replace("--2--", operation.commentId()+"")
				.replace("--3--", convertDate(operation.creationDate()));
	}

	public ArrayList<String> getUpdate4AddForum(LdbcUpdate4AddForum operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(queries.get(QueryType.Update4AddForum)
				.replace("--1--", operation.forumId()+"")
				.replace("--2--", operation.forumTitle())
				.replace("--3--", convertDate(operation.creationDate()))
				.replace("--4--", operation.moderatorPersonId()+""));
		if(operation.tagIds().size()>0) {
			list.add(queries.get(QueryType.Update4AddForumTags)
					.replace("--1--", converToValueList(operation.forumId(), operation.tagIds())));
		}
		return list;
	}

	public String getUpdate5AddForumMembership(LdbcUpdate5AddForumMembership operation) {
		return queries.get(QueryType.Update5AddForumMembership)
				.replace("--1--", operation.forumId()+"")
				.replace("--2--", operation.personId()+"")
				.replace("--3--", convertDate(operation.joinDate()));
	}

	public List<String> getUpdate6AddPost(LdbcUpdate6AddPost operation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(queries.get(QueryType.Update6AddPost)
				.replace("--1--", operation.postId()+"")
				.replace("--2--", operation.imageFile())
				.replace("--3--", convertDate(operation.creationDate()))
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
				.replace("--2--", convertDate(operation.creationDate()))
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
		return queries.get(QueryType.Update8AddFriendship)
				.replace("--1--", operation.person1Id()+"")
				.replace("--2--", operation.person2Id()+"")
				.replace("--3--", convertDate(operation.creationDate()));
	}

	private String convertLongList(Long[] values) {
		String res = "";
		for (int i = 0; i < values.length; i++) {
			if(i>0) {
				res+=",";
			}
			res+=" "+values[i]+" ";
		}
		return res;
	}
	
	private String convertLongList(List<Long> values) {
		String res = "";
		for (int i = 0; i < values.size(); i++) {
			if(i>0) {
				res+=",";
			}
			res+=" "+values.get(i)+" ";
		}
		return res;
	}
	
	private String convertStringList(List<String> values) {
		String res = "";
		for (int i = 0; i < values.size(); i++) {
			if(i>0) {
				res+=",";
			}
			res+="'"+values.get(i)+"'";
		}
		return res;
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
	
	
	private String convertDate(Date date) {
		//return "to_timestamp("+timestamp+")::timestamp";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		return "'"+sdf.format(date)+"'::timestamp";
	}
	
	private String loadQueryFromFile(String path, String fileName) throws DbException {
		try {
			return new String(readAllBytes(get(path+File.separator+fileName)));
		} catch (IOException e) {
			throw new DbException("Could not load query: " + path + "::" + fileName, e);
		}
	}
}
