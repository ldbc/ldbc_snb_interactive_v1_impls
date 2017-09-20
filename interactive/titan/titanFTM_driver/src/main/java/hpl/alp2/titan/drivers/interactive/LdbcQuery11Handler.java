package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
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
public class LdbcQuery11Handler implements OperationHandler<LdbcQuery11,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery11Handler.class);

    @Override
    public void executeOperation(final LdbcQuery11 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        String countryName = operation.countryName();
        int year = operation.workFromYear();
        final int limit = operation.limit();

        logger.debug("Query 11 called on Person id: {} with counrty {} and year {}",
                person_id, countryName, year);

        TitanFTMDb.BasicClient client = dbConnectionState.client();
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
        resultReporter.report(result.size(), result, operation);
    }
}
