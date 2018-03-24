package sparql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiTest;
import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlBiDb;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class SparqlBiTest extends BiTest {

    private static String endpoint = "http://localhost:5820/";
    private static String databaseName = "ldbcsf1";
    private static String queryDir = "queries/";

    public SparqlBiTest() throws DbException {
        super(new SparqlBiDb());
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
