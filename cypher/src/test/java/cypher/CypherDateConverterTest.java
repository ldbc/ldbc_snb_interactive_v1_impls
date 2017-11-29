package cypher;

import com.ldbc.impls.workloads.ldbc.snb.util.CypherConverter;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class CypherDateConverterTest {

    @Test
    public void epochToTimestampTest() throws ParseException {
        String timestamp = new CypherConverter().convertDate(1262300400000L);
        Assert.assertEquals("20091231230000000", timestamp);
    }

    @Test
    public void printTimestampForExperimenting() throws ParseException {
        // Q1
        System.out.println(new CypherConverter().convertDate(1311285600000L));
        System.out.println();
        // Q2
        System.out.println(new CypherConverter().convertDate(1262300400000L));
        System.out.println(new CypherConverter().convertDate(1289170800000L));
        System.out.println();
        // Q10
        System.out.println(new CypherConverter().convertDate(1311285600000L));
        System.out.println();
        // Q12
        System.out.println(new CypherConverter().convertDate(1311285600000L));
        System.out.println();
        // Q13
        System.out.println(new CypherConverter().convertDate(1338501600000L));
        System.out.println(new CypherConverter().convertDate(1341093600000L));
        System.out.println();
    }

}
