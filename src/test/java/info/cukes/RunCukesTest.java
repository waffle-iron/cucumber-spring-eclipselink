package info.cukes;

import org.junit.runner.RunWith;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * <p>RunCukesTest test class.</p>
 *
 * @author glick
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = {"info.cukes", "cucumber.runtime.java.spring.hooks"},
  format = {"html:target/cucumber-html-report"})
@Transactional
@EnableTransactionManagement
public class RunCukesTest
{
}
