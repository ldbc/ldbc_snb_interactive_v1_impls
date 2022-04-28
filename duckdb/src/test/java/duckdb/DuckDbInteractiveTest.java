package duckdb;

import org.ldbcouncil.snb.impls.workloads.duckdb.interactive.DuckDbInteractiveDb;
import org.ldbcouncil.snb.impls.workloads.interactive.InteractiveTest;

import java.util.HashMap;
import java.util.Map;

public class DuckDbInteractiveTest extends InteractiveTest {

    public DuckDbInteractiveTest() {
        super(new DuckDbInteractiveDb());
    }

    String queryDir = "queries";

    public Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "true");
        properties.put("printQueryResults", "true");
        properties.put("queryDir", queryDir);
        return properties;
    }

}
