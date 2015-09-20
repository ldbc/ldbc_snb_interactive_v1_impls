/**
 *
 */
package hpl.alp2.titan.importers;

import com.thinkaurelius.titan.core.*;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.batch.VertexIDType;
import com.tinkerpop.blueprints.util.wrappers.id.IdGraph;
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
public class TitanImporter implements DBgenImporter {

    public final static String CSVSPLIT = "\\|";
    public final static String TUPLESPLIT = "\\.";
    private final static String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_STRING);
    private final static String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);
    private static Class<?>[] VALID_CLASSES = {Integer.class, Long.class, String.class, Date.class, BigDecimal.class, Double.class, BigInteger.class};
    private TitanGraph g;
    private WorkloadEnum workload;
    private Map<String, Integer> typeMultMap;
    private int mult; //used to make ids globally unique, signifies the number of digits to push the number back by
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /* (non-Javadoc)
     * @see hpl.alp2.titan.importers.DBgenImporter#init(java.lang.String)
     */
    @Override
    public void init(String connectionURL, WorkloadEnum workload) throws ConnectionException {
        logger.debug("entered init");
        DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        g = TitanFactory.open(connectionURL);
        this.workload = workload;
        typeMultMap = workload.getSchema().getVertexTypes();
        System.out.println("Connected");
        if (!buildSchema(workload.getSchema())) {
            logger.error("failed to create schema");
        }

        logger.debug("Completed init");
    }

    /**
     * Builds interactive workload schema in database
     *
     * @return true if building the schema succeeded
     */
    private boolean buildSchema(WorkLoadSchema s) {
        logger.debug("entered build schema");
        TitanManagement m;
        //Create Vertex Labels and assign id suffix
        Set<String> vTypes = s.getVertexTypes().keySet();
        mult = (int) (Math.log10((double) vTypes.size()) + 1);
        for (String v : vTypes) {
            m = g.getManagementSystem();
            if (!g.containsVertexLabel(v)) {
                m.makeVertexLabel(v).make();
                m.commit();
            }
        }

        g.commit();
        logger.debug("created vertex labels");
        //Create Edge Labels
        for (String e : s.getEdgeTypes()) {
            m = g.getManagementSystem();
            if (!g.containsRelationType(e)) {
                m.makeEdgeLabel(e).multiplicity(Multiplicity.SIMPLE).make();
                m.commit();
            }
        }
        g.commit();
        logger.debug("created edge labels");
        //Titan doesn't care if a property is used for edges or vertices,
        // hence we collect all properties and create them

        //Create Vertex Property Labels
        Set<String> pCache = new HashSet<>();
        Set<Class<?>> allowed = new HashSet<>(Arrays.asList(VALID_CLASSES));
        makeID();
        for (String v : s.getVertexProperties().keySet()) {
            for (String p : s.getVertexProperties().get(v)) {

                m = g.getManagementSystem();
                if (!g.containsRelationType(p) && !pCache.contains(p)) {
                    Class<?> clazz = s.getVPropertyClass(v, p);
                    Cardinality c = (clazz.getSimpleName().equals("Arrays") ? Cardinality.LIST : Cardinality.SINGLE);
                    if (clazz.equals(Arrays.class))
                        clazz = String.class;

                    PropertyKey pk;
                    //Date represented as long values
                    if (clazz.equals(Date.class))
                        pk = m.makePropertyKey(p).dataType(Long.class).cardinality(c).make();
                    else
                        pk = m.makePropertyKey(p).dataType(clazz).cardinality(c).make();

                    if (!allowed.contains(clazz)) {
                        logger.error("Class {} unsupported by backend index", clazz.getSimpleName());
                        continue;
                    }

                    //if Date / number - make a mixed index to allow range queries
                    if (clazz.equals(String.class))
                        m.buildIndex("by" + p, Vertex.class).addKey(pk).buildCompositeIndex();
                    else
                        m.buildIndex("by" + p, Vertex.class).addKey(pk).buildMixedIndex("search");
                    m.commit();
                    pCache.add(p);
                }
            }
        }
        g.commit();

        //Create Edge Property Labels
        for (String e : s.getEdgeProperties().keySet()) {
            for (String p : s.getEdgeProperties().get(e)) {

                m = g.getManagementSystem();
                if (!g.containsRelationType(p) && !pCache.contains(p)) {
                    Class<?> clazz = s.getEPropertyClass(e, p);
                    if (clazz.equals(Arrays.class))
                        clazz = String.class;
                    Cardinality c = (clazz.isArray() ? Cardinality.LIST : Cardinality.SINGLE);
                    PropertyKey pk;
                    //Date represented as long values
                    if (clazz.equals(Date.class))
                        pk = m.makePropertyKey(p).dataType(Long.class).cardinality(c).make();
                    else
                        pk = m.makePropertyKey(p).dataType(clazz).cardinality(c).make();

                    if (!allowed.contains(clazz)) {
                        logger.error("Class {} unsupported by backend index", clazz.getSimpleName());
                        continue;
                    }

                    //if Date / number - make a mixed index to allow range queries
                    if (clazz.equals(String.class))
                        m.buildIndex("by" + p, Edge.class).addKey(pk).buildCompositeIndex();
                    else
                        m.buildIndex("by" + p, Vertex.class).addKey(pk).buildMixedIndex("search");

                    m.commit();
                    pCache.add(p);
                }
            }
        }
        g.commit();
        logger.debug("created property keys and finished build schema");
        return true;
    }

    /**
     * Creates an ID property
     */
    private void makeID() {
        if (!g.containsRelationType(IdGraph.ID)) {
            //make property and index
            TitanManagement m = g.getManagementSystem();
            PropertyKey id = m.makePropertyKey(IdGraph.ID).dataType(Long.class).cardinality(Cardinality.SINGLE).make();
            m.buildIndex("byvid", Vertex.class).addKey(id).unique().buildCompositeIndex();
            m.commit();
            g.commit();
        }
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

        logger.debug("entered import data, dir is: {}", dir.getAbsolutePath() );
        if (!dir.isDirectory())
            return false;

        WorkLoadSchema s = this.workload.getSchema();
        Map<String, String> vpMap = s.getVPFileMap();
        Map<String, String> eMap = s.getEFileMap();

        TypedBatchGraph bgraph = new TypedBatchGraph(g, VertexIDType.NUMBER, 10000);
        bgraph.setVertexIdKey(IdGraph.ID);

        loadVertices(bgraph, dir, s.getVertexTypes().keySet());
        loadVertexProperties(bgraph, dir, vpMap);
        loadEdges(bgraph, dir, eMap);
        logger.debug("completed import data");
        return true;


    }


    /**
     * Loads vertices and their properties from the csv files
     *
     * @param bgraph  A Typed Batch Graph shared between load methods
     * @param dir     Directory in which the files reside
     * @param vSet    Set pf expected vertex types
     * @throws IOException              if has trouble reading the file
     * @throws SchemaViolationException if file doesn't match the expected schema according to the workload definition
     */
    private void loadVertices(TypedBatchGraph bgraph, File dir, Set<String> vSet)
            throws IOException, SchemaViolationException {
        logger.debug("entered load vertices");
        WorkLoadSchema s = this.workload.getSchema();
        for (final String vLabel : vSet) {
            HashSet<String> fileSet = new HashSet<>();
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(vLabel.toLowerCase() + "_\\d+_0\\.csv");
                }
            })));
            for (String fName : fileSet) {
                logger.debug("reading file {}", fName);
                File csvFile = new File(dir, fName);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), Charset.forName("UTF-8")));

                //Read title line and map to vertex properties, throw exception if doesn't match
                String line = br.readLine();
                if (line==null)
                    throw new IOException("Empty file" + fName);
                String[] header = line.split(CSVSPLIT);
                short suffix;
                try {
                    suffix = validateVHeader(s, vLabel, header);
                } catch (SchemaViolationException e) {
                    br.close();
                    throw e;
                }
                int rowLength = header.length;
                String[] classNames = new String[rowLength];
                for (int i = 1; i < rowLength; i++) {
                    String prop = header[i];
                    Class clazz = s.getVPropertyClass(vLabel, prop);
                    if (clazz == null)
                        /*
                    This is a hack due to the fact that Titan has
                    global property keys and language has SINGLE
                    cardinality for Post.language and LIST cardinality
                    in Person.language.
                    */
                        if (prop.equals("language"))
                            clazz = s.getVPropertyClass(vLabel, "lang");
                        else
                            throw new SchemaViolationException("property " + prop + " not found in " + vLabel + " schema definition");
                    classNames[i] = clazz.getSimpleName();

                }

                //Read and load rest of file

                try {
                    while ((line = br.readLine()) != null) {
                        String[] row = line.split(CSVSPLIT);
                        long id = (long) Math.pow(10, mult) * Long.parseLong(row[0]) + suffix;
                        Object[] propArray = new Object[(rowLength - 1) * 2];
                        //This is safe since the header has been validated against the property map
                        for (int i = 0; i < (rowLength - 1) * 2; i += 2) {
                            int originalIndex = 1 + i / 2;
                            String prop = header[originalIndex];
                            propArray[i] = prop;
                            propArray[i + 1] = parseEntry(row[originalIndex], classNames[originalIndex]);
                        }
                        bgraph.addVertex(id, vLabel, propArray);
                    }
                } catch (Exception e) {
                    System.err.println("Vertex load failed");
                    e.printStackTrace();
                    bgraph.shutdown();
                } finally {
                    br.close();
                }

                bgraph.commit();
            }
            logger.debug("completed {}" , vLabel);
        }

        logger.debug("completed load vertices");
    }

    /**
     * Loads edges and their properties from the csv files
     *
     * @param bgraph  A Typed Batch Graph to be shared between load methods
     * @param dir     Directory in which the files reside
     * @param eMap    Map between edge description triples (FromVertexType.EdgeLabel.ToVertexType) nd expected filenames
     * @throws IOException              if has trouble reading the file
     * @throws SchemaViolationException if file doesn't match the expected schema according to the workload definition
     */
    private void loadEdges(TypedBatchGraph bgraph, File dir, Map<String, String> eMap)
            throws IOException, SchemaViolationException {
        logger.debug("entered load edges");
        WorkLoadSchema s = this.workload.getSchema();
        for (Map.Entry<String,String> ent : eMap.entrySet()) {
            HashSet<String> fileSet = new HashSet<>();
            final String fNameSuffix = ent.getValue().replace("_0.csv", "");
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(fNameSuffix + "_\\d+_0\\.csv");
                }
            })));
            for (String fName : fileSet) {
                logger.debug("reading {}" , fName);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(new File(dir, fName))
                                ,"UTF-8"));


                //Read title line and map to vertex properties, throw exception if doesn't match
                String line = br.readLine();
                if (line==null)
                    throw new IOException("Empty file" + fName);
                String[] header = line.split(CSVSPLIT);
                short[] idSuffixes;
                try {
                    idSuffixes = validateEHeader(s, ent.getKey(), header);
                } catch (SchemaViolationException e) {
                    br.close();
                    throw e;
                }
                int rowLength = header.length;
                String eLabel = ent.getKey().split(TUPLESPLIT)[1];
                //Read and load rest of file
                try {
                    while ((line = br.readLine()) != null) {
                        String[] row = line.split(CSVSPLIT);
                        if (row.length < 2 || row[0].equals("") || row[1].equals("")) {
                            br.close();
                            bgraph.shutdown();
                            throw new NumberFormatException("In " + fName + " expected long id, got" + Arrays.toString(row));
                        }
                        long id1 = (long) Math.pow(10, mult) * Long.parseLong(row[0]) + idSuffixes[0];
                        long id2 = (long) Math.pow(10, mult) * Long.parseLong(row[1]) + idSuffixes[1];
                        Vertex fV = bgraph.getVertex(id1);
                        Vertex tV = bgraph.getVertex(id2);
                        Edge edge = bgraph.addEdge(null, fV, tV, eLabel);
                        //This is safe since the header has been validated against the property map
                        for (int i = 2; i < rowLength; i++)
                            edge.setProperty(header[i], parseEntry(row[i],
                                    s.getEPropertyClass(eLabel, header[i]).getSimpleName()));
                    }
                } catch (Exception e) {
                    logger.error("Failed to add edge {} from line {} ", ent.getKey(), line);
                    br.close();
                    bgraph.shutdown();
                    e.printStackTrace();
                } finally {
                    br.close();
                }
                bgraph.commit();
            }
            logger.debug("completed {}" , ent.getKey());
        }
        logger.debug("completed load edges");
    }

    /**
     * Loads n-Cardinality (1NF violating) vertex properties from the csv files
     *
     * @param bGraph  Typed Batch Graph to be shared between load methods
     * @param dir     Directory in which the files reside
     * @param vpMap   Map between vertices and their property files
     * @throws IOException              thrown if files / directory are inaccessible
     * @throws SchemaViolationException if file schema doesn't match workload schema
     */
    private void loadVertexProperties(TypedBatchGraph bGraph, File dir, Map<String, String> vpMap)
            throws IOException, SchemaViolationException {
        logger.debug("entered load VP");
        WorkLoadSchema s = this.workload.getSchema();

        for (Map.Entry<String,String> entry : vpMap.entrySet()) {
            HashSet<String> fileSet = new HashSet<>();
            final String fNameSuffix = entry.getValue().replace("_0.csv", "");
            fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(fNameSuffix + "_\\d+_0\\.csv");
                }
            })));
            for (String fName : fileSet) {
                logger.debug("reading {}", fName);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(new File(dir, fName))
                                ,"UTF-8"));

                //Read title line and map to vertex properties, throw exception if doesn't match
                String line = br.readLine();
                if (line==null)
                    throw new IOException("Empty file" + fName);
                String[] header = line.split(CSVSPLIT);
                short suffix;
                String vLabel = entry.getKey().split(TUPLESPLIT)[0];
                try {

                    suffix = validateVHeader(s, vLabel, header);
                } catch (SchemaViolationException e) {
                    br.close();
                    throw e;
                }
                int rowLength = header.length;
                //Read and load rest of file
                try {
                    while ((line = br.readLine()) != null) {
                        String[] row = line.split(CSVSPLIT);
                        long id = (long) Math.pow(10, mult) * Long.parseLong(row[0]) + suffix;
                        TypedBatchGraph.BatchVertex vF = bGraph.getVertex(id);
                        if (vF == null) {
                            long vid = id % mult;
                            logger.error("Vertex property update failed, since no vertex with id {} from line {}",vid, line );
                            continue;
                        }
                        //This is safe since the header has been validated against the property map

                        for (int i = 1; i < rowLength; i++)
                            vF.addProperty(header[i], parseEntry(row[i],
                                    s.getVPropertyClass(vLabel, header[i]).getSimpleName()));
                    }
                } catch (Exception e) {
                    System.err.println("Failed to add properties in " + entry.getKey());
                    br.close();
                    bGraph.shutdown();
                    e.printStackTrace();
                } finally {
                    br.close();
                }
                bGraph.commit();
            }
        }
        logger.debug("completed load VP");
    }

    /**
     * Validates the file header against the schema used by the importer
     *
     * @param s      schema to validate against
     * @param vLabel vertex type to validate against
     * @param header header of csv file
     * @return suffix of this vertex
     */
    private short validateVHeader(WorkLoadSchema s, String vLabel, String[] header) throws SchemaViolationException {
        Set<String> props = s.getVertexProperties().get(vLabel);
        if (props == null)
            throw new SchemaViolationException("No properties found for the vertex label " + vLabel);

        if (!header[0].equals("id") && !header[0].endsWith(".id"))
            throw new SchemaViolationException("First column of " + vLabel
                    + " is not labeled 'id', but:" + header[0]);

        for (String col : header) {
            if (col.equals("id") || col.endsWith(".id"))
                continue;

            if (!props.contains(col)) {
                /*
                This is a due to the fact that Titan has
                global property keys and language has SINGLE
                cardinality for Post.language and LIST cardinality
                in Person.language.
                */
                if (col.equals("language") && props.contains("lang"))
                    continue;
                else
                    throw new SchemaViolationException("Unknown property for vertex Type" + vLabel
                            + ", found " + col + " expected " + props);
            }


            if (s.getVPropertyClass(vLabel, col) == null)
                throw new SchemaViolationException("Class definition missing for " + vLabel + "." + col);
        }

        return typeMultMap.get(vLabel).shortValue();

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
    private short[] validateEHeader(WorkLoadSchema s, String eTriple, String[] header)
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

        return new short[]{typeMultMap.get(vF).shortValue(), typeMultMap.get(vT).shortValue()};
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

    /**
     * Use to shut-down the graph
     */
    public void shutdown() {
        g.shutdown();
    }
}
