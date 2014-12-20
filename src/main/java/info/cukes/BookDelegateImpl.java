package info.cukes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * <p>BookDelegateImpl class.</p>
 *
 * @author glick
 */
@ApplicationScoped
public class BookDelegateImpl implements BookDelegate
{
  @SuppressWarnings("UnusedDeclaration")
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Inject
  AuthorDelegate authorDelegate;

  @Override
  public void setAuthorDelegate(AuthorDelegate authorDelegate)
  {
    this.authorDelegate = authorDelegate;
  }

  /**
   * <p>getListOfTitles.</p>
   *
   * @param bookList a {@link java.util.List} object.
   * @return a {@link java.util.List} object.
   */
  @Override
  public List<String> getListOfTitles(List<Book> bookList)
  {
    List<String> titlesOfBooks = Lists.transform(bookList, new Function<Book, String>()
    {
      @Override
      public String apply(Book book)
      {
        return book.getTitle();
      }
    });

    return titlesOfBooks;
  }

  @Override
  public String recursionSafeBooksToString(List<Book> books)
  {
    StringBuilder builder = new StringBuilder();

    String delimiter = "{";

    for (Book book : books)
    {
      builder.append(delimiter)
        .append("Book{book=")
        .append(book.getBook())
        .append(", title='")
        .append(book.getTitle())
        .append("'}");

      delimiter = ", ";
    }

    builder.append("}");

    return builder.toString();
  }
}
