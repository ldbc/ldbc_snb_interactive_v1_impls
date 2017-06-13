package com.ldbc.snb.janusgraph.importers;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.SchemaViolationException;
import org.janusgraph.graphdb.tinkerpop.JanusGraphBlueprintsGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by aprat on 9/06/17.
 */
public class VertexLoadingTask implements Runnable {

    public final static String CSVSPLIT = "\\|";
    public final static int REPORTING_INTERVAL = 10000;

    public String fileName = null;
    public JanusGraphBlueprintsGraph graph = null;
    public int transactionSize = 100000;
    public WorkLoadSchema schema = null;
    public String vertexLabel = null;
    public long numberOfVerticesLoaded = 0;
    public long elapsedTime = 0;

    public boolean executed = false;

    private Logger logger = LoggerFactory.getLogger("org.janusgraph");

    public VertexLoadingTask(JanusGraphBlueprintsGraph graph, WorkLoadSchema schema, String fileName, String vertexLabel, int transactionSize) {
        this.graph = graph;
        this.schema = schema;
        this.fileName = fileName;
        this.vertexLabel = vertexLabel;
        this.transactionSize = transactionSize;
    }

    private void validateVHeader(WorkLoadSchema s, String vLabel, String[] header) throws SchemaViolationException {
        Set<String> props = s.getVertexProperties().get(vLabel);
        if (props == null)
            throw new SchemaViolationException("No properties found for the vertex label " + vLabel);

        for (String col : header) {
            if (!props.contains(col)) {
                throw new SchemaViolationException("Unknown property for vertex Type" + vLabel
                        + ", found " + col + " expected " + props);
            }
            if (s.getVPropertyClass(vLabel, col) == null)
                throw new SchemaViolationException("Class definition missing for " + vLabel + "." + col);
        }
    }

    @Override
    public void run() {

        try {
            logger.info("reading file {}", fileName);
            File csvFile = new File(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), Charset.forName("UTF-8")));

            //Read title line and map to vertex properties, throw exception if doesn't match
            String line = br.readLine();
            if (line == null)
                throw new IOException("Empty file" + fileName);
            String[] header = line.split(CSVSPLIT);
            try {
                validateVHeader(schema, vertexLabel, header);
            } catch (SchemaViolationException e) {
                br.close();
                throw e;
            }
            int rowLength = header.length;
            logger.info("Number of columns " + rowLength);
            Class[] classes = new Class[rowLength];
            for (int i = 0; i < rowLength; i++) {
                String prop = header[i];
                logger.info("Column " + prop);
                classes[i] = schema.getVPropertyClass(vertexLabel, prop);
            }

            //Read and load rest of file
            try {
                long counter = 0;
                Function<String, Object> parsers[] = new Function[header.length];
                for (int i = 0; i < header.length; ++i) {
                    parsers[i] = Parsers.getParser(classes[i]);
                }

                JanusGraphTransaction transaction = graph.newThreadBoundTransaction()  ;
                int transactionCount = 0;
                long start = System.currentTimeMillis();
                while ((line = br.readLine()) != null) {
                    if (transactionCount >= transactionSize) {
                        logger.info("Commiting transaction ...");
                        long commitStart = System.currentTimeMillis();
                        transaction.commit();
                        long commitEnd = System.currentTimeMillis();
                        long diff = commitEnd - commitStart;
                        logger.info("Transaction commited in "+diff+" ms");
                        transaction = graph.newTransaction();
                        transactionCount = 0;
                    }
                    String[] row = line.split(CSVSPLIT);
                    JanusGraphVertex vertex = transaction.addVertex(vertexLabel);
                    for (int i = 0; i < row.length; ++i) {
                        String prop = header[i];
                        Object value = parsers[i].apply(row[i]);
                        vertex.property(vertexLabel + "." + prop, value);
                    }
                    transactionCount++;
                    counter++;
                    if (counter % REPORTING_INTERVAL == 0) {
                        logger.info("Loaded " + vertexLabel + " " + counter + " at a rate of " + (counter * 1000L / (System.currentTimeMillis() - start)) + " per second");
                    }
                }
                transaction.commit();
                long diff = System.currentTimeMillis() - start;
                if (diff > 0) {
                    logger.info("Loaded " + vertexLabel + " at a rate of " + (counter * 1000L / (diff)) + " per second");
                }
                elapsedTime = diff;
                numberOfVerticesLoaded = counter;
            } catch (Exception e) {
                System.err.println("Vertex load failed");
                e.printStackTrace();
                graph.close();
            } finally {
                br.close();
            }
            logger.info("completed {} loading", fileName);
        } catch (Exception e) {
        }
        executed = true;
    }

    public void printStats(){
       if(elapsedTime > 0) {
           logger.info("Loaded "+numberOfVerticesLoaded+" "+vertexLabel+" at a rate of "+numberOfVerticesLoaded*1000/elapsedTime+" vertices per second");
       } else {
           logger.info("Loaded "+numberOfVerticesLoaded+" "+vertexLabel+" at the speed of light ;)");
       }
    }
}
