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
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery11Result;
import com.tinkerpop.blueprints.Compare;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given a start Person, find that Person’s friends and friends of friends (excluding start Person)
 * who started Working in some Company in a given Country, before a given date (year). Return top 10
 * Persons, the Company they worked at, and the year they started working at that Company. Sort results
 * ascending by the start date, then ascending by Person identifier, and lastly by Organization name descending.
 */
public class LdbcQuery11Handler extends OperationHandler<LdbcQuery11> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery11Handler.class);

    @Override
    public OperationResultReport executeOperation(LdbcQuery11 operation) {
        long person_id = operation.personId();
        String countryName = operation.countryName();
        int year = operation.workFromYear();
        final int limit = operation.limit();

        logger.debug("Query 11 called on Person id: {} with counrty {} and year {}",
                person_id, countryName, year);

        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
        final Set<Vertex> friends = QueryUtils.getInstance().getFoF(person_id, client);

        GremlinPipeline<Collection<Vertex>, Vertex> gpf = new GremlinPipeline<>(friends);

        Iterable<Row> it = gpf.as("friend")
                .outE("workAt")
                .has("workFrom", Compare.LESS_THAN, year)
                .as("startWork")
                .inV().as("comp").out("isLocatedIn")
                .has("name", countryName)
                .select();

        List<LdbcQuery11Result> result = new ArrayList<>();

        for (Row r : it) {
            Vertex person = (Vertex) r.getColumn(0);
            Edge workAt = (Edge) r.getColumn(1);
            Vertex company = (Vertex) r.getColumn(2);
            LdbcQuery11Result res = new LdbcQuery11Result(client.getVLocalId((Long) person.getId()),
                    (String) person.getProperty("firstName"), (String) person.getProperty("lastName")
                    , (String) company.getProperty("name"), (Integer) workAt.getProperty("workFrom"));
            result.add(res);
        }

        Collections.sort(result, new Comparator<LdbcQuery11Result>() {
            @Override
            public int compare(LdbcQuery11Result o1, LdbcQuery11Result o2) {
                if (o1.organizationWorkFromYear() == o2.organizationWorkFromYear()) {
                    if (o1.personId() == o2.personId())
                        return o2.organizationName().compareTo(o1.organizationName());
                    else
                        return Long.compare(o1.personId(), o2.personId());
                } else
                    return Integer.compare(o1.organizationWorkFromYear(), o2.organizationWorkFromYear());
            }
        });

        if (result.size() > limit)
            result = result.subList(0, limit);
        return operation.buildResult(0, result);
    }
}
