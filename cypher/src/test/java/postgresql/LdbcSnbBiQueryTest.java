package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery12TrendingPosts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery13PopularMonthlyTags;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery14TopThreadInitiators;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery15SocialNormals;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery17FriendshipTriangles;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery18PersonPostCounts;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery19StrangerInteraction;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery20HighLevelTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery21Zombies;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery22InternationalDialog;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery23HolidayDestinations;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery24MessagesByTopic;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.bi.BiDb;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiQueryTest {
	private static String endpoint = "localhost:7687";
	private static String user = "neo4j";
	private static String password = "admin";
	private static String queryDir = "cypher/bi";

	private static int LIMIT = 100;

	private static Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("user", user);
		properties.put("password", password);
		properties.put("queryDir", queryDir);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "true");
		properties.put("printQueryResults", "true");
		return properties;
	}

	@SuppressWarnings("unchecked")
	public Object runOperation(BiDb db, Operation<?> op) throws DbException {
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
		BiDb sqldb = new BiDb();
		sqldb.init(getProperties(), null, mapping);

		run(sqldb, new LdbcSnbBiQuery1PostingSummary(1311307200000L));
////		run(sqldb, new LdbcSnbBiQuery2TopTags(1262322000000L, 1289192400000, "Ethiopia", "Spain", LIMIT));
//paramgen		run(sqldb, new LdbcSnbBiQuery3TagEvolution(2015, 12, 100 ));
		run(sqldb, new LdbcSnbBiQuery4PopularCountryTopics("MusicalArtist", "Netherlands", LIMIT));
		run(sqldb, new LdbcSnbBiQuery5TopCountryPosters("Ethiopia", LIMIT));
//slow		run(sqldb, new LdbcSnbBiQuery6ActivePosters("Ehud_Olmert", LIMIT));
		run(sqldb, new LdbcSnbBiQuery7AuthoritativeUsers("Che_Guevara", LIMIT));
		run(sqldb, new LdbcSnbBiQuery8RelatedTopics("Imelda_Marcos", LIMIT));
//slow		run(sqldb, new LdbcSnbBiQuery9RelatedForums("BaseballPlayer", "ChristianBishop", 200, LIMIT));
		run(sqldb, new LdbcSnbBiQuery10TagPerson("Che_Guevara", 1311307200000L, LIMIT));
//		run(sqldb, new LdbcSnbBiQuery11UnrelatedReplies("Germany", Arrays.asList("also"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery12TrendingPosts(1311307200000L, 400, LIMIT));
		run(sqldb, new LdbcSnbBiQuery13PopularMonthlyTags("Ethiopia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery14TopThreadInitiators(1338523200000L, 1341115200000L, LIMIT));
		run(sqldb, new LdbcSnbBiQuery15SocialNormals("Ethiopia", LIMIT));
//paramgen distances		run(sqldb, new LdbcSnbBiQuery16ExpertsInSocialCircle(19791209310731L, "MusicalArtist", "Germany", 0, 100, LIMIT));
		run(sqldb, new LdbcSnbBiQuery17FriendshipTriangles("Ethiopia"));
		run(sqldb, new LdbcSnbBiQuery18PersonPostCounts(1311307200000L, 0, Arrays.asList("English"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery19StrangerInteraction(599634000000L, "MusicalArtist", "OfficeHolder", LIMIT));
		run(sqldb, new LdbcSnbBiQuery20HighLevelTopics(Arrays.asList("Country"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery21Zombies("Ethiopia", 1357016400000L, 0, LIMIT));
		run(sqldb, new LdbcSnbBiQuery22InternationalDialog("Mexico", "Indonesia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery23HolidayDestinations("Ethiopia", LIMIT));
		run(sqldb, new LdbcSnbBiQuery24MessagesByTopic("Single", LIMIT));
//		run(sqldb, new LdbcSnbBiQuery25WeightedPaths(0L, 1L, 0L, 1L));

		sqldb.close();
		workload.close();
	}

	private void run( BiDb sqldb, Operation op ) throws DbException {
		runOperation( sqldb, op );
	}
}
