package io.cucumber;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.cukes.AuthorRepository;
import info.cukes.BookRepository;
import io.magentys.Agent;

import java.lang.invoke.MethodHandles;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import static io.magentys.AgentProvider.agent;
import static org.junit.Assert.assertNotNull;

/**
 * @author glick
 */
@Ignore
@RunWith(CdiTestRunner.class)
public class CherryMissionExplorerCdiIT
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static CdiContainer cdiContainer;

  @Inject
  @NotNull
  private BookRepository bookRepository;

  @Inject
  @NotNull
  private AuthorRepository authorRepository;

  private static Agent agent;

  @BeforeClass
  public static void setUp()
  {
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    Assertions.assertThat(cdiContainer.getContextControl()).isNotNull();

    ContextControl contextControl = cdiContainer.getContextControl();

    Assertions.assertThat(contextControl).isNotNull();

    LOGGER.info("contextControl is " + contextControl);

    // Starting the application-context allows to use @ApplicationScoped beans
    contextControl.startContext(ApplicationScoped.class);

    agent = agent();
  }

  @Test()
  // @Ignore
  public void testCherryMissionProducesResults() {

    assertNotNull(bookRepository);
    assertNotNull(authorRepository);
    assertNotNull(agent);

    agent.obtains(bookRepository, authorRepository);
  }

  @AfterClass
  public static void tearDown()
  {
    cdiContainer.shutdown();
  }
}
