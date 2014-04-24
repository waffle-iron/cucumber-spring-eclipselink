package info.cukes;

import org.junit.Before;
import org.junit.Test;

import org.fest.assertions.Assertions;

import java.util.List;

/**
 * @author glick
 */
public class BookTest
{
  Book book;

  @Before
  public void setUp()
  {
    book = new Book();

    book.setTitle("A Book with Two Authors");

    Author aFirstAuthor = new Author("Mary Shelley");

    Author aSecondAuthor = new Author("John Keats");

    book.addAnAuthor(aFirstAuthor);
    book.addAnAuthor(aSecondAuthor);

    Assertions.assertThat(book.getBookAuthors().size()).isEqualTo(2);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testBookAuthorsAreImmutableCannotAdd()
  {
    List<Author> authors = book.getBookAuthors();

    Author aThirdAuthor = new Author("William Shakespeare");

    authors.add(aThirdAuthor);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testBookAuthorsAreImmutableCannotRemove()
  {
    List<Author> authors = book.getBookAuthors();

    authors.remove(0);
  }
}
