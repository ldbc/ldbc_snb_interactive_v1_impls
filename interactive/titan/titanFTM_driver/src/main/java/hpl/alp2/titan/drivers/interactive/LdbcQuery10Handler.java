package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery10Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.PipesFunction;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 06-Oct-14. Given a start Person, find that Person’s friends of friends (excluding start Person, and
 * immediate friends), who were born on or after the 21st of a given month (in any year) and before the 22nd of
 * the following month. Calculate the similarity between each of these Persons and start Person, where
 * similarity for any Person is defined as follows:
 * – common = number of Posts created by that Person, such that the Post has a Tag that start Person is
 * Interested in
 * – uncommon = number of Posts created by that Person, such that the Post has no Tag that start Person is
 * Interested in
 * – similarity = common - uncommon
 * Return top 10 Persons, their Place, and their similarity score. Sort results descending by similarity score,
 * and then ascending by Person identifier
 */
public class LdbcQuery10Handler implements OperationHandler<LdbcQuery10,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery10Handler.class);

    @Override
    public void executeOperation(final LdbcQuery10 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        final int monthNum = operation.month();
        final int limit = operation.limit();

        logger.debug("Query 10 called on Person id: {} with month number {}",
                person_id, monthNum);

        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Vertex root = null;

        PipeFunction<Vertex, Boolean> Q10Friends = new PipesFunction<Vertex, Boolean>() {
            @Override
            public Boolean compute(Vertex person) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date((Long) person.getProperty("birthday")));
                c.setTimeZone(TimeZone.getTimeZone("GMT"));
                int bMonth = c.get(Calendar.MONTH) + 1;
                int bDay = c.get(Calendar.DAY_OF_MONTH);
                return ((bMonth == monthNum && bDay >= 21)
                        || (bMonth == ((monthNum + 1) % 12) && bDay < 22));
            }
        };

        try {
            root = client.getVertex(person_id, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        //Prepare set of tags root likes.
        GremlinPipeline<Vertex, Vertex> gp = (new GremlinPipeline<>(root));
        final Set<Vertex> rootTags = new HashSet<>();
        gp.out("hasInterest").fill(rootTags);

        gp = (new GremlinPipeline<>(root));
        Set<Vertex> friends = new HashSet<>();
        Set<Vertex> fofs = new HashSet<>();
        Iterable<Row> it = gp.as("root").out("knows").as("friend").aggregate(friends)
                .out("knows").except("root").except(friends)
                .filter(Q10Friends)
                .as("fof").store(fofs).in("hasCreator").filter(QueryUtils.ONLYPOSTS).as("post")
                .transform(new PipesFunction<Vertex, Boolean>() {
                    @Override
                    public Boolean compute(Vertex post) {
                        Iterable<Vertex> tags = post.query().labels("hasTag").vertices();
                        for (Vertex tag : tags)
                            if (rootTags.contains(tag))
                                return true;
                        return false;
                    }
                }).as("isCommon").select();

        Map<Vertex, Q10Res> qRes = new HashMap<>();
        for (Row r : it) {
            Vertex person = (Vertex) r.getColumn(2);
            Vertex post = (Vertex) r.getColumn(3);
            Q10Res res = (qRes.containsKey(person) ? qRes.get(person) : new Q10Res());
            if (r.getColumn(4) != null) {
                if ((boolean) r.getColumn(4))
                    res.common.add(post);
                else
                    res.uncommon.add(post);
            }
            qRes.put(person, res);
        }
        fofs.removeAll(qRes.keySet());
        for (Vertex fofWOPosts : fofs)
            qRes.put(fofWOPosts, new Q10Res());

        qRes = QueryUtils.sortByValueAndKey(qRes, false, true, TitanFTMDb.BasicClient.IDCOMP);
        List<LdbcQuery10Result> result = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Vertex,Q10Res> qresE : qRes.entrySet()) {
            LdbcQuery10Result res = new LdbcQuery10Result(client.getVLocalId((Long) qresE.getKey().getId()),
                    (String) qresE.getKey().getProperty("firstName"), (String) qresE.getKey().getProperty("lastName")
                    , qresE.getValue().getSim(),
                    (String) qresE.getKey().getProperty("gender"), (String) QueryUtils.getPersonCity(qresE.getKey()).getProperty("name"));
            result.add(res);
            i++;
            if (i == limit)
                break;
        }


        resultReporter.report(result.size(), result, operation);
    }

    static public class Q10Res implements Comparable {
        Set<Vertex> common = new HashSet<>();
        Set<Vertex> uncommon = new HashSet<>();

        public int getSim() {
            return common.size() - uncommon.size();
        }

        @Override
        public int compareTo(Object o) {
            Q10Res other = (Q10Res) o;
            return Integer.compare(this.getSim(), other.getSim());
        }



        public String toString() {
            return Integer.toString(getSim());
        }

        @Override
        public boolean equals(Object o) {
            if (!this.getClass().isInstance(o))
                return false;
            return this.getSim() == ((Q10Res) o).getSim();
        }

        @Override
        public int hashCode() {
            int result = common != null ? common.hashCode() : 0;
            result = 31 * result + (uncommon != null ? uncommon.hashCode() : 0);
            return result;
        }
    }
}
