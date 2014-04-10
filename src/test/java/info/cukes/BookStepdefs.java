package info.cukes;

import org.junit.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import java.lang.invoke.MethodHandles;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(locations = "/cucumber.xml")
public class BookStepdefs
{
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  List<Book> books = null;

  List<String> bookTitles = new ArrayList<>();

  List<Book> booksAddedByCucumber;

  int booksInDatabaseAtTestStart = 0;

  @Autowired
  private BookRepository bookRepository;

  @Before
  public void beforeStepDefs()
  {
    bookRepository.deleteAll();
  }

  @Given("^a writer has contributed to the following books:$")
  public void a_writer_has_contributed_to_the_following_books(List<Book> books) throws Throwable
  {
    List<Book> booksInDatabase = bookRepository.findAll();

    booksInDatabaseAtTestStart = booksInDatabase.size();

    Assert.assertNotNull("book list must not be null", books);

    Assert.assertTrue("book list must contain at least one book", books.size() > 0);

    LOGGER.info("the size of the book list is " + books.size());

    for (Book b : books)
    {
      bookTitles.add(b.getTitle());
      LOGGER.info("one book is " + b);
    }

    Assert.assertNotNull("the bookRepository must not be null", bookRepository);

    for (Book b : books)
    {
      bookRepository.save(b);
    }

    booksAddedByCucumber = books;
  }

  @When("^someone fetches the books$")
  public void someone_fetches_the_books() throws Throwable
  {
    books = bookRepository.findAll();
  }

  @Then("^(\\d+) books named as above have been added to the database$")
  public void books_named_as_above_have_been_added_to_the_database(int arg1) throws Throwable
  {
    LOGGER.info("the number of books is " + books.size());

    Assert.assertEquals(booksInDatabaseAtTestStart + arg1, books.size());

    List<Book> listOfBooksInDatabase = bookRepository.findAll();

    List<String> titlesOfBooksInDatabase = Book.getListOfTitles(listOfBooksInDatabase);

    Assert.assertEquals(booksInDatabaseAtTestStart + arg1, titlesOfBooksInDatabase.size());

    LOGGER.info("the names of the book that were added to the database are " + bookTitles);

    LOGGER.info("the names of all books in database are " + titlesOfBooksInDatabase);

    Assert.assertTrue(titlesOfBooksInDatabase.containsAll(bookTitles));

    LOGGER.info("and they each appear in the database");
  }

  @After
  public void afterStepDefs()
  {
    LOGGER.info("\n\nin method BookStepdefs afterStepDefs\n\n");
  }
}
