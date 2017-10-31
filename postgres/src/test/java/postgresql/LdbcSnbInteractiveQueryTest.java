package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.Workload;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcSnbInteractiveWorkload;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.interactive.InteractiveDb;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

@Ignore
public class LdbcSnbInteractiveQueryTest extends LdbcSnbQueryTest<InteractiveDb> {

	@Before
	public void initProperties() {
		super.initProperties();
		properties.put("queryDir", "queries/interactive");
	}

	@Test
	public void testQueries() throws DbException, IOException {
		System.out.println(System.getProperty("user.dir"));
		Workload workload = new LdbcSnbInteractiveWorkload();

		@SuppressWarnings("rawtypes")
		Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
		InteractiveDb sqldb = new InteractiveDb();
		sqldb.init(properties, null, mapping);

		LdbcQuery1 interactiveQ1 = new LdbcQuery1(1, "Abraham_Lincoln", 100);
		System.out.println(runOperation(sqldb, interactiveQ1));

		sqldb.close();
		workload.close();
	}
}
