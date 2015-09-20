package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery7Result;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Given a start Person, find (most recent) Likes on any of start Person’s Posts/Comments.
 * Return top 20 Persons that Liked any of start Person’s Posts/Comments, the Post/Comment they liked most
 * recently, creation date of that Like, and the latency (in minutes) between creation of Post/Comment and
 * Like. Additionally, return a flag indicating whether the liker is a friend of start Person. In the case that a
 * Person Liked multiple Posts/Comments at the same time, return the Post/Comment with lowest identifier.
 * Sort results descending by creation time of Like, then ascending by Person identifier of liker.
 */
public class LdbcQuery7Handler implements OperationHandler<LdbcQuery7,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery7Handler.class);

    @Override
    public void executeOperation(final LdbcQuery7 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        final int limit = operation.limit();

        logger.debug("Query 7 called on Person id: {}",
                person_id);
        TitanFTMDb.BasicClient client = dbConnectionState.client();

        Vertex root = null;
        try {
            root = client.getVertex(person_id, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        GremlinPipeline<Vertex, Vertex> gp = (new GremlinPipeline<>(root));
        Set<Vertex> friends = new HashSet<>();
        gp.out("knows").fill(friends);
        gp = (new GremlinPipeline<>(root));
        Iterable<Row> it = gp.as("root").in("hasCreator").as("post").inE("likes").as("like").outV().as("liker")
                .select();

        Map<Vertex, LdbcQuery7Result> qRes = new HashMap<>();
        for (Row r : it) {
            Vertex post = (Vertex) r.getColumn(1);
            Edge like = (Edge) r.getColumn(2);
            Vertex liker = (Vertex) r.getColumn(3);
            boolean isNotFriend = (!friends.contains(liker));
            long id = client.getVLocalId((Long) liker.getId());
            String fName = liker.getProperty("firstName");
            String lName = liker.getProperty("lastName");
            long lcDate = like.getProperty("creationDate");
            long pcDate = post.getProperty("creationDate");
            long postID = client.getVLocalId((Long) post.getId());
            String content = post.getProperty("content");
            if (content.length() == 0)
                content = post.getProperty("imageFile");

            int latency = (int) ((lcDate - pcDate) / 60000);
            LdbcQuery7Result res = new LdbcQuery7Result(id, fName, lName, lcDate, postID, content, latency, isNotFriend);
            //if liker has res, replace according to recent like, and then lower likeid if time is the same
            if (qRes.containsKey(liker)) {
                LdbcQuery7Result other = qRes.get(liker);
                if (other.likeCreationDate() > res.likeCreationDate())
                    continue;
                else if (other.likeCreationDate() == res.likeCreationDate() && other.commentOrPostId() < res.commentOrPostId())
                    continue;
            }

            /*it is implied from the fact that the program reached this point that either this person has not been
             recorded in qRes yet or that the current like is more recent or it is as recent as the other but with
             a lower postID */
            qRes.put(liker, res);

        }

        List<LdbcQuery7Result> result = new ArrayList<>(qRes.values());
        Collections.sort(result, new Comparator<LdbcQuery7Result>() {
            @Override
            public int compare(LdbcQuery7Result o1, LdbcQuery7Result o2) {
                if (o1.likeCreationDate() == o2.likeCreationDate())
                    return Long.compare(o1.personId(), o2.personId());
                return Long.compare(o2.likeCreationDate(), o1.likeCreationDate());
            }
        });
        if (result.size() > limit)
            result = result.subList(0, limit);

        resultReporter.report(result.size(), result, operation);
    }
}
