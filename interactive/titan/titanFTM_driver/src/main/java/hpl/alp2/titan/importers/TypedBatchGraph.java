package hpl.alp2.titan.importers;

import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanVertex;
import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.util.ExceptionFactory;
import com.tinkerpop.blueprints.util.StringFactory;
import com.tinkerpop.blueprints.util.wrappers.batch.VertexIDType;
import com.tinkerpop.blueprints.util.wrappers.batch.cache.VertexCache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tomer Sagi on 23-Sep-14.
 * Extended version of @Link{BatchGraph}
 */
public class TypedBatchGraph {
    /**
     * Default buffer size
     */
    public static final long DEFAULT_BUFFER_SIZE = 100000;
    private long bufferSize = DEFAULT_BUFFER_SIZE;
    private final TitanGraph baseGraph;
    private final VertexCache cache;
    private String vertexIdKey = null;
    private String edgeIdKey = null;
    private boolean loadingFromScratch = true;
    private long remainingBufferSize;

    private BatchEdge currentEdge = null;
    private Edge currentEdgeCached = null;

    private Object previousOutVertexId = null;

    /**
     * Constructs a BatchGraph wrapping the provided baseGraph, using the specified buffer size and expecting vertex ids of
     * the specified IdType. Supplying vertex ids which do not match this type will throw exceptions.
     *
     * @param graph      Graph to be wrapped
     * @param type       Type of vertex id expected. This information is used to optimize the vertex cache memory footprint.
     * @param bufferSize Defines the number of vertices and edges loaded before starting a new transaction. The larger this value, the more memory is required but the faster the loading process.
     */
    public TypedBatchGraph(final TitanGraph graph, final VertexIDType type, final long bufferSize) {
        if (graph == null) throw new IllegalArgumentException("Graph may not be null");
        if (type == null) throw new IllegalArgumentException("Type may not be null");
        if (bufferSize <= 0) throw new IllegalArgumentException("BufferSize must be positive");
        this.baseGraph = graph;
        this.bufferSize = bufferSize;

        vertexIdKey = null;
        edgeIdKey = null;

        cache = type.getVertexCache();

        remainingBufferSize = this.bufferSize;
    }

    /**
     * Constructs a BatchGraph wrapping the provided baseGraph.
     *
     * @param graph      Graph to be wrapped
     * @param bufferSize Defines the number of vertices and edges loaded before starting a new transaction. The larger this value, the more memory is required but the faster the loading process.
     */
    public TypedBatchGraph(final TitanGraph graph, final long bufferSize) {
        this(graph, VertexIDType.OBJECT, bufferSize);
    }

    /**
     * Constructs a BatchGraph wrapping the provided baseGraph.
     *
     * @param graph Graph to be wrapped
     */
    public TypedBatchGraph(final TitanGraph graph) {
        this(graph, VertexIDType.OBJECT, DEFAULT_BUFFER_SIZE);
    }

    private static UnsupportedOperationException retrievalNotSupported() {
        return new UnsupportedOperationException("Retrieval operations are not supported during batch loading");
    }

    private static UnsupportedOperationException removalNotSupported() {
        return new UnsupportedOperationException("Removal operations are not supported during batch loading");
    }

    /**
     * Returns the key used to set the id on the vertices or null if such has not been set
     * via {@link #setVertexIdKey(String)}
     *
     * @return The key used to set the id on the vertices or null if such has not been set
     * via {@link #setVertexIdKey(String)}
     */
    public String getVertexIdKey() {
        return vertexIdKey;
    }

    /**
     * Sets the key to be used when setting the vertex id as a property on the respective vertex.
     * If the key is null, then no property will be set.
     * If the loaded baseGraph should later be wrapped with {@link com.tinkerpop.blueprints.util.wrappers.id.IdGraph} use IdGraph.ID.
     *
     * @param key Key to be used.
     */
    public void setVertexIdKey(final String key) {
        if (!loadingFromScratch && key == null && baseGraph.getFeatures().ignoresSuppliedIds)
            throw new IllegalStateException("Cannot set vertex id key to null when not loading from scratch while ids are ignored.");
        this.vertexIdKey = key;
    }

    /**
     * Returns the key used to set the id on the edges or null if such has not been set
     * via {@link #setEdgeIdKey(String)}
     *
     * @return The key used to set the id on the edges or null if such has not been set
     * via {@link #setEdgeIdKey(String)}
     */
    public String getEdgeIdKey() {
        return edgeIdKey;
    }

    /**
     * Sets the key to be used when setting the edge id as a property on the respective edge.
     * If the key is null, then no property will be set.
     * If the loaded baseGraph should later be wrapped with {@link com.tinkerpop.blueprints.util.wrappers.id.IdGraph} use IdGraph.ID.
     *
     * @param key Key to be used.
     */
    public void setEdgeIdKey(final String key) {
        this.edgeIdKey = key;
    }

    /**
     * Whether this TypedBatchGraph is loading data from scratch or incrementally into an existing graph.
     * <p/>
     * By default, this returns true.
     *
     * @return Whether this TypedBatchGraph is loading data from scratch or incrementally into an existing graph.
     * @see #setLoadingFromScratch(boolean)
     */
    public boolean isLoadingFromScratch() {
        return loadingFromScratch;
    }

    /**
     * Sets whether the graph loaded through this instance of {@link TypedBatchGraph} is loaded from scratch
     * (i.e. the wrapped graph is initially empty) or whether graph is loaded incrementally into an
     * existing graph.
     * <p/>
     * In the former case, TypedBatchGraph does not need to check for the existence of vertices with the wrapped
     * graph but only needs to consult its own cache which can be significantly faster. In the latter case,
     * the cache is checked first but an additional check against the wrapped graph may be necessary if
     * the vertex does not exist.
     * <p/>
     * By default, TypedBatchGraph assumes that the data is loaded from scratch.
     * <p/>
     * When setting loading from scratch to false, a vertex id key must be specified first using
     * {@link #setVertexIdKey(String)} - otherwise an exception is thrown.
     *
     * @param fromScratch
     */
    public void setLoadingFromScratch(boolean fromScratch) {
        if (!fromScratch && vertexIdKey == null && baseGraph.getFeatures().ignoresSuppliedIds)
            throw new IllegalStateException("Vertex id key is required to query existing vertices in wrapped graph.");
        loadingFromScratch = fromScratch;
    }

    private void nextElement() {
        currentEdge = null;
        currentEdgeCached = null;
        if (remainingBufferSize <= 0) {
            baseGraph.commit();
            cache.newTransaction();
            remainingBufferSize = bufferSize;
        }
        remainingBufferSize--;
    }

    /**
     * Should only be invoked after loading is complete. Stopping the transaction before will cause the loading to fail.
     * Only Conclusion.SUCCESS is accepted.
     *
     * @param conclusion whether or not the current transaction was successful
     */
    public void stopTransaction(TransactionalGraph.Conclusion conclusion) {
        if (TransactionalGraph.Conclusion.SUCCESS == conclusion)
            commit();
        else
            rollback();
    }

    /**
     * Should only be invoked after loading is complete. Committing the transaction before will cause the loading to fail.
     */
    public void commit() {
        currentEdge = null;
        currentEdgeCached = null;
        remainingBufferSize = 0;
        baseGraph.commit();
    }

    /**
     * Not supported for batch loading, since data may have already been partially persisted.
     */
    public void rollback() {
        throw new IllegalStateException("Can not rollback during batch loading");
    }

    public void shutdown() {
        baseGraph.commit();
        baseGraph.shutdown();
        currentEdge = null;
        currentEdgeCached = null;
    }

    public TitanGraph getBaseGraph() {
        return baseGraph;
    }

    public Features getFeatures() {
        Features features = baseGraph.getFeatures().copyFeatures();
        features.ignoresSuppliedIds = false;
        features.isWrapper = true;
        features.supportsEdgeIteration = false;
        features.supportsThreadedTransactions = false;
        features.supportsVertexIteration = false;
        return features;
    }

    private Vertex retrieveFromCache(final Object externalID) {
        Object internal = cache.getEntry(externalID);
        if (internal instanceof Vertex) {
            return (Vertex) internal;
        } else if (internal != null) { //its an internal id
            Vertex v = baseGraph.getVertex(internal);
            cache.set(v, externalID);
            return v;
        } else return null;
    }

    private Vertex getCachedVertex(final Object externalID) {
        Vertex v = retrieveFromCache(externalID);
        if (v == null) throw new IllegalArgumentException("Vertex for given ID cannot be found: " + externalID);
        return v;
    }

    /**
     * If the input data are sorted, then out vertex will be repeated for several edges in a row.
     * In this case, bypass cache and instead immediately return a new vertex using the known id.
     * This gives a modest performance boost, especially when the cache is large or there are
     * on average many edges per vertex.
     */
    public BatchVertex getVertex(final Object id) {

        if ((previousOutVertexId != null) && (previousOutVertexId.equals(id))) {
            return new BatchVertex(previousOutVertexId);
        } else {

            Vertex v = retrieveFromCache(id);
            if (v == null) {
                if (loadingFromScratch) return null;
                else {
                    if (baseGraph.getFeatures().ignoresSuppliedIds) {
                        assert vertexIdKey != null;
                        Iterator<Vertex> iter = baseGraph.getVertices(vertexIdKey, id).iterator();
                        if (!iter.hasNext()) return null;
                        v = iter.next();
                        if (iter.hasNext())
                            throw new IllegalArgumentException("There are multiple vertices with the provided id in the database: " + id);
                    } else {
                        v = baseGraph.getVertex(id);
                        if (v == null) return null;
                    }
                    cache.set(v, id);
                }
            }
            return new BatchVertex(id);
        }
    }

    public Vertex addVertex(final Object id, String type, final Object... properties) {
        if (id == null) throw ExceptionFactory.vertexIdCanNotBeNull();
        if (retrieveFromCache(id) != null) throw ExceptionFactory.vertexWithIdAlreadyExists(id);
        nextElement();

        Vertex v = baseGraph.addVertexWithLabel(type);
        if (vertexIdKey != null) {
            v.setProperty(vertexIdKey, id);
        }
        cache.set(v, id);
        final BatchVertex newVertex = new BatchVertex(id);

        setProperties(newVertex, properties);

        return newVertex;
    }

    public Edge addEdge(final Object id, final Vertex outVertex, final Vertex inVertex, final String label) {
        return addEdge(id, outVertex, inVertex, label, (Object[]) null);
    }

    public Edge addEdge(final Object id, final Vertex outVertex, final Vertex inVertex, final String label, final Object... properties) {
        if (!BatchVertex.class.isInstance(outVertex) || !BatchVertex.class.isInstance(inVertex))
            throw new IllegalArgumentException("Given element was not created in this baseGraph");
        nextElement();

        final Vertex ov = getCachedVertex(outVertex.getId());
        final Vertex iv = getCachedVertex(inVertex.getId());

        previousOutVertexId = outVertex.getId();  //keep track of the previous out vertex id

        currentEdgeCached = baseGraph.addEdge(id, ov, iv, label);
        if (edgeIdKey != null && id != null) {
            currentEdgeCached.setProperty(edgeIdKey, id);
        }

        currentEdge = new BatchEdge();

        setProperties(currentEdge, properties);

        return currentEdge;
    }

    protected <E extends Element> E setProperties(final E element, final Object... properties) {
        if (properties != null && properties.length > 0) {
            if (properties.length == 1) {
                final Object f = properties[0];
                if (f instanceof Map<?, ?>) {
                    for (Map.Entry<?, ?> entry : ((Map<?, ?>) f).entrySet())
                        element.setProperty(entry.getKey().toString(), entry.getValue());
                } else
                    throw new IllegalArgumentException(
                            "Invalid properties: expecting a pairs of fields as String,Object or a single Map<String,Object>, but found: " + f);
            } else
                // SET PROPERTIES ONE BY ONE
                for (int i = 0; i < properties.length; i += 2)
                    element.setProperty(properties[i].toString(), properties[i + 1]);
        }
        return element;
    }

    protected Edge addEdgeSupport(final Vertex outVertex, final Vertex inVertex, final String label) {
        return this.addEdge(null, outVertex, inVertex, label);
    }

    @Override
    public String toString() {
        return StringFactory.graphString((Graph) this, this.baseGraph.toString());
    }

    public class BatchVertex implements Vertex {

        private final Object externalID;

        BatchVertex(Object id) {
            if (id == null) throw new IllegalArgumentException("External id may not be null");
            externalID = id;
        }

        @Override
        public Iterable<Edge> getEdges(Direction direction, String... labels) {
            throw retrievalNotSupported();
        }

        @Override
        public Iterable<Vertex> getVertices(Direction direction, String... labels) {
            throw retrievalNotSupported();
        }

        @Override
        public VertexQuery query() {
            throw retrievalNotSupported();
        }

        public Edge addEdge(final String label, final Vertex vertex) {
            return addEdgeSupport(this, vertex, label);
        }

        @Override
        public void setProperty(String key, Object value) {
            try {
                getCachedVertex(externalID).setProperty(key, value);
            } catch (Exception e) {
                TitanVertex v = ((TitanVertex) getCachedVertex(externalID));
                v.addProperty(key, value);
            }

        }

        public void addProperty(String key, Object value) {
            TitanVertex v = ((TitanVertex) getCachedVertex(externalID));
            v.addProperty(key, value);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getId() {
            return externalID;
        }

        @Override
        public Object getProperty(String key) {
            return getCachedVertex(externalID).getProperty(key);
        }

        @Override
        public Set<String> getPropertyKeys() {
            return getCachedVertex(externalID).getPropertyKeys();
        }

        @Override
        public Object removeProperty(String key) {
            return getCachedVertex(externalID).removeProperty(key);
        }

        @Override
        public String toString() {
            return "v[" + externalID + "]";
        }
    }

    private class BatchEdge implements Edge {

        @Override
        public Vertex getVertex(Direction direction) throws IllegalArgumentException {
            return getWrappedEdge().getVertex(direction);
        }

        @Override
        public String getLabel() {
            return getWrappedEdge().getLabel();
        }

        @Override
        public void setProperty(String key, Object value) {
            getWrappedEdge().setProperty(key, value);
        }

        @Override
        public Object getId() {
            return getWrappedEdge().getId();
        }

        @Override
        public Object getProperty(String key) {
            return getWrappedEdge().getProperty(key);
        }

        @Override
        public Set<String> getPropertyKeys() {
            return getWrappedEdge().getPropertyKeys();
        }

        @Override
        public Object removeProperty(String key) {
            return getWrappedEdge().removeProperty(key);
        }

        private Edge getWrappedEdge() {
            if (this != currentEdge) {
                throw new UnsupportedOperationException("This edge is no longer in scope");
            }
            return currentEdgeCached;
        }

        @Override
        public String toString() {
            return getWrappedEdge().toString();
        }

        public void remove() {
            throw removalNotSupported();
        }

    }


}
