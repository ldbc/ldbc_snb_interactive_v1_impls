package com.ldbc.impls.workloads.ldbc.snb.graphdb;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.graphdb.converter.GraphDBConverter;

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
	public String getUpdate4Single(LdbcUpdate4AddForum operation) {
	 return super.getUpdate4Single(operation).replace(getParameterPrefix() + "subjectId" + getParameterPostfix(),
						getConverter().convertId(operation.forumId()));
	}

	@Override
	public List<String> getUpdate1Multiple(LdbcUpdate1AddPerson operation) {
		List<String> list = new ArrayList<>();
		list.add(prepare(
				QueryType.InteractiveUpdate1AddPerson,
				new ImmutableMap.Builder<String, String>()
						.put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.personId()))
						.put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.personFirstName()))
						.put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.personLastName()))
						.put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.gender()))
						.put(LdbcUpdate1AddPerson.BIRTHDAY, super.getConverter().convertDate(operation.birthday()))
						.put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()))
						.put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.locationIp()))
						.put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.browserUsed()))
						.put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertIdForInsertion(operation.cityId()))
						.put(SUBJECT_ID, getConverter().convertId(operation.personId()))
						.build()
		));

		for (LdbcUpdate1AddPerson.Organization organization : operation.workAt()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonCompanies,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.personId()),
							"organizationId", getConverter().convertIdForInsertion(organization.organizationId()),
							"worksFromYear", getConverter().convertInteger(organization.year())
					)
			));
		}
		for (String email : operation.emails()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonEmails,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.personId()),
							"email", getConverter().convertString(email)
					)
			));
		}
		for (String language : operation.languages()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonLanguages,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.personId()),
							"language", getConverter().convertString(language)
					)
			));
		}

		for (long tagId : operation.tagIds()) {
			list.add(prepare(
							QueryType.InteractiveUpdate1AddPersonTags,
							ImmutableMap.of(
									SUBJECT_ID, getConverter().convertId(operation.personId()),
									"tagId", getConverter().convertIdForInsertion(tagId))
					)
			);
		}
		for (LdbcUpdate1AddPerson.Organization organization : operation.studyAt()) {
			list.add(prepare(
					QueryType.InteractiveUpdate1AddPersonUniversities,
					ImmutableMap.of(
							SUBJECT_ID, getConverter().convertId(operation.personId()),
							"organizationId", getConverter().convertIdForInsertion(organization.organizationId()),
							"studiesFromYear", getConverter().convertInteger(organization.year())
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
						LdbcUpdate4AddForum.FORUM_ID, getConverter().convertIdForInsertion(operation.forumId()),
						LdbcUpdate4AddForum.FORUM_TITLE, getConverter().convertString(operation.forumTitle()),
						LdbcUpdate4AddForum.CREATION_DATE, getConverter().convertDateTime(operation.creationDate()),
						LdbcUpdate4AddForum.MODERATOR_PERSON_ID, getConverter().convertId(operation.moderatorPersonId()),
						SUBJECT_ID, getConverter().convertId(operation.forumId())
				)
		));

		for (long tagId : operation.tagIds()) {
			list.add(prepare(
							QueryType.InteractiveUpdate4AddForumTags,
							ImmutableMap.of(
									SUBJECT_ID, getConverter().convertId(operation.forumId()),
									"tagId", getConverter().convertIdForInsertion(tagId))
					)
			);
		}
		return list;
	}

	public List<String> getUpdate6Multiple(LdbcUpdate6AddPost operation) {
		List<String> list = new ArrayList<>();
		list.add(prepare(
						QueryType.InteractiveUpdate6AddPost,
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
								.put(SUBJECT_ID, getConverter().convertId(operation.postId()))
								.build()
				)
		);
		for (long tagId : operation.tagIds()) {
			list.add(prepare(
							QueryType.InteractiveUpdate6AddPostTags,
							ImmutableMap.of(
									SUBJECT_ID, getConverter().convertId(operation.postId()),
									"tagId", getConverter().convertIdForInsertion(tagId))
					)
			);
		}
		return list;
	}
}
