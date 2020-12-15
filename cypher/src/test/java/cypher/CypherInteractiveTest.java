package cypher;

import com.ldbc.impls.workloads.ldbc.snb.cypher.interactive.CypherInteractiveDb;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class CypherInteractiveTest extends InteractiveTest implements CypherSnbTest {

    public CypherInteractiveTest() {
        super(new CypherInteractiveDb());
    }

    @Override
    public Map<String, String> getProperties() {
        return CypherSnbTest.super.getProperties();
    }

}
