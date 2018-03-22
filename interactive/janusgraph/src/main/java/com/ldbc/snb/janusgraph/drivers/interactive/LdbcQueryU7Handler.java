package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Update Query 7: add comment
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU7Handler implements OperationHandler<LdbcUpdate7AddComment,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU7Handler.class);

    @Override
    public void executeOperation(LdbcUpdate7AddComment operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState, ResultReporter
            reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        Vertex commentVertex = transaction.addVertex("Comment");

        commentVertex.property("Comment.id",operation.commentId());
        commentVertex.property("creationDate",operation.creationDate().getTime());
        commentVertex.property("locationIP", operation.locationIp());
        commentVertex.property("browserUsed",operation.browserUsed());
        commentVertex.property("content",operation.content());
        commentVertex.property("length", operation.length());

        Vertex creatorVertex = transaction.traversal().V().has("Person.id", operation.authorPersonId()).next();
        commentVertex.addEdge("hasCreator",creatorVertex);

        Vertex countryVertex = transaction.traversal().V().has("Place.id", operation.countryId()).next();
        commentVertex.addEdge("isLocatedIn",countryVertex);

        Vertex repliedVertex = null;
        if(operation.replyToPostId() != -1) {
            repliedVertex = transaction.traversal().V().has("Post.id", operation.replyToPostId()).next();
        } else {
            repliedVertex = transaction.traversal().V().has("Comment.id", operation.replyToCommentId()).next();
        }

        commentVertex.addEdge("replyOf", repliedVertex);

        for(Long tagId : operation.tagIds()) {
            Vertex tagVertex = transaction.traversal().V().has("Tag.id", tagId).next();
            commentVertex.addEdge("hasTag",tagVertex);
        }

        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
