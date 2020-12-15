package cypher;

import java.util.HashMap;
import java.util.Map;

public interface CypherSnbTest {

    String endpoint = "bolt://localhost:7687";
    String queryDir = "queries";
    String user = "neo4j";
    String password = "";

    default Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", endpoint);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("queryDir", queryDir);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "false");
        properties.put("printQueryResults", "false");
        return properties;
    }

}
