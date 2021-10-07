package com.ldbc.impls.workloads.ldbc.snb.graphdb.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.SimpleIRI;
import org.eclipse.rdf4j.query.BindingSet;

public class GraphDBConverter extends Converter {

	//converts date of type "2011-08-12T20:17:46.384Z" to millisecond.
	// This is the datetime type of sparql
	public long timestampToEpoch(BindingSet bindingSet, String name) {
		Instant instant = Instant.parse(bindingSet.getValue(name).stringValue());
		return instant.toEpochMilli();
	}

	//converts date of type "2011-08-12" to millisecond.
	public long localDateToEpoch(BindingSet bindingSet, String name) {
		String s = bindingSet.getValue(name).stringValue();
		return LocalDate.parse(s).atStartOfDay(ZoneId.of("GMT")).toInstant().toEpochMilli();
	}

	public Iterable<String> asStringCollection(BindingSet bindingSet, String name) {
		Value value = bindingSet.getValue(name);
		if (value == null) {
			return new ArrayList<>();
		} else {
			return Arrays.asList(value.stringValue().split(", "));
		}
	}

	public Iterable<List<Object>> asObjectCollection(BindingSet bindingSet, String name) {
		Value value = bindingSet.getValue(name);
		if (value == null) {
			return new ArrayList<>();
		} else {
			return Collections.singletonList(Arrays.asList(value.stringValue().split(", ")));
		}
	}

	public long asLong(BindingSet bindingSet, String name) {
		int numberIndex = 4;
		String localName = ((SimpleIRI) bindingSet.getValue(name)).getLocalName();
		return Long.parseLong(localName.substring(numberIndex));
	}

	public int asInt(BindingSet bindingSet, String name) {
		return ((Literal) bindingSet.getValue(name)).intValue();
	}

	public String asString(BindingSet bindingSet, String name) {
		return bindingSet.getValue(name).stringValue();
	}

	@Override
	public String convertId(long value) {
		String personIdAsString = String.valueOf(value);
		int maxLength = 20;

		StringBuilder sb = new StringBuilder(personIdAsString);
		if (sb.length() <= maxLength) {
			while (sb.length() < maxLength) {
				sb.insert(0, '0');
			}
		}
		return sb.toString();
	}

	@Override
	public String convertString(String value) {
		return value;
	}
}
