package org.ldbcouncil.impls.workloads.ldbc.snb.tigergraph;

import com.google.common.collect.ImmutableList;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery14;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery6MessageForum;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcShortQuery7MessageReplies;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import org.ldbcouncil.impls.workloads.ldbc.snb.tigergraph.interactive.TigerGraphInteractiveDb;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TigerGraphInteractiveSF1Test extends InteractiveTest {


    String databaseName = "LDBC_SNB";
    String queryDir = "queries";

    public TigerGraphInteractiveSF1Test() {
        super(new TigerGraphInteractiveDb());
    }

    public Map<String, String> getProperties() {

        String endpoint = System.getenv("TIGERGRAPH_ENDPOINT");
        if (endpoint == null) {
            endpoint = "http://127.0.0.1:9000";
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("name", "LDBC_SNB");
        params.put("endpoint", endpoint);
        params.put("databaseName", databaseName);
        params.put("printQueryNames", "true");
        params.put("printQueryStrings", "true");
        params.put("printQueryResults", "true");
        params.put("queryDir", queryDir);
        return params;
    }

    @Test
    public void testUpdateQuery1() throws Exception {
        final LdbcUpdate1AddPerson.Organization university1 = new LdbcUpdate1AddPerson.Organization(5142L, 2004);
        run(db, new LdbcUpdate1AddPerson(
                        10995116277777L,
                        "Almira",
                        "Patras",
                        "female",
                        new Date(425606400000L), // note that java.util.Date has no timezone
                        new Date(1291394394934L),
                        "193.104.227.215",
                        "Internet Explorer",
                        1226L,
                        ImmutableList.of("ru", "en"),
                        ImmutableList.of("Almira10995116277777@gmail.com", "Almira10995116277777@gmx.com"),
                        ImmutableList.of(1916L),
                        ImmutableList.of(university1),
                        ImmutableList.of()
                )
        );
    }

    @Test
    public void testUpdateQuery1_2() throws Exception {

        run(db, new LdbcUpdate1AddPerson(
                        35184372096705L,
                        "Richard",
                        "Santos",
                        "female",
                        new Date(367459200000L), // note that java.util.Date has no timezone
                        new Date(1347555574586L),
                        "121.54.18.191",
                        "Internet Explorer",
                        828L,
                        ImmutableList.of("en"),
                        ImmutableList.of("Richard35184372096705@gmail.com", "Richard35184372096705@gmx.com", "Richard35184372096705@yahoo.com"),
                        ImmutableList.of(4L),
                        ImmutableList.of(), //studyAt
                        ImmutableList.of(new LdbcUpdate1AddPerson.Organization(966L, 2001), new LdbcUpdate1AddPerson.Organization(1110L, 2003)) //workAt
                )
        );
    }

    @Test
    public void testShortQuery6() throws Exception {
        run(db, new LdbcShortQuery6MessageForum(2061584476422L));
    }

    @Test
    public void testShortQuery7() throws Exception {
        run(db, new LdbcShortQuery7MessageReplies(1786706436881L));
    }

    @Test
    public void testQuery14() throws Exception
    {
        run( db, new LdbcQuery14( 32985348833679L, 2199023256862L ) );
    }

}
