package com.ldbc.impls.workloads.ldbc.snb.bi.test;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.SnbDb;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public abstract class LdbcSnbBiQueryTest {

    protected final SnbDb db;
    protected final Workload workload;

    public LdbcSnbBiQueryTest(SnbDb db) throws DbException {
        this.db = db;
        this.workload = new LdbcSnbBiWorkload();

        @SuppressWarnings("rawtypes")
        Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
        db.init(getProperties(), null, mapping);
    }

    @SuppressWarnings("unchecked")
    public Object runOperation(SnbDb<BiQueryStore> db, Operation<?> op) throws DbException {
        OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
        ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
        handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
        handler.cleanup();
        return reporter.result();
    }

    protected final int LIMIT = 100;
    protected abstract Map<String, String> getProperties();

    @Test
    public void testQueries() throws DbException, IOException {
//        run(db, new LdbcSnbBiQuery1PostingSummary(1311307200000L));
//        run(db, new LdbcSnbBiQuery2TopTags(1262322000000L, 128919240000L, "Ethiopia", "Spain", LIMIT));
//        run(db, new LdbcSnbBiQuery3TagEvolution(2015, 12, 100 ));
//        run(db, new LdbcSnbBiQuery4PopularCountryTopics("MusicalArtist", "Netherlands", LIMIT));
//        run(db, new LdbcSnbBiQuery5TopCountryPosters("Ethiopia", LIMIT));
//        run(db, new LdbcSnbBiQuery6ActivePosters("Ehud_Olmert", LIMIT));
//        run(db, new LdbcSnbBiQuery7AuthoritativeUsers("Che_Guevara", LIMIT));
//        run(db, new LdbcSnbBiQuery8RelatedTopics("Imelda_Marcos", LIMIT));
//        run(db, new LdbcSnbBiQuery9RelatedForums("BaseballPlayer", "ChristianBishop", 200, LIMIT));
//        run(db, new LdbcSnbBiQuery10TagPerson("Che_Guevara", 1311307200000L, LIMIT));
//        run(db, new LdbcSnbBiQuery11UnrelatedReplies("Germany", Arrays.asList("also"), LIMIT));
//        run(db, new LdbcSnbBiQuery12TrendingPosts(1311307200000L, 400, LIMIT));
//        run(db, new LdbcSnbBiQuery13PopularMonthlyTags("Ethiopia", LIMIT));
//        run(db, new LdbcSnbBiQuery14TopThreadInitiators(1338523200000L, 1341115200000L, LIMIT));
//        run(db, new LdbcSnbBiQuery15SocialNormals("Ethiopia", LIMIT));
//        run(db, new LdbcSnbBiQuery16ExpertsInSocialCircle(19791209310731L, "MusicalArtist", "Germany", 1, 2, LIMIT));
        run(db, new LdbcSnbBiQuery17FriendshipTriangles("Ethiopia"));
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

    private void run(SnbDb<BiQueryStore> db, Operation op ) throws DbException {
        runOperation( db, op );
    }
}

