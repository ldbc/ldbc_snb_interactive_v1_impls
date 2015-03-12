package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
import com.thinkaurelius.titan.core.TitanException;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Adds forum and it's moderator and tags. Assume moderator person and tags exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU4Handler implements OperationHandler<LdbcUpdate4AddForum,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU4Handler.class);

    @Override
    public void executeOperation(LdbcUpdate4AddForum operation, TitanFTMDb.BasicDbConnectionState dbConnectionState, ResultReporter reporter) throws DbException {

        TitanFTMDb.BasicClient client = dbConnectionState.client();

        try {
            Map<String, Object> props = new HashMap<>(2);
            props.put("title", operation.forumTitle());
            props.put("creationDate", operation.creationDate().getTime());
            logger.debug("U4 Adding forum {} , {}" , operation.forumId(), props.toString());
            Vertex forum = client.addVertex(operation.forumId(), "Forum", props);
            Map<String, Object> eProps = new HashMap<>(0);
            for (Long tagID : operation.tagIds()) {
                Vertex tagV = client.getVertex(tagID, "Tag");
                client.addEdge(forum, tagV, "hasTag", eProps);
            }

            Vertex mod = client.getVertex(operation.moderatorPersonId(), "Person");
            client.addEdge(forum, mod, "hasModerator", eProps);
            logger.debug("U4 completed Adding forum {} " , operation.forumId());

        } catch (SchemaViolationException e) {
            logger.error("invalid vertex label requested by query update");
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (TitanException e) {
            logger.error("Couldn't complete U4 handler, db didn't commit");
            e.printStackTrace();
        }
        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
