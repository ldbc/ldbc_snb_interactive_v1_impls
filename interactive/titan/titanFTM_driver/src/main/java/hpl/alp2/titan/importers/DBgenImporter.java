/**
 * 
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
