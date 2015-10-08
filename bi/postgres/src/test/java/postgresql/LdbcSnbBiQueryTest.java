package postgresql;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery3TagEvolution;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery4PopularCountryTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery5TopCountryPosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery6ActivePosters;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery7AuthoritativeUsers;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery8RelatedTopics;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery9RelatedForums;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiWorkload;
import com.ldbc.impls.workloads.ldbc.snb.bi.jdbc.db.BiJdbcDb;

public class LdbcSnbBiQueryTest {
	private static String endpoint = "localhost:5432";
	private static String user = "foo";
	private static String password = "bar";
	private static String database = "ldbcsf1";
	private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
	private static String queryDir = "src/main/sql/postgres/queries/bi";
	
	private static Map<String, String> getProperties() {
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("endpoint", endpoint);
		properties.put("user", user);
		properties.put("password", password);
		properties.put("database", database);
		properties.put("queryDir", queryDir);
		properties.put("jdbcDriver", jdbcDriver);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "true");
		properties.put("printQueryResults", "true");
		return properties;
	}
	
	@SuppressWarnings("unchecked")
	public static Object runOperation(BiJdbcDb db, Operation<?> op) throws DbException {
		OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
		ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
		handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
		handler.cleanup();
		return reporter.result();
	}
	
	@Test
	public void testQueries() throws DbException, IOException {
		System.out.println(System.getProperty("user.dir"));
		Workload workload = new LdbcSnbBiWorkload();
		@SuppressWarnings("rawtypes")
		Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
		BiJdbcDb sqldb = new BiJdbcDb();
		sqldb.init(getProperties(), null, mapping);

		LdbcSnbBiQuery1PostingSummary q1 = new LdbcSnbBiQuery1PostingSummary(630);
		System.out.println(runOperation(sqldb, q1));
//		
////		LdbcSnbBiQuery2TopTags q2 = new LdbcSnbBiQuery2TopTags(dateA, dateB, countryA, countryB, minMessageCount, limit)
////		System.out.println(runOperation(sqldb, q1));

		LdbcSnbBiQuery3TagEvolution q3 = new LdbcSnbBiQuery3TagEvolution(280, 308);
		System.out.println(runOperation(sqldb, q3));

		LdbcSnbBiQuery4PopularCountryTopics q4 = new LdbcSnbBiQuery4PopularCountryTopics("Artist", "United_States", 100);
		System.out.println(runOperation(sqldb, q4));
		
		LdbcSnbBiQuery5TopCountryPosters q5 = new LdbcSnbBiQuery5TopCountryPosters("Cameroon", 100, 100);
		System.out.println(runOperation(sqldb, q5));
		
		LdbcSnbBiQuery6ActivePosters q6 = new LdbcSnbBiQuery6ActivePosters("Al_Gore", 100);
		System.out.println(runOperation(sqldb, q6));
		
		LdbcSnbBiQuery7AuthoritativeUsers q7 = new LdbcSnbBiQuery7AuthoritativeUsers("Abraham_Lincoln", 100);
		System.out.println(runOperation(sqldb, q7));
		
		LdbcSnbBiQuery8RelatedTopics q8 = new LdbcSnbBiQuery8RelatedTopics("Abraham_Lincoln", 100);
		System.out.println(runOperation(sqldb, q8));
		
		LdbcSnbBiQuery9RelatedForums q9 = new LdbcSnbBiQuery9RelatedForums("Artist", "BaseballPlayer", 100);
		System.out.println(runOperation(sqldb, q9));
		
		LdbcSnbBiQuery10TagPerson q10 = new LdbcSnbBiQuery10TagPerson("Abraham_Lincoln", 100);
		System.out.println(runOperation(sqldb, q10));
		
		sqldb.close();
		workload.close();
	}
}
