/**
 (c) Copyright [2015] Hewlett-Packard Development Company, L.P.
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hpl.alp2.titan.test;

import com.ldbc.driver.Client;
import hpl.alp2.titan.importers.TitanImporter;
import hpl.alp2.titan.importers.WorkloadEnum;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * @author Tomer Sagi
 */
public class DriverTest {
    Logger logger = LoggerFactory.getLogger(DriverTest.class);

//    @Test
//    public void testDriverMWE() throws Exception {
////        URL bdbURL = this.getClass().getClassLoader().getResource("bdb_tmp.conf");
////        String confPath = bdbURL.getPath();
////        TitanGraph g = TitanFactory.open(confPath);
////        TitanManagement m = g.getManagementSystem();
////

////        logger.debug("make id property");
////        PropertyKey id = m.makePropertyKey(IdGraph.ID).dataType(Long.class).cardinality(Cardinality.SINGLE).make();
////        logger.debug("make index");
////        m.buildIndex("byvid", Vertex.class).addKey(id).unique().buildCompositeIndex();
////        logger.info("index status {}", m.getGraphIndex("byvid").getIndexStatus(m.getPropertyKey(IdGraph.ID)));
////        logger.debug("make IDGraph wrapping TitanGraph");
////        KeyIndexableGraph kg = new IdGraph<>(g, true, false);
////        logger.debug("add vertex");
////        kg.addVertex(11);
////        logger.debug("get vertex");
////        kg.getVertex(11);
//    }

    @Test
    public void testDriver() throws Exception {

        URL bdbURL = this.getClass().getClassLoader().getResource("bdb_tmp.conf");

        if (bdbURL == null)
            throw new Exception("Missing resource bdb_tmp.conf");

        String confPath = bdbURL.getPath();
        TitanImporter ti = new TitanImporter();
        ti.init(confPath, WorkloadEnum.INTERACTIVE);

        File dir = new File(System.getProperty("validationFolderName"));
        if (!dir.exists())
            throw new Exception("Missing imported file directory");

        ti.importData(dir);
        ti.shutdown(); //Use this code to build a graph and load data to it on the server
        String dbClassName = "hpl.alp2.titan.drivers.interactive.TitanFTMDb";
//        URL workloadPropsPath = this.getClass().getClassLoader().getResource("workloads/ldbc_driver_debug.properties"); //"workloads/ldbc_snb_interactive.properties ,workloads/ldbc_snb_Q2.properties");
        URL driverPropsPath = this.getClass().getClassLoader().getResource("workloads/readwrite_neo4j--ldbc_driver_config--db_validation.properties");
        URL validationParamsPath = this.getClass().getClassLoader().getResource("workloads/validation_params.csv");
        String updateQPath = "N:\\SharedData\\ALP2\\LDBC\\snb_validation\\updateStream.properties";

//        if (workloadPropsPath == null)
//            logger.error("invalid path for workload properties");
        if (driverPropsPath == null)
            logger.error("invalid path for driver properties");
        else if (validationParamsPath == null)
            logger.error("invalid path for validation parameters");
        else {
            String[] args = new String[]{"-db", dbClassName,
                    "-P", //, workloadPropsPath.getPath(),
                    driverPropsPath.getPath(),
                    "-vdb", validationParamsPath.getPath()
                    ,updateQPath ,"-oc" , "10"};
            Client.main(args);
        }


    }

}

