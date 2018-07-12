package com.ldbc.impls.workloads.ldbc.snb.bi;

import com.google.common.collect.ImmutableMap;
import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedReplies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircle;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteraction;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21Zombies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialog;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinations;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopic;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery25WeightedPaths;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery2TopTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;

public abstract class BiQueryStore extends QueryStore<BiQueryStore.BiQuery> {

    public enum BiQuery {
        Query1("bi-1"),
        Query2("bi-2"),
        Query3("bi-3"),
        Query4("bi-4"),
        Query5("bi-5"),
        Query6("bi-6"),
        Query7("bi-7"),
        Query8("bi-8"),
        Query9("bi-9"),
        Query10("bi-10"),
        Query11("bi-11"),
        Query12("bi-12"),
        Query13("bi-13"),
        Query14("bi-14"),
        Query15("bi-15"),
        Query16("bi-16"),
        Query17("bi-17"),
        Query18("bi-18"),
        Query19("bi-19"),
        Query20("bi-20"),
        Query21("bi-21"),
        Query22("bi-22"),
        Query23("bi-23"),
        Query24("bi-24"),
        Query25("bi-25"),
        ;

        private String name;

        BiQuery(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public BiQueryStore(String path, String postfix) throws DbException {
        for (BiQuery biQuery : BiQuery.values()) {
            queries.put(biQuery, loadQueryFromFile(path, biQuery.getName() + postfix));
        }
    }

    // query getters
    public String getQuery1(LdbcSnbBiQuery1PostingSummary operation) {
        return prepare(BiQuery.Query1, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery1PostingSummary.DATE, getConverter().convertDateTime(operation.date()))
                .build());
    }

    public String getQuery2(LdbcSnbBiQuery2TopTags operation) {
        return prepare(BiQuery.Query2, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery2TopTags.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery2TopTags.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .put(LdbcSnbBiQuery2TopTags.COUNTRY1, getConverter().convertString(operation.country1()))
                .put(LdbcSnbBiQuery2TopTags.COUNTRY2, getConverter().convertString(operation.country2()))
                .build());
    }

    public String getQuery3(LdbcSnbBiQuery3TagEvolution operation) {
        return prepare(BiQuery.Query3, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery3TagEvolution.YEAR, getConverter().convertInteger(operation.year()))
                .put(LdbcSnbBiQuery3TagEvolution.MONTH, getConverter().convertInteger(operation.month()))
                .build());
    }

    public String getQuery4(LdbcSnbBiQuery4PopularCountryTopics operation) {
        return prepare(BiQuery.Query4, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery4PopularCountryTopics.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .put(LdbcSnbBiQuery4PopularCountryTopics.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery5(LdbcSnbBiQuery5TopCountryPosters operation) {
        return prepare(BiQuery.Query5, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery5TopCountryPosters.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery6(LdbcSnbBiQuery6ActivePosters operation) {
        return prepare(BiQuery.Query6, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery6ActivePosters.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery7(LdbcSnbBiQuery7AuthoritativeUsers operation) {
        return prepare(BiQuery.Query7, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery7AuthoritativeUsers.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery8(LdbcSnbBiQuery8RelatedTopics operation) {
        return prepare(BiQuery.Query8, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery8RelatedTopics.TAG, getConverter().convertString(operation.tag()))
                .build());
    }

    public String getQuery9(LdbcSnbBiQuery9RelatedForums operation) {
        return prepare(BiQuery.Query9, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery9RelatedForums.TAG_CLASS1, getConverter().convertString(operation.tagClass1()))
                .put(LdbcSnbBiQuery9RelatedForums.TAG_CLASS2, getConverter().convertString(operation.tagClass2()))
                .put(LdbcSnbBiQuery9RelatedForums.THRESHOLD, getConverter().convertInteger(operation.threshold()))
                .build());
    }

    public String getQuery10(LdbcSnbBiQuery10TagPerson operation) {
        return prepare(BiQuery.Query10, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery10TagPerson.TAG, getConverter().convertString(operation.tag()))
                .put(LdbcSnbBiQuery10TagPerson.DATE, getConverter().convertDateTime(operation.date()))
                .build());
    }

    public String getQuery11(LdbcSnbBiQuery11UnrelatedReplies operation) {
        return prepare(BiQuery.Query11, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery11UnrelatedReplies.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery11UnrelatedReplies.BLACKLIST, getConverter().convertBlacklist(operation.blacklist()))
                .build());
    }

    public String getQuery12(LdbcSnbBiQuery12TrendingPosts operation) {
        return prepare(BiQuery.Query12, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery12TrendingPosts.DATE, getConverter().convertDateTime(operation.date()))
                .put(LdbcSnbBiQuery12TrendingPosts.LIKE_THRESHOLD, getConverter().convertInteger(operation.likeThreshold()))
                .build());
    }

    public String getQuery13(LdbcSnbBiQuery13PopularMonthlyTags operation) {
        return prepare(BiQuery.Query13, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery13PopularMonthlyTags.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery14(LdbcSnbBiQuery14TopThreadInitiators operation) {
        return prepare(BiQuery.Query14, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery14TopThreadInitiators.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery14TopThreadInitiators.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery15(LdbcSnbBiQuery15SocialNormals operation) {
        return prepare(BiQuery.Query15, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery15SocialNormals.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery16(LdbcSnbBiQuery16ExpertsInSocialCircle operation) {
        return prepare(BiQuery.Query16, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.PERSON_ID, getConverter().convertId(operation.personId()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.MIN_PATH_DISTANCE, getConverter().convertInteger(operation.minPathDistance()))
                .put(LdbcSnbBiQuery16ExpertsInSocialCircle.MAX_PATH_DISTANCE, getConverter().convertInteger(operation.maxPathDistance()))
                .build());
    }

    public String getQuery17(LdbcSnbBiQuery17FriendshipTriangles operation) {
        return prepare(BiQuery.Query17, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery17FriendshipTriangles.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery18(LdbcSnbBiQuery18PersonPostCounts operation) {
        return prepare(BiQuery.Query18, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery18PersonPostCounts.DATE, getConverter().convertDateTime(operation.date()))
                .put(LdbcSnbBiQuery18PersonPostCounts.LENGTH_THRESHOLD, getConverter().convertInteger(operation.lengthThreshold()))
                .put(LdbcSnbBiQuery18PersonPostCounts.LANGUAGES, getConverter().convertStringList(operation.languages()))
                .build());
    }

    public String getQuery19(LdbcSnbBiQuery19StrangerInteraction operation) {
        return prepare(BiQuery.Query19, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery19StrangerInteraction.DATE, getConverter().convertDate(operation.date()))
                .put(LdbcSnbBiQuery19StrangerInteraction.TAG_CLASS1, getConverter().convertString(operation.tagClass1()))
                .put(LdbcSnbBiQuery19StrangerInteraction.TAG_CLASS2, getConverter().convertString(operation.tagClass2()))
                .build());
    }

    public String getQuery20(LdbcSnbBiQuery20HighLevelTopics operation) {
        return prepare(BiQuery.Query20, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery20HighLevelTopics.TAG_CLASSES, getConverter().convertStringList(operation.tagClasses()))
                .build());
    }

    public String getQuery21(LdbcSnbBiQuery21Zombies operation) {
        return prepare(BiQuery.Query21, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery21Zombies.COUNTRY, getConverter().convertString(operation.country()))
                .put(LdbcSnbBiQuery21Zombies.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

    public String getQuery22(LdbcSnbBiQuery22InternationalDialog operation) {
        return prepare(BiQuery.Query22, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery22InternationalDialog.COUNTRY1, getConverter().convertString(operation.country1()))
                .put(LdbcSnbBiQuery22InternationalDialog.COUNTRY2, getConverter().convertString(operation.country2()))
                .build());
    }

    public String getQuery23(LdbcSnbBiQuery23HolidayDestinations operation) {
        return prepare(BiQuery.Query23, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery23HolidayDestinations.COUNTRY, getConverter().convertString(operation.country()))
                .build());
    }

    public String getQuery24(LdbcSnbBiQuery24MessagesByTopic operation) {
        return prepare(BiQuery.Query24, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery24MessagesByTopic.TAG_CLASS, getConverter().convertString(operation.tagClass()))
                .build());
    }

    public String getQuery25(LdbcSnbBiQuery25WeightedPaths operation) {
        return prepare(BiQuery.Query25, new ImmutableMap.Builder<String, String>()
                .put(LdbcSnbBiQuery25WeightedPaths.PERSON1_ID, getConverter().convertId(operation.person1Id()))
                .put(LdbcSnbBiQuery25WeightedPaths.PERSON2_ID, getConverter().convertId(operation.person2Id()))
                .put(LdbcSnbBiQuery25WeightedPaths.START_DATE, getConverter().convertDateTime(operation.startDate()))
                .put(LdbcSnbBiQuery25WeightedPaths.END_DATE, getConverter().convertDateTime(operation.endDate()))
                .build());
    }

}
