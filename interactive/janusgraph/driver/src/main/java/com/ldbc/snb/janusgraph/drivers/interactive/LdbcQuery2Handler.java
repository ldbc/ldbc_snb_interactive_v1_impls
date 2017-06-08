package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery2Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Strategy: Double expansion root->friend->post
 */
public class LdbcQuery2Handler implements OperationHandler<LdbcQuery2,JanusGraphDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery2Handler.class);

    @Override
    public void executeOperation(final LdbcQuery2 operation, JanusGraphDb.BasicDbConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException {
/*        long person_id = operation.personId();
        final long maxDate = operation.maxDate().getTime();//QueryUtils.addDays(operation.maxDate(),1);
        logger.debug("Query 2 called on Person id: {} with max date {}", person_id, maxDate);
        int limit = operation.limit();
        JanusGraphDb.BasicClient client = dbConnectionState.client();

        Vertex root = null;
        try {
            root = client.getVertex(person_id, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        GremlinPipeline<Vertex, Vertex> gp = new GremlinPipeline<>(root);
        Iterable<Row> qResult = gp.out("knows").as("friend").in("hasCreator")
                .has("creationDate", Compare.LESS_THAN, maxDate)
                .as("post").select()
                .order(QueryUtils.COMP_CDate_Postid).range(0, limit - 1);

        List<LdbcQuery2Result> result = new ArrayList<>();
        for (Row r : qResult) {
            Vertex post = (Vertex) r.getColumn("post");
            Long pid = client.getVLocalId((Long) post.getId());
            Vertex friend = (Vertex) r.getColumn("friend");
            String content = post.getProperty("content");
            if (content.length() == 0)
                content = post.getProperty("imageFile");
            LdbcQuery2Result res = new LdbcQuery2Result(client.getVLocalId((Long) friend.getId()),
                    (String) friend.getProperty("firstName"), (String) friend.getProperty("lastName"),
                    pid, content, (Long) post.getProperty("creationDate"));

            result.add(res);
        }
        resultReporter.report(result.size(), result, operation);
        */
    }
}