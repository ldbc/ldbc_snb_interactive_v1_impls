package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate4AddForum;
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
 * Adds forum and it's moderator and tags. Assume moderator person and tags exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU4Handler implements OperationHandler<LdbcUpdate4AddForum,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU4Handler.class);

    @Override
    public void executeOperation(LdbcUpdate4AddForum operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState, ResultReporter
            reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        Vertex forumVertex = transaction.addVertex("Forum");
        forumVertex.property("Forum.id", operation.forumId());
        forumVertex.property("title", operation.forumTitle());
        forumVertex.property("creationDate", operation.creationDate().getTime());

        Vertex moderatorVertex = transaction.traversal().V().has("Person.id", operation.moderatorPersonId()).next();
        forumVertex.addEdge("hasModerator",moderatorVertex);
        for(Long tagId : operation.tagIds()) {
            Vertex tagVertex = transaction.traversal().V().has("Tag.id", tagId).next();
            forumVertex.addEdge("hasTag",tagVertex);
        }

        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);

    }
}
