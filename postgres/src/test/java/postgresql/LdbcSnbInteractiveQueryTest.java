package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcSnbInteractiveWorkload;
import com.ldbc.impls.workloads.ldbc.snb.SnbDb;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive.InteractiveDb;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LdbcSnbInteractiveQueryTest {

    private static String endpoint = "localhost:5432";
    private static String user = "postgres";
    private static String password = "foo";
    private static String databaseName = "ldbcsf01";
    private static String jdbcDriver = "org.postgresql.ds.PGPoolingDataSource";
    private static String queryDir = "queries/interactive";

    public Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", endpoint);
        properties.put("user", user);
        properties.put("password", password);
        properties.put("databaseName", databaseName);
        properties.put("jdbcDriver", jdbcDriver);
        properties.put("queryDir", queryDir);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "false");
        properties.put("printQueryResults", "false");
        return properties;
    }

	@Test
	public void testQueries() throws DbException, IOException {
		System.out.println(System.getProperty("user.dir"));
		Workload workload = new LdbcSnbInteractiveWorkload();

		@SuppressWarnings("rawtypes")
		Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
		InteractiveDb sqldb = new InteractiveDb();
		sqldb.init(getProperties(), null, mapping);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2012, 11, 28, 0, 0, 0);
        Date date = cal.getTime();

		LdbcQuery1 interactiveQ1 = new LdbcQuery1(30786325579101L, "Ian", 100);
        LdbcQuery2 interactiveQ2 = new LdbcQuery2(19791209300143L, date, 100);
        LdbcQuery3 interactiveQ3 = new LdbcQuery3(15393162790207L, "Puerto_Rico", "Republic_of_Macedonia", date, 30, 100);
        LdbcQuery4 interactiveQ4 = new LdbcQuery4(10995116278874L, date, 28, 100);
        LdbcQuery5 interactiveQ5 = new LdbcQuery5(15393162790207L, date, 100);
        LdbcQuery6 interactiveQ6 = new LdbcQuery6(30786325579101L, "Shakira", 100);
        LdbcQuery7 interactiveQ7 = new LdbcQuery7(26388279067534L, 100);
        LdbcQuery8 interactiveQ8 = new LdbcQuery8(2199023256816L, 100);
        LdbcQuery9 interactiveQ9 = new LdbcQuery9( 32985348834013L, date,100);
        LdbcQuery10 interactiveQ10 = new LdbcQuery10(30786325579101L, 7, 100);
        LdbcQuery11 interactiveQ11 = new LdbcQuery11(30786325579101L, "Puerto_Rico", 2004, 100);
        LdbcQuery12 interactiveQ12 = new LdbcQuery12(19791209300143L, "BasketballPlayer", 100);
        LdbcQuery13 interactiveQ13 = new LdbcQuery13( 32985348833679L, 26388279067108L);
        LdbcQuery14 interactiveQ14 = new LdbcQuery14( 32985348833679L, 2199023256862L);

        System.out.println(runOperation(sqldb, interactiveQ1));
        System.out.println(runOperation(sqldb, interactiveQ2));
        System.out.println(runOperation(sqldb, interactiveQ3));
        System.out.println(runOperation(sqldb, interactiveQ4));
        System.out.println(runOperation(sqldb, interactiveQ5));
        System.out.println(runOperation(sqldb, interactiveQ6));
        System.out.println(runOperation(sqldb, interactiveQ7));
        System.out.println(runOperation(sqldb, interactiveQ8));
        System.out.println(runOperation(sqldb, interactiveQ9));
        System.out.println(runOperation(sqldb, interactiveQ10));
        System.out.println(runOperation(sqldb, interactiveQ11));
        System.out.println(runOperation(sqldb, interactiveQ12));
        System.out.println(runOperation(sqldb, interactiveQ13));
        System.out.println(runOperation(sqldb, interactiveQ14));

		sqldb.close();
		workload.close();
	}

    @SuppressWarnings("unchecked")
    public Object runOperation(SnbDb<BiQueryStore> db, Operation<?> op) throws DbException {
        OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
        ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
        handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
        handler.cleanup();
        return reporter.result();
    }

}
