package com.ldbc.snb.janusgraph.importers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Function;

/**
 * Created by aprat on 8/06/17.
 */
public class Parsers {

    public static Function<String,Object> getParser(Class objectClass) {
        String className = objectClass.getSimpleName();
        switch (className) {
            case "String":
            case "Arrays":
            return new Function<String,Object>() {
                @Override
                public Object apply(String s) {
                    return s;
                }
            };
            case "Date":
                return new Function<String,Object>() {
                    private final String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
                    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_STRING);
                    private final String DATE_FORMAT_STRING = "yyyy-MM-dd";
                    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);
                    Date dateTime;

                    @Override
                    public Object apply(String s) {
                        /*DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
                        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
                        try {
                            if (s.length() > 10) {
                                dateTime = DATE_TIME_FORMAT.parse(s);
                            } else {
                                dateTime = DATE_FORMAT.parse(s);
                            }
                        } catch(Exception e) {
                            throw new RuntimeException("Error parsing date: "+s);
                        }
                        return dateTime.getTime();*/
                        return 0L;
                    }
                };
            case "Integer":
                return new Function<String,Object>() {
                    @Override
                    public Object apply(String s) {
                        return Integer.parseInt(s);
                    }
                };
            case "Long":
            return new Function<String,Object>() {
                @Override
                public Object apply(String s) {
                    return Long.parseLong(s);
                }
            };
            default:
                throw new ClassCastException("No parse strategy for " + className);
        }
    }
}
