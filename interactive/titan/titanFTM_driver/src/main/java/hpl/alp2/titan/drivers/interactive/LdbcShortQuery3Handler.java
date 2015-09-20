package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriendsResult;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of LDBC Interactive workload short query 3
 Given a start Person, retrieve all of their friends, and the date at which they became friends.
 Order results descending by friendship creation date, then ascending by friend identifier
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery3Handler implements OperationHandler<LdbcShortQuery3PersonFriends,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery3Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery3PersonFriends operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        List<LdbcShortQuery3PersonFriendsResult> result = new ArrayList<>();
        long person_id = operation.personId();
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        final Vertex root;
        try {
            root = client.getVertex(person_id, "Person");
            logger.debug("Short Query 3 called on person id: {}", person_id);
            GremlinPipeline<Vertex, Vertex> gp = new GremlinPipeline<>(root);
            Iterable<Row> qResult = gp.outE("knows").as("knowEdge").inV().as("friend").select()
                    .order(new PipeFunction<Pair<Row, Row>, Integer>() {
                        @Override
                        public Integer compute(Pair<Row, Row> argument) {
                            long d1 = ((Edge) argument.getA().getColumn("knowEdge")).getProperty("creationDate");
                            long d2 = ((Edge) argument.getB().getColumn("knowEdge")).getProperty("creationDate");
                            if (d1 == d2)
                                return Long.compare((Long) ((Vertex) argument.getA().getColumn("friend")).getId(), (Long) ((Vertex) argument.getB().getColumn("friend")).getId());
                            else
                                return Long.compare(d2, d1);
                        }
                    });

            for (Row r : qResult) {
                Vertex friend = (Vertex) r.getColumn("friend");
                Edge knowsE = (Edge) r.getColumn("knowEdge");

                LdbcShortQuery3PersonFriendsResult res = new LdbcShortQuery3PersonFriendsResult(
                        client.getVLocalId((Long) friend.getId()),
                        (String) friend.getProperty("firstName"), (String) friend.getProperty("lastName"),
                        (Long) knowsE.getProperty("creationDate"));

                result.add(res);
            }
            resultReporter.report(result.size(), result, operation);
        } catch (SchemaViolationException e) {
        e.printStackTrace();
        resultReporter.report(-1, null, operation);
    }

    }


}