package info.cukes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author glick
 */
@SuppressWarnings({"CdiInjectionPointsInspection", "WeakerAccess"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DelegateConfigurationIT
{
  @Inject
  AuthorDelegate authorDelegate;

  @Inject
  BookDelegate bookDelegate;

  DelegateConfiguration delegateConfiguration;

  @SuppressWarnings("ConstantConditions")
  @Before
  public void setUp()
  {
    delegateConfiguration = new DelegateConfiguration();

    assertThat(delegateConfiguration).isNotNull();
  }

  @Test
  public void testAuthorDelegateConfig()
  {
    AuthorDelegate authorDelegate = delegateConfiguration.getAuthorDelegate();

    assertThat(authorDelegate).isNotNull();
  }

  @Test
  public void testBookDelegateConfig()
  {
    BookDelegate bookDelegate = delegateConfiguration.getBookDelegate();

    assertThat(bookDelegate).isNotNull();
  }

  @Test
  public void explicitlyTestAssignBookToAuthorsestStepDef()
  {
    Author ericClapton = new Author("Eric Clapton");
    Author stevieWinwood = new Author("Stevie Winwood");

    Book liveAtMadisonSquareGarden = new Book("Live At Madison Square");

    AuthorDelegate authorDelegate = new AuthorDelegateImpl();

    authorDelegate.assignBookToAuthors(Arrays.asList(ericClapton, stevieWinwood), liveAtMadisonSquareGarden);
  }
}
