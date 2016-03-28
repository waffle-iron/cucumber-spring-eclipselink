package info.cukes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

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

  Book authoredBook;

  List<Author> expectedAuthorList = new ArrayList<>();

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
    createAuthorsWithABook();

    List<Author> persistentAuthors = authorRepository.findAll();

    Assertions.assertThat(persistentAuthors).isNotNull();
    Assertions.assertThat(persistentAuthors).hasSize(2);

    Book persistentBook = bookRepository.findByTitle("Spring in Action");

    Assertions.assertThat(persistentBook).isNotNull();
    Assertions.assertThat(persistentBook.getTitle()).isEqualTo("Spring in Action");

    Assertions.assertThat(persistentAuthors).hasSize(2);

    Assert.assertEquals(2, persistentAuthors.size());

    List<String> persistentAuthorNameList = authorDelegate.getListOfAuthorNames(persistentAuthors);

    List<String> expectedAuthorNameList = authorDelegate.getListOfAuthorNames(expectedAuthorList);

    Assertions.assertThat(persistentAuthorNameList).hasSameSizeAs(persistentAuthors);

    Assertions.assertThat(persistentAuthorNameList).hasSameSizeAs(expectedAuthorNameList);

    Assertions.assertThat(expectedAuthorNameList).containsAll(persistentAuthorNameList);

    Assertions.assertThat(expectedAuthorNameList).containsAll(persistentAuthorNameList);

    for (Author author : persistentAuthors)
    {
      Assertions.assertThat(author.getAuthoredBooks()).hasSize(1);
      Assertions.assertThat(author.getAuthoredBooks()).contains(persistentBook);
    }
  }

  public void createAuthorsWithABook()
  {
    Author andyGlick = new Author("Andy Glick");

    Author jimLaSpada = new Author("Jim La Spada");

    authoredBook = new Book("Spring in Action");

    authoredBook.addAnAuthor(andyGlick);
    authoredBook.addAnAuthor(jimLaSpada);

    andyGlick.addAuthoredBook(authoredBook);
    jimLaSpada.addAuthoredBook(authoredBook);

    authorRepository.save(Arrays.asList(andyGlick, jimLaSpada));

    expectedAuthorList.add(andyGlick);
    expectedAuthorList.add(jimLaSpada);
  }
}
