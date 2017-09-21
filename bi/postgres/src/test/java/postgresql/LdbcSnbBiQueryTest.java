package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery10TagPerson;
import com.ldbc.driver.workloads.ldbc.snb.bi.LdbcSnbBiQuery1PostingSummary;
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
import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiQueryTest {
	private static String endpoint = "localhost:5432";
	private static String user = "postgres";
	private static String password = "";
	private static String database = "bi";
	private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
	private static String queryDir = "sql/queries";
	
	private static Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();
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
	public Object runOperation(BiDb db, Operation<?> op) throws DbException {
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
		BiDb sqldb = new BiDb();
		sqldb.init(getProperties(), null, mapping);

		LdbcSnbBiQuery1PostingSummary q1 = new LdbcSnbBiQuery1PostingSummary(630);
		System.out.println(runOperation(sqldb, q1));

//		LdbcSnbBiQuery2TopTags q2 = new LdbcSnbBiQuery2TopTags(dateA, dateB, countryA, countryB, minMessageCount, limit)
//		System.out.println(runOperation(sqldb, q1));
//
//		LdbcSnbBiQuery3TagEvolution q3 = new LdbcSnbBiQuery3TagEvolution(280, 308);
//		System.out.println(runOperation(sqldb, q3));

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

		LdbcSnbBiQuery9RelatedForums q9 = new LdbcSnbBiQuery9RelatedForums("Artist", "BaseballPlayer", 100, 100);
		System.out.println(runOperation(sqldb, q9));

		LdbcSnbBiQuery10TagPerson q10 = new LdbcSnbBiQuery10TagPerson("Abraham_Lincoln", 100);
		System.out.println(runOperation(sqldb, q10));

		sqldb.close();
		workload.close();
	}
}
