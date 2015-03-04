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
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.thinkaurelius.titan.core.TitanException;
import com.thinkaurelius.titan.core.TitanVertex;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.id.IdVertex;
import hpl.alp2.titan.importers.TitanImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomer Sagi on 14-Nov-14.
 * Add a person. Currently assuming all edge end point vertices exist
 */
public class LdbcQueryU1Handler extends OperationHandler<LdbcUpdate1AddPerson> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU1Handler.class);

    @Override
    protected OperationResultReport executeOperation(LdbcUpdate1AddPerson operation) throws DbException {
        TitanFTMDb.BasicClient client = ((TitanFTMDb.BasicDbConnectionState) dbConnectionState()).client();
        Map<String, Object> props = new HashMap<>(9);
        props.put("firstName", operation.personFirstName());
        props.put("lastName", operation.personLastName());
        props.put("gender", operation.gender());
        props.put("birthday", operation.birthday().getTime());
        props.put("creationDate", operation.creationDate().getTime());
        props.put("locationIP", operation.locationIp());
        props.put("browserUsed", operation.browserUsed());
        logger.debug("U1 Adding person {} , {}" ,operation.personId(), props.toString());
        Vertex person = client.addVertex(operation.personId(), "Person", props);
        TitanVertex v = ((TitanVertex)(((IdVertex) person).getBaseVertex()));
        for (String l : operation.languages())
            v.addProperty("language",l);

        for (String em : operation.emails())
            v.addProperty("email",em);

        try {
            for (LdbcUpdate1AddPerson.Organization org : operation.studyAt()) {

                Vertex orgV = client.getVertex(org.organizationId(), "Organisation");
                Map<String, Object> eProps = new HashMap<>();
                eProps.put("classYear", org.year());
                client.addEdge(person, orgV, "studyAt", eProps);

            }

        } catch (Exception e) {
            logger.error("add university failed");
            e.printStackTrace();
        }
        try {
        for (LdbcUpdate1AddPerson.Organization org : operation.workAt()) {
                Vertex orgV = client.getVertex(org.organizationId(), "Organisation");
                Map<String, Object> eProps = new HashMap<>();
                eProps.put("workFrom", org.year());
                client.addEdge(person, orgV, "workAt", eProps);
            }
        } catch (Exception e) {
            logger.error("add company failed");
            e.printStackTrace();
        }
        try {
            Vertex cityV = client.getVertex(operation.cityId(), "Place");
            client.addEdge(person, cityV, "isLocatedIn", new HashMap<String, Object>());

        } catch (Exception e) {
            logger.error("add city failed");
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (TitanException e) {
            logger.error("Couldn't complete U1 handler, db didn't commit");
            e.printStackTrace();
        }

        logger.debug("U1 complete for person {}" ,operation.personId());
        return operation.buildResult(0,null);
    }
}
