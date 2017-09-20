package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery13Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.branch.LoopPipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given two Persons, find the shortest path between these two Persons in the
 * subgraph induced by the Knows relationships. Return the length of this path.
 * – -1 : no path found
 * – 0: start person = end person
 * – > 0: regular case
 * <p/>
 * SPT Code by Daniel Kuppitz: https://groups.google.com/forum/#!msg/aureliusgraphs/d3jvgJeArOU/lMoObkk-If0J
 * //TODO since we don't need the paths themselves, consider using this instead: https://groups.google.com/forum/#!searchin/gremlin-users/shortest$20path/gremlin-users/GMIUZ7eFF7Y/yZy4yWN3Vg8J
 */
public class LdbcQuery13Handler implements OperationHandler<LdbcQuery13,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery13Handler.class);

    @Override
    public void executeOperation(final LdbcQuery13 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        final long person1id = operation.person1Id();
        final long person2id = operation.person2Id();

        logger.debug("Query 13 called on Person1 id: {} and Person2Id {}",
                person1id, person2id);

        if (person1id == person2id) {
            resultReporter.report(0, new LdbcQuery13Result(0), operation);
            return;
        }

        TitanFTMDb.BasicClient client = dbConnectionState.client();
        try {
            final Vertex v1 = client.getVertex(person1id, "Person");
            final Vertex v2 = client.getVertex(person2id, "Person");

            final Set<Vertex> x = new HashSet<>(Collections.singleton(v1));

            final GremlinPipeline<Object, List> pipe = (new GremlinPipeline<>(v1)).as("x")
                    .out("knows").except(x).store(x).loop("x", new PipeFunction<LoopPipe.LoopBundle<Vertex>, Boolean>() {
                        @Override
                        public Boolean compute(LoopPipe.LoopBundle<Vertex> bundle) {
                            return !x.contains(v2);
                        }
                    }, new PipeFunction<LoopPipe.LoopBundle<Vertex>, Boolean>() {
                        @Override
                        public Boolean compute(LoopPipe.LoopBundle<Vertex> bundle) {
                            return bundle.getObject().equals(v2);
                        }
                    }).path();

            LdbcQuery13Result r = new LdbcQuery13Result(-1);

            for (final List path : pipe)
                r = new LdbcQuery13Result(path.size() - 1);

            resultReporter.report(1, r, operation);
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }
    }
}
