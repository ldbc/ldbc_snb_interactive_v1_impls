/**(c) Copyright [2015] Hewlett-Packard Development Company, L.P.
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
**/
package com.hp.hpl.livegraph;

import com.ldbc.driver.Client;
import com.ldbc.driver.ClientException;
import hpl.alp2.titan.importers.TitanImporter;
import hpl.alp2.titan.importers.WorkloadEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Command line interface for livegraph experiments
 * Created by Tomer Sagi on 29-Dec-14.
 */
public class CPMain {
    Logger logger = LoggerFactory.getLogger(CPMain.class);

    /**
     * Console application
     */
    public void consoleApp() {
        //TODO write console application
        System.out.println("Console application TBD");
    }

    /**
     * Command line application
     * @param args 0: -L load data / -R run driver 1: db.conf path 2: data folder
     */
    public void cliApp(List<String> args) {
        if (args.size()==0)
            die("No arguments supplied");

        if (args.get(0).equalsIgnoreCase("-L")) {
            //load data to database
            if (args.size()<2)
                die("Missing second argument - db.conf path");

            File dbConf = new File(args.get(1));
            if (!dbConf.exists())
                die("Missing db.conf file at supplied path" + dbConf.getPath());

            TitanImporter ti = new TitanImporter();
            try {
                if (args.size()<3)
                    die("No data folder supplied");

                ti.init(dbConf.getPath(), WorkloadEnum.INTERACTIVE);
                File dir = new File(args.get(2));
                if (!dir.exists())
                    die("Missing data file directory at supplied path" + dir.getPath());

                ti.importData(dir);
                ti.shutdown(); //Use this code to build a graph and load data to it on the server
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (args.get(0).equalsIgnoreCase("-R")) {
            if (args.size()<3)
                die("Missing arguments - property files path");


            String dbClassName = "hpl.alp2.titan.drivers.interactive.TitanFTMDb";
            File workloadPropsPath = new File(args.get(1));
            File driverPropsPath = new File(args.get(2));

            if (!workloadPropsPath.exists())
                logger.error("invalid path for workload properties");
            else if (!driverPropsPath.exists())
                logger.error("invalid path for driver properties");
            else {
                String[] cargs = new String[]{"-db", dbClassName,
                        "-P ", workloadPropsPath.getPath(),
                        "-P ", driverPropsPath.getPath()};
                try {
                    Client.main(cargs);
                } catch (ClientException e) {
                    logger.error("LDBC driver client failed");
                    e.printStackTrace();
                }
            }
        } else {
            die("Invalid second argument. Use -R to run an experiment or -L to load data");
        }


    }

    private void die(String msg) {
        logger.error(msg);
        System.exit(-1);
    }
    /**
     * public main that switches between console and CLI mode
     * 0: cli = command line app / console = console app  1 and onwards according to CLI app
     */
    public static void main(String args[]) {
        String instructions = "use cli for command line interface and console for console application interface";

        if (args.length==0)
        {
            System.err.println("No arguments supplied, " + instructions);
            System.exit(-1);
        }
        CPMain cpMain = new CPMain();
        if (args[0].equalsIgnoreCase("cli")) {
            cpMain.cliApp(Arrays.asList(args).subList(1, args.length));
        }
        else if (args[0].equalsIgnoreCase("console")) {
            cpMain.consoleApp();
        } else {
            System.err.println("Invalid first argument. "  + instructions);
            System.exit(-1);
        }
        System.exit(0);
    }

}
