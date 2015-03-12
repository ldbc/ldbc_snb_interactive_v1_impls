package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Adds like from person to comment, assumes person and comment exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU3Handler implements OperationHandler<LdbcUpdate3AddCommentLike,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU3Handler.class);

    @Override
    public void executeOperation(LdbcUpdate3AddCommentLike operation, TitanFTMDb.BasicDbConnectionState dbConnectionState, ResultReporter reporter) throws DbException {

        TitanFTMDb.BasicClient client = dbConnectionState.client();

        try {
            Vertex person = client.getVertex(operation.personId(), "Person");
            Vertex post = client.getVertex(operation.commentId(), "Comment");
            Map<String, Object> props = new HashMap<>(1);
            props.put("creationDate", operation.creationDate().getTime());
            client.addEdge(person, post, "likes", props);

        } catch (SchemaViolationException e) {
            logger.error("invalid vertex label requested by query update");
            e.printStackTrace();
        }


        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
