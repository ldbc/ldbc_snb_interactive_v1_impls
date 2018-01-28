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
import com.ldbc.impls.workloads.ldbc.snb.util.SparqlConverter;
import org.openrdf.model.impl.BooleanLiteralImpl;
import org.openrdf.model.impl.IntegerLiteralImpl;
import org.openrdf.query.BindingSet;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// https://github.com/ldbc/ldbc_snb_driver/commit/2cb756078fb1da950c6240e9a879dfb6df375dc4
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
//		registerOperationHandler(LdbcSnbBiQuery10TagPerson.class, BiQuery10.class);
		registerOperationHandler(LdbcSnbBiQuery11UnrelatedReplies.class, BiQuery11.class);
		registerOperationHandler(LdbcSnbBiQuery12TrendingPosts.class, BiQuery12.class);
//		registerOperationHandler(LdbcSnbBiQuery13PopularMonthlyTags.class, BiQuery13.class);
		registerOperationHandler(LdbcSnbBiQuery14TopThreadInitiators.class, BiQuery14.class);
		registerOperationHandler(LdbcSnbBiQuery15SocialNormals.class, BiQuery15.class);
//		registerOperationHandler(LdbcSnbBiQuery16ExpertsInSocialCircle.class, BiQuery16.class);
		registerOperationHandler(LdbcSnbBiQuery17FriendshipTriangles.class, BiQuery17.class);
		registerOperationHandler(LdbcSnbBiQuery18PersonPostCounts.class, BiQuery18.class);
//		registerOperationHandler(LdbcSnbBiQuery19StrangerInteraction.class, BiQuery19.class);
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
		    int messageYear             = ((IntegerLiteralImpl) bs.getBinding("messageYear"         )).intValue();
			boolean isComment           = ((BooleanLiteralImpl) bs.getBinding("isComment"           )).booleanValue();
			int lengthCategory          = ((IntegerLiteralImpl) bs.getBinding("lengthCategory"      )).intValue();
			long messageCount           = ((IntegerLiteralImpl) bs.getBinding("messageCount"        )).longValue();
			int averageMessageLength    = ((IntegerLiteralImpl) bs.getBinding("averageMessageLength")).intValue();
			int sumMessageLength        = ((IntegerLiteralImpl) bs.getBinding("totalMessageCount"   )).intValue();
			double percentageOfMessages = ((IntegerLiteralImpl) bs.getBinding("percentageOfMessages")).doubleValue();

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
			String countryName  =                       bs.getBinding("countryName" ).getValue().stringValue();
			int messageMonth    = ((IntegerLiteralImpl) bs.getBinding("messageMonth")).intValue();
			String personGender =                       bs.getBinding("personGender").getValue().stringValue();
			int ageGroup        = ((IntegerLiteralImpl) bs.getBinding("ageGroup"    )).intValue();
			String tagName      =                       bs.getBinding("tagName"     ).getValue().stringValue();
			int messageCount    = ((IntegerLiteralImpl) bs.getBinding("messageCount")).intValue();
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
			String tagName =                       bs.getBinding("tagName").getValue().stringValue();
			int countA     = ((IntegerLiteralImpl) bs.getBinding("countA")).intValue();
			int countB     = ((IntegerLiteralImpl) bs.getBinding("countB")).intValue();
			int diffCount  = ((IntegerLiteralImpl) bs.getBinding("diff"  )).intValue();
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
			long forumId           = ((IntegerLiteralImpl) bs.getBinding("forumId"   )).longValue();
			String forumTitle      =                       bs.getBinding("forumTitle").getValue().stringValue();
			long forumCreationDate = new SparqlConverter().convertTimestampToEpoch(
					                                       bs.getBinding("forumCreationDate"));
			long personId          = ((IntegerLiteralImpl) bs.getBinding("personId"  )).longValue();
			int postCount          = ((IntegerLiteralImpl) bs.getBinding("postCount" )).intValue();
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
			long personId           = ((IntegerLiteralImpl) bs.getBinding("personId"       )).longValue();
			String personFirstName  =                       bs.getBinding("personFirstName").getValue().stringValue();
			String personLastName   =                       bs.getBinding("personLastName" ).getValue().stringValue();
			long personCreationDate = new SparqlConverter().convertTimestampToEpoch(
					                                        bs.getBinding("personCreationDate"));
			int pontCount           = ((IntegerLiteralImpl) bs.getBinding("pontCount"      )).intValue();
			return new LdbcSnbBiQuery5TopCountryPostersResult(personId, personFirstName, personLastName, personCreationDate, pontCount);
		}

	}

	public static class BiQuery6 extends SparqlListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, SparqlBiQueryStore> {

		@Override
		public String getQueryString(SparqlDriverConnectionStore<SparqlBiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
			return state.getQueryStore().getQuery6(operation);
		}

		@Override
		public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(BindingSet bs) {
			long personId    = ((IntegerLiteralImpl) bs.getBinding("personId"    )).longValue();
			int replyCount   = ((IntegerLiteralImpl) bs.getBinding("replyCount"  )).intValue();
			int likeCount    = ((IntegerLiteralImpl) bs.getBinding("likeCount"   )).intValue();
			int messageCount = ((IntegerLiteralImpl) bs.getBinding("messageCount")).intValue();
			int score        = ((IntegerLiteralImpl) bs.getBinding("score"       )).intValue();
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
			long personId      = ((IntegerLiteralImpl) bs.getBinding("personId"      )).longValue();
			int authorityScore = ((IntegerLiteralImpl) bs.getBinding("authorityScore")).intValue();
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
			String relatedTagName =                       bs.getBinding("relatedTagName").getValue().stringValue();
			int count             = ((IntegerLiteralImpl) bs.getBinding("count")).intValue();
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
			long forumId = ((IntegerLiteralImpl) bs.getBinding("forumId")).longValue();
			int count1   = ((IntegerLiteralImpl) bs.getBinding("count1" )).intValue();
			int count2   = ((IntegerLiteralImpl) bs.getBinding("count2" )).intValue();
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
			long personId =    ((IntegerLiteralImpl) bs.getBinding("personId"    )).longValue();
			int score =        ((IntegerLiteralImpl) bs.getBinding("score"       )).intValue();
			int friendsScore = ((IntegerLiteralImpl) bs.getBinding("friendsScore")).intValue();
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
			long personId  = ((IntegerLiteralImpl) bs.getBinding("personId"  )).longValue();
			String tagName =                       bs.getBinding("tagName"   ).getValue().stringValue();
			int likeCount  = ((IntegerLiteralImpl) bs.getBinding("likeCount" )).intValue();
			int replyCount = ((IntegerLiteralImpl) bs.getBinding("replyCount")).intValue();
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
			long messageId           = ((IntegerLiteralImpl) bs.getBinding("messageId")).longValue();
			long messageCreationDate = new SparqlConverter().convertTimestampToEpoch(
					                                         bs.getBinding("messageCreationDate"));
			String creatorFirstName  =                       bs.getBinding("creatorFirstName"   ).getValue().stringValue();
			String creatorLastName   =                       bs.getBinding("creatorLastName"    ).getValue().stringValue();
			int likeCount            = ((IntegerLiteralImpl) bs.getBinding("likeCount"          )).intValue();
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
			int year  = ((IntegerLiteralImpl) bs.getBinding("year"  )).intValue();
			int month = ((IntegerLiteralImpl) bs.getBinding("mongth")).intValue();
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
			long personId          = ((IntegerLiteralImpl) bs.getBinding("personId"       )).longValue();
			String personFirstName =                       bs.getBinding("personFirstName").getValue().stringValue();
			String personLastName  =                       bs.getBinding("personLastName" ).getValue().stringValue();
			int threadCount        = ((IntegerLiteralImpl) bs.getBinding("threadCount"    )).intValue();
			int messageCount       = ((IntegerLiteralImpl) bs.getBinding("messageCount"   )).intValue();
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
			long personId = ((IntegerLiteralImpl) bs.getBinding("personId")).longValue();
			int count     = ((IntegerLiteralImpl) bs.getBinding("count"   )).intValue();
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
			long personId    = ((IntegerLiteralImpl) bs.getBinding("personId")).longValue();
			String tagName   =                       bs.getBinding("tagName" ).getValue().stringValue();
			int messageCount = ((IntegerLiteralImpl) bs.getBinding("messageCount")).intValue();
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
			int count = ((IntegerLiteralImpl) bs.getBinding("count")).intValue();
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
			int messageCount = ((IntegerLiteralImpl) bs.getBinding("messageCount")).intValue();
			int personCount  = ((IntegerLiteralImpl) bs.getBinding("personCount" )).intValue();
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
			long personId        = ((IntegerLiteralImpl) bs.getBinding("personId"        )).longValue();
			int strangerCount    = ((IntegerLiteralImpl) bs.getBinding("strangerCount"   )).intValue();
			int interactionCount = ((IntegerLiteralImpl) bs.getBinding("interactionCount")).intValue();
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
			String tagClassName =                       bs.getBinding("tagClassName").getValue().stringValue();
			int messageCount    = ((IntegerLiteralImpl) bs.getBinding("messageCount")).intValue();
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
			long zombieId       = ((IntegerLiteralImpl) bs.getBinding("zombieId"       )).longValue();
			int zombieLikeCount = ((IntegerLiteralImpl) bs.getBinding("zombieLikeCount")).intValue();
			int totalLikeCount  = ((IntegerLiteralImpl) bs.getBinding("totalLikeCount" )).intValue();
			double zombieScore  = ((IntegerLiteralImpl) bs.getBinding("zombieScore"    )).doubleValue();
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
			long person1Id   = ((IntegerLiteralImpl) bs.getBinding("person1Id")).longValue();
			long person2Id   = ((IntegerLiteralImpl) bs.getBinding("person2Id")).longValue();
			String city1Name =                       bs.getBinding("city1Name").getValue().stringValue();
			int score        = ((IntegerLiteralImpl) bs.getBinding("score"    )).intValue();
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
			int messageCount       = ((IntegerLiteralImpl) bs.getBinding("messageCount"   )).intValue();
			String destinationName =                       bs.getBinding("destinationName").getValue().stringValue();
			int month              = ((IntegerLiteralImpl) bs.getBinding("month"          )).intValue();
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
			int messageCount     = ((IntegerLiteralImpl) bs.getBinding("messageCount" )).intValue();
			int likeCount        = ((IntegerLiteralImpl) bs.getBinding("likeCount"    )).intValue();
			int year             = ((IntegerLiteralImpl) bs.getBinding("year"         )).intValue();
			int month            = ((IntegerLiteralImpl) bs.getBinding("month"        )).intValue();
			String continentName =                       bs.getBinding("continentName").getValue().stringValue();
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

}
