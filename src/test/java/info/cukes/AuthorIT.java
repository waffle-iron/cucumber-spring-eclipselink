package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>AuthorIT test class.</p>
 *
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@EnableTransactionManagement
public class AuthorIT
{
  @Test
  @Transactional
  public void testAuthorAndUseDeclaredMethods()
  {
    Author author = new Author();

    author.setAuthorName("Herman Melville");

    author.getAuthor();
  }

  @Test(expected = UnsupportedOperationException.class)
  @Transactional
  public void testBookAuthorsAreImmutableCannotAdd()
  {
    Book aBook = new Book("One Hundred and FortyEight Below");

    Author anAuthor = new Author("Bill Giles");

    anAuthor.addAuthoredBook(aBook);

    aBook.addAnAuthor(anAuthor);

    List<Book> books = anAuthor.getAuthoredBooks();

    Book anotherBook = new Book("Small Changes");

    books.add(anotherBook);
  }

  @Test(expected = UnsupportedOperationException.class)
  @Transactional
  public void testBookAuthorsAreImmutableCannotRemove()
  {
    Book aBook = new Book("One Hundred and FortyEight Below");

    Author anAuthor = new Author("Bill Giles");

    anAuthor.addAuthoredBook(aBook);

    aBook.addAnAuthor(anAuthor);

    List<Book> books = anAuthor.getAuthoredBooks();

    books.remove(0);
  }

  @Test
  public void testABookIsNotAnAuthor()
  {
    Book book = new Book("Hamlet");

    Author author = new Author("William Shakespeare");

    Assertions.assertThat(author).isNotEqualTo(book);
  }
}
