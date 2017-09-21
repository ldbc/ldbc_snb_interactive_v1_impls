package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcSnbInteractiveWorkload;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive.InteractiveDb;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Ignore
public class LdbcSnbInteractiveQueryTest {
	private static String endpoint = "localhost:5432";
	private static String user = "postgres";
	private static String password = "foo";
	private static String database = "ldbcsf1";
	private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
	private static String queryDir = "sql/interactive";
	
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
	public Object runOperation(InteractiveDb db, Operation<?> op) throws DbException {
		OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
		ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
		handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
		handler.cleanup();
		return reporter.result();
	}
	
	@Test
	public void testQueries() throws DbException, IOException {
		System.out.println(System.getProperty("user.dir"));
		Workload workload = new LdbcSnbInteractiveWorkload();

		@SuppressWarnings("rawtypes")
		Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
		InteractiveDb sqldb = new InteractiveDb();
		sqldb.init(getProperties(), null, mapping);

		LdbcQuery1 interactiveQ1 = new LdbcQuery1(1, "Abraham_Lincoln", 100);
		System.out.println(runOperation(sqldb, interactiveQ1));

		sqldb.close();
		workload.close();
	}
}
