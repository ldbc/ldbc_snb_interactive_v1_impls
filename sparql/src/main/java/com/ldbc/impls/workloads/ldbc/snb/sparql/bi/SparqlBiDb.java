package com.ldbc.impls.workloads.ldbc.snb.sparql.bi;

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
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDriverConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlPoolingDbConnectionStore;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlSingletonOperationHandler;
import org.openrdf.model.impl.BooleanLiteralImpl;
import org.openrdf.model.impl.IntegerLiteralImpl;
import org.openrdf.query.BindingSet;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SparqlBiDb extends SparqlDb {

	@Override
	protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
		dbs = new SparqlPoolingDbConnectionStore(properties, new SparqlBiQueryStore(properties.get("queryDir")));

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
//		registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, BiQuery16.class);
		registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, BiQuery17.class);
		registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, BiQuery18.class);
		registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, BiQuery19.class);
		registerOperationHandler(LdbcSnbBiQuery20HighLevelTopics.class, BiQuery20.class);
//		registerOperationHandler(LdbcSnbBiQuery21Zombies.class, BiQuery21.class);
//		registerOperationHandler(LdbcSnbBiQuery22InternationalDialog.class, BiQuery22.class);
		registerOperationHandler(LdbcSnbBiQuery23HolidayDestinations.class, BiQuery23.class);
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
			int messageYear             = convertInteger(bs, "messageYear"         );
			boolean isComment           = ((BooleanLiteralImpl) bs.getBinding("isComment"           )).booleanValue();
			int lengthCategory          = convertInteger(bs, "lengthCategory"      );
			long messageCount           = convertLong   (bs, "messageCount"        );
			int averageMessageLength    = convertInteger(bs, "averageMessageLength");
			int sumMessageLength        = convertInteger(bs, "totalMessageCount"   );
			double percentageOfMessages = convertDouble (bs, "percentageOfMessages");

			return new LdbcSnbBiQuery1PostingSummaryResult(messageYear, isComment, lengthCategory, messageCount, averageMessageLength, sumMessageLength, (float) percentageOfMessages);
		}

	}

	public static class BiQuery2 extends SparqlListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery2TopTags operation) {
			return state.getQueryStore().getQuery2(operation);
		}

		@Override
		public LdbcSnbBiQuery2TopTagsResult convertSingleResult(BindingSet bs) {
			String countryName  = convertString (bs, "countryName" );
			int messageMonth    = convertInteger(bs, "messageMonth");
			String personGender = convertString (bs, "personGender");
			int ageGroup        = convertInteger(bs, "ageGroup"    );
			String tagName      = convertString (bs, "tagName"     );
			int messageCount    = convertInteger(bs, "messageCount");
			return new LdbcSnbBiQuery2TopTagsResult(countryName, messageMonth, personGender, ageGroup, tagName, messageCount);
		}

	}

	public static class BiQuery3 extends SparqlListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery3TagEvolution operation) {
			return state.getQueryStore().getQuery3(operation);
		}

		@Override
		public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(BindingSet bs) {
			String tagName = convertString (bs, "tagName");
			int countA     = convertInteger(bs, "countA");
			int countB     = convertInteger(bs, "countB");
			int diffCount  = convertInteger(bs, "diff"  );
			return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
		}

	}

	public static class BiQuery4 extends SparqlListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery4PopularCountryTopics operation) {
			return state.getQueryStore().getQuery4(operation);
		}

		@Override
		public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(BindingSet bs) throws ParseException {
			long forumId           = convertLong   (bs, "forumId"          );
			String forumTitle      = convertString (bs, "forumTitle"       );
			long forumCreationDate = convertDate   (bs, "forumCreationDate");
			long personId          = convertLong   (bs, "personId"         );
			int postCount          = convertInteger(bs, "postCount"        );
			return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, forumTitle, forumCreationDate, personId, postCount);
		}

	}

	public static class BiQuery5 extends SparqlListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery5TopCountryPosters operation) {
			return state.getQueryStore().getQuery5(operation);
		}

		@Override
		public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(BindingSet bs) throws ParseException {
			long personId           = convertLong   (bs, "personId"          );
			String personFirstName  = convertString (bs, "personFirstName"   );
			String personLastName   = convertString (bs, "personLastName"    );
			long personCreationDate = convertDate   (bs, "personCreationDate");
			int postCount           = convertInteger(bs, "postCount"         );
			return new LdbcSnbBiQuery5TopCountryPostersResult(personId, personFirstName, personLastName, personCreationDate, postCount);
		}

	}

	public static class BiQuery6 extends SparqlListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(BindingSet bs) {
			long personId    = convertLong   (bs, "personId"    );
			int replyCount   = convertInteger(bs, "replyCount"  );
			int likeCount    = convertInteger(bs, "likeCount"   );
			int messageCount = convertInteger(bs, "messageCount");
			int score        = convertInteger(bs, "score"       );
			return new LdbcSnbBiQuery6ActivePostersResult(personId, replyCount, likeCount, messageCount, score);
		}

	}

	public static class BiQuery7 extends SparqlListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
			return state.getQueryStore().getQuery7(operation);
		}

		@Override
		public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(BindingSet bs) {
			long personId      = convertLong   (bs, "personId"      );
			int authorityScore = convertInteger(bs, "authorityScore");
			return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, authorityScore);
		}

	}

	public static class BiQuery8 extends SparqlListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery8RelatedTopics operation) {
			return state.getQueryStore().getQuery8(operation);
		}

		@Override
		public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(BindingSet bs) {
			String relatedTagName = convertString (bs, "relatedTagName");
			int count             = convertInteger(bs, "count");
			return new LdbcSnbBiQuery8RelatedTopicsResult(relatedTagName, count);
		}

	}

	public static class BiQuery9 extends SparqlListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery9RelatedForums operation) {
			return state.getQueryStore().getQuery9(operation);
		}

		@Override
		public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(BindingSet bs) {
			long forumId = convertLong   (bs, "forumId");
			int count1   = convertInteger(bs, "count1" );
			int count2   = convertInteger(bs, "count2" );
			return new LdbcSnbBiQuery9RelatedForumsResult(forumId, count1, count2);
		}

	}

	public static class BiQuery10 extends SparqlListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery10TagPerson operation) {
			return state.getQueryStore().getQuery10(operation);
		}

		@Override
		public LdbcSnbBiQuery10TagPersonResult convertSingleResult(BindingSet bs) {
			long personId =    convertLong   (bs, "personId"    );
			int score =        convertInteger(bs, "score"       );
			int friendsScore = convertInteger(bs, "friendsScore");
			return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
		}

	}

	public static class BiQuery11 extends SparqlListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery11UnrelatedReplies operation) {
			return state.getQueryStore().getQuery11(operation);
		}

		@Override
		public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(BindingSet bs) {
			long personId  = convertLong   (bs, "personId"  );
			String tagName = convertString (bs, "tagName"   );
			int likeCount  = convertInteger(bs, "likeCount" );
			int replyCount = convertInteger(bs, "replyCount");
			return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, likeCount, replyCount);
		}

	}

	public static class BiQuery12 extends SparqlListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery12TrendingPosts operation) {
			return state.getQueryStore().getQuery12(operation);
		}

		@Override
		public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(BindingSet bs) throws ParseException {
			long messageId           = convertLong   (bs, "messageId"          );
			long messageCreationDate = convertDate   (bs, "messageCreationDate");
			String creatorFirstName  = convertString (bs, "creatorFirstName"   );
			String creatorLastName   = convertString (bs, "creatorLastName"    );
			int likeCount            = convertInteger(bs, "likeCount"          );
			return new LdbcSnbBiQuery12TrendingPostsResult(messageId, messageCreationDate, creatorFirstName, creatorLastName, likeCount);
		}
	}

	public static class BiQuery13 extends SparqlListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
			return state.getQueryStore().getQuery13(operation);
		}

		@Override
		public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(BindingSet bs) {
			int year  = convertInteger(bs, "year" );
			int month = convertInteger(bs, "month");
			final String[] popularTagsArray = bs.getBinding("popularTags").getValue().stringValue().split("\\|");
			final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> popularTags = Arrays.stream(popularTagsArray).map(popularTag -> {
				final String[] tag = popularTag.split("#");
				return new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag[0], Integer.parseInt(tag[1]));
			}).collect(Collectors.toList());

			return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, popularTags);
		}
	}

	public static class BiQuery14 extends SparqlListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery14TopThreadInitiators operation) {
			return state.getQueryStore().getQuery14(operation);
		}

		@Override
		public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(BindingSet bs) {
			long personId          = convertLong   (bs, "personId"       );
			String personFirstName = convertString (bs, "personFirstName");
			String personLastName  = convertString (bs, "personLastName" );
			int threadCount        = convertInteger(bs, "threadCount"    );
			int messageCount       = convertInteger(bs, "messageCount"   );
			return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, personFirstName, personLastName, threadCount, messageCount);
		}
	}

	public static class BiQuery15 extends SparqlListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery15SocialNormals operation) {
			return state.getQueryStore().getQuery15(operation);
		}

		@Override
		public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(BindingSet bs) {
			long personId = convertLong   (bs, "personId");
			int count     = convertInteger(bs, "count"   );
			return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
		}
	}

	public static class BiQuery16 extends SparqlListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
			return state.getQueryStore().getQuery16(operation);
		}

		@Override
		public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(BindingSet bs) {
			long personId    = convertLong   (bs, "personId");
			String tagName   = convertString (bs, "tagName" );
			int messageCount = convertInteger(bs, "messageCount");
			return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tagName, messageCount);
		}
	}

	public static class BiQuery17 extends SparqlSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery17FriendshipTriangles operation) {
			return state.getQueryStore().getQuery17(operation);
		}

		@Override
		public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(BindingSet bs) {
			int count = convertInteger(bs, "count");
			return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
		}
	}

	public static class BiQuery18 extends SparqlListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery18PersonPostCounts operation) {
			return state.getQueryStore().getQuery18(operation);
		}

		@Override
		public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(BindingSet bs) {
			int messageCount = convertInteger(bs, "messageCount");
			int personCount  = convertInteger(bs, "personCount" );
			return new LdbcSnbBiQuery18PersonPostCountsResult(messageCount, personCount);
		}
	}

	public static class BiQuery19 extends SparqlListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery19StrangerInteraction operation) {
			return state.getQueryStore().getQuery19(operation);
		}

		@Override
		public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(BindingSet bs) {
			long personId        = convertLong   (bs, "personId"        );
			int strangerCount    = convertInteger(bs, "strangerCount"   );
			int interactionCount = convertInteger(bs, "interactionCount");
			return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, interactionCount);
		}
	}

	public static class BiQuery20 extends SparqlListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery20HighLevelTopics operation) {
			return state.getQueryStore().getQuery20(operation);
		}

		@Override
		public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(BindingSet bs) {
			String tagClassName = convertString (bs, "tagClassName");
			int messageCount    = convertInteger(bs, "messageCount");
			return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClassName, messageCount);
		}
	}

	public static class BiQuery21 extends SparqlListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery21Zombies operation) {
			return state.getQueryStore().getQuery21(operation);
		}

		@Override
		public LdbcSnbBiQuery21ZombiesResult convertSingleResult(BindingSet bs) {
			long zombieId       = convertLong   (bs, "zombieId"       );
			int zombieLikeCount = convertInteger(bs, "zombieLikeCount");
			int totalLikeCount  = convertInteger(bs, "totalLikeCount" );
			double zombieScore  = convertDouble (bs, "zombieScore"    );
			return new LdbcSnbBiQuery21ZombiesResult(zombieId, zombieLikeCount, totalLikeCount, zombieScore);
		}
	}

	public static class BiQuery22 extends SparqlListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery22InternationalDialog operation) {
			return state.getQueryStore().getQuery22(operation);
		}

		@Override
		public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(BindingSet bs) {
			long person1Id   = convertLong   (bs, "person1Id");
			long person2Id   = convertLong   (bs, "person2Id");
			String city1Name = convertString (bs, "city1Name");
			int score        = convertInteger(bs, "score"    );
			return new LdbcSnbBiQuery22InternationalDialogResult(person1Id, person2Id, city1Name, score);
		}
	}

	public static class BiQuery23 extends SparqlListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery23HolidayDestinations operation) {
			return state.getQueryStore().getQuery23(operation);
		}

		@Override
		public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(BindingSet bs) {
			int messageCount       = convertInteger(bs, "messageCount"   );
			String destinationName = convertString (bs, "destinationName");
			int month              = convertInteger(bs, "month"          );
			return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, destinationName, month);
		}
	}


	public static class BiQuery24 extends SparqlListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery24MessagesByTopic operation) {
			return state.getQueryStore().getQuery24(operation);
		}

		@Override
		public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(BindingSet bs) {
			int messageCount     = convertInteger(bs, "messageCount" );
			int likeCount        = convertInteger(bs, "likeCount"    );
			int year             = convertInteger(bs, "year"         );
			int month            = convertInteger(bs, "month"        );
			String continentName = convertString (bs, "continentName");
			return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continentName);
		}
	}

	public static class BiQuery25 extends SparqlListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery25WeightedPaths operation) {
			return state.getQueryStore().getQuery25(operation);
		}

		@Override
		public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(BindingSet bs) {
			String[] personIdStrings = bs.getBinding("personIds").getValue().stringValue().split(",");
			final List<Long> personIds = Arrays.stream(personIdStrings).map(Long::parseLong).collect(Collectors.toList());
			return new LdbcSnbBiQuery25WeightedPathsResult(personIds);
		}
	}

	static Pattern ID_PATTERN = Pattern.compile("[0-9]+$");

	public static long convertDate(BindingSet bs, String name) {
		return 0; //bs.getBinding(name);
	}

	public static double convertDouble(BindingSet bs, String name) {
		return ((IntegerLiteralImpl) bs.getBinding(name)).doubleValue();
	}

	public static int convertInteger(BindingSet bs, String name) {
		return Integer.parseInt(bs.getBinding(name).getValue().stringValue());
	}

	public static long convertLong(BindingSet bs, String name) {
		String string = bs.getBinding(name).getValue().stringValue();
		Matcher matcher = ID_PATTERN.matcher(string);
		matcher.find();
		return Long.parseLong(matcher.group(0));
	}

	public static String convertString(BindingSet bs, String name) {
		return bs.getBinding(name).getValue().stringValue();
	}

}
