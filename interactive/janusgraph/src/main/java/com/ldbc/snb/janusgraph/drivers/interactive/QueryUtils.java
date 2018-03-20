package com.ldbc.snb.janusgraph.drivers.interactive;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

/**
 * Created by Tomer Sagi on 19-Oct-14.
 * Contains utilities for implementing LDBC queries.
 * Singleton design pattern
 */
public class QueryUtils {
    public static final String ALL_MATCH  = "\"([^\"]+)\""; //Match all except "
    public static final String LIST_MATCH = "(\\[[^\\]]+\\]|\"\")"; //Match all except ] or an empty list
    public static final Integer CODE_OK    =  0;
    public static final Integer CODE_ERROR = -1;
}
