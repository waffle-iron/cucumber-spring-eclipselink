package info.cukes;

import org.junit.runner.RunWith;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * <p>RunCukesIT test class.</p>
 *
 * @author glick
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = {"info.cukes", "cucumber.api.spring"},
  plugin = {"pretty", "html:target/site/cucumber-pretty", "usage:target/cucumber-usage/cucumber-usage.json",
  "json:target/cucumber/cucumber.json"},
  tags = {"@txn"})
@Transactional
@EnableTransactionManagement
public class RunCukesIT
{
}
