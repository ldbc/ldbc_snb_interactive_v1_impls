package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery12Result;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.PipesFunction;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
* Created by Tomer Sagi on 06-Oct-14.
 * Given a start Person, find the Comments that this Person’s friends made in reply to Posts,
 considering only those Comments that are immediate (1-hop) replies to Posts, not the transitive (multi-hop)
 case. Only consider Posts with a Tag in a given TagClass or in a descendent of that TagClass. Count the
 number of these reply Comments, and collect the Tags that were attached to the Posts they replied to. Return
 top 20 Persons, the reply count, and the collection of Tags. Sort results descending by Comment count, and
 then ascending by Person identifier
 */
public class LdbcQuery12Handler implements OperationHandler<LdbcQuery12,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery12Handler.class);

    @Override
    public void executeOperation(final LdbcQuery12 operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) throws DbException {
        long person_id = operation.personId();
        final String tagClassName = operation.tagClassName();
        final int limit = operation.limit();

        logger.debug("Query 12 called on Person id: {} with TagClassName {}",
                person_id, tagClassName);

        TitanFTMDb.BasicClient client = dbConnectionState.client();

        //Prepare TagClass Set
        Vertex rootTag = client.getQuery().has("label", "TagClass").has("name", tagClassName).vertices().iterator().next();
        final Set<Vertex> tagClasses = new HashSet<>();
        GremlinPipeline<Vertex, Vertex> gpt = (new GremlinPipeline<>(rootTag));
        gpt.as("start").in("isSubclassOf").loop("start", QueryUtils.LOOPTRUEFUNC, QueryUtils.LOOPTRUEFUNC).fill(tagClasses);
        tagClasses.add(rootTag);

        Vertex root = null;
        try {
            root = client.getVertex(person_id, "Person");
        } catch (SchemaViolationException e) {
            e.printStackTrace();
        }

        GremlinPipeline<Vertex, Vertex> gp = (new GremlinPipeline<>(root));

        Set<Vertex> friends = new HashSet<>();
        Iterable<Row> qRes = gp.out("knows").as("friend").store(friends).in("hasCreator").as("comment")
                .out("replyOf").filter(QueryUtils.ONLYPOSTS).as("post")
                .out("hasTag").as("tag").out("hasType")
                .filter(new PipesFunction<Vertex, Boolean>() {
                    @Override
                    public Boolean compute(Vertex v) {
                        return tagClasses.contains(v);
                    }
                }).as("tagClass").select();

        Map<Vertex, Q12Res> qResInt = new HashMap<>();
        for (Row r : qRes) {
            Vertex friend = (Vertex) r.getColumn(0);
            Vertex comment = (Vertex) r.getColumn(1);
            String tagName = ((Vertex) r.getColumn(3)).getProperty("name");
            Q12Res res = (qResInt.containsKey(friend) ? qResInt.get(friend) : new Q12Res());
            res.tagNames.add(tagName);
            res.comments.add(comment);
            qResInt.put(friend, res);
        }

        friends.removeAll(qResInt.keySet());
        for (Vertex friend : friends)
            qResInt.put(friend, new Q12Res());

        qResInt = QueryUtils.sortByValueAndKey(qResInt, false, true, TitanFTMDb.BasicClient.IDCOMP);
        List<LdbcQuery12Result> result = new ArrayList<>();

        int i = 0;
        for (Map.Entry<Vertex, Q12Res> e : qResInt.entrySet()) {
            Vertex person = e.getKey();
            LdbcQuery12Result res = new LdbcQuery12Result(client.getVLocalId((Long) person.getId()),
                    (String) person.getProperty("firstName"), (String) person.getProperty("lastName"),
                    e.getValue().tagNames, e.getValue().comments.size());
            result.add(res);
            i++;
            if (i == limit)
                break;
        }
        resultReporter.report(result.size(), result, operation);
    }

    public class Q12Res implements Comparable<Q12Res> {
        Set<String> tagNames = new HashSet<>();
        Set<Vertex> comments = new HashSet<>();

        @Override
        public int compareTo(Q12Res o) {
            return Integer.compare(this.comments.size(), o.comments.size());
        }
    }
}
