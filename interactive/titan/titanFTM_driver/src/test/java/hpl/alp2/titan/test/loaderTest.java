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
package hpl.alp2.titan.test;

import hpl.alp2.titan.importers.TitanImporter;
import hpl.alp2.titan.importers.WorkloadEnum;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Tomer Sagi
 */
public class loaderTest {

    @Test
    public void testFileMatch() {
        File dir = new File("C:\\Users\\sagit\\Desktop\\temp\\");
        final String vLabel = "Person";
        HashSet<String> fileSet = new HashSet<>();
        fileSet.addAll(Arrays.asList(dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(vLabel.toLowerCase() + "_\\d+\\.csv");
            }
        })));
    }

    @Test
    public void testDBInit() throws Exception {

        TitanImporter ti = new TitanImporter();
        URL url = this.getClass().getClassLoader().getResource("bdb_tmp.conf");
        if (url == null)
            throw new Exception("Missing resource bdb_tmp.conf");
        String confPath = url.getPath();
        ti.init(confPath, WorkloadEnum.INTERACTIVE);
        ti.shutdown();
    }

    @Test
    public void testDBLoad() throws Exception {

        TitanImporter ti = new TitanImporter();
        URL url = this.getClass().getClassLoader().getResource("bdb_tmp.conf");
        if (url == null)
            throw new Exception("Missing resource bdb_tmp.conf");
        String confPath = url.getPath();
        ti.init(confPath, WorkloadEnum.INTERACTIVE);
        File dir = new File(System.getProperty("toyFolderName"));
        if (!dir.exists())
            throw new Exception("Missing imported file directory");
        ti.importData(dir);
        ti.shutdown();
    }

}
