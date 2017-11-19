package postgresql;

import com.ldbc.impls.workloads.ldbc.snb.util.PostgresConverter;
import org.junit.Test;

import java.text.ParseException;

public class PostgresDateConverterTest {

    @Test
    public void printTimestampForExperimenting() throws ParseException {
        // Q1
        System.out.println(new PostgresConverter().convertDate(1311285600000L));
        System.out.println();
        // Q2
        System.out.println(new PostgresConverter().convertDate(1262300400000L));
        System.out.println(new PostgresConverter().convertDate(1289170800000L));
        System.out.println();
        // Q10
        System.out.println(new PostgresConverter().convertDate(1311285600000L));
        System.out.println();
    }

}
