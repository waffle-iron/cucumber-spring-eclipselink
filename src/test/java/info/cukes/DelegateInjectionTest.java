package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * <p>DelegateInjectionTest test class.</p>
 *
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DelegateInjectionTest
{
  @Inject
  AuthorDelegate authorDelegate;

  @Inject
  BookDelegate bookDelegate;

  @Test
  public void testAuthorDelegateInjection()
  {
    Assertions.assertThat(authorDelegate).isNotNull();
  }

  @Test
  public void testBookDelegateInjection()
  {
    Assertions.assertThat(bookDelegate).isNotNull();
  }
}
