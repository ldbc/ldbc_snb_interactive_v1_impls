package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import com.ldbc.impls.workloads.ldbc.snb.postgres.interactive.PostgresInteractiveDb;

import java.util.Map;

public class PostgresInteractiveTest extends InteractiveTest implements PostgresSnbTest {

    static String queryDir = "queries/";

    public PostgresInteractiveTest() throws DbException {
        super(new PostgresInteractiveDb());
    }

    @Override
    public Map<String, String> getProperties() {
        final Map<String, String> properties = PostgresSnbTest.super.getProperties();
        properties.put("queryDir", queryDir);
        return properties;
    }

}
