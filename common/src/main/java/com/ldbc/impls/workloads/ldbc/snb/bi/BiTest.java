package com.ldbc.impls.workloads.ldbc.snb.bi;

import com.ldbc.driver.DbException;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.SnbTest;
import com.ldbc.impls.workloads.ldbc.snb.db.SnbDb;
import org.junit.Test;

import java.io.IOException;

public abstract class BiTest extends SnbTest {

    public BiTest(SnbDb db) throws DbException {
        super(db, new LdbcSnbBiWorkload());
    }

    @Test
    public void testQueries() throws DbException, IOException {
        run(db, new LdbcSnbBiQuery1PostingSummary(1311307200000L));
//        run(db, new LdbcSnbBiQuery2TopTags(1265583600000L, 1290380400000L, "Germany", "United_States", LIMIT));
//        run(db, new LdbcSnbBiQuery3TagEvolution(2015, 12, 100 ));
//        run(db, new LdbcSnbBiQuery4PopularCountryTopics("MusicalArtist", "Netherlands", LIMIT));
//        run(db, new LdbcSnbBiQuery5TopCountryPosters("Ethiopia", LIMIT));
//        run(db, new LdbcSnbBiQuery6ActivePosters("Ehud_Olmert", LIMIT));
//        run(db, new LdbcSnbBiQuery7AuthoritativeUsers("Che_Guevara", LIMIT));
//        run(db, new LdbcSnbBiQuery8RelatedTopics("Imelda_Marcos", LIMIT));
//        run(db, new LdbcSnbBiQuery9RelatedForums("BaseballPlayer", "ChristianBishop", 200, LIMIT));
//        run(db, new LdbcSnbBiQuery10TagPerson("Che_Guevara", 1311307200000L, LIMIT));
//        run(db, new LdbcSnbBiQuery11UnrelatedReplies("Germany", Arrays.asList("also"), LIMIT));
//        run(db, new LdbcSnbBiQuery12TrendingPosts(1311307200000L, 100, LIMIT));
//        run(db, new LdbcSnbBiQuery13PopularMonthlyTags("Ethiopia", LIMIT));
//        run(db, new LdbcSnbBiQuery14TopThreadInitiators(1338523200000L, 1341115200000L, LIMIT));
//        run(db, new LdbcSnbBiQuery15SocialNormals("Egypt", LIMIT));
//        run(db, new LdbcSnbBiQuery16ExpertsInSocialCircle(13194139534730L, "Germany", "MusicalArtist", 1, 2, LIMIT));
//        run(db, new LdbcSnbBiQuery17FriendshipTriangles("Ethiopia"));
//        run(db, new LdbcSnbBiQuery18PersonPostCounts(1311307200000L, 0, Arrays.asList("English"), LIMIT));
//        run(db, new LdbcSnbBiQuery19StrangerInteraction(599634000000L, "MusicalArtist", "OfficeHolder", LIMIT));
//        run(db, new LdbcSnbBiQuery20HighLevelTopics(Arrays.asList("Country"), LIMIT));
//        run(db, new LdbcSnbBiQuery21Zombies("Ethiopia", 1357016400000L, LIMIT));
//        run(db, new LdbcSnbBiQuery22InternationalDialog("Mexico", "Indonesia", LIMIT));
//        run(db, new LdbcSnbBiQuery23HolidayDestinations("Ethiopia", LIMIT));
//        run(db, new LdbcSnbBiQuery24MessagesByTopic("Single", LIMIT));
//        run(db, new LdbcSnbBiQuery25WeightedPaths(2199023264119L, 8796093028894L, 1275364800000L, 1277956800000L));

        db.close();
        workload.close();
    }

}

