package com.ldbc.impls.workloads.ldbc.snb.graphdb.converter;

import java.time.Instant;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

public class GraphDBConverter extends Converter {

	//converts date of type "2011-08-12T20:17:46.384Z" to millisecond.
	// This is the datetime type of sparql
	public static long stringTimestampToEpoch(String date)  {
		Instant instant = Instant.parse (date);
		return instant.toEpochMilli();
	}
}
