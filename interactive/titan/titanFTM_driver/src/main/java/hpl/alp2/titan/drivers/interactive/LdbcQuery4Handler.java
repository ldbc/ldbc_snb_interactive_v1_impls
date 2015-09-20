package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery4Result;
import com.tinkerpop.blueprints.Compare;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given a start Person, find Tags that are attached to Posts that were created by that Person’s
 * friends. Only include Tags that were attached to Posts created within a given time interval, and that were
 * never attached to Posts created before this interval. Return top 10 Tags, and the count of Posts, which were
 * created within the given time interval, that this Tag was attached to. Sort results descending by Post count,
 * and then ascending by Tag name.
 */
public class LdbcQuery4Handler implements OperationHandler<LdbcQuery4,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery4Handler.class);

    @Override
    public void executeOperation(final LdbcQuery4 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        /*
        Given a start Person, find Tags that are attached to Posts that were created by that Person’s
        friends. Only include Tags that were attached to Posts created within a given time interval, and that were
        never attached to Posts created before this interval. Return top 10 Tags, and the count of Posts, which were
        created within the given time interval, that this Tag was attached to. Sort results descending by Post count,
        and then ascending by Tag name.
         */
        long person_id = operation.personId();
        final long min_date = operation.startDate().getTime();
        final long max_date = QueryUtils.addDays(min_date, operation.durationDays());
        int limit = operation.limit();
        logger.debug("Query 4 called on Person id: {} with min date {} and max date {}",
                person_id, min_date, max_date);

        TitanFTMDb.BasicClient client = dbConnectionState.client();


        //Prepare result map
        long startResMap = System.currentTimeMillis();
        Map<Vertex, Number> qRes = new HashMap<>();
//        PipeFunction Q4KEY_FUNC = new PipesFunction<Vertex, Vertex>() {
//            @Override
//            public Vertex compute(Vertex argument) {
//                return (Vertex) this.asMap.get("tag");
//            }
//        };

        long resMapTime = System.currentTimeMillis() - startResMap;
        logger.debug("Query 4 prepare res map time: {}", resMapTime );

        //Prepare nonTags
        long startTags = System.currentTimeMillis();
        Set<Vertex> nonTags = new HashSet<>();
        Iterable<Vertex> invalidPosts = client.getQuery().has("creationDate", Compare.LESS_THAN, min_date).vertices();
        GremlinPipeline<Iterable<Vertex>, Vertex> gpp = new GremlinPipeline<>(invalidPosts);
        gpp.filter(QueryUtils.ONLYPOSTS).out("hasTag").fill(nonTags); //TODO replace with a combined index on type and creationDate
        long nonTagTime = System.currentTimeMillis() - startTags;
        logger.debug("Query 4 prepare non tags time: {}", nonTagTime );

        //Main query
        long startMain = System.currentTimeMillis();
        Vertex root = null;
        try {
            root = client.getVertex(person_id, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }
        GremlinPipeline<Vertex, Vertex> gp = (new GremlinPipeline<Vertex, Vertex>(root)).as("root");

        gp.out("knows").in("hasCreator")
                .filter(QueryUtils.ONLYPOSTS)//.has("label", "Comment") //this doesn't work for vertices - for edges only
                .interval("creationDate", min_date, max_date).out("hasTag")
                .except(nonTags).as("tag")
                .groupCount(qRes).iterate();
        //.orderMap(Tokens.T.decr).range(0,limit); //can't sort secondary sort on name

        long timeMain = System.currentTimeMillis() - startMain;
        logger.debug("Query 4 main time: {}", timeMain);

        //Sort result
        long startPost = System.currentTimeMillis();
        Map<Vertex, Long> qResInt = new HashMap<>();
        for (Map.Entry<Vertex, Number> e : qRes.entrySet())
            qResInt.put(e.getKey(), (Long) e.getValue());

        qResInt = QueryUtils.sortByValueAndKey(qResInt, false, true, TitanFTMDb.BasicClient.NAMECOMP);
        List<LdbcQuery4Result> result = new ArrayList<>();

        //output to res and limit
        int i = 0;
        for (Map.Entry<Vertex, Long> entry : qResInt.entrySet()) {
            Vertex tag = entry.getKey();
            i++;
            LdbcQuery4Result res = new LdbcQuery4Result((String) tag.getProperty("name"), entry.getValue().intValue());
            result.add(res);
            if (i == limit)
                break;
        }

        long timePost = System.currentTimeMillis() - startPost;
        logger.debug("Query 4 post-process time: {}", timePost);

        resultReporter.report(result.size(), result, operation);

    }
}
