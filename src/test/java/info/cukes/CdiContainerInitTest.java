package info.cukes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.assertj.core.api.Assertions;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;

import javax.enterprise.context.ApplicationScoped;

/**
 * <p>CdiContainerInitTest test class.</p>
 *
 * @author glick
 */
public class CdiContainerInitTest
{
  CdiContainer cdiContainer;
  ContextControl contextControl;

  @Before
  public void setUp()
  {
    cdiContainer = CdiContainerLoader.getCdiContainer();
    cdiContainer.boot();

    Assertions.assertThat(cdiContainer.getContextControl()).isNotNull();

    contextControl = cdiContainer.getContextControl();

    Assertions.assertThat(contextControl).isNotNull();

    System.out.println("contextControl is " + contextControl);

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
