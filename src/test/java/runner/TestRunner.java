package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = "stepDefinitions",
		plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
		tags = "@CartTest or @CheckoutTest or @FilterTest" 
		)
public class TestRunner extends AbstractTestNGCucumberTests {
	
}