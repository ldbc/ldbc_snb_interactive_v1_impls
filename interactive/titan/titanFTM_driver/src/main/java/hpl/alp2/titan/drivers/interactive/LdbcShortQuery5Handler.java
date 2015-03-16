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
package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreatorResult;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;

/**
 * Implementation of LDBC Interactive workload short query 5
 * Given a Message (Post or Comment), retrieve its author.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery5Handler implements OperationHandler<LdbcShortQuery5MessageCreator,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery5Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery5MessageCreator operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) {
        long mid = operation.messageId();
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Vertex m;
        try {
            logger.debug("Short Query 5 called on message id: {}", mid);
            m = client.getVertex(mid, "Comment");
            if (m==null)
                m = client.getVertex(mid, "Post");

            GremlinPipeline<Vertex,Vertex> gp = new GremlinPipeline<>(m);
            Vertex person = gp.out("hasCreator").next();
            LdbcShortQuery5MessageCreatorResult res = new LdbcShortQuery5MessageCreatorResult(
                        client.getVLocalId((Long)person.getId()),
                        (String) person.getProperty("firstName"),(String) person.getProperty("lastName"));
            resultReporter.report(1, res, operation);

        } catch (Exception e) {
        e.printStackTrace();
        resultReporter.report(-1, null, operation);
    }

    }


}