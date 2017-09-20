package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery3Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.PipesFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Handles Q3 by running a groupBy pipe and then sorting using
 * a sortable map.
 */
public class LdbcQuery3Handler implements OperationHandler<LdbcQuery3,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery3Handler.class);

    /*Given a start Person, find Persons that are their friends and friends of friends (excluding start
    Person) that have made Posts/Comments in the given Countries X and Y within a given period. Only
    Persons that are foreign to Countries X and Y are considered, that is Persons whose Location is not Country
    X or Country Y. Return top 20 Persons, and their Post/Comment counts, in the given countries and period.
    Sort results descending by total number of Posts/Comments, and then ascending by Person identifier. */

    @Override
    public void executeOperation(final LdbcQuery3 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {

        long person_id = operation.personId();
        final String country_x = operation.countryXName();
        final String country_y = operation.countryYName();
        final long min_date = operation.startDate().getTime();
        final long max_date = QueryUtils.addDays(min_date, operation.durationDays());
        logger.debug("Query 3 called on {} for country x {} country y {} min_date {} max _date {}"
                , person_id, country_x, country_y, min_date, max_date);

        int limit = operation.limit();
        TitanFTMDb.BasicClient client = dbConnectionState.client();

        //collect cities to exclude friends with
        Vertex country;
        final Set<Long> cityIDs = new HashSet<>();
        try {
            Map<String, String> pvMap = new HashMap<>();
            pvMap.put("type", "country");
            pvMap.put("name", country_x);
            country = client.getVertices("Place", pvMap, Integer.MAX_VALUE).iterator().next();
            cityIDs.addAll(QueryUtils.getCities(country));

            pvMap.put("name", country_y);
            country = client.getVertices("Place", pvMap, Integer.MAX_VALUE).iterator().next();
            cityIDs.addAll(QueryUtils.getCities(country));

        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }


        //Main Query
        Map qResX = new HashMap<>();
        Map qResY = new HashMap<>();
        PipeFunction Q3KEY_FUNC = new PipesFunction<Vertex, Vertex>() {
            @Override
            public Vertex compute(Vertex argument) {
                return (Vertex) this.asMap.get("friend");
            }
        };

        GremlinPipeline<Collection<Vertex>, Vertex> gp = new GremlinPipeline<>(QueryUtils.getInstance().getFoF(person_id, client));
        gp.filter(new PipeFunction<Vertex, Boolean>() { //Friends not from countries X and Y
            public Boolean compute(Vertex v) {
                Long friendCityID = (Long) (new GremlinPipeline<Vertex, List<Vertex>>(v)).as("root").out("isLocatedIn").toList().get(0).getId();
                return !cityIDs.contains(friendCityID);
            }
        }).as("friend")
                .in("hasCreator").interval("creationDate", min_date, max_date).as("post")
                .out("isLocatedIn").has("name", country_x).groupCount(qResX, Q3KEY_FUNC)
                .back("friend").in("hasCreator").interval("creationDate", min_date, max_date)
                .out("isLocatedIn").has("name", country_y)
                .groupCount(qResY, Q3KEY_FUNC).iterate();


        //Collect results and summarize
        Map<Vertex, Q3Res> counter = new HashMap<>();
        for (Object o : qResX.keySet()) {
            Vertex friendV = (Vertex) o;
            if (!qResY.containsKey(o))
                continue;

            int xRes = ((Long) qResX.get(o)).intValue();
            int yRes = ((Long) qResY.get(o)).intValue();
            counter.put(friendV, new Q3Res(xRes, yRes));
        }

        //sort
        counter = QueryUtils.sortByValueAndKey(counter, false, true, TitanFTMDb.BasicClient.IDCOMP);

        //parse map to result
        List<LdbcQuery3Result> result = new ArrayList<>();
        int i = 1;
        for (Map.Entry<Vertex, Q3Res> entry : counter.entrySet()) {
            Vertex friend = entry.getKey();
            long id = client.getVLocalId((Long) friend.getId());
            i++;
            LdbcQuery3Result res = new LdbcQuery3Result(id, (String) friend.getProperty("firstName")
                    , (String) friend.getProperty("lastName"),
                    entry.getValue().x, entry.getValue().y, entry.getValue().x + entry.getValue().y);
            result.add(res);
            if (i == limit)
                break;
        }

        resultReporter.report(result.size(), result, operation);
    }


    protected class Q3Res implements Comparable<Q3Res> {
        public int x = 0;
        public int y = 0;

        public Q3Res(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            return this.getClass().isInstance(o) && (this.x == ((Q3Res) o).x && this.y == ((Q3Res) o).y);
        }
        @Override
        public int compareTo(Q3Res o) {
            if (this.getClass().isInstance(o)) {
                return Integer.compare(x + y, o.x + o.y);
            } else
                return 0;
        }
    }
}
