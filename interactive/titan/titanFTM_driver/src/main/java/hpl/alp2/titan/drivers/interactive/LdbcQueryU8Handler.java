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
    along with this program.  If not, see <http://www.gnu.org/licenses/>.**/

package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.OperationResultReport;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Add knows edge between two given people assumed to exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU8Handler extends OperationHandler<LdbcUpdate8AddFriendship> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU8Handler.class);

    @Override
    protected OperationResultReport executeOperation(LdbcUpdate8AddFriendship operation) throws DbException {

        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
        try {

            Map<String, Object> props = new HashMap<>(1);
            props.put("creationDate", operation.creationDate().getTime());
            Vertex person = client.getVertex(operation.person1Id(), "Person");
            Vertex friend = client.getVertex(operation.person2Id(), "Person");
            client.addEdge(person, friend, "knows", props);

        } catch (SchemaViolationException e) {
            logger.error("invalid vertex label requested by query update");
            e.printStackTrace();
        }
        return operation.buildResult(0, null);
    }
}
