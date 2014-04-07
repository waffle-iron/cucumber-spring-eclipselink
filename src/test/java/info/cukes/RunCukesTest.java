package info.cukes;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(glue = {"info.cukes", "cucumber.runtime.java.spring.hooks"}, format = "pretty")
public class RunCukesTest
{
}
