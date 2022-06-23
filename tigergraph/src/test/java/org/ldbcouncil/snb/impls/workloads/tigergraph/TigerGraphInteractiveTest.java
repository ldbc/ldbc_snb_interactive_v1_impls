package org.ldbcouncil.snb.impls.workloads.tigergraph;

import org.ldbcouncil.snb.driver.workloads.interactive.queries.*;
import org.ldbcouncil.snb.impls.workloads.interactive.InteractiveTest;
import org.ldbcouncil.snb.impls.workloads.tigergraph.interactive.TigerGraphInteractiveDb;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TigerGraphInteractiveTest extends InteractiveTest {

    String databaseName = "LDBC_SNB";
    String queryDir = "queries";

    public TigerGraphInteractiveTest() {
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
    public void testQuery1() throws Exception {
        // LdbcQuery1{personId=4398046511333, firstName='Jose', limit=20}
        // LdbcQuery1{personId=10995116277918, firstName='Ayesha', limit=20}
//        run(db, new LdbcQuery1(4398046511333L, "Jose", LIMIT));
        run(db, new LdbcQuery1(30786325579101L, "Ian", LIMIT));
    }


    @Test
    public void testQuery2() throws Exception {
        // {personId=4398046511133, maxDate=Tue Nov 09 01:00:00 CET 2010, limit=20}
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        run(db, new LdbcQuery2(4398046511133L, dateFormat.parse("2010-11-09 00:00:00"), LIMIT));
    }

    @Test
    public void testQuery3() throws Exception {
        run(db, new LdbcQuery3(6597069766734L, "India", "Lithuania", new Date(1275343200000L), 28, LIMIT));
    }

    @Test
    public void testQuery4() throws Exception {
        run(db, new LdbcQuery4(10995116277918L, new Date(1285891200000L), 31, LIMIT));
    }

    @Test
    public void testQuery6() throws Exception {
        run(db, new LdbcQuery6(4398046511333L, "Carl_Gustaf_Emil_Mannerheim", LIMIT));
    }

    @Test
    public void testQuery7() throws Exception {
        //LdbcQuery7{personId=8796093022238, limit=20}|
        run(db, new LdbcQuery7(8796093022238L, LIMIT));
    }

    @Test
    public void testQuery8() throws Exception {
        //LdbcQuery8{personId=150, limit=20}
        run(db, new LdbcQuery8(150L, LIMIT));
    }

    @Test
    public void testQuery10() throws Exception {
        run(db, new LdbcQuery10(10995116277918L, 3, LIMIT));
    }

    @Test
    public void testQuery11() throws Exception {
        //(long personId, String countryName, int workFromYear, int limit) {
        //{personId=, countryName='Hungary', workFromYear=2011, limit=10}
        run(db, new LdbcQuery11(10995116277918L, "Hungary", 2011, LIMIT));
    }

    @Test
    public void testQuery12() throws Exception {
        run(db, new LdbcQuery12(4398046511133L, "ChristianBishop", LIMIT));
    }

    @Test
    public void testQuery13() throws Exception {
        run(db, new LdbcQuery13(8796093022357L, 8796093022390L));
    }

    @Test
    public void testQuery14() throws Exception {
        run(db, new LdbcQuery14(8796093022390L, 8796093022357L));
    }

    @Test
    public void testQuery9() throws Exception {
        // 2012-08-28 00:00:00
        run(db, new LdbcQuery9(13194139542834L, new Date(1346112000000L), LIMIT));
    }
}
