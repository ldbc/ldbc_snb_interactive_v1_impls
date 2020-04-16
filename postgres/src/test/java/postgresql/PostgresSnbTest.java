package postgresql;

import java.util.HashMap;
import java.util.Map;

public interface PostgresSnbTest {

    String endpoint = "localhost:5432";
    String user = "postgres";
    String password = "foo";
    String databaseName = "ldbcsftest";
    String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
    String queryDir = "queries";

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
        properties.put("queryDir", queryDir);
        return properties;
    }

}
