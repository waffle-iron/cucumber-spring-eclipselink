package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AuthorTest
{
  @Autowired
  AuthorRepository authorRepository;

  @Test
  public void testAuthorAndUseDeclaredMethods()
  {
    Author author = new Author();

    author.setAuthorName("Herman Melville");

    author.getAuthor();
  }

  @Test(expected = UnsupportedOperationException.class)
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
  public void testBookAuthorsAreImmutableCannotRemove()
  {
    Book aBook = new Book("One Hundred and FortyEight Below");

    Author anAuthor = new Author("Bill Giles");

    anAuthor.addAuthoredBook(aBook);

    aBook.addAnAuthor(anAuthor);

    List<Book> books = anAuthor.getAuthoredBooks();

    books.remove(0);
  }
}
