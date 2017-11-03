package postgresql;

import com.ldbc.impls.workloads.ldbc.snb.util.DateConverter;
import org.junit.Assert;
import org.junit.Test;

public class DateConverterTest {

    @Test
    public void test() {
        Assert.assertEquals("'2009-12-31T23:00:00.000+00:00'::timestamp", DateConverter.convertDate(1262300400000L));
        Assert.assertEquals("2009-12-31T23:00:00.000+0000", DateConverter.convertDateDatagenFormat(1262300400000L));
        //System.out.println(DateConverter.convertDateDatagenFormat(0L));
    }

}
