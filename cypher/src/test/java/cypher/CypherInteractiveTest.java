package cypher;

import com.ldbc.impls.workloads.ldbc.snb.cypher.interactive.CypherInteractiveDb;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class CypherInteractiveTest extends InteractiveTest {

    private final String endpoint = "bolt://localhost:7687";
    private final String queryDir = "queries";
    private final String user = "neo4j";
    private final String password = "";

    public CypherInteractiveTest() {
        super(new CypherInteractiveDb());
    }

    @Override
    protected final Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", endpoint);
        properties.put("queryDir", queryDir);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "false");
        properties.put("printQueryResults", "false");
        return properties;
    }

}
