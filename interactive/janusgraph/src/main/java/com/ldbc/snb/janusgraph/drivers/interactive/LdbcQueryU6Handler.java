package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
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
 * Add post vertex, creator edge, container edge, isLocatedIn edge and tag edges.
 * All other vertices are assumed to exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU6Handler implements OperationHandler<LdbcUpdate6AddPost,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU6Handler.class);

    @Override
    public void executeOperation(LdbcUpdate6AddPost operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState, ResultReporter
            reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        try {
            Vertex postVertex = transaction.addVertex("Post");

            postVertex.property("Post.id", operation.postId());
            postVertex.property("imageFile", operation.imageFile());
            postVertex.property("creationDate", operation.creationDate().getTime());
            postVertex.property("locationIP", operation.locationIp());
            postVertex.property("browserUsed", operation.browserUsed());
            postVertex.property("language", operation.language());
            postVertex.property("content", operation.content());
            postVertex.property("length", operation.length());

            Vertex creatorVertex = transaction.traversal().V().has("Person.id", operation.authorPersonId()).next();
            postVertex.addEdge("hasCreator", creatorVertex);
            Vertex forumVertex = transaction.traversal().V().has("Forum.id", operation.forumId()).next();
            forumVertex.addEdge("containerOf", postVertex);
            Vertex countryVertex = transaction.traversal().V().has("Place.id", operation.countryId()).next();
            postVertex.addEdge("isLocatedIn", countryVertex);

            for (Long tagId : operation.tagIds()) {
                Vertex tagVertex = transaction.traversal().V().has("Tag.id", tagId).next();
                postVertex.addEdge("hasTag", tagVertex);
            }

            reporter.report(0, LdbcNoResult.INSTANCE, operation);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            transaction.commit();
        }
    }
}
