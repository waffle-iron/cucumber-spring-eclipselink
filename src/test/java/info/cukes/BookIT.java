package info.cukes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.assertj.core.api.Assertions;

import java.util.List;

/**
 * <p>BookIT test class.</p>
 *
 * @author glick
 */
public class BookIT
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

    Assertions.assertThat(book.getTitle()).isNotEmpty();

    Assertions.assertThat(book.getBook().length()).isEqualTo(36);

    Assertions.assertThat(book.getBookAuthors()).hasSize(2);
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

  @Test
  public void testAnAuthorIsNotABook()
  {
    Book book = new Book();

    Author author = new Author();

    Assertions.assertThat(book).isNotEqualTo(author);
  }

  @Test
  public void testVerifyBooksNotSame()
  {
    Book hamlet = new Book("Hamlet");

    Author shakespeare = new Author("William Shakespeare");

    hamlet.addAnAuthor(shakespeare);

    Book romeoAndJuliet = new Book("Romeo and Juliet");

    romeoAndJuliet.addAnAuthor(shakespeare);

    Assert.assertFalse(hamlet.equals(romeoAndJuliet));

    Assertions.assertThat(hamlet).isNotEqualTo(romeoAndJuliet);
  }
}
