package info.cukes;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * <p>AuthorDelegateImpl class.</p>
 *
 * @author glick
 */
@ApplicationScoped
public class AuthorDelegateImpl implements AuthorDelegate
{
  @SuppressWarnings("UnusedDeclaration")
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
  @Inject
  BookDelegate bookDelegate;

  @Override
  public void setBookDelegate(BookDelegate bookDelegate)
  {
    this.bookDelegate = bookDelegate;
  }

  /**
   * <p>getListOfAuthorNames.</p>
   *
   * @param authorList a {@link java.util.List} object.
   * @return a {@link java.util.List} object.
   */
  @Override
  public List<String> getListOfAuthorNames(List<Author> authorList)
  {
    List<String> listOfAuthorNames = Lists.transform(authorList, Author::getAuthorName);

    return listOfAuthorNames;
  }

  @Override
  public List<Author> assignBookToAuthors(List<Author> authorList, final Book book)
  {
    List<Author> transformedAuthors = Lists.transform(authorList, author ->
    {
      assert author != null;
      author.addAuthoredBook(book);

      book.addAnAuthor(author);

      return author;
    });

    return transformedAuthors;
  }

  @Override
  public String recursionSafeAuthorsToString(final List<Author> authors)
  {
    StringBuilder builder = new StringBuilder();

    String delimiter = "";

    builder.append("{");

    for (Author author : authors)
    {
      builder.append(delimiter)
        .append("Author{author=")
        .append(author.getAuthor())
        .append(", authorName='")
        .append(author.getAuthorName())
        .append("'}");

      delimiter = ", ";
    }

    builder.append("}");

    return builder.toString();
  }

  /**
   * start with the lists of the authors of the current book and the book to compare, if the size of the
   * lists differ then the lists don't match
   * <p/>
   * for lists of the same size compare the lists
   */
  @Override
  public boolean compareAuthorLists(List<Author> authorsOfThisBook, List<Author> authorsOfThatBook)
  {
    boolean result = false;

    System.out.println("authors of this book = " + authorsOfThisBook);
    System.out.println("authors of that book = " + authorsOfThatBook);

    if (authorsOfThisBook.size() != authorsOfThatBook.size())
    {
      return false;
    }

    if (!authorsOfThisBook.isEmpty())
    {
      List<String> namesOfAuthorsOfThisBook = getListOfAuthorNames(authorsOfThisBook);
      List<String> namesOfAuthorsOfThatBook = getListOfAuthorNames(authorsOfThatBook);

      result = namesOfAuthorsOfThisBook.containsAll(namesOfAuthorsOfThatBook);
    }

    return result;
  }
}
