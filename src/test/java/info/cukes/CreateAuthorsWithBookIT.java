package info.cukes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>CreateAuthorsWithBookIT test class.</p>
 *
 * @author glick
 */
@SuppressWarnings("CdiInjectionPointsInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
@EnableTransactionManagement
public class CreateAuthorsWithBookIT
{
  @Inject
  AuthorDelegate authorDelegate;

  @Inject
  BookRepository bookRepository;

  @Inject
  AuthorRepository authorRepository;

  private List<Author> expectedAuthorList = new ArrayList<>();

  @Before
  public void setUp()
  {
    bookRepository.deleteAll();
    authorRepository.deleteAll();
  }

  @After
  public void tearDown()
  {
    bookRepository.deleteAll();
    authorRepository.deleteAll();
  }

  @Test
  public void testCreateAuthorsEachWithOneBook()
  {
    createAbookWithMultipleAuthors();

    List<Author> persistentAuthors = authorRepository.findAll();

    assertThat(persistentAuthors).isNotNull();
    assertThat(persistentAuthors).hasSize(2);

    Book persistentBook = bookRepository.findByTitle("Spring in Action");

    assertThat(persistentBook).isNotNull();
    assertThat(persistentBook.getTitle()).isEqualTo("Spring in Action");

    assertThat(persistentAuthors).hasSize(2);

    Assert.assertEquals(2, persistentAuthors.size());

    List<String> persistentAuthorNameList = authorDelegate.getListOfAuthorNames(persistentAuthors);

    List<String> expectedAuthorNameList = authorDelegate.getListOfAuthorNames(expectedAuthorList);

    assertThat(persistentAuthorNameList).hasSameSizeAs(persistentAuthors);

    assertThat(persistentAuthorNameList).hasSameSizeAs(expectedAuthorNameList);

    assertThat(expectedAuthorNameList).containsAll(persistentAuthorNameList);

    assertThat(expectedAuthorNameList).containsAll(persistentAuthorNameList);

    for (Author author : persistentAuthors)
    {
      assertThat(author.getAuthoredBooks()).hasSize(1);
      assertThat(author.hasAuthoredBook(persistentBook));
    }
  }

  public void createAbookWithMultipleAuthors()
  {
    Author andyGlick = new Author("Andy Glick");

    Author jimLaSpada = new Author("Jim La Spada");

    Book authoredBook = new Book("Spring in Action");

    authoredBook.addAnAuthor(andyGlick);
    authoredBook.addAnAuthor(jimLaSpada);

    andyGlick.addAuthoredBook(authoredBook);
    jimLaSpada.addAuthoredBook(authoredBook);

    List<Author> authors = Arrays.asList(andyGlick, jimLaSpada);

    authorRepository.save(authors);

    expectedAuthorList.add(andyGlick);
    expectedAuthorList.add(jimLaSpada);
  }
}
