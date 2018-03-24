package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public abstract class QueryStore<T> {

    protected Map<T, String> queries = new HashMap<>();

    protected String loadQueryFromFile(String path, String filename) throws DbException {
        final String filePath = path + File.separator + filename;
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new DbException("Could not load query: " + filePath);
        }
    }

    protected abstract Converter getConverter();

    protected abstract String prepare(T queryType, Map<String, String> parameterSubstitutions);

}
