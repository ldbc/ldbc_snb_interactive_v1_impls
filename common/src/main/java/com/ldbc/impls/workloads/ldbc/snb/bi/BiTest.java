package com.ldbc.impls.workloads.ldbc.snb.bi;

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
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.SnbTest;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import org.junit.Test;

import java.util.Arrays;

public abstract class BiTest extends SnbTest {

    public BiTest(BaseDb db) {
        super(db, new LdbcSnbBiWorkload());
    }

    @Test
    public void testBiQuery1() throws DbException {
        run(db, new LdbcSnbBiQuery1PostingSummary(1311307200000L));
    }

    @Test
    public void testBiQuery2() throws DbException {
        run(db, new LdbcSnbBiQuery2TopTags(1265583600000L, 1290380400000L, "Germany", "United_States", LIMIT));
    }

    @Test
    public void testBiQuery3() throws DbException {
        run(db, new LdbcSnbBiQuery3TagEvolution(2015, 12, 100));
    }

    @Test
    public void testBiQuery4() throws DbException {
        run(db, new LdbcSnbBiQuery4PopularCountryTopics("MusicalArtist", "Netherlands", LIMIT));
    }

    @Test
    public void testBiQuery5() throws DbException {
        run(db, new LdbcSnbBiQuery5TopCountryPosters("Ethiopia", LIMIT));
    }

    @Test
    public void testBiQuery6() throws DbException {
        run(db, new LdbcSnbBiQuery6ActivePosters("Ehud_Olmert", LIMIT));
    }

    @Test
    public void testBiQuery7() throws DbException {
        run(db, new LdbcSnbBiQuery7AuthoritativeUsers("Che_Guevara", LIMIT));
    }

    @Test
    public void testBiQuery8() throws DbException {
        run(db, new LdbcSnbBiQuery8RelatedTopics("Imelda_Marcos", LIMIT));
    }

    @Test
    public void testBiQuery9() throws DbException {
        run(db, new LdbcSnbBiQuery9RelatedForums("BaseballPlayer", "ChristianBishop", 200, LIMIT));
    }

    @Test
    public void testBiQuery10() throws DbException {
        run(db, new LdbcSnbBiQuery10TagPerson("Che_Guevara", 1311307200000L, LIMIT));
    }

    @Test
    public void testBiQuery11() throws DbException {
        run(db, new LdbcSnbBiQuery11UnrelatedReplies("Germany", Arrays.asList("also"), LIMIT));
    }

    @Test
    public void testBiQuery12() throws DbException {
        run(db, new LdbcSnbBiQuery12TrendingPosts(1311307200000L, 100, LIMIT));
    }

    @Test
    public void testBiQuery13() throws DbException {
        run(db, new LdbcSnbBiQuery13PopularMonthlyTags("Ethiopia", LIMIT));
    }

    @Test
    public void testBiQuery14() throws DbException {
        run(db, new LdbcSnbBiQuery14TopThreadInitiators(1338523200000L, 1341115200000L, LIMIT));
    }

    @Test
    public void testBiQuery15() throws DbException {
        run(db, new LdbcSnbBiQuery15SocialNormals("Egypt", LIMIT));
    }

    @Test
    public void testBiQuery16() throws DbException {
        run(db, new LdbcSnbBiQuery16ExpertsInSocialCircle(13194139534730L, "Germany", "MusicalArtist", 1, 2, LIMIT));
    }

    @Test
    public void testBiQuery17() throws DbException {
        run(db, new LdbcSnbBiQuery17FriendshipTriangles("Ethiopia"));
    }

    @Test
    public void testBiQuery18() throws DbException {
        run(db, new LdbcSnbBiQuery18PersonPostCounts(1311307200000L, 0, Arrays.asList("English"), LIMIT));
    }

    @Test
    public void testBiQuery19() throws DbException {
        run(db, new LdbcSnbBiQuery19StrangerInteraction(599634000000L, "MusicalArtist", "OfficeHolder", LIMIT));
    }

    @Test
    public void testBiQuery20() throws DbException {
        run(db, new LdbcSnbBiQuery20HighLevelTopics(Arrays.asList("Country"), LIMIT));
    }

    @Test
    public void testBiQuery21() throws DbException {
        run(db, new LdbcSnbBiQuery21Zombies("Ethiopia", 1357016400000L, LIMIT));
    }

    @Test
    public void testBiQuery22() throws DbException {
        run(db, new LdbcSnbBiQuery22InternationalDialog("Mexico", "Indonesia", LIMIT));
    }

    @Test
    public void testBiQuery23() throws DbException {
        run(db, new LdbcSnbBiQuery23HolidayDestinations("Ethiopia", LIMIT));
    }

    @Test
    public void testBiQuery24() throws DbException {
        run(db, new LdbcSnbBiQuery24MessagesByTopic("Single", LIMIT));
    }

    @Test
    public void testBiQuery25() throws DbException {
        run(db, new LdbcSnbBiQuery25WeightedPaths(2199023264119L, 8796093028894L, 1275364800000L, 1277956800000L));
    }

}

