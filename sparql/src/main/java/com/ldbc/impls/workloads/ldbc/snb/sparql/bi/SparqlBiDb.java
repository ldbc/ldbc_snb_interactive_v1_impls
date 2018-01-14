package com.ldbc.impls.workloads.ldbc.snb.sparql.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTrianglesResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummaryResult;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolutionResult;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDriverConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlPoolingDbConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlSingletonOperationHandler;
import org.openrdf.model.Literal;
import org.openrdf.model.impl.BooleanLiteralImpl;
import org.openrdf.model.impl.IntegerLiteralImpl;
import org.openrdf.query.BindingSet;

import java.util.Map;

public class SparqlBiDb extends SparqlDb {

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		dbs = new SparqlPoolingDbConnectionStore(properties, new SparqlBiQueryStore(properties.get("queryDir")));

		registerOperationHandler(LdbcSnbBiQuery1PostingSummary.class, BiQuery1.class);
//		registerOperationHandler(LdbcSnbBiQuery2TopTags.class, BiQuery2.class);
		registerOperationHandler(LdbcSnbBiQuery3TagEvolution.class, BiQuery3.class);
//		registerOperationHandler(LdbcSnbBiQuery4PopularCountryTopics.class, BiQuery4.class);
//		registerOperationHandler(LdbcSnbBiQuery5TopCountryPosters.class, BiQuery5.class);
//		registerOperationHandler(LdbcSnbBiQuery6ActivePosters.class, BiQuery6.class);
//		registerOperationHandler(LdbcSnbBiQuery7AuthoritativeUsers.class, BiQuery7.class);
//		registerOperationHandler(LdbcSnbBiQuery8RelatedTopics.class, BiQuery8.class);
//		registerOperationHandler(LdbcSnbBiQuery9RelatedForums.class, BiQuery9.class);
//		registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, BiQuery10.class);
//		registerOperationHandler(LdbcSnbBiQuery11UnrelatedReplies.class, BiQuery11.class);
//		registerOperationHandler(LdbcSnbBiQuery12TrendingPosts.class, BiQuery12.class);
//		registerOperationHandler(LdbcSnbBiQuery13PopularMonthlyTags.class, BiQuery13.class);
//		registerOperationHandler(LdbcSnbBiQuery14TopThreadInitiators.class, BiQuery14.class);
//		registerOperationHandler(LdbcSnbBiQuery15SocialNormals.class, BiQuery15.class);
//		registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, BiQuery16.class);
		registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, BiQuery17.class);
//		registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, BiQuery18.class);
//		registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, BiQuery19.class);
//		registerOperationHandler(LdbcSnbBiQuery20HighLevelTopics.class, BiQuery20.class);
//		registerOperationHandler(LdbcSnbBiQuery21Zombies.class, BiQuery21.class);
//		registerOperationHandler(LdbcSnbBiQuery22InternationalDialog.class, BiQuery22.class);
//		registerOperationHandler(LdbcSnbBiQuery23HolidayDestinations.class, BiQuery23.class);
//		registerOperationHandler(LdbcSnbBiQuery24MessagesByTopic.class, BiQuery24.class);
//		registerOperationHandler(LdbcSnbBiQuery25WeightedPaths.class, BiQuery25.class);
	}

	public static class BiQuery1 extends SparqlListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery1PostingSummary operation) {
			return state.getQueryStore().getQuery1(operation);
		}

		@Override
		public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(BindingSet bs) {
		    int year          = ((IntegerLiteralImpl) bs.getBinding("messageYear"         )).intValue();
			boolean isComment = ((BooleanLiteralImpl) bs.getBinding("isComment"           )).booleanValue();
			int size          = ((IntegerLiteralImpl) bs.getBinding("size"                )).intValue();
			long count        = ((IntegerLiteralImpl) bs.getBinding("messageCount"        )).longValue();
			int avgLen        = ((IntegerLiteralImpl) bs.getBinding("averageMessageLength")).intValue();
			int total         = ((IntegerLiteralImpl) bs.getBinding("totalMessageCount"   )).intValue();
			double pct        = ((IntegerLiteralImpl) bs.getBinding("percentageOfMessages")).doubleValue();

			return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, (float) pct);
		}

	}

//	public static class BiQuery2 extends SparqlListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery2TopTags operation) {
//			return state.getQueryStore().getQuery2(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery2TopTagsResult convertSingleResult(BindingSet bs) {
//			String country = bs.get(0).asString();
//			int month = bs.get(1).asInt();
//			String gender = bs.get(2).asString();
//			int ageGroup = bs.get(3).asInt();
//			String tag = bs.get(4).asString();
//			int count = bs.get(5).asInt();
//			return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
//		}
//
//	}

	public static class BiQuery3 extends SparqlListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery3TagEvolution operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(BindingSet bs) {
			String tagName =                       bs.getBinding("tagName").getValue().stringValue();
			int countA     = ((IntegerLiteralImpl) bs.getBinding("countA")).intValue();
			int countB     = ((IntegerLiteralImpl) bs.getBinding("countB")).intValue();
			int diffCount  = ((IntegerLiteralImpl) bs.getBinding("diff"  )).intValue();
			return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
		}

	}

//	public static class BiQuery4 extends SparqlListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery4PopularCountryTopics operation) {
//			return state.getQueryStore().getQuery4(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(BindingSet bs) throws ParseException {
//			long forumId = ((IntegerLiteralImpl) bs.getBinding("forumId").getValue()).longValue();
//			String title = bs.get(1).asString();
//			long creationDate = new SparqlConverter().convertLongTimestampToEpoch(bs.get(2).asLong());
//			long moderator = bs.get(3).asLong();
//			int count = bs.get(4).asInt();
//			return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
//		}
//
//	}
//
//	public static class BiQuery5 extends SparqlListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery5TopCountryPosters operation) {
//			return state.getQueryStore().getQuery5(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(BindingSet bs) throws ParseException {
//			long personId = bs.get(0).asLong();
//			String firstName = bs.get(1).asString();
//			String lastName = bs.get(2).asString();
//			long creationDate = new SparqlConverter().convertLongTimestampToEpoch(bs.get(3).asLong());
//			int count = bs.get(4).asInt();
//			return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
//		}
//
//	}
//
//	public static class BiQuery6 extends SparqlListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
//			return state.getQueryStore().getQuery6(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			int postCount = bs.get(1).asInt();
//			int replyCount = bs.get(2).asInt();
//			int likeCount = bs.get(3).asInt();
//			int score = bs.get(4).asInt();
//			return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
//		}
//
//	}
//
//	public static class BiQuery7 extends SparqlListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
//			return state.getQueryStore().getQuery7(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			int score = bs.get(1).asInt();
//			return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
//		}
//
//	}
//
//	public static class BiQuery8 extends SparqlListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery8RelatedTopics operation) {
//			return state.getQueryStore().getQuery8(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(BindingSet bs) {
//			String tag = bs.get(0).asString();
//			int count = bs.get(1).asInt();
//			return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
//		}
//
//	}
//
//	public static class BiQuery9 extends SparqlListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery9RelatedForums operation) {
//			return state.getQueryStore().getQuery9(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(BindingSet bs) {
//			long forumId = bs.get(0).asLong();
//			int sumA = bs.get(1).asInt();
//			int sumB = bs.get(2).asInt();
//			return new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB);
//		}
//
//	}
//
//	public static class BiQuery10 extends SparqlListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery10TagPerson operation) {
//			return state.getQueryStore().getQuery10(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery10TagPersonResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			int score = bs.get(1).asInt();
//			int friendsScore = bs.get(2).asInt();
//			return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
//		}
//
//	}
//
//	public static class BiQuery11 extends SparqlListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery11UnrelatedReplies operation) {
//			return state.getQueryStore().getQuery11(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			String tagName = bs.get(1).asString();
//			int countLikes = bs.get(2).asInt();
//			int countReplies = bs.get(3).asInt();
//			return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, countLikes, countReplies);
//		}
//
//	}
//
//	public static class BiQuery12 extends SparqlListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery12TrendingPosts operation) {
//			return state.getQueryStore().getQuery12(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(BindingSet bs) throws ParseException {
//			long personId = bs.get(0).asLong();
//			long creationDate = new SparqlConverter().convertLongTimestampToEpoch(bs.get(1).asLong());
//			String firstName = bs.get(2).asString();
//			String lastName = bs.get(3).asString();
//			int likeCount = bs.get(4).asInt();
//			return new LdbcSnbBiQuery12TrendingPostsResult(personId, creationDate, firstName, lastName, likeCount);
//		}
//	}
//
//	public static class BiQuery13 extends SparqlListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
//			return state.getQueryStore().getQuery13(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(BindingSet bs) {
//			int year = bs.get(0).asInt();
//			int month = bs.get(1).asInt();
//			final List<List<Object>> tagPopularitiesRaw = bs.get(2).asList(Values.ofList());
//
//			final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> tagPopularities = new ArrayList<>();
//			for (List<Object> tagPopularityRaw : tagPopularitiesRaw) {
//				final String tag = (String) tagPopularityRaw.get(0);
//				final int popularity = Ints.saturatedCast((long) tagPopularityRaw.get(1));
//				tagPopularities.add(new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag, popularity));
//			}
//
//			return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tagPopularities);
//		}
//	}
//
//	public static class BiQuery14 extends SparqlListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery14TopThreadInitiators operation) {
//			return state.getQueryStore().getQuery14(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			String firstName = bs.get(1).asString();
//			String lastName = bs.get(2).asString();
//			int count = bs.get(3).asInt();
//			int threadCount = bs.get(4).asInt();
//			return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
//		}
//	}
//
//	public static class BiQuery15 extends SparqlListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery15SocialNormals operation) {
//			return state.getQueryStore().getQuery15(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			int count = bs.get(1).asInt();
//			return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
//		}
//	}
//
//	public static class BiQuery16 extends SparqlListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
//			return state.getQueryStore().getQuery16(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			String tag = bs.get(1).asString();
//			int count = bs.get(2).asInt();
//			return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
//		}
//	}

	public static class BiQuery17 extends SparqlSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery17FriendshipTriangles operation) {
			return state.getQueryStore().getQuery17(operation);
		}

		@Override
		public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(BindingSet bs) {
			int count = ((Literal) bs.getBinding("count").getValue()).intValue();
			return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
		}
	}

//	public static class BiQuery18 extends SparqlListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery18PersonPostCounts operation) {
//			return state.getQueryStore().getQuery18(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(BindingSet bs) {
//			int postCount = bs.get(0).asInt();
//			int count = bs.get(1).asInt();
//			return new LdbcSnbBiQuery18PersonPostCountsResult(postCount, count);
//		}
//	}
//
//
//	public static class BiQuery19 extends SparqlListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery19StrangerInteraction operation) {
//			return state.getQueryStore().getQuery19(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			int strangerCount = bs.get(1).asInt();
//			int count = bs.get(2).asInt();
//			return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
//		}
//	}
//
//	public static class BiQuery20 extends SparqlListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery20HighLevelTopics operation) {
//			return state.getQueryStore().getQuery20(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(BindingSet bs) {
//			String tagClass = bs.get(0).asString();
//			int count = bs.get(1).asInt();
//			return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
//		}
//	}
//
//	public static class BiQuery21 extends SparqlListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery21Zombies operation) {
//			return state.getQueryStore().getQuery21(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery21ZombiesResult convertSingleResult(BindingSet bs) {
//			long personId = bs.get(0).asLong();
//			int zombieCount = bs.get(1).asInt();
//			int realCount = bs.get(2).asInt();
//			double score = bs.get(3).asDouble();
//			return new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
//		}
//	}
//
//	public static class BiQuery22 extends SparqlListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery22InternationalDialog operation) {
//			return state.getQueryStore().getQuery22(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(BindingSet bs) {
//			long personIdA = bs.get(0).asLong();
//			long personIdB = bs.get(1).asLong();
//			String city1Name = bs.get(2).asString();
//			int  score = bs.get(3).asInt();
//			return new LdbcSnbBiQuery22InternationalDialogResult(personIdA, personIdB, city1Name, score);
//		}
//	}
//
//	public static class BiQuery23 extends SparqlListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery23HolidayDestinations operation) {
//			return state.getQueryStore().getQuery23(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(BindingSet bs) {
//			int messageCount = bs.get(0).asInt();
//			String country = bs.get(1).asString();
//			int month = bs.get(2).asInt();
//			return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, country, month);
//		}
//	}
//
//
//	public static class BiQuery24 extends SparqlListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery24MessagesByTopic operation) {
//			return state.getQueryStore().getQuery24(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(BindingSet bs) {
//			int messageCount = bs.get(0).asInt();
//			int likeCount = bs.get(1).asInt();
//			int year = bs.get(2).asInt();
//			int month = bs.get(3).asInt();
//			String continent = bs.get(4).asString();
//			return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
//		}
//	}
//
//	public static class BiQuery25 extends SparqlListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult, SparqlBiQueryStore> {
//
//		@Override
//		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery25WeightedPaths operation) {
//			return state.getQueryStore().getQuery25(operation);
//		}
//
//		@Override
//		public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(BindingSet bs) {
//			List<Long> personIds = bs.get(0).asList(Values.ofLong());
//			return new LdbcSnbBiQuery25WeightedPathsResult(personIds);
//		}
//	}
}
