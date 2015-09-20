package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Add knows edge between two given people assumed to exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU8Handler implements OperationHandler<LdbcUpdate8AddFriendship,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU8Handler.class);

    @Override
    public void executeOperation(LdbcUpdate8AddFriendship operation, TitanFTMDb.BasicDbConnectionState dbConnectionState, ResultReporter reporter) throws DbException {

        TitanFTMDb.BasicClient client = dbConnectionState.client();
        try {

            Map<String, Object> props = new HashMap<>(1);
            props.put("creationDate", operation.creationDate().getTime());
            Vertex person = client.getVertex(operation.person1Id(), "Person");
            Vertex friend = client.getVertex(operation.person2Id(), "Person");
            client.addEdge(person, friend, "knows", props);

        } catch (SchemaViolationException e) {
            logger.error("invalid vertex label requested by query update");
            e.printStackTrace();
        }

        reporter.report(0, LdbcNoResult.INSTANCE, operation);
    }
}
