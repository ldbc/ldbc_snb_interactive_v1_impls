package postgresql;

import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import com.ldbc.impls.workloads.ldbc.snb.postgres.interactive.PostgresInteractiveDb;
import org.junit.Ignore;

import java.util.Map;

@Ignore
public class PostgresInteractiveTest extends InteractiveTest implements PostgresSnbTest {

    public PostgresInteractiveTest() {
        super(new PostgresInteractiveDb());
    }

    @Override
    public Map<String, String> getProperties() {
        return PostgresSnbTest.super.getProperties();
    }

}
