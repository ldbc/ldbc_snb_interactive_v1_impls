package com.ldbc.impls.workloads.ldbc.snb.util;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateConverterTest {

    @Test
    public void test() throws ParseException {
        Assert.assertEquals(
                "'2009-12-31T23:00:00.000+0000'",
                new Converter().convertDate(1262300400000L)
        );

        long timestamp = new Converter().convertTimestampToEpoch("2009-12-31T23:00:00.000+0000");
        ZonedDateTime actual = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("GMT"));
        Assert.assertEquals(1262300400000L, actual);
    }

}
