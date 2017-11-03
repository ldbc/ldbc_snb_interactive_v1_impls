package cypher;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery11UnrelatedReplies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery16ExpertsInSocialCircle;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
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
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.bi.test.LdbcSnbBiQueryTest;
import com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiDb;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiCypherTest extends LdbcSnbBiQueryTest {
	private final String endpoint = "bolt://localhost:7687";
	private final String user = "neo4j";
	private final String password = "admin";
	private final String queryDir = "queries/";

	@Override
	protected final Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("user", user);
		properties.put("password", password);
		properties.put("queryDir", queryDir);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "true");
		properties.put("printQueryResults", "false");
		return properties;
	}

	@SuppressWarnings("unchecked")
	public Object runOperation(CypherBiDb db, Operation<?> op) throws DbException {
		OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
		ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
		handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
		handler.cleanup();
		return reporter.result();
	}

	@Test
	public void testQueries() throws DbException, IOException {
		Workload workload = new LdbcSnbBiWorkload();

		@SuppressWarnings("rawtypes")
		Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
		CypherBiDb sqldb = new CypherBiDb();
		sqldb.init(getProperties(), null, mapping);

		run(sqldb, new LdbcSnbBiQuery1PostingSummary(1311307200000L));
		run(sqldb, new LdbcSnbBiQuery2TopTags(1262322000000L, 128919240000L, "Ethiopia", "Spain", LIMIT));
		run(sqldb, new LdbcSnbBiQuery3TagEvolution(2015, 12, 100 ));
		run(sqldb, new LdbcSnbBiQuery4PopularCountryTopics("MusicalArtist", "Netherlands", LIMIT));
		run(sqldb, new LdbcSnbBiQuery5TopCountryPosters("Ethiopia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery6ActivePosters("Ehud_Olmert", LIMIT));
//		run(sqldb, new LdbcSnbBiQuery7AuthoritativeUsers("Che_Guevara", LIMIT));
		run(sqldb, new LdbcSnbBiQuery8RelatedTopics("Imelda_Marcos", LIMIT));
		run(sqldb, new LdbcSnbBiQuery9RelatedForums("BaseballPlayer", "ChristianBishop", 200, LIMIT));
//		run(sqldb, new LdbcSnbBiQuery10TagPerson("Che_Guevara", 1311307200000L, LIMIT));
		run(sqldb, new LdbcSnbBiQuery11UnrelatedReplies("Germany", Arrays.asList("also"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery12TrendingPosts(1311307200000L, 400, LIMIT));
		run(sqldb, new LdbcSnbBiQuery13PopularMonthlyTags("Ethiopia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery14TopThreadInitiators(1338523200000L, 1341115200000L, LIMIT));
		run(sqldb, new LdbcSnbBiQuery15SocialNormals("Ethiopia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery16ExpertsInSocialCircle(19791209310731L, "MusicalArtist", "Germany", 1, 2, LIMIT));
		run(sqldb, new LdbcSnbBiQuery17FriendshipTriangles("Ethiopia"));
		run(sqldb, new LdbcSnbBiQuery18PersonPostCounts(1311307200000L, 0, Arrays.asList("English"), LIMIT));
//		run(sqldb, new LdbcSnbBiQuery19StrangerInteraction(599634000000L, "MusicalArtist", "OfficeHolder", LIMIT));
		run(sqldb, new LdbcSnbBiQuery20HighLevelTopics(Arrays.asList("Country"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery21Zombies("Ethiopia", 1357016400000L, 0, LIMIT));
		run(sqldb, new LdbcSnbBiQuery22InternationalDialog("Mexico", "Indonesia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery23HolidayDestinations("Ethiopia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery24MessagesByTopic("Single", LIMIT));
		run(sqldb, new LdbcSnbBiQuery25WeightedPaths(0L, 1L, 0L, 1L));

		sqldb.close();
		workload.close();
	}

	private void run(CypherBiDb sqldb, Operation op ) throws DbException {
		runOperation( sqldb, op );
	}
}
