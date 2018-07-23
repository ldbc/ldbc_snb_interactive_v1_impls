package postgresql;

import com.ldbc.impls.workloads.ldbc.snb.bi.BiTest;
import com.ldbc.impls.workloads.ldbc.snb.postgres.bi.PostgresBiDb;

import java.util.Map;

public class PostgresBiTest extends BiTest implements PostgresSnbTest {

    public PostgresBiTest() {
        super(new PostgresBiDb());
    }

    @Override
    public Map<String, String> getProperties() {
        return PostgresSnbTest.super.getProperties();
    }

}
