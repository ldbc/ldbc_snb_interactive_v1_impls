package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDb;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class LdbcSnbQueryTest<TDb extends JdbcDb> {

    private static String endpoint = "localhost:5432";
    private static String user = "postgres";
    private static String password = "foo";
    private static String databaseName = "ldbcsf1";
    private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";

    protected static Map<String, String> properties = new HashMap<>();

    @Before
    public void initProperties() {
        properties.put("endpoint", endpoint);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("databaseName", databaseName);
        properties.put("jdbcDriver", jdbcDriver);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "true");
        properties.put("printQueryResults", "true");
    }

    public Object runOperation(TDb db, Operation<?> op) throws DbException {
        OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
        ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
        handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
        handler.cleanup();
        return reporter.result();
    }

}
