package com.ldbc.impls.workloads.ldbc.snb.graphdb.converter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.SimpleIRI;

public class GraphDBConverter extends Converter {

	//converts date of type "2011-08-12T20:17:46.384Z" to millisecond.
	// This is the datetime type of sparql
	public static long stringTimestampToEpoch(String date)  {
		Instant instant = Instant.parse (date);
		return instant.toEpochMilli();
	}

	public Iterable<String> convertToStringCollection(Value value) {
		if (value == null) {
			return new ArrayList<>();
		} else {
			return Arrays.asList(value.stringValue().split(", "));
		}
	}

	public long asLong(Value value) {
		int numberIndex = 4;
		String localName = ((SimpleIRI) value).getLocalName();
		return Long.parseLong(localName.substring(numberIndex));
	}

}
