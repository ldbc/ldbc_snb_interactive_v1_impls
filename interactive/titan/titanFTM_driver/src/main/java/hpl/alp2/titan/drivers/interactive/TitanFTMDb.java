/**
 *
 */
package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.Db;
import com.ldbc.driver.DbConnectionState;
import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.attribute.Cmp;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.GraphQuery;
import com.tinkerpop.blueprints.KeyIndexableGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.id.IdGraph;
import hpl.alp2.titan.importers.InteractiveWorkloadSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Tomer Sagi
 */
public class TitanFTMDb extends Db {

    final static Logger logger = LoggerFactory.getLogger(TitanFTMDb.class);
    private BasicDbConnectionState connectionState = null;

    /* (non-Javadoc)
     * @see com.ldbc.driver.Db#onInit(java.util.Map)
     */
    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        String configFile = properties.get("confFile");
        if (configFile==null)
            throw new DbException("Titan LDBC implementation is missing a configuration parameter named confFile pointing to the titan config file");
        URL u = this.getClass().getClassLoader().getResource(configFile);
        String confFilePath = u != null ? u.getPath() : configFile;
        File confFile = new File(configFile);
        if (confFile.exists())
            confFilePath = configFile;
        if (confFilePath == null)
            throw new DbException("No db configuration file found for " + configFile);
        connectionState = new BasicDbConnectionState(confFilePath);
        //connectionState = new BasicDbConnectionState(configFile);
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
            throw new DbException("Titan not ready");
        return connectionState;
    }

    static class BasicClient {

        public static Comparator<Vertex> IDCOMP = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return Long.compare((Long) o1.getId(), (Long) o2.getId());
            }
        };

        public static Comparator<Vertex> NAMECOMP = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return ((String) o1.getProperty("name")).compareTo((String) o2.getProperty("name"));
            }
        };

        public static Comparator<Vertex> TITLECOMP = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return ((String) o1.getProperty("title")).compareTo((String) o2.getProperty("title"));
            }
        };
        private KeyIndexableGraph g;
        private InteractiveWorkloadSchema s = new InteractiveWorkloadSchema();
        private long mult;

        BasicClient(String pathToConfFile) {
            //Shifting all ids by mult to accomodate vertex type id suffixes
            mult = (int) (Math.log10((double) s.getVertexTypes().size()) + 1);
            //Using IDgraph to wrap titan graph which doesn't allow user vertex defined vertex IDs
            logger.debug("pathtoConfFile: {}",pathToConfFile);
            TitanGraph base = TitanFactory.open(pathToConfFile);
            g = new IdGraph<>(base, true, false);
        }

        /**
         * Gets the specified vertex or null if no such vertex is found
         *
         * @param id A long representing the id of the vertex to be retrieved
         * @return the specified vertex or null if no such vertex is found
         */
        public Vertex getVertex(long id, String label) throws SchemaViolationException {
            return g.getVertex(getVId(id, label));
        }

        /**
         * Adds a vertex
         * //TODO migrate to work directly on titan graph and not IDGraph
         * @param id id of vertex to add (without the type suffix)
         * @param label type of vertex to add
         * @param props property map for vertex (excluding id)
         * @return true if adding succeeded
         */
        public Vertex addVertex(long id, String label, Map<String, Object> props) {
            Vertex v = g.addVertex(getVId(id, label));
            if (v == null)
                return null;
            for (Map.Entry<String, Object> p : props.entrySet())
                v.setProperty(p.getKey(), p.getValue());
            return v;
        }

        /**
         * Returns the graph-global ID (GID) for the given id and vertex label
         *
         * @param id    id to be converted
         * @param label Vertex type label
         * @return GID
         */
        public long getVId(long id, String label) {
            Integer suffix = s.getVertexTypes().get(label);
            if (suffix == null) {
                logger.error("{} vertex type is not defined in the schema for {}", label, s.getClass().getSimpleName());
                return id;
            }

            return (long) (id * Math.pow(10, mult)) + suffix;
        }

        /**
         * Returns the type-specific id from a given GID
         *
         * @param id GID to convert
         * @return local id
         */
        public long getVLocalId(long id) {
            return id / ((long) Math.pow(10, mult));
        }

        /**
         * Gets the specified vertices or null if no such vertex is found
         *
         * @param label     vertex type label
         * @param limit     int value limiting the result. use Integer.MAX_VALUE for unlimited
         * @param pValueMap PropertyKey->Value map
         * @return the specified vertices or null if no such vertex is found
         */
        public Iterable<Vertex> getVertices(String label, Map<String, String> pValueMap, int limit) throws SchemaViolationException {
            Integer suffix = s.getVertexTypes().get(label);
            Set<Vertex> res = new HashSet<>();
            if (suffix == null)
                throw new SchemaViolationException(label + " vertex type is not defined in the schema for " + s.getClass().getSimpleName());
            GraphQuery gq = g.query();
            for (String property : pValueMap.keySet())
                gq = gq.has(property, pValueMap.get(property));
            if (limit != Integer.MAX_VALUE)
                gq = gq.limit(limit);

            for (Vertex v : gq.vertices())
                if ((((Long) v.getId()) % mult) == suffix)
                    res.add(v);

            return res;
        }

        /**
         * Gets the specified vertices or null if no such vertex is found
         *
         * @param property name
         * @param label    vertex type label
         * @param value    value in property field
         * @param limit    int value limiting the result. use Integer.MAX_VALUE for unlimited
         * @return the specified vertices or null if no such vertex is found
         */
        public Set<Vertex> getVertices(String label, String property, Object value, int limit) throws SchemaViolationException {
            Integer suffix = s.getVertexTypes().get(label);
            Set<Vertex> res = new HashSet<>();
            if (suffix == null)
                throw new SchemaViolationException(label + " vertex type is not defined in the schema for " + s.getClass().getSimpleName());

            Iterable<Vertex> qres;
            GraphQuery q = g.query().has("label", label).has(property, Cmp.EQUAL, value);
            if (limit == Integer.MAX_VALUE)
                qres = q.vertices();
            else
                qres = q.limit(limit).vertices();

            for (Vertex v : qres)
                res.add(v);

            return res;
        }

        /**
         * Gets the specified vertices or null if no such vertex is found
         *
         * @param property name
         * @param value    value in property field
         * @param limit    int value limiting the result. use Integer.MAX_VALUE for unlimited
         * @return the specified vertices or null if no such vertex is found
         */
        @SuppressWarnings("unchecked")
        public Iterable<Vertex> getVertices(String property, String value, int limit) {
            if (limit == Integer.MAX_VALUE)
                return g.query().has(value, Cmp.EQUAL, value).vertices();
            else
                return g.query().has(property, Cmp.EQUAL, value).limit(limit).vertices();

        }

        public GraphQuery getQuery() {
            return g.query();
        }

        /**
         * Adds an edge accorsing to supplied parameters
         *
         * @param vOut   Outgoing vertex
         * @param vIn    Incoming vertex
         * @param label  Edge label
         * @param eProps Map of edge property name->value
         */
        public void addEdge(Vertex vOut, Vertex vIn, String label, Map<String, Object> eProps) {
            Edge e = g.addEdge(null, vOut, vIn, label);
            for (Map.Entry<String, Object> entry : eProps.entrySet())
                e.setProperty(entry.getKey(), entry.getValue());
        }

        public void commit() {
            ((TitanGraph)((IdGraph)this.g).getBaseGraph()).commit();
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
            this.basicClient.g.shutdown();
        }
    }

}
