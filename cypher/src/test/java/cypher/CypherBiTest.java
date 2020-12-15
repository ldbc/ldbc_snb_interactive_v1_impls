package cypher;

import com.ldbc.impls.workloads.ldbc.snb.bi.BiTest;
import com.ldbc.impls.workloads.ldbc.snb.cypher.bi.CypherBiDb;

import java.util.HashMap;
import java.util.Map;

public class CypherBiTest extends BiTest implements CypherSnbTest {

    public CypherBiTest() {
        super(new CypherBiDb());
    }

    @Override
    public Map<String, String> getProperties() {
        return CypherSnbTest.super.getProperties();
    }

}
