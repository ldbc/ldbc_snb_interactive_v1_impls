package com.ldbc.impls.workloads.ldbc.snb.util;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class DateConverterTest {

    @Test
    public void epochToTimestampTest() throws ParseException {
        String timestamp = new Converter().convertDateTime(1262300400000L);
        Assert.assertEquals("'2009-12-31T23:00:00.000+0000'", timestamp);
    }
    
    @Test
    public void timestampToEpochTest() throws ParseException {
        long epoch = new Converter().convertTimestampToEpoch("2009-12-31T23:00:00.000+0000");
        Assert.assertEquals(1262300400000L, epoch);
    }

}
