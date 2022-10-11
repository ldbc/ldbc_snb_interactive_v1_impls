package graphdb;

import java.util.HashMap;
import java.util.Map;

import com.ldbc.impls.workloads.ldbc.snb.graphdb.interactive.GraphDBInteractive;
import org.ldbcouncil.snb.impls.workloads.interactive.InteractiveTest;

public class GraphDBInteractiveTest extends InteractiveTest {

	private final String endpoint = "http://localhost:7200/repositories/test_benchmark_update";
	private final String user = "admin";
	private final String password = "root";
	private final String queryDir = "queries";

	public GraphDBInteractiveTest() {
		super(new GraphDBInteractive());
	}

	@Override
	protected final Map<String, String> getProperties() {
		final Map<String, String> properties = new HashMap<>();
		properties.put("endpoint", endpoint);
		properties.put("user", user);
		properties.put("password", password);
		properties.put("queryDir", queryDir);
		properties.put("printQueryNames", "true");
		properties.put("printQueryStrings", "true");
		properties.put("printQueryResults", "true");
		return properties;
	}

}

