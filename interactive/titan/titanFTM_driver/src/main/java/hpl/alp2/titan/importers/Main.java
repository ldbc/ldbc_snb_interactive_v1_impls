package hpl.alp2.titan.importers;

import com.thinkaurelius.titan.core.Cardinality;
import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.SchemaAction;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.id.IdGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by Tomer Sagi on 06-Oct-14.
 * Class to run an importer
 */
public class Main {

    public static void main(String args[]) {
        //Load database
        //URL url = TitanImporter.class.getClassLoader().getResource("bdb.conf");
        URL url = TitanImporter.class.getClassLoader().getResource("bdbMWE.conf");
        if (url == null) {
            System.err.println("Missing resource bdb.conf");
            return;
        }
        String confPath = url.getPath();
        TitanGraph g = TitanFactory.open(confPath);
        TitanManagement m = g.getManagementSystem();
//        m.set("index.search.elasticsearch.client-only",false);
//        m.set("index.search.elasticsearch.local-mode",true);
//        m.commit();
//        g.commit();
//        g.shutdown();
//
//        g = TitanFactory.open(confPath);

        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.debug("make id property");
        m.makePropertyKey(IdGraph.ID).dataType(Long.class).cardinality(Cardinality.SINGLE).make();
        m.commit();
        g.commit();

        logger.debug("make index");
        m = g.getManagementSystem();
        PropertyKey id = m.getPropertyKey(IdGraph.ID);
        m.buildIndex("byvid", Vertex.class).addKey(id).unique().buildCompositeIndex();
        logger.info("index status {}", m.getGraphIndex("byvid").getIndexStatus(m.getPropertyKey(IdGraph.ID)));
        m.commit();
        m = g.getManagementSystem();
        logger.info("index status {}", m.getGraphIndex("byvid").getIndexStatus(m.getPropertyKey(IdGraph.ID)));
        m.commit();
        g.commit();
        m = g.getManagementSystem();
        m.updateIndex(m.getGraphIndex("byvid"), SchemaAction.REGISTER_INDEX);
        logger.info("set register trigger");
        m.commit();
        g.commit();
        m = g.getManagementSystem();
        logger.info("index status {}", m.getGraphIndex("byvid").getIndexStatus(m.getPropertyKey(IdGraph.ID)));
        m.commit();

        m = g.getManagementSystem();
        logger.info("index status {}", m.getGraphIndex("byvid").getIndexStatus(m.getPropertyKey(IdGraph.ID)));
        m.updateIndex(m.getGraphIndex("byvid"), SchemaAction.ENABLE_INDEX);
        m.commit();
        m = g.getManagementSystem();
        logger.info("index status {}", m.getGraphIndex("byvid").getIndexStatus(m.getPropertyKey(IdGraph.ID)));
        m.commit();
        Vertex p = g.addVertexWithLabel("person");
        p.setProperty(IdGraph.ID, 11);
        Iterable<Vertex> vL = g.query().has(IdGraph.ID, 11).vertices();
        for (Vertex v : vL)
            logger.info("found: {}", v.toString());
//        TitanImporter ti = new TitanImporter();
//        try {
//            ti.init(confPath, WorkloadEnum.INTERACTIVE);
//        } catch (DBgenImporter.ConnectionException e) {
//            e.printStackTrace();
//        }
//        File dir = new File(System.getProperty("validationFolderName"));
//        if (!dir.exists())
//            System.err.println("Validation data folder not found ");
//        try {
//            ti.importData(dir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SchemaViolationException e) {
//            e.printStackTrace();
//        }
//        ti.shutdown();
    }
}
