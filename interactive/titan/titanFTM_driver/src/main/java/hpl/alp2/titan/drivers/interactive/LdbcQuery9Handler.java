package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery9Result;
import com.tinkerpop.blueprints.Compare;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given a start Person, find the (most recent) Posts/Comments created by that Person’s friends
 or friends of friends (excluding start Person). Only consider the Posts/Comments created before a given date
 (excluding that date). Return the top 20 Posts/Comments, and the Person that created each of those
 Posts/Comments. Sort results descending by creation date of Post/Comment, and then ascending by
 Post/Comment identifier.
 */
public class LdbcQuery9Handler implements OperationHandler<LdbcQuery9,TitanFTMDb.BasicDbConnectionState> {

    final static Logger logger = LoggerFactory.getLogger(LdbcQuery9Handler.class);

    @Override
    public void executeOperation(final LdbcQuery9 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        long max_date = operation.maxDate().getTime();
        final int limit = operation.limit();

        logger.debug("Query 9 called on Person id: {} with maxDate {}",
                person_id, max_date);
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        final Set<Vertex> friends = QueryUtils.getInstance().getFoF(person_id, client);

        GremlinPipeline<Collection<Vertex>, Vertex> gpf = new GremlinPipeline<>(friends);

        Iterable<Row> it = gpf.as("friend").in("hasCreator").has("creationDate", Compare.LESS_THAN, max_date).as("post").select();
        List<LdbcQuery9Result> result = new ArrayList<>();

        for (Row r : it) {
            Vertex commenter = (Vertex) r.getColumn(0);
            Vertex comment = (Vertex) r.getColumn(1);
            long id = client.getVLocalId((Long) commenter.getId());
            String fName = commenter.getProperty("firstName");
            String lName = commenter.getProperty("lastName");
            long cDate = comment.getProperty("creationDate");
            long commentID = client.getVLocalId((Long) comment.getId());
            String content = comment.getProperty("content");
            if (content.length() == 0)
                content = comment.getProperty("imageFile");

            LdbcQuery9Result res = new LdbcQuery9Result(id, fName, lName, commentID, content, cDate);
            result.add(res);
        }


        Collections.sort(result, new Comparator<LdbcQuery9Result>() {
            @Override
            public int compare(LdbcQuery9Result o1, LdbcQuery9Result o2) {
                if (o1.commentOrPostCreationDate() == o2.commentOrPostCreationDate())
                    return Long.compare(o1.commentOrPostId(), o2.commentOrPostId());
                return Long.compare(o2.commentOrPostCreationDate(), o1.commentOrPostCreationDate());
            }
        });
        if (result.size() > limit)
            result = result.subList(0, limit);

        resultReporter.report(result.size(), result, operation);
    }
}
