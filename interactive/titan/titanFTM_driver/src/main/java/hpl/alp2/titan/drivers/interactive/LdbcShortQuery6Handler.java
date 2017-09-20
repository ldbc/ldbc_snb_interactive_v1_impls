package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForumResult;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.Iterator;

/**
 * Implementation of LDBC Interactive workload short query 6
 * Given a Message (Post or Comment), retrieve the Forum that contains it and the Person that moderates that forum.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery6Handler implements OperationHandler<LdbcShortQuery6MessageForum,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery6Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery6MessageForum operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long mid = operation.messageId();
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Vertex m;
        try {
            boolean isComment = true;
            logger.debug("Short Query 6 called on message id: {}", mid);
            m = client.getVertex(mid, "Comment");
            if (m==null) {
                m = client.getVertex(mid, "Post");
                isComment = false;
            }

            GremlinPipeline<Vertex,Vertex> gp = new GremlinPipeline<>(m);
            Iterator<Row> qResult;
            if (isComment) {
                qResult = gp.as("start").out("replyOf")
                        .loop("start", QueryUtils.LOOPTRUEFUNC, QueryUtils.LOOPTRUEFUNC).filter(QueryUtils.ONLYPOSTS).as("post")
                        .in("containerOf").as("forum").out("hasModerator").as("person").select();
            } else {

                qResult = gp.in("containerOf").as("forum").out("hasModerator").as("person").select();
            }


            if (!qResult.hasNext())
            {
                logger.error("Unexpected empty set");
                resultReporter.report(-1, new LdbcShortQuery6MessageForumResult(0,"",0,"",""), operation);
            } else {

            Row r = qResult.next();
                Vertex forum = (Vertex)r.getColumn("forum");
                Vertex person = (Vertex)r.getColumn("person");

                LdbcShortQuery6MessageForumResult res = new LdbcShortQuery6MessageForumResult(
                        client.getVLocalId((Long)forum.getId()),
                        (String)forum.getProperty("title"),
                        client.getVLocalId((Long)person.getId()),
                        (String) person.getProperty("firstName"),(String) person.getProperty("lastName"));
                resultReporter.report(1, res, operation); }
        } catch (SchemaViolationException e) {
        e.printStackTrace();
            resultReporter.report(-1, new LdbcShortQuery6MessageForumResult(0,"",0,"",""), operation);

    }

    }


}
