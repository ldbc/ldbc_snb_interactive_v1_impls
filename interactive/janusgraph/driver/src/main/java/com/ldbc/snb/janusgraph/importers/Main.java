package com.ldbc.snb.janusgraph.importers;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.Cardinality;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.schema.SchemaAction;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Class to run an importer
 */
public class Main {

    static final String ID = "id";

    static private Logger logger = LoggerFactory.getLogger("org.janusgraph");

    public static void main(String args[]) {
        //Load database
        JanusGraphImporter importer = new JanusGraphImporter();
        try {
            //importer.init("src/main/resources/bdb.conf", WorkloadEnum.INTERACTIVE);
            importer.init("src/main/resources/bdb.conf", WorkloadEnum.INTERACTIVE);
            if(args.length == 1) {
                importer.setTransactionSize(Integer.parseInt(args[0]));
            }
            importer.importData(new File("/home/aprat/temp/datasets/0001/social_network"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
