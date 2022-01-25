package com.ldbc.impls.workloads.ldbc.snb.tigergraph;

import com.ldbc.driver.ClientException;
import junit.framework.TestCase;

import java.text.ParseException;

public class CreateParametersTest extends TestCase {

    public void test1() throws ParseException, ClientException {
        com.ldbc.driver.Client.main(new String[]{"-P", "driver/create-validation-parameters.properties"});
    }
}