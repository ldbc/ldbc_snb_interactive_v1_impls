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
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery6Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.PipesFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Given a start Person and some Tag, find the other Tags that occur together with this Tag on
 * Posts that were created by start Person’s friends and friends of friends (excluding start Person).
 * Return top 10 Tags, and the count of Posts that were created by these Persons,
 * which contain both this Tag and the given Tag.
 * Sort results descending by count, and then ascending by Tag name.
 */
public class LdbcQuery6Handler extends OperationHandler<LdbcQuery6> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery6Handler.class);

    @Override
    public OperationResultReport executeOperation(LdbcQuery6 operation) {
        long person_id = operation.personId();
        final String tagName = operation.tagName();
        final int limit = operation.limit();

        logger.debug("Query 6 called on Person id: {} and Tag {}",
                person_id, tagName);

        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
        final Set<Vertex> friends = QueryUtils.getInstance().getFoF(person_id, client);

        Map<Vertex, Number> qRes = new HashMap<>();
        GremlinPipeline<Collection<Vertex>, Vertex> gpf = new GremlinPipeline<>(friends);
        gpf._().in("hasCreator")
                .filter(new PipesFunction<Vertex, Boolean>() {
                    @Override
                    public Boolean compute(Vertex v) {
                        return ((String) v.getProperty("label")).equalsIgnoreCase("Post");
                    }
                }).as("post").out("hasTag").has("name", tagName)
                .back("post").out("hasTag").hasNot("name", tagName)
                .groupCount(qRes).iterate();

        //Sort result
        Map<Vertex, Long> qResInt = new HashMap<>();
        for (Map.Entry<Vertex, Number> e : qRes.entrySet())
            qResInt.put(e.getKey(), (Long) e.getValue());

        qResInt = QueryUtils.sortByValueAndKey(qResInt, false, true, TitanFTMDb.BasicClient.NAMECOMP);


        //output to res and limit
        List<LdbcQuery6Result> result = new ArrayList<LdbcQuery6Result>();
        int i = 0;
        for (Map.Entry<Vertex, Long> entry : qResInt.entrySet()) {
            Vertex tag = entry.getKey();
            i++;
            LdbcQuery6Result res = new LdbcQuery6Result((String) tag.getProperty("name"),
                    entry.getValue().intValue());
            result.add(res);
            if (i == limit)
                break;
        }

        return operation.buildResult(0, result);
    }
}

