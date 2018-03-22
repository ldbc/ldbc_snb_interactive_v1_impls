package postgresql;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiTest;
import com.ldbc.impls.workloads.ldbc.snb.postgres.bi.PostgresBiDb;

import java.util.Map;

public class PostgresBiTest extends BiTest implements PostgresSnbTest {

	static String queryDir = "queries/bi";

	public PostgresBiTest() throws DbException {
		super(new PostgresBiDb());
	}

	@Override
	public Map<String, String> getProperties() {
		final Map<String, String> properties = PostgresSnbTest.super.getProperties();
		properties.put("queryDir", queryDir);
		return properties;
	}

}
