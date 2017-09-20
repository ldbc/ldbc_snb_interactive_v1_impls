package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery5Result;
import com.tinkerpop.blueprints.Compare;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.PipesFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Given a start Person, find the Forums which that Person's friends and friends of friends
 * (excluding start Person) became Members of after a given date. Return top 20 Forums, and the number of
 * Posts in each Forum that was Created by any of these Persons. For each Forum consider only those Persons
 * which joined that particular Forum after the given date. Sort results descending by the count of Posts, and
 * then ascending by Forum identifier
 */
public class LdbcQuery5Handler implements OperationHandler<LdbcQuery5,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery5Handler.class);

    @Override
    public void executeOperation(final LdbcQuery5 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        final long join_date = operation.minDate().getTime();
        final int limit = operation.limit();

        logger.debug("Query 5 called on Person id: {} with join date {}",
                person_id, join_date);

        TitanFTMDb.BasicClient client = dbConnectionState.client();

        //Prepare result map
        Map<Vertex, Number> qRes = new HashMap<>();
        PipeFunction Q5KEY_FUNC = new PipesFunction<Vertex, Vertex>() {
            @Override
            public Vertex compute(Vertex argument) {
                return (Vertex) this.asMap.get("forum");
            }
        };

        //Main query
        final Set<Vertex> friends = QueryUtils.getInstance().getFoF(person_id, client);
        Set<Vertex> forums = new HashSet<>();

        GremlinPipeline<Collection<Vertex>, Vertex> gpf = new GremlinPipeline<>(friends);

        gpf.as("friend").inE("hasMember").has("joinDate", Compare.GREATER_THAN, join_date)
                .outV().as("forum").store(forums)
                .out("containerOf").as("PostInForum")
                .out("hasCreator") //Sticky point - how to limit to posts by friends that joined this forum?
                .filter(new PipesFunction<Vertex, Boolean>() {
                    public Boolean compute(Vertex v) {
                        return this.asMap.get("friend").equals(v);
                    }
                }).groupCount(qRes, Q5KEY_FUNC).iterate();

        //.orderMap(Tokens.T.decr).range(0,limit); //can't sort secondary sort on name

        //Sort result
        Map<Vertex, Long> qResInt = new HashMap<>();
        for (Map.Entry<Vertex, Number> e : qRes.entrySet())
            qResInt.put(e.getKey(), (Long) e.getValue());

        qResInt = QueryUtils.sortByValueAndKey(qResInt, false, true, TitanFTMDb.BasicClient.IDCOMP);
        List<LdbcQuery5Result> result = new ArrayList<>();

        //output to res and limit
        int i = 0;
        for (Map.Entry<Vertex, Long> entry : qResInt.entrySet()) {
            Vertex forum = entry.getKey();
            i++;
            LdbcQuery5Result res = new LdbcQuery5Result((String) forum.getProperty("title"), entry.getValue().intValue());
            result.add(res);
            if (i == limit)
                break;
        }

        //TODO If limit has not been reached,  add 0 count forums from forums to reach limit
        if (i < limit) {
            forums.removeAll(qRes.keySet());
            ArrayList<Vertex> forumsList = new ArrayList<>(forums);
            Collections.sort(forumsList, TitanFTMDb.BasicClient.IDCOMP);
            for (int j = 0; j < Math.min(limit-i,forums.size()); j++) {
                Vertex forum = forumsList.get(j);
                LdbcQuery5Result res = new LdbcQuery5Result((String) forum.getProperty("title"), 0);
                result.add(res);
            }
        }

        resultReporter.report(result.size(), result, operation);
    }
}
