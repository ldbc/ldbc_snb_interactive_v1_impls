package com.ldbc.impls.workloads.ldbc.snb.postgres.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPersonResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedReplies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedRepliesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPostsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTagsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiatorsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormalsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircle;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircleResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTrianglesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCountsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteraction;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteractionResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummaryResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21Zombies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21ZombiesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialog;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialogResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinations;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinationsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopic;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopicResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPaths;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPathsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTagsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolutionResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPostersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePostersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsersResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopicsResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForumsResult;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresDb;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresPoolingDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.postgres.PostgresSingletonOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostgresBiDb extends PostgresDb<BiQueryStore> {

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		try {
			dbs = new PostgresPoolingDbConnectionState<>(properties, new PostgresBiQueryStore(properties.get("queryDir")));
		} catch (ClassNotFoundException | SQLException e) {
			throw new DbException(e);
		}
		registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, BiQuery1.class);
		registerOperationHandler(LdbcSnbBiQuery2TopTags.class, BiQuery2.class);
		registerOperationHandler(LdbcSnbBiQuery3TagEvolution.class, BiQuery3.class);
		registerOperationHandler(LdbcSnbBiQuery4PopularCountryTopics.class, BiQuery4.class);
		registerOperationHandler(LdbcSnbBiQuery5TopCountryPosters.class, BiQuery5.class);
		registerOperationHandler(LdbcSnbBiQuery6ActivePosters.class, BiQuery6.class);
		registerOperationHandler(LdbcSnbBiQuery7AuthoritativeUsers.class, BiQuery7.class);
		registerOperationHandler(LdbcSnbBiQuery8RelatedTopics.class, BiQuery8.class);
		registerOperationHandler(LdbcSnbBiQuery9RelatedForums.class, BiQuery9.class);
		registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, BiQuery10.class);
		registerOperationHandler(LdbcSnbBiQuery11UnrelatedReplies.class, BiQuery11.class);
		registerOperationHandler(LdbcSnbBiQuery12TrendingPosts.class, BiQuery12.class);
		registerOperationHandler(LdbcSnbBiQuery13PopularMonthlyTags.class, BiQuery13.class);
		registerOperationHandler(LdbcSnbBiQuery14TopThreadInitiators.class, BiQuery14.class);
		registerOperationHandler(LdbcSnbBiQuery15SocialNormals.class, BiQuery15.class);
		registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, BiQuery16.class);
		registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, BiQuery17.class);
		registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, BiQuery18.class);
		registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, BiQuery19.class);
		registerOperationHandler(LdbcSnbBiQuery20HighLevelTopics.class, BiQuery20.class);
		registerOperationHandler(LdbcSnbBiQuery21Zombies.class, BiQuery21.class);
		registerOperationHandler(LdbcSnbBiQuery22InternationalDialog.class, BiQuery22.class);
		registerOperationHandler(LdbcSnbBiQuery23HolidayDestinations.class, BiQuery23.class);
		registerOperationHandler(LdbcSnbBiQuery24MessagesByTopic.class, BiQuery24.class);
		registerOperationHandler(LdbcSnbBiQuery25WeightedPaths.class, BiQuery25.class);
	}
	
	public static class BiQuery1 extends PostgresListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery1PostingSummary operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(ResultSet result) throws SQLException {
			int messageYear = result.getInt(1);
			boolean isComment = result.getBoolean(2);
			int lengthCategory = result.getInt(3);
			long messageCount = result.getLong(4);
			int averageMessageLength = result.getInt(5);
			int sumMessageLength = result.getInt(6);
			double percentageOfMessages = result.getDouble(7);
			
			return new LdbcSnbBiQuery1PostingSummaryResult(messageYear, isComment, lengthCategory, messageCount, averageMessageLength, sumMessageLength, (float) percentageOfMessages);
		}
		
	}

	public static class BiQuery2 extends PostgresListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery2TopTags operation) {
			return state.getQueryStore().getQuery2(operation);
		}

		@Override
		public LdbcSnbBiQuery2TopTagsResult convertSingleResult(ResultSet result) throws SQLException {
			String countryName = result.getString(1);
			int messageMonth = result.getInt(2);
			String personGender = result.getString(3);
			int ageGroup = result.getInt(4);
			String tagName = result.getString(5);
			int messageCount = result.getInt(6);
			return new LdbcSnbBiQuery2TopTagsResult(countryName, messageMonth, personGender, ageGroup, tagName, messageCount);
		}
		
	}
	
	public static class BiQuery3 extends PostgresListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery3TagEvolution operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(ResultSet result) throws SQLException {
			String tagName = result.getString(1);
			int countMonth1 = result.getInt(2);
			int countMonth2 = result.getInt(3);
			int diff = result.getInt(4);
			return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countMonth1, countMonth2, diff);
		}
		
	}
	
	public static class BiQuery4 extends PostgresListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery4PopularCountryTopics operation) {
			return state.getQueryStore().getQuery4(operation);
		}

		@Override
		public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			long forumId = result.getLong(1);
			String forumTitle = result.getString(2);
			long forumCreationDate = stringTimestampToEpoch(result,3);
			long personId = result.getLong(4);
			int postCount = result.getInt(5);
			return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, forumTitle, forumCreationDate, personId, postCount);
		}
		
	}
	
	public static class BiQuery5 extends PostgresListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery5TopCountryPosters operation) {
			return state.getQueryStore().getQuery5(operation);
		}

		@Override
		public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String personFirstName = result.getString(2);
			String personLastName = result.getString(3);
			long personCreationDate = stringTimestampToEpoch(result, 4);
			int postCount = result.getInt(5);
			return new LdbcSnbBiQuery5TopCountryPostersResult(personId, personFirstName, personLastName, personCreationDate, postCount);
		}
		
	}
	
	public static class BiQuery6 extends PostgresListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int replyCount = result.getInt(2);
			int likeCount = result.getInt(3);
			int messageCount = result.getInt(4);
			int score = result.getInt(5);
			return new LdbcSnbBiQuery6ActivePostersResult(personId, replyCount, likeCount, messageCount, score);
		}
		
	}

	public static class BiQuery7 extends PostgresListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
			return state.getQueryStore().getQuery7(operation);
		}

		@Override
		public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int authorityScore = result.getInt(2);
			return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, authorityScore);
		}
		
	}
	
	public static class BiQuery8 extends PostgresListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery8RelatedTopics operation) {
			return state.getQueryStore().getQuery8(operation);
		}

		@Override
		public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			String relatedTagName = result.getString(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery8RelatedTopicsResult(relatedTagName, count);
		}
		
	}
	
	public static class BiQuery9 extends PostgresListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery9RelatedForums operation) {
			return state.getQueryStore().getQuery9(operation);
		}

		@Override
		public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(ResultSet result) throws SQLException {
			long forumId = result.getLong(1);
			int count1 = result.getInt(2);
			int count2 = result.getInt(3);
			return new LdbcSnbBiQuery9RelatedForumsResult(forumId, count1, count2);
		}
		
	}
	
	public static class BiQuery10 extends PostgresListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery10TagPerson operation) {
			return state.getQueryStore().getQuery10(operation);
		}

		@Override
		public LdbcSnbBiQuery10TagPersonResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int score = result.getInt(2);
			int friendsScore = result.getInt(3);
			return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
		}
		
	}

	public static class BiQuery11 extends PostgresListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery11UnrelatedReplies operation) {
			return state.getQueryStore().getQuery11(operation);
		}

		@Override
		public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String tagName = result.getString(2);
			int likeCount = result.getInt(3);
			int replyCount = result.getInt(4);
			return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, likeCount, replyCount);
		}

	}

	public static class BiQuery12 extends PostgresListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery12TrendingPosts operation) {
			return state.getQueryStore().getQuery12(operation);
		}

		@Override
		public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(ResultSet result) throws SQLException {
			long messageId = result.getLong(1);
			long messageCreationDate = stringTimestampToEpoch(result, 2);
			String creatorFirstName = result.getString(3);
			String creatorLastName = result.getString(4);
			int likeCount = result.getInt(5);
			return new LdbcSnbBiQuery12TrendingPostsResult(messageId, messageCreationDate, creatorFirstName, creatorLastName, likeCount);
		}
	}
	
	public static class BiQuery13 extends PostgresListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
			return state.getQueryStore().getQuery13(operation);
		}

		@Override
		public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(ResultSet result) throws SQLException {
			int year = result.getInt(1);
			int month = result.getInt(2);
			final Object array = result.getArray(3).getArray();

			final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> popularTags;
			if (array instanceof String[][]) {
				final String[][] nestedArray = (String[][]) (Object) array;
				popularTags = Arrays.stream(nestedArray).map(
						el -> new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(el[0], Integer.parseInt(el[1]))
				).collect(Collectors.toList());
			} else {
				popularTags = new ArrayList<>();
			}
			return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, popularTags);
		}
	}
	
	public static class BiQuery14 extends PostgresListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery14TopThreadInitiators operation) {
			return state.getQueryStore().getQuery14(operation);
		}

		@Override
		public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String personFirstName = result.getString(2);
			String personLastName = result.getString(3);
			int threadCount = result.getInt(4);
			int messageCount = result.getInt(5);
			return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, personFirstName, personLastName, threadCount, messageCount);
		}
	}
	
	public static class BiQuery15 extends PostgresListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery15SocialNormals operation) {
			return state.getQueryStore().getQuery15(operation);
		}

		@Override
		public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
		}
	}
	
	public static class BiQuery16 extends PostgresListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
			return state.getQueryStore().getQuery16(operation);
		}

		@Override
		public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String tagName = result.getString(2);
			int messageCount = result.getInt(3);
			return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tagName, messageCount);
		}
	}
	
	public static class BiQuery17 extends PostgresSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery17FriendshipTriangles operation) {
			return state.getQueryStore().getQuery17(operation);
		}

		@Override
		public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(ResultSet result) throws SQLException {
			int count = result.getInt(1);
			return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
		}
	}
	
	public static class BiQuery18 extends PostgresListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery18PersonPostCounts operation) {
			return state.getQueryStore().getQuery18(operation);
		}

		@Override
		public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(ResultSet result) throws SQLException {
			int messageCount = result.getInt(1);
			int personCount = result.getInt(2);
			return new LdbcSnbBiQuery18PersonPostCountsResult(messageCount, personCount);
		}
	}
	
	
	public static class BiQuery19 extends PostgresListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery19StrangerInteraction operation) {
			return state.getQueryStore().getQuery19(operation);
		}

		@Override
		public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int strangerCount = result.getInt(2);
			int interactionCount = result.getInt(3);
			return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, interactionCount);
		}
	}
	
	public static class BiQuery20 extends PostgresListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery20HighLevelTopics operation) {
			return state.getQueryStore().getQuery20(operation);
		}

		@Override
		public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			String tagClassName = result.getString(1);
			int messageCount = result.getInt(2);
			return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClassName, messageCount);
		}
	}
	
	public static class BiQuery21 extends PostgresListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery21Zombies operation) {
			return state.getQueryStore().getQuery21(operation);
		}

		@Override
		public LdbcSnbBiQuery21ZombiesResult convertSingleResult(ResultSet result) throws SQLException {
			long zombieId = result.getLong(1);
			int zombieLikeCount = result.getInt(2);
			int totalLikeCount = result.getInt(3);
			int zombieScore = result.getInt(4);
			return new LdbcSnbBiQuery21ZombiesResult(zombieId, zombieLikeCount, totalLikeCount, zombieScore);
		}
	}
	
	public static class BiQuery22 extends PostgresListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery22InternationalDialog operation) {
			return state.getQueryStore().getQuery22(operation);
		}

		@Override
		public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(ResultSet result) throws SQLException {
			long person1Id = result.getLong(1);
			long person2Id = result.getLong(2);
			String city1Name = result.getString(3);
			int  score = result.getInt(4);
			return new LdbcSnbBiQuery22InternationalDialogResult(person1Id, person2Id, city1Name, score);
		}
	}
	
	public static class BiQuery23 extends PostgresListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery23HolidayDestinations operation) {
			return state.getQueryStore().getQuery23(operation);
		}

		@Override
		public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(ResultSet result) throws SQLException {
			int messageCount = result.getInt(1);
			String destinationName = result.getString(2);
			int month = result.getInt(3);
			return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, destinationName, month);
		}
	}
	
	
	public static class BiQuery24 extends PostgresListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery24MessagesByTopic operation) {
			return state.getQueryStore().getQuery24(operation);
		}

		@Override
		public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(ResultSet result) throws SQLException {
			int messageCount = result.getInt(1);
			int likeCount = result.getInt(2);
			int year = result.getInt(3);
			int month = result.getInt(4);
			String continentName = result.getString(5);
			return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continentName);
		}
	}

	public static class BiQuery25 extends PostgresListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult, BiQueryStore> {

		@Override
		public String getQueryString(PostgresDbConnectionState<BiQueryStore> state, LdbcSnbBiQuery25WeightedPaths operation) {
			return state.getQueryStore().getQuery25(operation);
		}

		@Override
		public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(ResultSet result) throws SQLException {
			final Long[] array = (Long[]) result.getArray(1).getArray();
			final List<Long> personIds = Arrays.asList(array);
			double weight = result.getDouble(2);
			return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
		}
	}

}
