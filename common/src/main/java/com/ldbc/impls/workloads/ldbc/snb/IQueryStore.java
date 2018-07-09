package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

public interface IQueryStore {

    Converter getConverter();

    default String getParameterPrefix() { return "$"; }

    default String getParameterPostfix() { return ""; }

}
