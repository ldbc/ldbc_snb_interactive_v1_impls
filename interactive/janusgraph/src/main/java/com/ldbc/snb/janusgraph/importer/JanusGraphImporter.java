/**
 *
 */
package com.ldbc.snb.janusgraph.importer;

import com.ldbc.snb.janusgraph.importer.utils.LoadingStats;
import com.ldbc.snb.janusgraph.importer.utils.StatsReportingThread;
import com.ldbc.snb.janusgraph.importer.utils.ThreadPool;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Tomer Sagi
 *         <p/>
 *         Importer for a titan database
 */
public class JanusGraphImporter {

    public final static String CSVSPLIT = "\\|";
    public final static String TUPLESPLIT = "\\.";
    private static Class<?>[] VALID_CLASSES = {Integer.class, Long.class, String.class, Date.class, BigDecimal.class, Double.class, BigInteger.class};
    private StandardJanusGraph graph;
    private WorkloadEnum workload;
    private Logger logger = LoggerFactory.getLogger("org.janusgraph");
    private ArrayList<String> vertexIndexes = new ArrayList<String>();
    private JanusGraphImporterConfig config;

    /* (non-Javadoc)
     * @see hpl.alp2.titan.importers.DBgenImporter#init(java.lang.String)
     */
    public void init(String connectionURL, WorkloadEnum workload, JanusGraphImporterConfig config) throws ConnectionException {
        logger.info("entered init");
        graph = (StandardJanusGraph)JanusGraphFactory.open(connectionURL);
        this.workload = workload;
        this.config = config;
        System.out.println("Connected");
        if (!buildSchema(workload.getSchema())) {
            logger.error("failed to create schema");
        }
        logger.info("Completed init");
    }

    /**
     * Builds interactive workload schema in database
     *
     * @return true if building the schema succeeded
     */
    private boolean buildSchema(WorkLoadSchema s) {
        logger.info("Building Schema");
        JanusGraphManagement management;
        //Create Vertex Labels and assign id suffix
        Set<String> vTypes = s.getVertexTypes().keySet();
        for (String v : vTypes) {
            management = graph.openManagement();
            if (!graph.containsVertexLabel(v)) {
                management.makeVertexLabel(v).make();
                management.commit();
            }
        }

        logger.info("Creating Vertex Labels");
        for (String e : s.getEdgeTypes()) {
            management = graph.openManagement();
            if (!graph.containsRelationType(e)) {
                management.makeEdgeLabel(e).multiplicity(Multiplicity.SIMPLE).make();
                management.commit();
            }
        }

        logger.info("Creating edge labels");
        //Create Vertex Property Labels
        Set<Class<?>> allowed = new HashSet<>(Arrays.asList(VALID_CLASSES));
        for (String v : s.getVertexProperties().keySet()) {
            for (String p : s.getVertexProperties().get(v)) {
                String janusPropertyKey = v+"."+p;
                logger.info("Created Property Key "+janusPropertyKey);
                if (!graph.containsRelationType(p)) {
                    management = graph.openManagement();
                    Class<?> clazz = s.getVPropertyClass(v, p);
                    Cardinality c = (clazz.getSimpleName().equals("Arrays") ? Cardinality.LIST : Cardinality.SINGLE);
                    if (clazz.equals(Arrays.class))
                        clazz = String.class;

                    PropertyKey pk;
                    //Date represented as long values
                    if (clazz.equals(Date.class))
                        pk = management.makePropertyKey(janusPropertyKey).dataType(Long.class).cardinality(c).make();
                    else
                        pk = management.makePropertyKey(janusPropertyKey).dataType(clazz).cardinality(c).make();

                    if (!allowed.contains(clazz)) {
                        logger.error("Class {} unsupported by backend index", clazz.getSimpleName());
                        continue;
                    }

                    if(p.compareTo("id") == 0 || p.compareTo("creationDate") == 0) {
                        management.buildIndex("by" + janusPropertyKey, Vertex.class).addKey(pk).buildCompositeIndex();
                        //vertexIndexes.add(janusPropertyKey);
                    }
                    management.commit();
                }
            }
        }

        logger.info("Creating edge property labels");
        for (String e : s.getEdgeProperties().keySet()) {
            for (String p : s.getEdgeProperties().get(e)) {

                String janusPropertyKey = e+"."+p;
                if (!graph.containsRelationType(p)) {
                    management = graph.openManagement();
                    Class<?> clazz = s.getEPropertyClass(e, p);
                    if (clazz.equals(Arrays.class))
                        clazz = String.class;
                    Cardinality c = (clazz.isArray() ? Cardinality.LIST : Cardinality.SINGLE);
                    PropertyKey pk;
                    //Date represented as long values
                    if (clazz.equals(Date.class))
                        pk = management.makePropertyKey(janusPropertyKey).dataType(Long.class).cardinality(c).make();
                    else
                        pk = management.makePropertyKey(janusPropertyKey).dataType(clazz).cardinality(c).make();

                    if (!allowed.contains(clazz)) {
                        logger.error("Class {} unsupported by backend index", clazz.getSimpleName());
                        continue;
                    }

                    if(p.compareTo("id") == 0 || p.compareTo("creationDate") == 0) {
                        management.buildIndex("by" + janusPropertyKey, Vertex.class).addKey(pk).buildCompositeIndex();
                    }
                    management.commit();
                }
            }
        }
        graph.tx().commit();
        logger.info("Finished schema creation");
        return true;
    }

    /* (non-Javadoc)
     * @see hpl.alp2.titan.importers.DBgenImporter#importData(java.io.File)
     */
    public boolean importData(File dir) throws IOException, SchemaViolationException {
        logger.info("entered import data, dir is: {}", dir.getAbsolutePath() );
        if (!dir.isDirectory())
            return false;

        LoadingStats stats = new LoadingStats();

        StatsReportingThread statsThread = new StatsReportingThread(stats,5000);
        statsThread.start();

        WorkLoadSchema s = this.workload.getSchema();
        Map<String, String> vpMap = s.getVPFileMap();
        Map<String, String> eMap = s.getEFileMap();

        loadVertices(dir, s.getVertexTypes().keySet(), stats);
        loadEdges(dir, eMap,stats);
        loadVertexProperties(dir, vpMap, stats);
        logger.info("completed import data");
        try {
            statsThread.interrupt();
        } catch(Exception e) {

        }
        statsThread.interrupt();
        logger.info("Number of vertices loaded: {}. Number of edges loaded {}", stats.getNumVertices(), stats.getNumEdges());
        return true;
    }

    /**
     * Loads vertices and their properties from the csv files
     *
     * @param dir     Directory in which the files reside
     * @param vSet    Set pf expected vertex types
     * @throws IOException              if has trouble reading the file
     * @throws SchemaViolationException if file doesn't match the expected schema according to the workload definition
     */
    private void loadVertices(File dir, Set<String> vSet, LoadingStats stats)
            throws IOException, SchemaViolationException {
        logger.info("entered load vertices");

        List<VertexFileReadingTask> tasks = new ArrayList<VertexFileReadingTask>();
        WorkLoadSchema schema = this.workload.getSchema();

        ThreadPool vertexLoadingThreadPool = new ThreadPool(config.getNumThreads(),2*config.getNumThreads());
        for (final String vertexLabel : vSet) {
            HashSet<String> fileSet = new HashSet<>();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(vertexLabel.toLowerCase() + "_\\d+_\\d+\\.csv");
                }
            })));
            for (String fileName : fileSet) {
                tasks.add(new VertexFileReadingTask(graph,schema,dir+"/"+fileName,vertexLabel,vertexLoadingThreadPool.getTaskQueue(),config.getTransactionSize(),stats));
            }
        }

        ThreadPool fileReadingThreadPool = new ThreadPool(1,tasks.size());
        for(VertexFileReadingTask task : tasks) {
            try {
                fileReadingThreadPool.execute(task);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        try {
            fileReadingThreadPool.stop();
            vertexLoadingThreadPool.stop();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        logger.info("completed loading of Vertices");
    }


    private void loadEdges(File dir, Map<String, String> eMap, LoadingStats stats)
            throws IOException, SchemaViolationException {
        logger.info("entered load edges");
        List<EdgeFileReadingTask> tasks = new ArrayList<EdgeFileReadingTask>();
        WorkLoadSchema schema = this.workload.getSchema();

        ThreadPool edgeLoadingThreadPool = new ThreadPool(config.getNumThreads(),config.getNumThreads());
        for (Map.Entry<String,String> ent : eMap.entrySet()) {
            HashSet<String> fileSet = new HashSet<>();
            final String fNamePrefix = ent.getValue();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(fNamePrefix + "_\\d+_\\d+\\.csv");
                }
            })));
            for (String fileName : fileSet) {
                tasks.add(new EdgeFileReadingTask(graph,schema,dir+"/"+fileName,ent.getKey(),edgeLoadingThreadPool.getTaskQueue(),config.getTransactionSize(),stats));
            }
        }

        ThreadPool fileReadingThreadPool = new ThreadPool(1,tasks.size());
        for(EdgeFileReadingTask task : tasks) {
            try {
                fileReadingThreadPool.execute(task);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        try {
            fileReadingThreadPool.stop();
            edgeLoadingThreadPool.stop();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        logger.info("completed loading of Edges");
    }

    private void loadVertexProperties(File dir, Map<String, String> vpMap, LoadingStats stats)
            throws IOException, SchemaViolationException {
        logger.info("entered load VP");
        WorkLoadSchema s = this.workload.getSchema();

        List<VertexPropertyFileReadingTask> tasks = new ArrayList<VertexPropertyFileReadingTask>();
        WorkLoadSchema schema = this.workload.getSchema();

        ThreadPool vertexPropertyLoadingThreadPool = new ThreadPool(config.getNumThreads(),2*config.getNumThreads());
        for (final Map.Entry<String,String> entry : vpMap.entrySet()) {
            HashSet<String> fileSet = new HashSet<>();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(entry.getValue() + "_\\d+_\\d+\\.csv");
                }
            })));
            String vertexLabel = entry.getKey().substring(0,entry.getKey().indexOf("."));
            for (String fileName : fileSet) {
                tasks.add(new VertexPropertyFileReadingTask(graph,schema,dir+"/"+fileName,vertexLabel,vertexPropertyLoadingThreadPool.getTaskQueue(),config.getTransactionSize(),stats));
            }
        }

        ThreadPool fileReadingThreadPool = new ThreadPool(1,tasks.size());
        for(VertexPropertyFileReadingTask task : tasks) {
            try {
                fileReadingThreadPool.execute(task);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        try {
            fileReadingThreadPool.stop();
            vertexPropertyLoadingThreadPool.stop();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        logger.info("completed loading of Vertex Properties");
    }


}
