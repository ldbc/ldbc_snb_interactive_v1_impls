package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.*;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.structure.io.gryo.GryoMapper;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.*;
import org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0;

/**
 * @author Tomer Sagi
 * @modified Arnau Prat
 */
public class JanusGraphDb extends Db {

    public class RemoteDBConnectionState extends DbConnectionState {

        private Cluster cluster = null;
        private Client client = null;
        private StandardJanusGraph graph;

        private RemoteDBConnectionState(String configFile) {
            GryoMapper kryo = GryoMapper.build().addRegistry(JanusGraphIoRegistry.getInstance()).create();
            MessageSerializer serializer = new GryoMessageSerializerV1d0(kryo);
            cluster = Cluster.build()
                    .serializer(serializer)
                    .create();
            client = cluster.connect().init();

            graph = (StandardJanusGraph) JanusGraphFactory.open(configFile);

        }

        @Override
        public void close() {
        }

        public ResultSet runQuery(String query) throws Exception {
            return client.submit(query);
        }

        public ResultSet runQuery(String query, Map<String,Object> parameters) throws Exception {
            return client.submit(query, parameters);
        }

        public StandardJanusGraph getGraph() {
            return graph;
        }
    }

    final static Logger logger = LoggerFactory.getLogger(JanusGraphDb.class);
    private RemoteDBConnectionState connectionState = null;

    /* (non-Javadoc)
     * @see com.ldbc.driver.Db#onInit(java.util.Map)
     */
    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        if(properties.get("janusgraph.configFile") == null) {
            throw new DbException("option janusgraph.configFile option not specified");
        }
        connectionState = new RemoteDBConnectionState(properties.get("janusgraph.configFile"));
        registerOperationHandler(LdbcQuery1.class, LdbcQuery1Handler.class);
        registerOperationHandler(LdbcQuery2.class, LdbcQuery2Handler.class);
        registerOperationHandler(LdbcQuery3.class, LdbcQuery3Handler.class);
        registerOperationHandler(LdbcQuery4.class, LdbcQuery4Handler.class);
        registerOperationHandler(LdbcQuery5.class, LdbcQuery5Handler.class);
        registerOperationHandler(LdbcQuery6.class, LdbcQuery6Handler.class);
        registerOperationHandler(LdbcQuery7.class, LdbcQuery7Handler.class);
        registerOperationHandler(LdbcQuery8.class, LdbcQuery8Handler.class);
        registerOperationHandler(LdbcQuery9.class, LdbcQuery9Handler.class);
        registerOperationHandler(LdbcQuery10.class, LdbcQuery10Handler.class);
        registerOperationHandler(LdbcQuery11.class, LdbcQuery11Handler.class);
        registerOperationHandler(LdbcQuery12.class, LdbcQuery12Handler.class);
        registerOperationHandler(LdbcQuery13.class, LdbcQuery13Handler.class);
        registerOperationHandler(LdbcQuery14.class, LdbcQuery14Handler.class);
        registerOperationHandler(LdbcShortQuery1PersonProfile.class, LdbcShortQuery1Handler.class);
        registerOperationHandler(LdbcShortQuery2PersonPosts.class, LdbcShortQuery2Handler.class);
        registerOperationHandler(LdbcShortQuery3PersonFriends.class, LdbcShortQuery3Handler.class);
        registerOperationHandler(LdbcShortQuery4MessageContent.class, LdbcShortQuery4Handler.class);
        registerOperationHandler(LdbcShortQuery5MessageCreator.class, LdbcShortQuery5Handler.class);
        registerOperationHandler(LdbcShortQuery6MessageForum.class, LdbcShortQuery6Handler.class);
        registerOperationHandler(LdbcShortQuery7MessageReplies.class, LdbcShortQuery7Handler.class);
        registerOperationHandler(LdbcUpdate1AddPerson.class, LdbcQueryU1Handler.class);
        registerOperationHandler(LdbcUpdate2AddPostLike.class, LdbcQueryU2Handler.class);
        registerOperationHandler(LdbcUpdate3AddCommentLike.class, LdbcQueryU3Handler.class);
        registerOperationHandler(LdbcUpdate4AddForum.class, LdbcQueryU4Handler.class);
        registerOperationHandler(LdbcUpdate5AddForumMembership.class, LdbcQueryU5Handler.class);
        registerOperationHandler(LdbcUpdate6AddPost.class, LdbcQueryU6Handler.class);
        registerOperationHandler(LdbcUpdate7AddComment.class, LdbcQueryU7Handler.class);
        registerOperationHandler(LdbcUpdate8AddFriendship.class, LdbcQueryU8Handler.class);
    }

    @Override
    protected void onClose() throws IOException {
        this.connectionState.close();
    }

    /* (non-Javadoc)
     * @see com.ldbc.driver.Db#getConnectionState()
     */
    @Override
    protected DbConnectionState getConnectionState() throws DbException {
        if (connectionState == null)
            throw new DbException("Db not initialized");
        return connectionState;
    }

}
