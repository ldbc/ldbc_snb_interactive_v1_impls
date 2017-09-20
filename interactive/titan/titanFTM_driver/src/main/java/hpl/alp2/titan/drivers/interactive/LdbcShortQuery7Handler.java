package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageRepliesResult;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Implementation of LDBC Interactive workload short query 7
 Given a Message (Post or Comment), retrieve the (1-hop) Comments that reply to it.
 In addition, return a boolean flag indicating if the author of the reply knows the author of the original message.
 If author is same as original author, return false for "knows" flag.
 Order results descending by comment identifier, then descending by author identifier.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery7Handler implements OperationHandler<LdbcShortQuery7MessageReplies,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery7Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery7MessageReplies operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long mid = operation.messageId();
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        List<LdbcShortQuery7MessageRepliesResult> result = new ArrayList<>();
        Vertex m;
        try {
            logger.debug("Short Query 7 called on message id: {}", mid);
            m = client.getVertex(mid, "Comment");
            if (m==null)
                m = client.getVertex(mid, "Post");

            GremlinPipeline<Vertex,Vertex> gp = new GremlinPipeline<>(m);
            Iterable<Row> qResult = gp.in("replyOf").as("reply").out("hasCreator").as("person")
                    .select().order(new PipeFunction<Pair<Row, Row>, Integer>() {
                        @Override
                        public Integer compute(Pair<Row, Row> argument) {
                            long cid1 = (Long)((Vertex)argument.getA().getColumn("reply")).getId();
                            long cid2 = (Long)((Vertex)argument.getB().getColumn("reply")).getId();
                            if (cid1==cid2)
                            {
                                long aid1 = (Long)((Vertex)argument.getA().getColumn("person")).getId();
                                long aid2 = (Long)((Vertex)argument.getB().getColumn("person")).getId();
                                return Long.compare(aid2,aid1);
                            } else
                                return Long.compare(cid2,cid1);
                        }
                    });

            GremlinPipeline<Vertex,Vertex> gpF = new GremlinPipeline<>(m);
            Set<Vertex> friends = new HashSet<>();
            gpF.out("hasCreator").out("knows").fill(friends);

            for (Row r : qResult) {
                Vertex reply = (Vertex) r.getColumn("reply");
                Vertex person = (Vertex) r.getColumn("person");

                String content = reply.getProperty("content");
                if (content.length() == 0)
                    content = reply.getProperty("imageFile");

                boolean knows = friends.contains(person);
                LdbcShortQuery7MessageRepliesResult res = new LdbcShortQuery7MessageRepliesResult(
                        client.getVLocalId((Long) reply.getId()),content,(Long)reply.getProperty("creationDate"),
                        client.getVLocalId((Long) person.getId()),
                        (String) person.getProperty("firstName"), (String) person.getProperty("lastName"),knows);
                resultReporter.report(1, result, operation);
                result.add(res);
            }
        } catch (SchemaViolationException e) {
        e.printStackTrace();
        resultReporter.report(-1, new ArrayList<LdbcShortQuery7MessageRepliesResult>(Collections.singletonList(new LdbcShortQuery7MessageRepliesResult(0,"",0,0,"","",false))), operation);
    }

    }


}
