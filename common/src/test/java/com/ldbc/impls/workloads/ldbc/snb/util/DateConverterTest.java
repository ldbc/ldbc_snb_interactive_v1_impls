package com.ldbc.impls.workloads.ldbc.snb.util;

import org.junit.Assert;
import org.junit.Test;

public class DateConverterTest {

    @Test
    public void test() {
        Assert.assertEquals(
                "'2009-12-31T23:00:00.000+0000'",
                new Converter().convertDate(1262300400000L)
        );
    }

}
