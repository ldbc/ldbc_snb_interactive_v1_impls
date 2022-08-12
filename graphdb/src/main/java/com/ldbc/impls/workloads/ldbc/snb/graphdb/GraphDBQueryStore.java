package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.google.common.collect.ImmutableMap;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.*;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class GraphDBQueryStore extends QueryStore {

	private static final String SUBJECT_ID = "subjectId";
	private static final String PARAMETER_SEPARATOR = "%";

	public GraphDBQueryStore(String path) throws DbException {
		super(path, ".rq");
	}

	protected Converter getConverter() {
		return new GraphDBConverter();
	}

	@Override
	protected String getParameterPrefix() { return PARAMETER_SEPARATOR; }

	@Override
	protected String getParameterPostfix() { return PARAMETER_SEPARATOR; }

	@Override
	public String getShortQuery3PersonFriends(LdbcShortQuery3PersonFriends operation) {
		return prepare(
				QueryType.InteractiveShortQuery3,
				ImmutableMap.of(LdbcShortQuery3PersonFriends.PERSON_ID, String.valueOf(operation.getPersonIdSQ3()))
		);
	}

	@Override
	public String getShortQuery4MessageContent(LdbcShortQuery4MessageContent operation) {
		return prepare(
				QueryType.InteractiveShortQuery4,
				ImmutableMap.of(LdbcShortQuery4MessageContent.MESSAGE_ID, String.valueOf(operation.getMessageIdContent()))
		);
	}

	@Override
	public String getShortQuery5MessageCreator(LdbcShortQuery5MessageCreator operation) {
		return prepare(
				QueryType.InteractiveShortQuery5,
				ImmutableMap.of(LdbcShortQuery5MessageCreator.MESSAGE_ID, String.valueOf(operation.getMessageIdCreator()))
		);
	}

	@Override
	public String getShortQuery6MessageForum(LdbcShortQuery6MessageForum operation) {
		return prepare(
				QueryType.InteractiveShortQuery6,
				ImmutableMap.of(LdbcShortQuery6MessageForum.MESSAGE_ID, String.valueOf(operation.getMessageForumId()))
		);
	}

	@Override
	public String getShortQuery7MessageReplies(LdbcShortQuery7MessageReplies operation) {
		return prepare(
				QueryType.InteractiveShortQuery7,
				ImmutableMap.of(LdbcShortQuery7MessageReplies.MESSAGE_ID, String.valueOf(operation.getMessageRepliesId()))
		);
	}

	@Override
	public List<String> getUpdate1Multiple(LdbcUpdate1AddPerson operation) {
		List<String> list = new ArrayList<>();
		list.add(prepare(
				QueryType.InteractiveUpdate1AddPerson,
				new ImmutableMap.Builder<String, Object>()
						.put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()))
						.put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.getPersonFirstName()))
						.put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.getPersonLastName()))
						.put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.getGender()))
						.put(LdbcUpdate1AddPerson.BIRTHDAY, GraphDBConverter.convertDateBirthday(operation.getBirthday()))
						.put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
						.put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
						.put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
						.put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertIdForInsertion(operation.getCityId()))
						.put(SUBJECT_ID, getConverter().convertId(operation.getPersonId()))
						.build()
		));

		for (LdbcUpdate1AddPerson.Organization organization : operation.getWorkAt()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonCompanies,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.getPersonId()),
							"organizationId", getConverter().convertIdForInsertion(organization.getOrganizationId()),
							"worksFromYear", getConverter().convertInteger(organization.getYear())
					)
			));
		}
		for (String email : operation.getEmails()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonEmails,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.getPersonId()),
							"email", getConverter().convertString(email)
					)
			));
		}
		for (String language : operation.getLanguages()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonLanguages,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.getPersonId()),
							"language", getConverter().convertString(language)
					)
			));
		}
		convertTags(list, operation.getTagIds(), operation.getPersonId());
		for (LdbcUpdate1AddPerson.Organization organization : operation.getStudyAt()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonUniversities,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.getPersonId()),
							"organizationId", getConverter().convertIdForInsertion(organization.getOrganizationId()),
							"studiesFromYear", getConverter().convertInteger(organization.getYear())
					)
			));
		}
		return list;
	}

	@Override
	public List<String> getUpdate4Multiple(LdbcUpdate4AddForum operation) {
		List<String> list = new ArrayList<>();
		list.add(prepare(
				QueryType.InteractiveUpdate4AddForum,
				ImmutableMap.of(
						LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.getForumId()),
						LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.getForumTitle()),
						LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()),
						LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.getModeratorPersonId()),
						SUBJECT_ID, getConverter().convertId(operation.getForumId())
				)
		));
		convertTags(list, operation.getTagIds(), operation.getForumId());
		return list;
	}

	public List<String> getUpdate6Multiple(LdbcUpdate6AddPost operation) {
		List<String> list = new ArrayList<>();
		list.add(prepare(
						QueryType.InteractiveUpdate6AddPost,
						new ImmutableMap.Builder<String, Object>()
								.put(LdbcUpdate6AddPost.POST_ID, getConverter().convertIdForInsertion(operation.getPostId()))
								.put(LdbcUpdate6AddPost.IMAGE_FILE, getConverter().convertString(operation.getImageFile()))
								.put(LdbcUpdate6AddPost.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
								.put(LdbcUpdate6AddPost.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
								.put(LdbcUpdate6AddPost.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
								.put(LdbcUpdate6AddPost.LANGUAGE, getConverter().convertString(operation.getLanguage()))
								.put(LdbcUpdate6AddPost.CONTENT, getConverter().convertString(operation.getContent()))
								.put(LdbcUpdate6AddPost.LENGTH, getConverter().convertInteger(operation.getLength()))
								.put(LdbcUpdate6AddPost.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
								.put(LdbcUpdate6AddPost.FORUM_ID, getConverter().convertId(operation.getForumId()))
								.put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertIdForInsertion(operation.getCountryId()))
								.put(SUBJECT_ID, getConverter().convertId(operation.getPostId()))
								.build()
				)
		);
		convertTags(list, operation.getTagIds(), operation.getPostId());
		return list;
	}

	@Override
	public List<String> getUpdate7Multiple(LdbcUpdate7AddComment operation) {
		List<String> list = new ArrayList<>();
		list.add(prepare(
				QueryType.InteractiveUpdate7AddComment,
				new ImmutableMap.Builder<String, Object>()
						.put(LdbcUpdate7AddComment.COMMENT_ID, getConverter().convertIdForInsertion(operation.getCommentId()))
						.put(LdbcUpdate7AddComment.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
						.put(LdbcUpdate7AddComment.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
						.put(LdbcUpdate7AddComment.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
						.put(LdbcUpdate7AddComment.CONTENT, getConverter().convertString(operation.getContent()))
						.put(LdbcUpdate7AddComment.LENGTH, getConverter().convertInteger(operation.getLength()))
						.put(LdbcUpdate7AddComment.AUTHOR_PERSON_ID, getConverter().convertId(operation.getAuthorPersonId()))
						.put(LdbcUpdate6AddPost.COUNTRY_ID, getConverter().convertIdForInsertion(operation.getCountryId()))
						.put(LdbcUpdate7AddComment.REPLY_TO_POST_ID, getConverter().convertId(operation.getReplyToPostId()))
						.put(LdbcUpdate7AddComment.REPLY_TO_COMMENT_ID, getConverter().convertId(operation.getReplyToCommentId()))
						.put(SUBJECT_ID, getConverter().convertId(operation.getCommentId()))
						.build()
		));
		convertTags(list, operation.getTagIds(), operation.getCommentId());
		return list;
	}

	private void convertTags(List<String> list, List<Long> tags, long id) {
		for (long tagId : tags) {
			list.add(prepare(
							QueryType.InteractiveUpdate7AddCommentTags,
							ImmutableMap.of(
									SUBJECT_ID, getConverter().convertId(id),
									"tagId", getConverter().convertIdForInsertion(tagId))
					)
			);
		}
	}

}
