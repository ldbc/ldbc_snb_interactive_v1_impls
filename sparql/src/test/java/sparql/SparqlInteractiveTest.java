package sparql;

import com.google.common.collect.ImmutableList;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import com.ldbc.impls.workloads.ldbc.snb.sparql.interactive.StardogInteractiveDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.interactive.VirtuosoInteractiveDb;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SparqlInteractiveTest extends InteractiveTest {

    private static final boolean isVirtuoso = false;

    private static BaseDb getBaseDb(){
        if (isVirtuoso) {
            return new VirtuosoInteractiveDb();
        }{
            return new StardogInteractiveDb();
        }
    }

    //     Stardog
    private static String endpointStardog = "http://localhost:5820/";
    //     Virtuoso
    private static String endpointVirtuoso = "localhost:1127";
    private static String databaseName = "ldbcsf1";
    private static String queryDir = "queries";
    private static String graphUri = "http://www.ldbc.eu";

    public SparqlInteractiveTest() {
        super(getBaseDb());
    }

    public void setUp() {

    }

    @Override
    public Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        if (isVirtuoso) {
            properties.put("endpoint", endpointVirtuoso);
        }{
            properties.put("endpoint", endpointStardog);
        }
        properties.put("graphUri", graphUri);
        properties.put("databaseName", databaseName);
        properties.put("queryDir", queryDir);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "true");
        properties.put("printQueryResults", "true");
        return properties;
    }

    @Ignore
    @Test
    public void testQuery13() {}

    @Ignore
    @Test
    public void testQuery14() {}

}
