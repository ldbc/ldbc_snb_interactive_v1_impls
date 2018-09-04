package sparql;

import com.google.common.collect.ImmutableList;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import com.ldbc.impls.workloads.ldbc.snb.sparql.interactive.StardogInteractiveDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.interactive.VirtuosoInteractiveDb;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SparqlInteractiveTest extends InteractiveTest {

    // Stardog
    // private static String endpoint = "http://localhost:5820/";
    // Virtuoso
    private static String endpoint = "localhost:1127";
    private static String databaseName = "ldbcsf1";
    private static String queryDir = "queries";
    private static String graphUri = "http://www.ldbc.eu";

    public SparqlInteractiveTest() {
        super(new VirtuosoInteractiveDb());
    }

    public void setUp(){

    }

    @Override
    public Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put("endpoint", endpoint);
        properties.put("graphUri", graphUri);
        properties.put("databaseName", databaseName);
        properties.put("queryDir", queryDir);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "true");
        properties.put("printQueryResults", "true");
        return properties;
    }

    @Test
    public void testQuery1() throws Exception {
        run(db, new LdbcQuery1(30786325583618L, "Chau", LIMIT));
    }

    @Test
    public void testQuery10() throws Exception {
        run(db, new LdbcQuery10(13194139542130L, 6, LIMIT));

    }

    @Test
    public void testQuery3() throws Exception {
        run(db, new LdbcQuery3(17592186055119L, "Laos", "Scotland", new Date(1306886400000L), 28, LIMIT));

    }

    @Test
    public void testQuery4() throws Exception {
        run(db, new LdbcQuery4(21990232559429L,new Date(1335830400000L),37, LIMIT));

    }

    @Test
    public void testQuery6() throws Exception {
        run(db, new LdbcQuery6(30786325583618L, "Angola", LIMIT));

    }

    @Test
    public void testQuery7() throws Exception {
        run(db, new LdbcQuery7(6597069777240L, LIMIT));

    }

    @Test
    public void testShortQuery1() throws Exception {
        run(db, new LdbcShortQuery1PersonProfile(32985348839299L));
    }

    @Test
    public void testShortQuery3() throws Exception {
        run(db, new LdbcShortQuery3PersonFriends(32985348839299L));
    }

    @Test
    public void testShortQuery2() throws Exception {
        run(db, new LdbcShortQuery2PersonPosts(8796093030860L,10));
    }
    @Test
    public void testUpdateQuery2() throws Exception {
        run(db, new LdbcUpdate2AddPostLike(26388279073665L,1236953235741L,new Date(1347528982194L)));
    }
    @Test
    public void testUpdateQuery6() throws Exception {
        run(db, new LdbcUpdate6AddPost(111222333444555666L,"", new Date(2199025986581L),"61.16.220.210","Chrome", "tk","About Abbas I of Persia, w Shah Mohammed in a coup and placed the 16-year-old Abbas on the th",93,8796093029267L,549755863266L,0, ImmutableList.of(3L)));
    }

    @Test
    public void testUpdateQuery7() throws Exception {
        run(db, new LdbcUpdate7AddComment(2199024038763L,new Date(1347528969834L),"213.55.65.79","Firefox","About Arnold Schoenberg, ist movement in German poetry and art, andAbout Ecce Cor M",83,8796093030860L,76,219902403876110L, -1L, ImmutableList.of(146L, 11287L)));
    }

    @Ignore
    @Test
    public void testQuery13() {}

    @Ignore
    @Test
    public void testQuery14() {}

}
