package info.cukes;

import cucumber.api.java.After;
import cucumber.api.java.Before;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateAuthorsWithBookTest
{
  @Inject
  AuthorDelegate authorDelegate;

  Book authoredBook;

  List<Author> expectedAuthorList = new ArrayList<>();

  @Autowired
  BookRepository bookRepository;

  @Autowired
  AuthorRepository authorRepository;

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

    List<Author> authors = authorRepository.findAll();

    Assertions.assertThat(authors).hasSize(2);

    List<String> persistentAuthorNameList = authorDelegate.getListOfAuthorNames(authors);

    List<String> expectedAuthorNameList = authorDelegate.getListOfAuthorNames(expectedAuthorList);

    Assertions.assertThat(persistentAuthorNameList).hasSameSizeAs(authors);

    Assertions.assertThat(persistentAuthorNameList).hasSameSizeAs(expectedAuthorNameList);

    Assertions.assertThat(expectedAuthorNameList).containsAll(persistentAuthorNameList);

    Assert.assertTrue(expectedAuthorNameList.containsAll(persistentAuthorNameList));

    for (Author author : authors)
    {
      Assertions.assertThat(author.getAuthoredBooks()).contains(authoredBook);
    }
  }

  public void createAuthorsWithABook()
  {
    Author andyGlick = new Author("Andy Glick");

    Author jimLaSpada = new Author("Jim La Spada");

    authoredBook = new Book("Spring in Action");

    bookRepository.save(authoredBook);

    andyGlick.addAuthoredBook(authoredBook);

    jimLaSpada.addAuthoredBook(authoredBook);

    authorRepository.save(andyGlick);
    authorRepository.save(jimLaSpada);

    expectedAuthorList.add(andyGlick);
    expectedAuthorList.add(jimLaSpada);
  }
}
