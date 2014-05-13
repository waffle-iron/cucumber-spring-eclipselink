package info.cukes;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"info.cukes", "cucumber.runtime.java.spring.hooks"},
  format = {"html:target/cucumber-html-report"})
public class RunCukesTest
{
}
