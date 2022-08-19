package umbra;

import org.ldbcouncil.snb.impls.workloads.interactive.InteractiveTest;
import org.ldbcouncil.snb.impls.workloads.umbra.interactive.UmbraInteractiveDb;

import java.util.HashMap;
import java.util.Map;

public class UmbraInteractiveTest extends InteractiveTest {

    public UmbraInteractiveTest() {
        super(new UmbraInteractiveDb());
    }

    String endpoint = "localhost:8000";
    String user = "postgres";
    String password = "mysecretpassword";
    String databaseName = "ldbcsnb";
    String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
    String queryDir = "queries";

    public Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", endpoint);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("databaseName", databaseName);
        properties.put("jdbcDriver", jdbcDriver);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "true");
        properties.put("printQueryResults", "true");
        properties.put("queryDir", queryDir);
        return properties;
    }

}
