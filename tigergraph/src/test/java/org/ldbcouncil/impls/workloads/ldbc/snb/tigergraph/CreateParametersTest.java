package org.ldbcouncil.impls.workloads.ldbc.snb.tigergraph;

import org.ldbcouncil.driver.ClientException;
import junit.framework.TestCase;

import java.text.ParseException;

public class CreateParametersTest extends TestCase {

    public void test1() throws ParseException, ClientException {
        org.ldbcouncil.driver.Client.main(new String[]{"-P", "driver/create-validation-parameters.properties"});
    }
}