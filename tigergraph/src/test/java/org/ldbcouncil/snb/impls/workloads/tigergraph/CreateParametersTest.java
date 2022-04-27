package org.ldbcouncil.snb.impls.workloads.tigergraph;

import org.ldbcouncil.snb.driver.ClientException;
import junit.framework.TestCase;

import java.text.ParseException;

public class CreateParametersTest extends TestCase {

    public void test1() throws ParseException, ClientException {
        org.ldbcouncil.snb.driver.Client.main(new String[]{"-P", "driver/create-validation-parameters.properties"});
    }
}