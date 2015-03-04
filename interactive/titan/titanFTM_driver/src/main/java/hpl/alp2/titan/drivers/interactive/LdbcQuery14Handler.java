/**(c) Copyright [2015] Hewlett-Packard Development Company, L.P.
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.**/

package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.OperationResultReport;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery14Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.branch.LoopPipe;
import com.tinkerpop.pipes.util.PipesFunction;
import com.tinkerpop.pipes.util.structures.Pair;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given two Persons, find all (unweighted) shortest paths between these two Persons, in the
 subgraph induced by the Knows relationship. Then, for each path calculate a weight. The nodes in the path
 are Persons, and the weight of a path is the sum of weights between every pair of consecutive Person nodes
 in the path. The weight for a pair of Persons is calculated such that every reply (by one of the Persons) to a
 Post (by the other Person) contributes 1.0, and every reply (by ones of the Persons) to a Comment (by the
 other Person) contributes 0.5. Return all the paths with shortest length, and their weights. Sort results
 descending by path weight. The order of paths with the same weight is unspecified.
 */
public class LdbcQuery14Handler extends OperationHandler<LdbcQuery14> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery14Handler.class);

    @Override
    public OperationResultReport executeOperation(LdbcQuery14 operation) {

        final long person1id = operation.person1Id();
        final long person2id = operation.person2Id();

        logger.debug("Query 14 called on Person1 id: {} and Person2Id {}",
                person1id, person2id);

        List<LdbcQuery14Result> result = new ArrayList<>();

        //Early termination when ids are identical
        if (person1id == person2id) {
            return operation.buildResult(0, result);
        }

        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
        try {
            final Vertex v1 = client.getVertex(person1id, "Person");
            final Vertex v2 = client.getVertex(person2id, "Person");

            final Set<Vertex> x = new HashSet<>(Collections.singleton(v1));
            final Stack<Integer> s = new Stack<>();
            s.push(Integer.MAX_VALUE);


            //return all shortest paths and not just the first found
            final GremlinPipeline<Object, List> pipe = (new GremlinPipeline<>(v1)).as("x")
                    .out("knows").except(x).store(x).loop("x", new PipeFunction<LoopPipe.LoopBundle<Vertex>, Boolean>() {
                        @Override
                        public Boolean compute(LoopPipe.LoopBundle<Vertex> bundle) {
                            return (bundle.getPath().size() < s.peek()); //assuming BFS should stop when larger than found paths are considered
                        }
                    }, new PipeFunction<LoopPipe.LoopBundle<Vertex>, Boolean>() {
                        @Override
                        public Boolean compute(LoopPipe.LoopBundle<Vertex> bundle) {
                            if (bundle.getObject().equals(v2)) {
                                s.push(bundle.getLoops()); //assuming emit is performed for all objects before loop
                                return true;
                            }
                            return false;
                        }
                    }).path();

            //In case any paths were found
            Map<Pair<Vertex, Vertex>, Double> pairWeights = new HashMap<>();
            for (final List<Vertex> path : pipe) {
                List<Long> vIds = new ArrayList<>();
                for (Vertex v : path)
                    vIds.add(client.getVLocalId((Long) v.getId()));
                result.add(new LdbcQuery14Result(vIds, calcWeight(path, pairWeights)));
            }

        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        return operation.buildResult(0, result);
    }

    private double calcWeight(List<Vertex> vertexList, Map<Pair<Vertex, Vertex>, Double> pairWeights) {

        double w = 0.0;
        Pair<Vertex, Vertex> current;
        for (int i = 1; i < vertexList.size(); i++) {
            current = new Pair<>(vertexList.get(i - 1), vertexList.get(i));
            if (pairWeights.containsKey(current)) {
                w += pairWeights.get(current);
            } else {
                double newW = calcWeight(current.getA(), current.getB()) + calcWeight(current.getB(), current.getA());
                w += newW;
                pairWeights.put(current, newW);
            }
        }

        return w;
    }

    /**
     * Calculates the weight of the comment path as defined by Q14 between a and b
     * (directional)
     *
     * @param a Source (Person) vertex
     * @param b Target (Person) vertex
     * @return weight of comment-reply path between a and b as defined by Q14
     */
    private double calcWeight(final Vertex a, final Vertex b) {

        double w = 0.0;
        GremlinPipeline<Vertex, Vertex> pipe = (new GremlinPipeline<>(a));
        Iterable<Row> res1 = pipe.in("hasCreator").as("postComment").in("replyOf").out("hasCreator")
                .filter(new PipesFunction<Vertex, Boolean>() {
                    @Override
                    public Boolean compute(Vertex commentCreator) {
                        return commentCreator.equals(b);
                    }
                }).select();

        for (Row r : res1) {
            w += (((Vertex) r.getColumn(0)).getProperty("label").equals("Post") ? 1 : 0.5);
        }
        return w;
    }
}
