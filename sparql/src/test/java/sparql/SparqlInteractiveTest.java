package sparql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import com.ldbc.impls.workloads.ldbc.snb.sparql.interactive.SparqlInteractiveDb;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class SparqlInteractiveTest extends InteractiveTest {

	private static String endpoint = "http://localhost:5820/";
	private static String databaseName = "ldbcsf1";
	private static String queryDir = "queries/";

	public SparqlInteractiveTest() throws DbException {
		super(new SparqlInteractiveDb());
	}

	@Override
	public Map<String, String> getProperties() {
		final Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("databaseName", databaseName);
		properties.put("queryDir", queryDir);
		properties.put("printQueryNames", "false");
		properties.put("printQueryStrings", "true");
		properties.put("printQueryResults", "false");
		return properties;
	}

}
