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
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForumResult;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Implementation of LDBC Interactive workload short query 6
 * Given a Message (Post or Comment), retrieve the Forum that contains it and the Person that moderates that forum.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery6Handler implements OperationHandler<LdbcShortQuery6MessageForum,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery6Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery6MessageForum operation,TitanFTMDb.BasicDbConnectionState dbConnectionState,ResultReporter resultReporter) {
        long mid = operation.messageId();
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Vertex m;
        try {
            logger.debug("Short Query 6 called on message id: {}", mid);
            m = client.getVertex(mid, "Comment");

            if (m==null) { //If post
                m = client.getVertex(mid, "Post");
            }
            else { //If comment, get the original post
                GremlinPipeline<Vertex, Vertex> gpt = (new GremlinPipeline<>(m));
                Iterator<Vertex> it = gpt.as("start").out("replyOf").loop("start", QueryUtils.LOOPTRUEFUNC, QueryUtils.LOOPTRUEFUNC)
                        .as("originalM");
                while (it.hasNext())
                    m = it.next();
            }

            //Get forum and moderator
            GremlinPipeline<Vertex,Vertex> gp = new GremlinPipeline<>(m);
            Iterator<Row> qResult = gp.in("containerOf").as("forum").out("hasModerator").as("person").select();
            if (!qResult.hasNext())
            {
                logger.error("Unexpected empty set");
                resultReporter.report(-1, null, operation);
                return;
            }

            Row r = qResult.next();
                Vertex forum = (Vertex)r.getColumn("forum");
                Vertex person = (Vertex)r.getColumn("person");
                LdbcShortQuery6MessageForumResult res = new LdbcShortQuery6MessageForumResult(
                        client.getVLocalId((Long)forum.getId()),
                        (String)forum.getProperty("title"),
                        client.getVLocalId((Long)person.getId()),
                        (String) person.getProperty("firstName"),(String) person.getProperty("lastName"));
                resultReporter.report(1, res, operation);
        } catch (Exception e) {
        e.printStackTrace();
        resultReporter.report(-1, null, operation);
    }

    }


}
