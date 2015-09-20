package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContentResult;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;

/**
 * Implementation of LDBC Interactive workload short query 4
 * Given a Message (Post or Comment), retrieve its content and creation date.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery4Handler implements OperationHandler<LdbcShortQuery4MessageContent,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery4Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery4MessageContent operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long mid = operation.messageId();
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Vertex m;
        try {
            logger.debug("Short Query 4 called on message id: {}", mid);
            m = client.getVertex(mid, "Comment");
            if (m==null)
                m = client.getVertex(mid, "Post");

            String content = m.getProperty("content");
            if (content.length() == 0)
                content = m.getProperty("imageFile");
            LdbcShortQuery4MessageContentResult res = new LdbcShortQuery4MessageContentResult(
                    content,(Long)m.getProperty("creationDate"));

            resultReporter.report(1, res, operation);
        } catch (SchemaViolationException e) {
        e.printStackTrace();
        resultReporter.report(-1, null, operation);
    }

    }


}