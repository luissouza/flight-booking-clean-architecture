package org.pt.flightbooking;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty,json:target/cucumber-tests.json,html:target/cucumber-tests.html")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "org.pt.flightbooking")
public class FlightsAutomatedTests {

}
