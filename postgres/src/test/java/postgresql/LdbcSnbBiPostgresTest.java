package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.test.LdbcSnbBiQueryTest;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.bi.PostgresBiDb;

import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiPostgresTest extends LdbcSnbBiQueryTest {

	private static String endpoint = "localhost:5432";
	private static String user = "postgres";
	private static String password = "foo";
	private static String databaseName = "ldbcsf1";
	private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
	private static String queryDir = "queries/bi";

	public LdbcSnbBiPostgresTest() throws DbException {
		super(new PostgresBiDb());
	}

	@Override
	public Map<String, String> getProperties() {
		final Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("user", user);
		properties.put("password", password);
		properties.put("databaseName", databaseName);
		properties.put("jdbcDriver", jdbcDriver);
		properties.put("queryDir", queryDir);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "false");
		properties.put("printQueryResults", "false");
		return properties;
	}

}
