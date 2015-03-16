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

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate6AddPost;
import com.thinkaurelius.titan.core.TitanException;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Add post vertex, creator edge, container edge, isLocatedIn edge and tag edges.
 * All other vertices are assumed to exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU6Handler implements OperationHandler<LdbcUpdate6AddPost,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU6Handler.class);

    @Override
    public void executeOperation(LdbcUpdate6AddPost operation, TitanFTMDb.BasicDbConnectionState dbConnectionState, ResultReporter reporter) throws DbException {
        TitanFTMDb.BasicClient client = dbConnectionState.client();

        try {
            Map<String, Object> props = new HashMap<>(7);
            props.put("imageFile", operation.imageFile());
            props.put("creationDate", operation.creationDate().getTime());
            props.put("locationIP", operation.locationIp());
            props.put("browserUsed", operation.browserUsed());
            props.put("lang", operation.language());
            props.put("content", operation.content());
            props.put("length", operation.length());
            Vertex post = client.addVertex(operation.postId(), "Post", props);
            Map<String, Object> eProps = new HashMap<>(0);
            Vertex person = client.getVertex(operation.authorPersonId(), "Person");
            client.addEdge(post, person, "hasCreator", eProps);
            Vertex forum = client.getVertex(operation.forumId(), "Forum");
            client.addEdge(forum, post, "containerOf", eProps);
            Vertex country = client.getVertex(operation.countryId(), "Place");
            client.addEdge(post, country, "isLocatedIn", eProps);
            for (Long tagID : operation.tagIds()) {
                Vertex tagV = client.getVertex(tagID, "Tag");
                client.addEdge(post, tagV, "hasTag", eProps);
            }

        } catch (SchemaViolationException e) {
            logger.error("invalid vertex label requested by query update");
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (TitanException e) {
            logger.error("Couldn't complete U6 handler, db didn't commit");
            e.printStackTrace();
        }
        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
