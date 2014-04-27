package info.cukes;

import org.fest.assertions.Assertions;
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

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private BookRepository bookRepository;

  private int authorsAdded;
  private int booksAdded;

  /**
   * capture the author names in the authorNames list for later use
   * save the 2 authors persistently
   *
   * @param firstAuthorName   author name
   * @param secondAuthorName  author name
   * @throws Throwable
   */
  @Given("^\"(.*?)\" and \"(.*?)\" are authors$")
  public void and_are_authors(String firstAuthorName, String secondAuthorName) throws Throwable {

    authorsAdded = 2;

    Author firstAuthor = new Author(firstAuthorName);
    Author secondAuthor = new Author(secondAuthorName);

    authorNames.add(firstAuthorName);
    authorNames.add(secondAuthorName);

    authorRepository.save(firstAuthor);
    authorRepository.save(secondAuthor);
  }

  /**
   * create the book
   * fetch the authors from the persistent store
   * add the book to each author
   * add each author to the book
   * store the book persistently
   * spring/eclipselink dirty checking will cause the authors to be updated
   * @param bookTitle   the book title
   * @throws Throwable
   */
  @When("^they write a book entitled \"(.*?)\"$")
  public void they_write_a_book_entitled(String bookTitle) throws Throwable {

    Book localBook = new Book(bookTitle);

    List<Author> authorList = authorRepository.findAll();

    for (Author author : authorList)
    {
      author.addAuthoredBook(localBook);
      localBook.addAnAuthor(author);
    }

    bookRepository.save(localBook);

    booksAdded = 1;
  }

  @Then("^their names should be associated with that title in the persistent store$")
  public void their_names_should_be_associated_with_that_title_in_the_persistent_store() throws Throwable
  {
    List<Author> authors = authorRepository.findAll();

    Assertions.assertThat(authors.size()).isEqualTo(authorsAdded);

    List<Book> books = bookRepository.findAll();

    Assertions.assertThat(books.size()).isEqualTo(booksAdded);

    for (Author author : authors)
    {
      Assertions.assertThat(authorNames.contains(author.getAuthorName()));

      Assertions.assertThat(books.containsAll(author.getAuthoredBooks()));

      for (Book book : books)
      {
        Assertions.assertThat(book.getBookAuthors().contains(author));
      }
    }
  }
}
