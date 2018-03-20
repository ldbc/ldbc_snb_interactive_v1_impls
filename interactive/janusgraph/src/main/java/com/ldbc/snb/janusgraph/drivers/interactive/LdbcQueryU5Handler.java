package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate5AddForumMembership;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Adds membership edge between forum and person. Assumes both exist.
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU5Handler implements OperationHandler<LdbcUpdate5AddForumMembership,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU5Handler.class);

    @Override
    public void executeOperation(LdbcUpdate5AddForumMembership operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        Vertex forumVertex = transaction.traversal().V().has("Forum.id", operation.forumId()).next();
        Vertex personVertex = transaction.traversal().V().has("Person.id", operation.personId()).next();
        Edge edge = forumVertex.addEdge("hasMember",personVertex);
        edge.property("joinDate", operation.joinDate().getTime());


        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);

    }
}
