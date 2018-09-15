package cypher;

import com.ldbc.impls.workloads.ldbc.snb.cypher.interactive.CypherInteractiveDb;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class CypherInteractiveTest extends InteractiveTest {

    private final String endpoint = "bolt://geraint-oc.db.bme.hu:7687";
    private final String user = "neo4j";
    private final String password = "dba";
    private final String queryDir = "queries";

    public CypherInteractiveTest() {
        super(new CypherInteractiveDb());
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
