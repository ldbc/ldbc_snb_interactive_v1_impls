package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraph;
import com.ldbc.snb.janusgraph.importer.InteractiveWorkloadSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * @author Tomer Sagi
 * @modified Arnau Prat
 */
public class JanusGraphDb extends Db {

    final static Logger logger = LoggerFactory.getLogger(JanusGraphDb.class);
    private BasicDbConnectionState connectionState = null;

    /* (non-Javadoc)
     * @see com.ldbc.driver.Db#onInit(java.util.Map)
     */
    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        String configFile = properties.get("confFile");
        if (configFile==null)
            throw new DbException("JanusGraph LDBC implementation is missing a configuration parameter named confFile pointing to the titan config file");
        URL u = this.getClass().getClassLoader().getResource(configFile);
        String confFilePath = u != null ? u.getPath() : configFile;
        File confFile = new File(configFile);
        if (confFile.exists())
            confFilePath = configFile;
        if (confFilePath == null)
            throw new DbException("No db configuration file found for " + configFile);
        connectionState = new BasicDbConnectionState(confFilePath);
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
        if (!connectionState.isReady())
            throw new DbException("JanusGraph not ready");
        return connectionState;
    }

    static class BasicClient {

        public InteractiveWorkloadSchema s = new InteractiveWorkloadSchema();
        public JanusGraph graph = null;

        BasicClient(String pathToConfFile) {
            //Using IDgraph to wrap titan graph which doesn't allow user vertex defined vertex IDs
            logger.debug("pathtoConfFile: {}",pathToConfFile);
            graph = JanusGraphFactory.open(pathToConfFile);
        }
    }

    static class BasicDbConnectionState extends DbConnectionState {

        private final BasicClient basicClient;

        private BasicDbConnectionState(String pathToConfFile) {
            basicClient = new BasicClient(pathToConfFile);
        }

        BasicClient client() {
            return basicClient;
        }

        boolean isReady() {
            //TODO implement ping
            return true;
        }

        @Override
        public void close() throws IOException {
            this.basicClient.graph.close();
        }
    }

}
