package io.cucumber;

import static io.magentys.AgentProvider.agent;
import static org.junit.Assert.assertNotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.cukes.AuthorRepository;
import info.cukes.BookRepository;
import io.magentys.Agent;

import java.lang.invoke.MethodHandles;

/**
 * @author glick
 */
// @Ignore
public class CherryMissionExplorerCdiTest
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private CdiContainer cdiContainer;

  @Inject
  @NotNull
  private BookRepository bookRepository;

  @Inject
  @NotNull
  private AuthorRepository authorRepository;

  private Agent agent;

  @Before
  public void setUp()
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

  @After
  public void tearDown()
  {
    cdiContainer.shutdown();
  }

  @Test
  // @Ignore
  public void testCherryMissionProducesResults() {

    assertNotNull(bookRepository);
    assertNotNull(authorRepository);
    assertNotNull(agent);

    agent.obtains(bookRepository, authorRepository);
  }
}
