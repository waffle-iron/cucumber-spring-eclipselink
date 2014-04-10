package info.cukes;

import org.junit.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import java.lang.invoke.MethodHandles;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author glick
 */
@ContextConfiguration(locations = "/cucumber.xml")
public class AuthorStepDefs
{
  @SuppressWarnings("UnusedDeclaration")
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  List<String> authorNames = new ArrayList<>();

  Book firstBook = null;

  @Autowired
  private AuthorRepository authorRepository;

  @Given("^\"(.*?)\" and \"(.*?)\" are authors$")
  public void and_are_authors(String arg1, String arg2) throws Throwable {
    Author firstAuthor = new Author(arg1);
    Author secondAuthor = new Author(arg2);

    authorNames.add(arg1);
    authorNames.add(arg2);

    authorRepository.save(firstAuthor);
    authorRepository.save(secondAuthor);
  }

  @When("^they write a book entitled \"(.*?)\"$")
  public void they_write_a_book_entitled(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    firstBook = new Book(arg1);

    List<Author> authorList = authorRepository.findAll();

    for (Author author : authorList)
    {
      author.addAuthoredBook(firstBook);
    }
  }

  @Then("^their names should be associated with that title$")
  public void their_names_should_be_associated_with_that_title() throws Throwable {

    List<Author> authorList = authorRepository.findAll();

    List<Book> books = new ArrayList<>();

    books.add(firstBook);

    for (Author author : authorList)
    {
      Assert.assertTrue(authorNames.contains(author.getAuthorName()));

      Assert.assertTrue(books.containsAll(author.getAuthoredBooks()));
    }
  }
}
