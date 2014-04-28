package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.fest.assertions.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Iterables;
import com.mysema.query.types.expr.BooleanExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class QueryDSLTest
{
  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  BookRepository bookRepository;

  @Test
  public void testQuerySome()
  {
    List<String> moreAuthorNames = Arrays.asList("Mark Shead", "Andy Glick", "Christian Garcia",
      "Jim Laspada", "Monica Batra", "Jeffrey Braxton");

    int moreAuthorNamesCount = moreAuthorNames.size();

    Author anAuthor;

    for (String authorName : moreAuthorNames)
    {
      anAuthor = new Author(authorName);

      authorRepository.save(anAuthor);
    }

    List<Author> allPersistentAuthors = authorRepository.findAll();

    Assertions.assertThat(allPersistentAuthors.size()).isEqualTo(moreAuthorNamesCount);

    QAuthor author = QAuthor.author1;

    BooleanExpression authorsToLookup = author.authorName.eq("Andy Glick")
      .or(author.authorName.eq("Jim Laspada")).or(author.authorName.eq("Jeffrey Braxton"));

    @SuppressWarnings("unchecked")
    Iterable<Author> locatedAuthors = authorRepository.findAll(authorsToLookup);

    List<Author> authorList = new ArrayList<>();

    Iterables.addAll(authorList, locatedAuthors);

    List<String> authorNameList = Author.getListOfAuthorNames(authorList);

    List<String> authorNamesToFind = Arrays.asList("Andy Glick", "Jim Laspada", "Jeffrey Braxton");

    Assertions.assertThat(authorNameList.size()).isEqualTo(authorNamesToFind.size());

    Assertions.assertThat(authorNameList.containsAll(authorNamesToFind));

    Assertions.assertThat(authorNamesToFind.containsAll(authorNameList));
  }

  @Test
  public void testQueryDSLQuery()
  {
    QBook book = QBook.book1;

    Author anAuthor = new Author("Dick Francis");

    authorRepository.save(anAuthor);

    Book aBook = new Book();

    aBook.setTitle("Dead Run");

    aBook.addAnAuthor(anAuthor);

    bookRepository.save(aBook);

    anAuthor = new Author("Isaac Asimov");

    authorRepository.save(anAuthor);

    aBook = new Book();

    aBook.setTitle("I Robot");

    aBook.addAnAuthor(anAuthor);

    bookRepository.save(aBook);

    anAuthor = new Author("Andy Glick");

    authorRepository.save(anAuthor);

    aBook = new Book();

    aBook.setTitle("The Cucumber Relish Experience");

    aBook.addAnAuthor(anAuthor);

    bookRepository.save(aBook);

    List<Book> allBooks = bookRepository.findAll();

    Assertions.assertThat(allBooks.size()).isEqualTo(3);

    BooleanExpression findBookByAuthor = book.bookAuthors.contains(anAuthor);

    @SuppressWarnings("unchecked")
    Iterable<Book> locatedBooks = bookRepository.findAll(findBookByAuthor);

    List<Book> locatedBookList = new ArrayList<>();

    Iterables.addAll(locatedBookList, locatedBooks);

    Assertions.assertThat(locatedBookList.size()).isEqualTo(1);

    Assertions.assertThat(locatedBookList.get(0).getBookAuthors().contains(anAuthor));
  }
}
