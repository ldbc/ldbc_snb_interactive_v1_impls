package sparql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.test.LdbcSnbBiQueryTest;
import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlBiDb;

import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiSparqlTest extends LdbcSnbBiQueryTest {

	private static String endpoint = "http://localhost:9999/blazegraph";
	private static String queryDir = "queries/";

	public LdbcSnbBiSparqlTest() throws DbException {
		super(new SparqlBiDb() {});
	}

	@Override
	public Map<String, String> getProperties() {
		final Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("queryDir", queryDir);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "false");
		properties.put("printQueryResults", "false");
		return properties;
	}

}

