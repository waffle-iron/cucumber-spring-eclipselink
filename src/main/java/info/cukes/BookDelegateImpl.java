package info.cukes;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class BookDelegateImpl implements BookDelegate
{
  /**
   * <p>getListOfTitles.</p>
   *
   * @param bookList a {@link java.util.List} object.
   * @return a {@link java.util.List} object.
   */
  @Override
  public List<String> getListOfTitles(List<Book> bookList)
  {
    List<String> titlesOfBooks = Lists.transform(bookList,  new Function<Book, String>()
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

    String delimiter = "";

    for (Book book : books)
    {
      builder.append(delimiter)
        .append(book.getBook())
        .append(", '")
        .append(book.getTitle())
        .append("'}");

      delimiter = ", ";
    }

    return builder.toString();
  }
}
