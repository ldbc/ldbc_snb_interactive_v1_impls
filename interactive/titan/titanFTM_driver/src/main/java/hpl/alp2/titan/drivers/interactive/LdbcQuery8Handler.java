package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery8Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given a start Person, find (most recent) Comments that are replies to Posts/Comments of the
 start Person. Only consider immediate (1-hop) replies, not the transitive (multi-hop) case. Return the top 20
 reply Comments, and the Person that created each reply Comment. Sort results descending by creation date
 of reply Comment, and then ascending by identifier of reply Comment.
 */
public class LdbcQuery8Handler implements OperationHandler<LdbcQuery8,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery8Handler.class);
    @Override
    public void executeOperation(final LdbcQuery8 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        final int limit = operation.limit();

        logger.debug("Query 8 called on Person id: {}",
                person_id);

        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Vertex root = null;
        try {
            root = client.getVertex(person_id, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        GremlinPipeline<Vertex, Vertex> gp = (new GremlinPipeline<>(root));
        Iterable<Row> it = gp.in("hasCreator").in("replyOf").as("comment").out("hasCreator")
                .as("commenter").select();

        List<LdbcQuery8Result> result = new ArrayList<>();
        for (Row r : it) {
            Vertex comment = (Vertex) r.getColumn(0);
            Vertex commenter = (Vertex) r.getColumn(1);
            long id = client.getVLocalId((Long) commenter.getId());
            String fName = commenter.getProperty("firstName");
            String lName = commenter.getProperty("lastName");
            long cDate = comment.getProperty("creationDate");
            long commentID = client.getVLocalId((Long) comment.getId());
            String content = comment.getProperty("content");

            LdbcQuery8Result res = new LdbcQuery8Result(id, fName, lName, cDate, commentID, content);
            result.add(res);

        }


        Collections.sort(result, new Comparator<LdbcQuery8Result>() {
            @Override
            public int compare(LdbcQuery8Result o1, LdbcQuery8Result o2) {
                if (o1.commentCreationDate() == o2.commentCreationDate())
                    return Long.compare(o1.commentId(), o2.commentId());
                return Long.compare(o2.commentCreationDate(), o1.commentCreationDate());
            }
        });
        if (result.size() > limit)
            result = result.subList(0, limit);

        resultReporter.report(result.size(), result, operation);
    }
}
