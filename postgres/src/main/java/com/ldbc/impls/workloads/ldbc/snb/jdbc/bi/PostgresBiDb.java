package com.ldbc.impls.workloads.ldbc.snb.jdbc.bi;

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
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity;
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
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDb;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcPoolingDbConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcSingletonOperationHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostgresBiDb extends JdbcDb<BiQueryStore> {

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		try {
			dbs = new JdbcPoolingDbConnectionStore<>(properties, new PostgresQueryStore(properties.get("queryDir")));
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
	
	public static class BiQuery1 extends JdbcListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery1PostingSummary operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(ResultSet result) throws SQLException {
			int year = result.getInt(1);
			boolean isComment = result.getBoolean(2);
			int size = result.getInt(3);
			long count = result.getLong(4);
			int avgLen = result.getInt(5);
			int total = result.getInt(6);
			double pct = result.getDouble(7);
			
			return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, (float) pct);
		}
		
	}

	public static class BiQuery2 extends JdbcListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery2TopTags operation) {
			return state.getQueryStore().getQuery2(operation);
		}

		@Override
		public LdbcSnbBiQuery2TopTagsResult convertSingleResult(ResultSet result) throws SQLException {
			String country = result.getString(1);
			int month = result.getInt(2);
			String gender = result.getString(3);
			int ageGroup = result.getInt(4);
			String tag = result.getString(5);
			int count = result.getInt(6);
			return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
		}
		
	}
	
	public static class BiQuery3 extends JdbcListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery3TagEvolution operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(ResultSet result) throws SQLException {
			String tagName = result.getString(1);
			int countA = result.getInt(2);
			int countB = result.getInt(3);
			int diffCount = result.getInt(4);
			return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
		}
		
	}
	
	public static class BiQuery4 extends JdbcListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery4PopularCountryTopics operation) {
			return state.getQueryStore().getQuery4(operation);
		}

		@Override
		public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			long forumId = result.getLong(1);
			String title = result.getString(2);
			long creationDate = timestampToTimestamp(result,3);
			long moderator = result.getLong(4);
			int count = result.getInt(5);
			return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
		}
		
	}
	
	public static class BiQuery5 extends JdbcListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery5TopCountryPosters operation) {
			return state.getQueryStore().getQuery5(operation);
		}

		@Override
		public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String firstName = result.getString(2);
			String lastName = result.getString(3);
			long creationDate = timestampToTimestamp(result, 4);
			int count = result.getInt(5);
			return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
		}
		
	}
	
	public static class BiQuery6 extends JdbcListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int postCount = result.getInt(2);
			int replyCount = result.getInt(3);
			int likeCount = result.getInt(4);
			int score = result.getInt(5);
			return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
		}
		
	}

	public static class BiQuery7 extends JdbcListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
			return state.getQueryStore().getQuery7(operation);
		}

		@Override
		public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int score = result.getInt(2);
			return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
		}
		
	}
	
	public static class BiQuery8 extends JdbcListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery8RelatedTopics operation) {
			return state.getQueryStore().getQuery8(operation);
		}

		@Override
		public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			String tag = result.getString(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
		}
		
	}
	
	public static class BiQuery9 extends JdbcListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery9RelatedForums operation) {
			return state.getQueryStore().getQuery9(operation);
		}

		@Override
		public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(ResultSet result) throws SQLException {
			long forumId = result.getLong(1);
			int sumA = result.getInt(2);
			int sumB = result.getInt(3);
			return new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB);
		}
		
	}
	
	public static class BiQuery10 extends JdbcListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery10TagPerson operation) {
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

	public static class BiQuery11 extends JdbcListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery11UnrelatedReplies operation) {
			return state.getQueryStore().getQuery11(operation);
		}

		@Override
		public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String tagName = result.getString(2);
			int countLikes = result.getInt(3);
			int countReplies = result.getInt(4);
			return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, countLikes, countReplies);
		}

	}

	public static class BiQuery12 extends JdbcListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery12TrendingPosts operation) {
			return state.getQueryStore().getQuery12(operation);
		}

		@Override
		public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String firstName = result.getString(2);
			String lastName = result.getString(3);
			long creationDate = timestampToTimestamp(result, 4);
			int likeCount = result.getInt(5);
			return new LdbcSnbBiQuery12TrendingPostsResult(personId, creationDate, firstName, lastName, likeCount);
		}
	}
	
	public static class BiQuery13 extends JdbcListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
			return state.getQueryStore().getQuery13(operation);
		}

		@Override
		public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(ResultSet result) throws SQLException {
			int year = result.getInt(1);
			int month = result.getInt(2);
			String tag = result.getString(3);
			int count = result.getInt(4);
			//return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tag, count);
			return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, new ArrayList<TagPopularity>());
		}
	}
	
	public static class BiQuery14 extends JdbcListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery14TopThreadInitiators operation) {
			return state.getQueryStore().getQuery14(operation);
		}

		@Override
		public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String firstName = result.getString(2);
			String lastName = result.getString(3);
			int count = result.getInt(4);
			int threadCount = result.getInt(5);
			return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
		}
	}
	
	public static class BiQuery15 extends JdbcListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery15SocialNormals operation) {
			return state.getQueryStore().getQuery15(operation);
		}

		@Override
		public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
		}
	}
	
	public static class BiQuery16 extends JdbcListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
			return state.getQueryStore().getQuery16(operation);
		}

		@Override
		public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			String tag = result.getString(2);
			int count = result.getInt(3);
			return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
		}
	}
	
	public static class BiQuery17 extends JdbcSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery17FriendshipTriangles operation) {
			return state.getQueryStore().getQuery17(operation);
		}

		@Override
		public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(ResultSet result) throws SQLException {
			int count = result.getInt(1);
			return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
		}
	}
	
	public static class BiQuery18 extends JdbcListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery18PersonPostCounts operation) {
			return state.getQueryStore().getQuery18(operation);
		}

		@Override
		public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(ResultSet result) throws SQLException {
			int postCount = result.getInt(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery18PersonPostCountsResult(postCount, count);
		}
	}
	
	
	public static class BiQuery19 extends JdbcListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery19StrangerInteraction operation) {
			return state.getQueryStore().getQuery19(operation);
		}

		@Override
		public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int strangerCount = result.getInt(2);
			int count = result.getInt(3);
			return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
		}
	}
	
	public static class BiQuery20 extends JdbcListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery20HighLevelTopics operation) {
			return state.getQueryStore().getQuery20(operation);
		}

		@Override
		public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(ResultSet result) throws SQLException {
			String tagClass = result.getString(1);
			int count = result.getInt(2);
			return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
		}
	}
	
	public static class BiQuery21 extends JdbcListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery21Zombies operation) {
			return state.getQueryStore().getQuery21(operation);
		}

		@Override
		public LdbcSnbBiQuery21ZombiesResult convertSingleResult(ResultSet result) throws SQLException {
			long personId = result.getLong(1);
			int zombieCount = result.getInt(2);
			int realCount = result.getInt(3);
			int score = result.getInt(4);
			return new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
		}
	}
	
	public static class BiQuery22 extends JdbcListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery22InternationalDialog operation) {
			return state.getQueryStore().getQuery22(operation);
		}

		@Override
		public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(ResultSet result) throws SQLException {
			long personIdA = result.getLong(0);
			long personIdB = result.getLong(1);
			String city1Name = result.getString(2);
			int  score = result.getInt(3);
			return new LdbcSnbBiQuery22InternationalDialogResult(personIdA, personIdB, city1Name, score);
		}
	}
	
	public static class BiQuery23 extends JdbcListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery23HolidayDestinations operation) {
			return state.getQueryStore().getQuery23(operation);
		}

		@Override
		public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(ResultSet result) throws SQLException {
			int messageCount = result.getInt(1);
			String country = result.getString(2);
			int month = result.getInt(3);
			return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, country, month);
		}
	}
	
	
	public static class BiQuery24 extends JdbcListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery24MessagesByTopic operation) {
			return state.getQueryStore().getQuery24(operation);
		}

		@Override
		public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(ResultSet result) throws SQLException {
			int year = result.getInt(1);
			int month = result.getInt(2);
			String continent = result.getString(3);
			int messageCount = result.getInt(4);
			int likeCount = result.getInt(5);
			return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
		}
	}

	public static class BiQuery25 extends JdbcListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult, BiQueryStore> {

		@Override
		public String getQueryString(JdbcDbConnectionStore<BiQueryStore> state, LdbcSnbBiQuery25WeightedPaths operation) {
			return state.getQueryStore().getQuery25(operation);
		}

		@Override
		public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(ResultSet result) throws SQLException {
			final Long[] array = (Long[]) result.getArray(1).getArray();
			final List<Long> personIds = Arrays.asList(array);
			return new LdbcSnbBiQuery25WeightedPathsResult(personIds);
		}
	}
}
