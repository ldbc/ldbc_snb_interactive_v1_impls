package org.ldbcouncil.snb.impls.workloads.mssql;

import java.util.ArrayList;
import java.util.List;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.QueryType;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.mssql.converter.SQLServerConverter;

import com.google.common.collect.ImmutableMap;

public class SQLServerQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new SQLServerConverter();
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public SQLServerQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

    /**
     * Get prepared UpdateQuery1 strings.
     * This is used for system requiring multiple updates.
     * @param operation UpdateQuery1 operation containing parameter values
     * @return List of prepared UpdateQuery1 strings
     */
    @Override
    public List<String> getUpdate1Multiple(LdbcUpdate1AddPerson operation) {
        List<String> list = new ArrayList<>();
        list.add(prepare(
                QueryType.InteractiveUpdate1AddPerson,
                new ImmutableMap.Builder<String, Object>()
                        .put(LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()))
                        .put(LdbcUpdate1AddPerson.PERSON_FIRST_NAME, getConverter().convertString(operation.getPersonFirstName()))
                        .put(LdbcUpdate1AddPerson.PERSON_LAST_NAME, getConverter().convertString(operation.getPersonLastName()))
                        .put(LdbcUpdate1AddPerson.GENDER, getConverter().convertString(operation.getGender()))
                        .put(LdbcUpdate1AddPerson.BIRTHDAY, getConverter().convertDate(operation.getBirthday()))
                        .put(LdbcUpdate1AddPerson.CREATION_DATE, getConverter().convertDateTime(operation.getCreationDate()))
                        .put(LdbcUpdate1AddPerson.LOCATION_IP, getConverter().convertString(operation.getLocationIp()))
                        .put(LdbcUpdate1AddPerson.BROWSER_USED, getConverter().convertString(operation.getBrowserUsed()))
                        .put(LdbcUpdate1AddPerson.CITY_ID, getConverter().convertId(operation.getCityId()))
                        .put(LdbcUpdate1AddPerson.LANGUAGES, getConverter().convertStringList(operation.getLanguages()))
                        .put(LdbcUpdate1AddPerson.EMAILS, getConverter().convertStringList(operation.getEmails()))
                        .build()
        ));

        for (LdbcUpdate1AddPerson.Organization organization : operation.getWorkAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonCompanies,
                    ImmutableMap.of(
                        LdbcUpdate1AddPerson.CREATION_DATE, SQLServerConverter.convertDateToOffsetDateTime(operation.getCreationDate()),
                        LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                    "organizationId", getConverter().convertId(organization.getOrganizationId()),
                    "worksFromYear", getConverter().convertInteger(organization.getYear())
                    )
            ));
        }
        for (long tagId : operation.getTagIds()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonTags,
                    ImmutableMap.of(
                        LdbcUpdate1AddPerson.CREATION_DATE, SQLServerConverter.convertDateToOffsetDateTime(operation.getCreationDate()),
                        LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                        "tagId", getConverter().convertId(tagId))
                    )
            );
        }
        for (LdbcUpdate1AddPerson.Organization organization : operation.getStudyAt()) {
            list.add(prepare(
                    QueryType.InteractiveUpdate1AddPersonUniversities,
                    ImmutableMap.of(
                        LdbcUpdate1AddPerson.CREATION_DATE, SQLServerConverter.convertDateToOffsetDateTime(operation.getCreationDate()),
                        LdbcUpdate1AddPerson.PERSON_ID, getConverter().convertIdForInsertion(operation.getPersonId()),
                        "organizationId", getConverter().convertId(organization.getOrganizationId()),
                        "studiesFromYear", getConverter().convertInteger(organization.getYear())
                    )
            ));
        }
        return list;
    }
}
