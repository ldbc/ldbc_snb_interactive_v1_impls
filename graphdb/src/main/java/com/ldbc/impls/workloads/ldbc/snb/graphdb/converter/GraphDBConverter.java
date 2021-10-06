package com.ldbc.impls.workloads.ldbc.snb.graphdb.converter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
			return List.of(Arrays.asList(value.stringValue().split(", ")));
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
}
