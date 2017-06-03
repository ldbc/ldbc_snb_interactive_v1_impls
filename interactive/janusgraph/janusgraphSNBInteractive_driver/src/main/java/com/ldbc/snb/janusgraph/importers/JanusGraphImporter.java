/**
 *
 */
package com.ldbc.snb.janusgraph.importers;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.graphdb.idmanagement.IDManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Tomer Sagi
 *         <p/>
 *         Importer for a titan database
 */
public class JanusGraphImporter implements DBgenImporter {

    public final static String CSVSPLIT = "\\|";
    public final static String TUPLESPLIT = "\\.";
    private final static String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_STRING);
    private final static String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);
    private static Class<?>[] VALID_CLASSES = {Integer.class, Long.class, String.class, Date.class, BigDecimal.class, Double.class, BigInteger.class};
    private JanusGraph graph;
    private WorkloadEnum workload;
    private Logger logger = LoggerFactory.getLogger("org.janusgraph");

    /* (non-Javadoc)
     * @see hpl.alp2.titan.importers.DBgenImporter#init(java.lang.String)
     */
    @Override
    public void init(String connectionURL, WorkloadEnum workload) throws ConnectionException {
        logger.info("entered init");
        DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        /*graph = JanusGraphFactory.build().set("storage.batch-loading","true")
                                         .set("storage.backend","cassandra")
                                          .set("storage.hostname","147.83.34.145")
                                         .open();*/

        graph = JanusGraphFactory.open(connectionURL);
        this.workload = workload;
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
        logger.info("entered build schema");
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

        logger.info("created vertex labels");
        //Create Edge Labels
        for (String e : s.getEdgeTypes()) {
            management = graph.openManagement();
            if (!graph.containsRelationType(e)) {
                management.makeEdgeLabel(e).multiplicity(Multiplicity.SIMPLE).make();
                management.commit();
            }
        }
        logger.info("created edge labels");
        //Titan doesn't care if a property is used for edges or vertices,
        // hence we collect all properties and create them

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
                    }
                    /*if (clazz.equals(String.class))
                        management.buildIndex("by" + janusPropertyKey, Vertex.class).addKey(pk).buildCompositeIndex();
                    else
                        management.buildIndex("by" + janusPropertyKey, Vertex.class).addKey(pk).buildMixedIndex("search");*/
                    management.commit();
                }
            }
        }

        //Create Edge Property Labels
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

                    //if Date / number - make a mixed index to allow range queries

                    if(p.compareTo("id") == 0 || p.compareTo("creationDate") == 0) {
                        management.buildIndex("by" + janusPropertyKey, Vertex.class).addKey(pk).buildCompositeIndex();
                    }
                    /*if (clazz.equals(String.class))
                        management.buildIndex("by" + janusPropertyKey, Edge.class).addKey(pk).buildCompositeIndex();
                    else
                        management.buildIndex("by" + janusPropertyKey, Edge.class).addKey(pk).buildMixedIndex("search");
                    */
                    management.commit();
                }
            }
        }
        logger.info("created property keys and finished build schema");
        return true;
    }

    /* (non-Javadoc)
     * @see hpl.alp2.titan.importers.DBgenImporter#importData(java.io.File)
     */
    @Override
    public boolean importData(File dir) throws IOException, SchemaViolationException {
        //Based upon http://s3.thinkaurelius.com/docs/titan/current/bulk-loading.html
        //Enabled the storage.batch-loading configuration option in bdb.conf
        //Disabled automatic type creation by setting schema.default=none in bdb.conf
        //Using a local variation of BatchLoad https://github.com/tinkerpop/blueprints/wiki/Batch-Implementation

        logger.info("entered import data, dir is: {}", dir.getAbsolutePath() );
        if (!dir.isDirectory())
            return false;

        WorkLoadSchema s = this.workload.getSchema();
        Map<String, String> vpMap = s.getVPFileMap();
        Map<String, String> eMap = s.getEFileMap();

        loadVertices(dir, s.getVertexTypes().keySet());
        loadVertexProperties(dir, vpMap);
        loadEdges(dir, eMap);
        logger.info("completed import data");
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
    private void loadVertices(File dir, Set<String> vSet)
            throws IOException, SchemaViolationException {
        logger.info("entered load vertices");
        WorkLoadSchema s = this.workload.getSchema();
        for (final String vLabel : vSet) {
            HashSet<String> fileSet = new HashSet<>();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(vLabel.toLowerCase() + "_\\d+_\\d+\\.csv");
                }
            })));
            for (String fName : fileSet) {
                logger.info("reading file {}", fName);
                File csvFile = new File(dir, fName);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), Charset.forName("UTF-8")));

                //Read title line and map to vertex properties, throw exception if doesn't match
                String line = br.readLine();
                if (line==null)
                    throw new IOException("Empty file" + fName);
                String[] header = line.split(CSVSPLIT);
                try {
                    validateVHeader(s, vLabel, header);
                } catch (SchemaViolationException e) {
                    br.close();
                    throw e;
                }
                int rowLength = header.length;
                logger.info("Number of columns "+rowLength);
                String[] classNames = new String[rowLength];
                for (int i = 0; i < rowLength; i++) {
                    String prop = header[i];
                    logger.info("Column "+prop);
                    Class clazz = s.getVPropertyClass(vLabel, prop);
                    classNames[i] = clazz.getSimpleName();
                }

                //Read and load rest of file
                try {
                    int counter = 0;
                    Transaction transaction = graph.newTransaction();
                    while ((line = br.readLine()) != null) {
                        if(counter%1000 == 0) {
                            logger.info("Loading "+vLabel+" "+counter);
                        }
                        String[] row = line.split(CSVSPLIT);
                        JanusGraphVertex vertex = transaction.addVertex(vLabel);
                        for (int i = 0; i < row.length; ++i) {
                            String prop = header[i];
                            Object value = parseEntry(row[i], classNames[i]);
                            vertex.property(vLabel+"."+prop,value);
                        }
                        counter++;
                    }
                } catch (Exception e) {
                    System.err.println("Vertex load failed");
                    e.printStackTrace();
                    graph.close();
                } finally {
                    br.close();
                }
            }
            logger.info("completed {}" , vLabel);
        }

        logger.info("completed load vertices");
    }

    /**
     * Loads edges and their properties from the csv files
     *
     * @param dir     Directory in which the files reside
     * @param eMap    Map between edge description triples (FromVertexType.EdgeLabel.ToVertexType) nd expected filenames
     * @throws IOException              if has trouble reading the file
     * @throws SchemaViolationException if file doesn't match the expected schema according to the workload definition
     */
    private void loadEdges(File dir, Map<String, String> eMap)
            throws IOException, SchemaViolationException {
        logger.info("entered load edges");
        WorkLoadSchema s = this.workload.getSchema();
        for (Map.Entry<String,String> ent : eMap.entrySet()) {
            HashSet<String> fileSet = new HashSet<>();
            final String fNamePrefix = ent.getValue();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(fNamePrefix + "_\\d+_\\d+\\.csv");
                }
            })));
            for (String fName : fileSet) {
                logger.info("reading {}" , fName);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(new File(dir, fName))
                                ,"UTF-8"));

                //Read title line and map to vertex properties, throw exception if doesn't match
                String line = br.readLine();
                if (line==null)
                    throw new IOException("Empty file" + fName);
                String[] header = line.split(CSVSPLIT);
                try {
                    validateEHeader(s, ent.getKey(), header);
                } catch (SchemaViolationException e) {
                    br.close();
                    throw e;
                }
                int rowLength = header.length;
                String eLabel = ent.getKey().split(TUPLESPLIT)[1];
                //Read and load rest of file
                JanusGraphTransaction transaction= graph.newTransaction();
                try {
                    int counter = 0;
                    while ((line = br.readLine()) != null) {
                        if(counter%1000 == 0) {
                            logger.info("Loading edge "+counter);
                        }
                        String[] row = line.split(CSVSPLIT);
                        if (row.length < 2 || row[0].equals("") || row[1].equals("")) {
                            br.close();
                            graph.close();
                            throw new NumberFormatException("In " + fName + " expected long id, got" + Arrays.toString(row));
                        }
                        Long idTail = Long.parseLong(row[0]);
                        Long idHead = Long.parseLong(row[1]);
                        Vertex tail = graph.traversal().V().has(header[0],idTail).next();
                        Vertex head = graph.traversal().V().has(header[1],idHead).next();
                        Edge edge = tail.addEdge(eLabel,head);
                        //This is safe since the header has been validated against the property map
                        for (int i = 2; i < row.length; i++) {
                            edge.property(eLabel + "." + header[i], parseEntry(row[i],
                                    s.getEPropertyClass(eLabel, header[i]).getSimpleName()));
                        }
                        counter++;
                    }
                } catch (Exception e) {
                    logger.error("Failed to add edge {} from line {} ", ent.getKey(), line);
                    br.close();
                    graph.close();
                    e.printStackTrace();
                } finally {
                    br.close();
                }
                transaction.commit();
            }
            logger.info("completed {}" , ent.getKey());
        }
        logger.info("completed load edges");
    }

    /**
     * Loads n-Cardinality (1NF violating) vertex properties from the csv files
     *
     * @param dir     Directory in which the files reside
     * @param vpMap   Map between vertices and their property files
     * @throws IOException              thrown if files / directory are inaccessible
     * @throws SchemaViolationException if file schema doesn't match workload schema
     */
    private void loadVertexProperties(File dir, Map<String, String> vpMap)
            throws IOException, SchemaViolationException {
        logger.info("entered load VP");
        WorkLoadSchema s = this.workload.getSchema();

        for (Map.Entry<String,String> entry : vpMap.entrySet()) {
            HashSet<String> fileSet = new HashSet<>();
            final String fNameSuffix = entry.getValue();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(fNameSuffix + "_\\d+_\\d+\\.csv");
                }
            })));
            for (String fName : fileSet) {
                logger.info("reading {}", fName);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(new File(dir, fName))
                                ,"UTF-8"));

                //Read title line and map to vertex properties, throw exception if doesn't match
                String line = br.readLine();
                if (line==null)
                    throw new IOException("Empty file" + fName);
                String[] header = line.split(CSVSPLIT);
                String vLabel = entry.getKey().split(TUPLESPLIT)[0];
                try {
                    validateVPHeader(s, vLabel, header);
                } catch (SchemaViolationException e) {
                    br.close();
                    throw e;
                }
                int rowLength = header.length;
                //Read and load rest of file
                JanusGraphTransaction transaction = graph.newTransaction();
                try {
                    int counter = 0;
                    while ((line = br.readLine()) != null) {
                        if(counter%1000 == 0) {
                            logger.info("Loading property "+counter);
                        }
                        String[] row = line.split(CSVSPLIT);
                        Long vertexId = Long.parseLong(row[0]);
                        Vertex vertex = transaction.traversal().V().has(header[0],vertexId).next();
                        if (vertex == null) {
                            logger.error("Vertex property update failed, since no vertex with id {} from line {}",row[0], line );
                            continue;
                        }
                        //This is safe since the header has been validated against the property map

                        vertex.property(vLabel+"."+header[1], parseEntry(row[1],
                                s.getVPropertyClass(vLabel, header[1]).getSimpleName()));
                        counter++;
                    }
                } catch (Exception e) {
                    System.err.println("Failed to add properties in " + entry.getKey());
                    br.close();
                    graph.close();
                    e.printStackTrace();
                } finally {
                    br.close();
                }
                transaction.commit();
            }
        }
        logger.info("completed load VP");
    }

    /**
     * Validates the file header against the schema used by the importer
     *
     * @param s      schema to validate against
     * @param vLabel vertex type to validate against
     * @param header header of csv file
     * @return suffix of this vertex
     */
    private void validateVHeader(WorkLoadSchema s, String vLabel, String[] header) throws SchemaViolationException {
        Set<String> props = s.getVertexProperties().get(vLabel);
        if (props == null)
            throw new SchemaViolationException("No properties found for the vertex label " + vLabel);

        for (String col : header) {

            if (!props.contains(col)) {
                if (col.equals("language") && props.contains("language")) {
                    continue;
                }if (col.equals("email") && props.contains("email")) {
                    continue;
                }
                else
                    throw new SchemaViolationException("Unknown property for vertex Type" + vLabel
                            + ", found " + col + " expected " + props);
            }


            if (s.getVPropertyClass(vLabel, col) == null)
                throw new SchemaViolationException("Class definition missing for " + vLabel + "." + col);
        }
    }

    private void validateVPHeader(WorkLoadSchema s, String vLabel, String[] header) throws SchemaViolationException {
        Set<String> props = s.getVertexProperties().get(vLabel);
        if (props == null)
            throw new SchemaViolationException("No properties found for the vertex label " + vLabel);

        if (!header[0].equals(vLabel+".id") || !props.contains(header[1])) {
            throw new SchemaViolationException("Unknown property for vertex Type" + vLabel
                    + ", found " + header[1] + " expected " + props);
        }
    }

    /**
     * Validates the file header against the schema used by the importer
     *
     * @param s       schema to validate against
     * @param eTriple edge triple to validate against (triple = Vertex.Edge.Vertex)
     * @param header  header of csv file
     * @return short array of size two with the suffixes of
     * the source ([0]) and target ([1]) vertices
     */
    private void validateEHeader(WorkLoadSchema s, String eTriple, String[] header)
            throws SchemaViolationException, IllegalArgumentException {
        String[] triple = eTriple.split(TUPLESPLIT);
        if (triple.length != 3)
            throw new IllegalArgumentException("Expected parameter eTriple to " +
                    "contain a string with two '.' delimiters, found" + eTriple);
        String vF = triple[0];
        String eLabel = triple[1];
        String vT = triple[2];

        Set<String> vTypes = s.getVertexTypes().keySet();
        if (!vTypes.contains(vF) || !vTypes.contains(vT))
            throw new SchemaViolationException("Vertex types not found for triple" + eTriple + ", found " + vTypes);

        Set<String> eTypes = s.getEdgeTypes();
        if (!eTypes.contains(eLabel))
            throw new SchemaViolationException("Edge type not found for triple" + eTriple + ", found " + eTypes);

        //This may be null and that's fine, not all edges have properties
        Set<String> props = s.getEdgeProperties().get(eLabel);

        if (!header[0].equals(vF + ".id"))
            throw new SchemaViolationException("First column is not labeled " + vF + ".id, but:" + header[0]);

        if (!header[1].equals(vT + ".id"))
            throw new SchemaViolationException("Second column is not labeled " + vT + ".id, but:" + header[0]);

        for (String col : header) {
            if (col.contains(".id"))
                continue;

            if (props == null || !props.contains(col))
                throw new SchemaViolationException("Unknown property, found " + col + "expected" + props);
        }
    }

    /**
     * Converts string to appropriate Object according to class simple name
     * Currently supports String, Integer, Long, DateTime, Date and Arrays (assumed string arrays)
     *
     * @param entry           string to convert
     * @param simpleClassName name of the class to convert to
     * @return Object typed by the class with value from the string
     * @throws ClassCastException if simpleName is not supported.
     */
    public static Object parseEntry(String entry, String simpleClassName) throws ClassCastException, ParseException {
        switch (simpleClassName) {
            case "String":
            case "Arrays":
                return entry;
            case "Date":
                Date dateTime;
                if (entry.length() > 10) {
                    dateTime = DATE_TIME_FORMAT.parse(entry);
                } else {
                    dateTime = DATE_FORMAT.parse(entry);
                }
                return dateTime.getTime();
            case "Integer":
                return Integer.parseInt(entry);
            case "Long":
                return Long.parseLong(entry);
            default:
                throw new ClassCastException("No parse strategy for " + simpleClassName);
        }
    }

}
