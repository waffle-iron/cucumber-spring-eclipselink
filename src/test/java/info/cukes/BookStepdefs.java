package info.cukes;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * <p>BookStepdefs cucumber glue class.</p>
 *
 * @author glick
 */
@SuppressWarnings("CdiInjectionPointsInspection")
@ContextConfiguration(locations = "classpath:cucumber.xml")
@EnableTransactionManagement
@Transactional
public class BookStepdefs
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Inject
  BookDelegate bookDelegate;

  @Inject
  AuthorDelegate authorDelegate;

  @Inject
  private BookRepository bookRepository;

  @Inject
  private AuthorRepository authorRepository;

  private List<Author> authors = new ArrayList<>();

  private List<String> bookTitles = null;

  private List<String> authorNames;

  private int booksAdded = 0;

  private int authorsAdded = 0;

  @Before
  public void beforeStepDefs(Scenario scenario)
  {
    LOGGER.info("");
    LOGGER.info("executing cucumber scenario " + scenario.getName());
    LOGGER.info("");

    bookRepository.deleteAll();
    authorRepository.deleteAll();
  }

  @Transactional
  @Given("^\"(.*?)\" has contributed to the following titles:$")
  public void has_contributed_to_the_following_titles(String author, List<Book> books) throws Throwable
  {
    Assertions.assertThat(books).hasSize(2);

    Assertions.assertThat(author).isNotEmpty();

    booksAdded = books.size();

    bookTitles = bookDelegate.getListOfTitles(books);

    authorsAdded += 1;

    Author anAuthor = new Author(author);

    // authorRepository.save(anAuthor);

    authors = new ArrayList<>();

    authors.add(anAuthor);

    authorNames = authorDelegate.getListOfAuthorNames(authors);

    for (Book book : books)
    {
      book.addAnAuthor(anAuthor);
      anAuthor.addAuthoredBook(book);
    }

    authorRepository.save(anAuthor);
  }

  @When("^someone fetches the books$")
  public void someone_fetches_the_books() throws Throwable
  {
    List<Book> books = bookRepository.findAll();

    Assertions.assertThat(books).hasSize(booksAdded);

    authors = authorRepository.findAll();

    Assertions.assertThat(authors).hasSize(authorsAdded);
  }

  @Then("^(\\d+) titles named as above have been stored persistently$")
  public void titles_named_as_above_have_been_stored_persistently(int booksStored) throws Throwable
  {
    List<Book> books = bookRepository.findAll();

    Assertions.assertThat(books).hasSize(booksStored);

    List<String> localBookTitles = bookDelegate.getListOfTitles(books);

    Assertions.assertThat(localBookTitles).hasSameSizeAs(bookTitles);

    Assertions.assertThat(localBookTitles).containsAll(bookTitles);
  }

  @Then("^have \"(.*?)\" as an author$")
  public void have_as_an_author(String authorName) throws Throwable
  {
    List<Book> books = bookRepository.findAll();

    for (Book aBook : books)
    {
      Assertions.assertThat(authorDelegate.getListOfAuthorNames(aBook.getBookAuthors())).contains(authorName);
    }
  }
}
