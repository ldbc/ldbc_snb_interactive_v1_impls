package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreatorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;

/**
 * Implementation of LDBC Interactive workload short query 5
 * Given a Message (Post or Comment), retrieve its author.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery5Handler implements OperationHandler<LdbcShortQuery5MessageCreator,JanusGraphDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery5Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery5MessageCreator operation, JanusGraphDb.BasicDbConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException {
       /* long mid = operation.messageId();
        JanusGraphDb.BasicClient client = dbConnectionState.client();
        Vertex m;
        try {
            logger.debug("Short Query 5 called on message id: {}", mid);
            m = client.getVertex(mid, "Comment");
            if (m==null)
                m = client.getVertex(mid, "Post");

            GremlinPipeline<Vertex,Vertex> gp = new GremlinPipeline<>(m);
            Vertex person = gp.out("hasCreator").next();
            LdbcShortQuery5MessageCreatorResult res = new LdbcShortQuery5MessageCreatorResult(
                        client.getVLocalId((Long)person.getId()),
                        (String) person.getProperty("firstName"),(String) person.getProperty("lastName"));
            resultReporter.report(1, res, operation);

        } catch (SchemaViolationException e) {
        e.printStackTrace();
        resultReporter.report(-1, null, operation);
    }
    */

    }
}