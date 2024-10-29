package org.pt.flightbooking;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
                 plugin = { "pretty", "json:target/cucumber-tests.json", "html:target/cucumber-tests.html"})
public class FlightsAutomatedTests {

}
