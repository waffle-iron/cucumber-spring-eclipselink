package info.cukes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import javax.enterprise.context.ApplicationScoped;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>CdiContainerInitTest test class.</p>
 *
 * @author glick
 */
public class CdiContainerInitTest
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private CdiContainer cdiContainer;

  @Before
  public void setUp()
  {
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    assertThat(cdiContainer.getContextControl()).isNotNull();

    ContextControl contextControl = cdiContainer.getContextControl();

    assertThat(contextControl).isNotNull();

    LOGGER.info("contextControl is " + contextControl);

    // Starting the application-context allows to use @ApplicationScoped beans
    contextControl.startContext(ApplicationScoped.class);
  }

  @Test
  public void testInitCdiContainer()
  {

  }

  @After
  public void tearDown()
  {
    cdiContainer.shutdown();
  }
}
