package com.ldbc.impls.workloads.ldbc.snb.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.SnbTest;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import org.junit.Ignore;
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
        run(db, new LdbcSnbBiQuery2TagEvolution(2015, 12, 100));
    }

    @Test
    public void testBiQuery3() throws DbException {
        run(db, new LdbcSnbBiQuery3PopularCountryTopics("MusicalArtist", "Netherlands", LIMIT));
    }

    @Test
    public void testBiQuery4() throws DbException {
        run(db, new LdbcSnbBiQuery4TopCountryPosters("Ethiopia", LIMIT));
    }

    @Test
    public void testBiQuery5() throws DbException {
        run(db, new LdbcSnbBiQuery5ActivePosters("Ehud_Olmert", LIMIT));
    }

    @Test
    public void testBiQuery6() throws DbException {
        run(db, new LdbcSnbBiQuery6AuthoritativeUsers("Che_Guevara", LIMIT));
    }

    @Test
    public void testBiQuery7() throws DbException {
        run(db, new LdbcSnbBiQuery7RelatedTopics("Imelda_Marcos", LIMIT));
    }

    @Test
    public void testBiQuery8() throws DbException {
        run(db, new LdbcSnbBiQuery8TagPerson("Che_Guevara", 1311307200000L, LIMIT));
    }

    @Test
    public void testBiQuery9() throws DbException {
        run(db, new LdbcSnbBiQuery9TopThreadInitiators(1338523200000L, 1341115200000L, LIMIT));
    }

    @Test
    public void testBiQuery10() throws DbException {
        run(db, new LdbcSnbBiQuery10ExpertsInSocialCircle(13194139534730L, "Germany", "MusicalArtist", 1, 2, LIMIT));
    }

    @Test
    public void testBiQuery11() throws DbException {
        run(db, new LdbcSnbBiQuery11FriendshipTriangles("Ethiopia"));
    }

    @Test
    public void testBiQuery12() throws DbException {
        run(db, new LdbcSnbBiQuery12PersonPostCounts(1311307200000L, 0, Arrays.asList("English"), LIMIT));
    }

    @Test
    public void testBiQuery13() throws DbException {
        run(db, new LdbcSnbBiQuery13Zombies("Ethiopia", 1357016400000L, LIMIT));
    }

    @Test
    public void testBiQuery14() throws DbException {
        run(db, new LdbcSnbBiQuery14InternationalDialog("Mexico", "Indonesia", LIMIT));
    }

    @Test
    public void testBiQuery15() throws DbException {
        run(db, new LdbcSnbBiQuery15WeightedPaths(2199023264119L, 8796093028894L, 1275364800000L, 1277956800000L));
    }

    @Ignore
    @Test
    public void testBiQuery16() throws DbException {
        run(db, new LdbcSnbBiQuery16FakeNewsDetection("Imelda_Marcos", 1317859200L, "Che", 1318377600L, 5, 10));
    }

    @Ignore
    @Test
    public void testBiQuery17() throws DbException {
        run(db, new LdbcSnbBiQuery17InformationPropagationAnalysis("Elizabeth_Taylor", 10, 20));
    }

    @Ignore
    @Test
    public void testBiQuery18() throws DbException {
        run(db, new LdbcSnbBiQuery18FriendRecommendation(290L, "Elizabeth_Taylor", 20));
    }

    @Ignore
    @Test
    public void testBiQuery19() throws DbException {
        run(db, new LdbcSnbBiQuery19InteractionPathBetweenCities(1178L, 1142L));
    }

    @Ignore
    @Test
    public void testBiQuery20() throws DbException {
        run(db, new LdbcSnbBiQuery20Recruitment("TajAir", 13194139533688L));
    }

}

