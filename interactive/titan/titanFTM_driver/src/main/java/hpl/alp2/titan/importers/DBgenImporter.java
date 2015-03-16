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
package hpl.alp2.titan.importers;

import javax.naming.directory.SchemaViolationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Tomer Sagi
 * 
 * Import csv files generated from LDBC's dbgen to an SUT database
 *
 */
public interface DBgenImporter {
	
	/**
	 * Assumes public key encryption is used to provide access to database and thus all that is needed is a connection URL
	 * @param connectionURL user@host:port
	 * @param workload from @Link{WorkloadEnum}
     * @return true if connection succeeded
	 * @throw ConnectionException if failed 
	 */
	public void init(String connectionURL, WorkloadEnum workload) throws ConnectionException;
	
	/**
	 * 
	 * @param dir
     * @return true if import succeeded
	 */
	public boolean importData(File dir) throws IOException, SchemaViolationException;

	/**
	 * Connection exception for DB generator importer classes
	 */
	public class ConnectionException extends Exception {

		private static final long serialVersionUID = 3314495465017984850L;
	}

}
