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
public class LdbcQuery13Handler extends OperationHandler<LdbcQuery13> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery13Handler.class);

    @Override
    public OperationResultReport executeOperation(LdbcQuery13 operation) {
        final long person1id = operation.person1Id();
        final long person2id = operation.person2Id();

        logger.debug("Query 13 called on Person1 id: {} and Person2Id {}",
                person1id, person2id);

        List<LdbcQuery13Result> result = new ArrayList<>();
        if (person1id == person2id) {
            result.add(new LdbcQuery13Result(0));
            return operation.buildResult(0, result);
        }

        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
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

            result.add(r);
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        return operation.buildResult(0, result);
    }
}
