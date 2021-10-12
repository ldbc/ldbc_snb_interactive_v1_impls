package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;

public class GraphDBQueryStore extends QueryStore {

	private static final String SUBJECT_ID = "subjectId";

	public GraphDBQueryStore(String path) throws DbException {
		super(path, ".rq");
	}

	protected Converter getConverter() {
		return new GraphDBConverter();
	}

	@Override
	protected String getParameterPrefix() { return "%"; }

	@Override
	protected String getParameterPostfix() { return "%"; }

	@Override
	public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
		return prepare(
				QueryType.InteractiveShortQuery4,
				ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID, String.valueOf(operation.messageId()))
		);
	}

	@Override
	public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
		return prepare(
				QueryType.InteractiveShortQuery5,
				ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID, String.valueOf(operation.messageId()))
		);
	}

	@Override
	public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
		return prepare(
				QueryType.InteractiveShortQuery6,
				ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID, String.valueOf(operation.messageId()))
		);
	}

	@Override
	public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
		return prepare(
				QueryType.InteractiveShortQuery7,
				ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID, String.valueOf(operation.messageId()))
		);
	}

	@Override
	public String getUpdate6Single(LdbcUpdate6AddPost operation) {
//		return super.getUpdate6Single(operation).replace(getParameterPrefix() + "postIdSubj" + getParameterPostfix(),
//				getConverter().convertId(operation.postId()));

		return prepare(
				QueryType.InteractiveUpdate6,
				new ImmutableMap.Builder<String, String>()
						.put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.postId()))
						.put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.imageFile()))
						.put(LdbcUpdate6AddPost.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
						.put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.locationIp()))
						.put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
						.put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.language()))
						.put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.content()))
						.put(LdbcUpdate6AddPost.LENGTH, getConverter().convertInteger(operation.length()))
						.put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, getConverter().convertId(operation.authorPersonId()))
						.put(LdbcUpdate6AddPost.FORUM_ID, getConverter().convertId(operation.forumId()))
						.put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertIdForInsertion(operation.countryId()))
						.put(LdbcUpdate6AddPost.TAG_IDS, getConverter().convertLongList(operation.tagIds()))
						.put(SUBJECT_ID, getConverter().convertId(operation.postId()))
						.build()
		);
	}

	public String getUpdate7Single(LdbcUpdate7AddComment operation) {
		return prepare(
				QueryType.InteractiveUpdate7,
				new ImmutableMap.Builder<String, String>()
						.put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.commentId()))
						.put(LdbcUpdate7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
						.put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.locationIp()))
						.put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
						.put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.content()))
						.put(LdbcUpdate7AddComment.LENGTH, getConverter().convertInteger(operation.length()))
						.put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.authorPersonId()))
						.put(LdbcUpdate7AddComment.COUNTRY_ID, Long.toString(operation.countryId()))
						.put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.replyToPostId()))
						.put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.replyToCommentId()))
						.put(LdbcUpdate7AddComment.TAG_IDS, getConverter().convertLongList(operation.tagIds()))
						.build()
		);
	}
}
