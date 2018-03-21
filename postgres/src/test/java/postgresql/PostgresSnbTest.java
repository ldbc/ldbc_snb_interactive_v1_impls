package postgresql;

import java.util.HashMap;
import java.util.Map;

public interface PostgresSnbTest {

    String endpoint = "localhost:5432";
    String user = "postgres";
    String password = "foo";
    String databaseName = "ldbcsf1";
    String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";

    default Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", endpoint);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("databaseName", databaseName);
        properties.put("jdbcDriver", jdbcDriver);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "false");
        properties.put("printQueryResults", "false");
        return properties;
    }

}
