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
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate7AddComment;
import com.thinkaurelius.titan.core.TitanException;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Update Query 7: add comment
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU7Handler extends OperationHandler<LdbcUpdate7AddComment> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU7Handler.class);

    @Override
    protected OperationResultReport executeOperation(LdbcUpdate7AddComment operation) throws DbException {
        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
        try {

            Map<String, Object> props = new HashMap<>(5);
            props.put("creationDate", operation.creationDate().getTime());
            props.put("locationIP", operation.locationIp());
            props.put("browserUsed", operation.browserUsed());
            props.put("content", operation.content());
            props.put("length", operation.length());
            Vertex comment = client.addVertex(operation.commentId(), "Comment", props);
            Map<String, Object> eProps = new HashMap<>(0);
            Vertex person = client.getVertex(operation.authorPersonId(), "Person");
            client.addEdge(comment, person, "hasCreator", eProps);
            Vertex country = client.getVertex(operation.countryId(), "Place");
            client.addEdge(comment, country, "isLocatedIn", eProps);

            if (operation.replyToCommentId() != -1) {
                Vertex replyTo = client.getVertex(operation.replyToCommentId(), "Comment");
                client.addEdge(comment, replyTo, "replyOf", eProps);
            }

            if (operation.replyToPostId() != -1) {
                Vertex replyTo = client.getVertex(operation.replyToPostId(), "Post");
                client.addEdge(comment, replyTo, "replyOf", eProps);
            }

            for (Long tagID : operation.tagIds()) {
                Vertex tagV = client.getVertex(tagID, "Tag");
                client.addEdge(comment, tagV, "hasTag", eProps);
            }

        } catch (SchemaViolationException e) {
            logger.error("invalid vertex label requested by query update");
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (TitanException e) {
            logger.error("Couldn't complete U7 handler, db didn't commit");
            e.printStackTrace();
        }
        return operation.buildResult(0, null);
    }
}
