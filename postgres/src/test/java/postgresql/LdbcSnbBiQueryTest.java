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
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.bi.BiDb;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiQueryTest {
	private static String endpoint = "localhost:5432";
	private static String user = "postgres";
	private static String password = "foo";
	private static String database = "ldbcsf1";
	private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
	private static String queryDir = "sql/bi";

	private static int LIMIT = 100;
	
	private static Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("user", user);
		properties.put("password", password);
		properties.put("database", database);
		properties.put("queryDir", queryDir);
		properties.put("jdbcDriver", jdbcDriver);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "false");
		properties.put("printQueryResults", "false");
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

		run(sqldb, new LdbcSnbBiQuery1PostingSummary(630L));
//		run(sqldb, new LdbcSnbBiQuery2TopTags(0L, 1L, "United States", "Canada", LIMIT));
//		run(sqldb, new LdbcSnbBiQuery3TagEvolution(2015, 12, 100 ));
		run(sqldb, new LdbcSnbBiQuery4PopularCountryTopics("Artist", "United_States", LIMIT));
		run(sqldb, new LdbcSnbBiQuery5TopCountryPosters("Cameroon", 100, LIMIT));
		run(sqldb, new LdbcSnbBiQuery6ActivePosters("Al_Gore", LIMIT));
		run(sqldb, new LdbcSnbBiQuery7AuthoritativeUsers("Abraham_Lincoln", LIMIT));
		run(sqldb, new LdbcSnbBiQuery8RelatedTopics("Abraham_Lincoln", LIMIT));
		run(sqldb, new LdbcSnbBiQuery9RelatedForums("Artist", "BaseballPlayer", 100, LIMIT));
		run(sqldb, new LdbcSnbBiQuery10TagPerson("Abraham_Lincoln", 0, LIMIT));
//		run(sqldb, new LdbcSnbBiQuery11UnrelatedReplies("United States", Arrays.asList("someWord"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery12TrendingPosts(0L, 0, LIMIT));
		run(sqldb, new LdbcSnbBiQuery13PopularMonthlyTags("United States", LIMIT));
		run(sqldb, new LdbcSnbBiQuery14TopThreadInitiators(0L, 1L, LIMIT));
		run(sqldb, new LdbcSnbBiQuery15SocialNormals("United States", LIMIT));
		run(sqldb, new LdbcSnbBiQuery16ExpertsInSocialCircle(0L, "Artist", "Unites States", 0, 100, LIMIT));
		run(sqldb, new LdbcSnbBiQuery17FriendshipTriangles("Unites States"));
		run(sqldb, new LdbcSnbBiQuery18PersonPostCounts(0L, 0, Arrays.asList("English"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery19StrangerInteraction(0L, "Artist", "Movie", LIMIT));
		run(sqldb, new LdbcSnbBiQuery20HighLevelTopics(Arrays.asList("Artist"), LIMIT));
		run(sqldb, new LdbcSnbBiQuery21Zombies("United States", 0L, 0, LIMIT));
		run(sqldb, new LdbcSnbBiQuery22InternationalDialog("United States", "Canada", LIMIT));
		run(sqldb, new LdbcSnbBiQuery23HolidayDestinations("United States", LIMIT));
		run(sqldb, new LdbcSnbBiQuery24MessagesByTopic("Artist", LIMIT));
//		run(sqldb, new LdbcSnbBiQuery25WeightedPaths(0L, 1L, 0L, 1L));

		sqldb.close();
		workload.close();
	}

	private void run( BiDb sqldb, Operation op ) throws DbException {
		runOperation( sqldb, op );
	}
}
