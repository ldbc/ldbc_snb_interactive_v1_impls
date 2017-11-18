package cypher;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.test.LdbcSnbBiQueryTest;
import com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiDb;

import java.util.HashMap;
import java.util.Map;

public class LdbcSnbBiCypherTest extends LdbcSnbBiQueryTest {

	private final String endpoint = "bolt://localhost:7687";
	private final String user = "neo4j";
	private final String password = "admin";
	private final String queryDir = "queries-opencypher/";

	public LdbcSnbBiCypherTest() throws DbException {
		super(new CypherBiDb());
	}

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

}
