package cypher;

import com.ldbc.impls.workloads.ldbc.snb.cypher.interactive.CypherInteractiveDb;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import org.junit.Ignore;

import java.util.Map;

@Ignore
public class CypherInteractiveTest extends InteractiveTest {

    private final String endpoint = "bolt://localhost:7687";
    private final String user = "neo4j";
    private final String password = "admin";
    private final String queryDir = "queries/";

    public CypherInteractiveTest() {
        super(new CypherInteractiveDb());
    }

    @Override
    protected final Map<String, String> getProperties() {
        throw new UnsupportedOperationException();
    }

}
