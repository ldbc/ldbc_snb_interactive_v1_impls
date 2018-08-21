package sparql.converter;

import com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlConverter;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class SparqlConverterTest {

    @Test
    public void test() throws ParseException {
        final SparqlConverter converter = new SparqlConverter();
        final long timestamp = converter.convertTimestampToEpoch("2010-03-21T18:10:43Z");
        assertEquals(1269195043000L, timestamp);
    }

    @Test
    public void testWithMillis() throws ParseException {
        final SparqlConverter converter = new SparqlConverter();
        final long timestamp = converter.convertTimestampToEpoch("2010-03-21T18:10:43.000Z");
        assertEquals(1269195043000L, timestamp);
    }

}
