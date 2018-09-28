package com.ldbc.impls.workloads.ldbc.snb.sparql.converter;

import com.google.common.collect.ImmutableList;
import org.openrdf.model.Literal;
import org.openrdf.query.BindingSet;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SparqlInputConverter {

    protected static final SparqlConverter converter = new SparqlConverter();

    static Pattern ID_PATTERN = Pattern.compile("[0-9]+$");

    public static boolean convertBoolean(BindingSet bs, String name) {
        return ((Literal) bs.getBinding(name).getValue()).booleanValue();
    }

    public static long convertDate(BindingSet bs, String name) throws ParseException {
        final String timestamp = bs.getBinding(name).getValue().stringValue();
        return converter.convertDateToEpoch(timestamp);
    }

    public static long convertDateTime(BindingSet bs, String name) {
        final XMLGregorianCalendar calendar = ((Literal) bs.getBinding(name).getValue()).calendarValue();
        return calendar.toGregorianCalendar().toZonedDateTime().toInstant().toEpochMilli();
    }

    public static double convertDouble(BindingSet bs, String name) {
        return ((Literal) bs.getBinding(name).getValue()).doubleValue();
    }

    public static int convertInteger(BindingSet bs, String name) {
        return ((Literal) bs.getBinding(name).getValue()).intValue();
    }

    public static long convertLong(BindingSet bs, String name) {
        String string = bs.getBinding(name).getValue().stringValue();
        Matcher matcher = ID_PATTERN.matcher(string);
        matcher.find();
        return Long.parseLong(matcher.group(0));
    }

    public static String convertString(BindingSet bs, String name) {
        if (bs.getBinding(name) == null) {
            return "";
        }
        return bs.getBinding(name).getValue().stringValue();
    }

    public static Iterable<String> convertStringList(BindingSet bs, String name) {
		if (bs.getBinding(name) == null) {
			return Collections.emptyList();
		}
        final String[] strings = bs.getBinding(name).getValue().stringValue().split(", ");
        return Arrays.asList(strings);
    }

    public static Iterable<List<Object>> convertNestedList(BindingSet bs, String name) {
        if (bs.getBinding(name) == null) {
            return Collections.emptyList();
        }
        final String[] nestedStrings = bs.getBinding(name).getValue().stringValue().split(", ");
        return Arrays.stream(nestedStrings).map(
                n -> {
                    final String[] strings = n.split(" ");

                    String orgName = strings[0];
                    int year = Integer.valueOf(strings[1]);
                    String orgCountry = strings[2];
                    final List<Object> nestedList = ImmutableList.of(orgName, year, orgCountry);
                    return nestedList;
                }
        ).collect(Collectors.toList());
    }

}
