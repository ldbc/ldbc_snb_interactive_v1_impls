package com.ldbc.impls.workloads.ldbc.snb.cypher.bi;

import com.google.common.primitives.Ints;
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
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDb;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherDbConnectionState;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.cypher.CypherSingletonOperationHandler;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Values;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CypherBiDb extends CypherDb<BiQueryStore> {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new CypherDbConnectionState(properties, new CypherBiQueryStore(properties.get("queryDir")));

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

    public static class BiQuery1 extends CypherListOperationHandler<LdbcSnbBiQuery1PostingSummary, LdbcSnbBiQuery1PostingSummaryResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery1PostingSummary operation) {
            return state.getQueryStore().getQuery1(operation);
        }

        @Override
        public LdbcSnbBiQuery1PostingSummaryResult convertSingleResult(Record record) {
            int year = record.get(0).asInt();
            boolean isComment = record.get(1).asBoolean();
            int size = record.get(2).asInt();
            long count = record.get(3).asLong();
            int avgLen = record.get(4).asInt();
            int total = record.get(5).asInt();
            double pct = record.get(6).asDouble();

            return new LdbcSnbBiQuery1PostingSummaryResult(year, isComment, size, count, avgLen, total, (float) pct);
        }

    }

    public static class BiQuery2 extends CypherListOperationHandler<LdbcSnbBiQuery2TopTags, LdbcSnbBiQuery2TopTagsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery2TopTags operation) {
            return state.getQueryStore().getQuery2(operation);
        }

        @Override
        public LdbcSnbBiQuery2TopTagsResult convertSingleResult(Record record) {
            String country = record.get(0).asString();
            int month = record.get(1).asInt();
            String gender = record.get(2).asString();
            int ageGroup = record.get(3).asInt();
            String tag = record.get(4).asString();
            int count = record.get(5).asInt();
            return new LdbcSnbBiQuery2TopTagsResult(country, month, gender, ageGroup, tag, count);
        }

    }

    public static class BiQuery3 extends CypherListOperationHandler<LdbcSnbBiQuery3TagEvolution, LdbcSnbBiQuery3TagEvolutionResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery3TagEvolution operation) {
            return state.getQueryStore().getQuery3(operation);
        }

        @Override
        public LdbcSnbBiQuery3TagEvolutionResult convertSingleResult(Record record) {
            String tagName = record.get(0).asString();
            int countA = record.get(1).asInt();
            int countB = record.get(2).asInt();
            int diffCount = record.get(3).asInt();
            return new LdbcSnbBiQuery3TagEvolutionResult(tagName, countA, countB, diffCount);
        }

    }

    public static class BiQuery4 extends CypherListOperationHandler<LdbcSnbBiQuery4PopularCountryTopics, LdbcSnbBiQuery4PopularCountryTopicsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery4PopularCountryTopics operation) {
            return state.getQueryStore().getQuery4(operation);
        }

        @Override
        public LdbcSnbBiQuery4PopularCountryTopicsResult convertSingleResult(Record record) throws ParseException {
            long forumId = record.get(0).asLong();
            String title = record.get(1).asString();
            long creationDate = converter.convertLongTimestampToEpoch(record.get(2).asLong());
            long moderator = record.get(3).asLong();
            int count = record.get(4).asInt();
            return new LdbcSnbBiQuery4PopularCountryTopicsResult(forumId, title, creationDate, moderator, count);
        }

    }

    public static class BiQuery5 extends CypherListOperationHandler<LdbcSnbBiQuery5TopCountryPosters, LdbcSnbBiQuery5TopCountryPostersResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery5TopCountryPosters operation) {
            return state.getQueryStore().getQuery5(operation);
        }

        @Override
        public LdbcSnbBiQuery5TopCountryPostersResult convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            String firstName = record.get(1).asString();
            String lastName = record.get(2).asString();
            long creationDate = converter.convertLongTimestampToEpoch(record.get(3).asLong());
            int count = record.get(4).asInt();
            return new LdbcSnbBiQuery5TopCountryPostersResult(personId, firstName, lastName, creationDate, count);
        }

    }

    public static class BiQuery6 extends CypherListOperationHandler<LdbcSnbBiQuery6ActivePosters, LdbcSnbBiQuery6ActivePostersResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery6ActivePosters operation) {
            return state.getQueryStore().getQuery6(operation);
        }

        @Override
        public LdbcSnbBiQuery6ActivePostersResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int postCount = record.get(1).asInt();
            int replyCount = record.get(2).asInt();
            int likeCount = record.get(3).asInt();
            int score = record.get(4).asInt();
            return new LdbcSnbBiQuery6ActivePostersResult(personId, postCount, replyCount, likeCount, score);
        }

    }

    public static class BiQuery7 extends CypherListOperationHandler<LdbcSnbBiQuery7AuthoritativeUsers, LdbcSnbBiQuery7AuthoritativeUsersResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery7AuthoritativeUsers operation) {
            return state.getQueryStore().getQuery7(operation);
        }

        @Override
        public LdbcSnbBiQuery7AuthoritativeUsersResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int score = record.get(1).asInt();
            return new LdbcSnbBiQuery7AuthoritativeUsersResult(personId, score);
        }

    }

    public static class BiQuery8 extends CypherListOperationHandler<LdbcSnbBiQuery8RelatedTopics, LdbcSnbBiQuery8RelatedTopicsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery8RelatedTopics operation) {
            return state.getQueryStore().getQuery8(operation);
        }

        @Override
        public LdbcSnbBiQuery8RelatedTopicsResult convertSingleResult(Record record) {
            String tag = record.get(0).asString();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery8RelatedTopicsResult(tag, count);
        }

    }

    public static class BiQuery9 extends CypherListOperationHandler<LdbcSnbBiQuery9RelatedForums, LdbcSnbBiQuery9RelatedForumsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery9RelatedForums operation) {
            return state.getQueryStore().getQuery9(operation);
        }

        @Override
        public LdbcSnbBiQuery9RelatedForumsResult convertSingleResult(Record record) {
            long forumId = record.get(0).asLong();
            int sumA = record.get(1).asInt();
            int sumB = record.get(2).asInt();
            return new LdbcSnbBiQuery9RelatedForumsResult(forumId, sumA, sumB);
        }

    }

    public static class BiQuery10 extends CypherListOperationHandler<LdbcSnbBiQuery10TagPerson, LdbcSnbBiQuery10TagPersonResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery10TagPerson operation) {
            return state.getQueryStore().getQuery10(operation);
        }

        @Override
        public LdbcSnbBiQuery10TagPersonResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int score = record.get(1).asInt();
            int friendsScore = record.get(2).asInt();
            return new LdbcSnbBiQuery10TagPersonResult(personId, score, friendsScore);
        }

    }

    public static class BiQuery11 extends CypherListOperationHandler<LdbcSnbBiQuery11UnrelatedReplies, LdbcSnbBiQuery11UnrelatedRepliesResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery11UnrelatedReplies operation) {
            return state.getQueryStore().getQuery11(operation);
        }

        @Override
        public LdbcSnbBiQuery11UnrelatedRepliesResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String tagName = record.get(1).asString();
            int countLikes = record.get(2).asInt();
            int countReplies = record.get(3).asInt();
            return new LdbcSnbBiQuery11UnrelatedRepliesResult(personId, tagName, countLikes, countReplies);
        }

    }

    public static class BiQuery12 extends CypherListOperationHandler<LdbcSnbBiQuery12TrendingPosts, LdbcSnbBiQuery12TrendingPostsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery12TrendingPosts operation) {
            return state.getQueryStore().getQuery12(operation);
        }

        @Override
        public LdbcSnbBiQuery12TrendingPostsResult convertSingleResult(Record record) throws ParseException {
            long personId = record.get(0).asLong();
            long creationDate = converter.convertLongTimestampToEpoch(record.get(1).asLong());
            String firstName = record.get(2).asString();
            String lastName = record.get(3).asString();
            int likeCount = record.get(4).asInt();
            return new LdbcSnbBiQuery12TrendingPostsResult(personId, creationDate, firstName, lastName, likeCount);
        }
    }

    public static class BiQuery13 extends CypherListOperationHandler<LdbcSnbBiQuery13PopularMonthlyTags, LdbcSnbBiQuery13PopularMonthlyTagsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery13PopularMonthlyTags operation) {
            return state.getQueryStore().getQuery13(operation);
        }

        @Override
        public LdbcSnbBiQuery13PopularMonthlyTagsResult convertSingleResult(Record record) {
            int year = record.get(0).asInt();
            int month = record.get(1).asInt();
            final List<List<Object>> tagPopularitiesRaw = record.get(2).asList(Values.ofList());

            final List<LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity> tagPopularities = new ArrayList<>();
            for (List<Object> tagPopularityRaw : tagPopularitiesRaw) {
                final String tag = (String) tagPopularityRaw.get(0);
                final int popularity = Ints.saturatedCast((long) tagPopularityRaw.get(1));
                tagPopularities.add(new LdbcSnbBiQuery13PopularMonthlyTagsResult.TagPopularity(tag, popularity));
            }

            return new LdbcSnbBiQuery13PopularMonthlyTagsResult(year, month, tagPopularities);
        }
    }

    public static class BiQuery14 extends CypherListOperationHandler<LdbcSnbBiQuery14TopThreadInitiators, LdbcSnbBiQuery14TopThreadInitiatorsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery14TopThreadInitiators operation) {
            return state.getQueryStore().getQuery14(operation);
        }

        @Override
        public LdbcSnbBiQuery14TopThreadInitiatorsResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String firstName = record.get(1).asString();
            String lastName = record.get(2).asString();
            int count = record.get(3).asInt();
            int threadCount = record.get(4).asInt();
            return new LdbcSnbBiQuery14TopThreadInitiatorsResult(personId, firstName, lastName, count, threadCount);
        }
    }

    public static class BiQuery15 extends CypherListOperationHandler<LdbcSnbBiQuery15SocialNormals, LdbcSnbBiQuery15SocialNormalsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery15SocialNormals operation) {
            return state.getQueryStore().getQuery15(operation);
        }

        @Override
        public LdbcSnbBiQuery15SocialNormalsResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery15SocialNormalsResult(personId, count);
        }
    }

    public static class BiQuery16 extends CypherListOperationHandler<LdbcSnbBiQuery16ExpertsInSocialCircle, LdbcSnbBiQuery16ExpertsInSocialCircleResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
            return state.getQueryStore().getQuery16(operation);
        }

        @Override
        public LdbcSnbBiQuery16ExpertsInSocialCircleResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            String tag = record.get(1).asString();
            int count = record.get(2).asInt();
            return new LdbcSnbBiQuery16ExpertsInSocialCircleResult(personId, tag, count);
        }
    }

    public static class BiQuery17 extends CypherSingletonOperationHandler<LdbcSnbBiQuery17FriendshipTriangles, LdbcSnbBiQuery17FriendshipTrianglesResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery17FriendshipTriangles operation) {
            return state.getQueryStore().getQuery17(operation);
        }

        @Override
        public LdbcSnbBiQuery17FriendshipTrianglesResult convertSingleResult(Record record) {
            int count = record.get(0).asInt();
            return new LdbcSnbBiQuery17FriendshipTrianglesResult(count);
        }
    }

    public static class BiQuery18 extends CypherListOperationHandler<LdbcSnbBiQuery18PersonPostCounts, LdbcSnbBiQuery18PersonPostCountsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery18PersonPostCounts operation) {
            return state.getQueryStore().getQuery18(operation);
        }

        @Override
        public LdbcSnbBiQuery18PersonPostCountsResult convertSingleResult(Record record) {
            int postCount = record.get(0).asInt();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery18PersonPostCountsResult(postCount, count);
        }
    }


    public static class BiQuery19 extends CypherListOperationHandler<LdbcSnbBiQuery19StrangerInteraction, LdbcSnbBiQuery19StrangerInteractionResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery19StrangerInteraction operation) {
            return state.getQueryStore().getQuery19(operation);
        }

        @Override
        public LdbcSnbBiQuery19StrangerInteractionResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int strangerCount = record.get(1).asInt();
            int count = record.get(2).asInt();
            return new LdbcSnbBiQuery19StrangerInteractionResult(personId, strangerCount, count);
        }
    }

    public static class BiQuery20 extends CypherListOperationHandler<LdbcSnbBiQuery20HighLevelTopics, LdbcSnbBiQuery20HighLevelTopicsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery20HighLevelTopics operation) {
            return state.getQueryStore().getQuery20(operation);
        }

        @Override
        public LdbcSnbBiQuery20HighLevelTopicsResult convertSingleResult(Record record) {
            String tagClass = record.get(0).asString();
            int count = record.get(1).asInt();
            return new LdbcSnbBiQuery20HighLevelTopicsResult(tagClass, count);
        }
    }

    public static class BiQuery21 extends CypherListOperationHandler<LdbcSnbBiQuery21Zombies, LdbcSnbBiQuery21ZombiesResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery21Zombies operation) {
            return state.getQueryStore().getQuery21(operation);
        }

        @Override
        public LdbcSnbBiQuery21ZombiesResult convertSingleResult(Record record) {
            long personId = record.get(0).asLong();
            int zombieCount = record.get(1).asInt();
            int realCount = record.get(2).asInt();
            double score = record.get(3).asDouble();
            return new LdbcSnbBiQuery21ZombiesResult(personId, zombieCount, realCount, score);
        }
    }

    public static class BiQuery22 extends CypherListOperationHandler<LdbcSnbBiQuery22InternationalDialog, LdbcSnbBiQuery22InternationalDialogResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery22InternationalDialog operation) {
            return state.getQueryStore().getQuery22(operation);
        }

        @Override
        public LdbcSnbBiQuery22InternationalDialogResult convertSingleResult(Record record) {
            long personIdA = record.get(0).asLong();
            long personIdB = record.get(1).asLong();
            String city1Name = record.get(2).asString();
            int score = record.get(3).asInt();
            return new LdbcSnbBiQuery22InternationalDialogResult(personIdA, personIdB, city1Name, score);
        }
    }

    public static class BiQuery23 extends CypherListOperationHandler<LdbcSnbBiQuery23HolidayDestinations, LdbcSnbBiQuery23HolidayDestinationsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery23HolidayDestinations operation) {
            return state.getQueryStore().getQuery23(operation);
        }

        @Override
        public LdbcSnbBiQuery23HolidayDestinationsResult convertSingleResult(Record record) {
            int messageCount = record.get(0).asInt();
            String country = record.get(1).asString();
            int month = record.get(2).asInt();
            return new LdbcSnbBiQuery23HolidayDestinationsResult(messageCount, country, month);
        }
    }


    public static class BiQuery24 extends CypherListOperationHandler<LdbcSnbBiQuery24MessagesByTopic, LdbcSnbBiQuery24MessagesByTopicResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery24MessagesByTopic operation) {
            return state.getQueryStore().getQuery24(operation);
        }

        @Override
        public LdbcSnbBiQuery24MessagesByTopicResult convertSingleResult(Record record) {
            int messageCount = record.get(0).asInt();
            int likeCount = record.get(1).asInt();
            int year = record.get(2).asInt();
            int month = record.get(3).asInt();
            String continent = record.get(4).asString();
            return new LdbcSnbBiQuery24MessagesByTopicResult(messageCount, likeCount, year, month, continent);
        }
    }

    public static class BiQuery25 extends CypherListOperationHandler<LdbcSnbBiQuery25WeightedPaths, LdbcSnbBiQuery25WeightedPathsResult, CypherBiQueryStore> {

        @Override
        public String getQueryString(CypherDbConnectionState<CypherBiQueryStore> state, LdbcSnbBiQuery25WeightedPaths operation) {
            return state.getQueryStore().getQuery25(operation);
        }

        @Override
        public LdbcSnbBiQuery25WeightedPathsResult convertSingleResult(Record record) {
            List<Long> personIds = record.get(0).asList(Values.ofLong());
            double weight = record.get(1).asDouble();
            return new LdbcSnbBiQuery25WeightedPathsResult(personIds, weight);
        }
    }
}
