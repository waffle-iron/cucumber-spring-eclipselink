package info.cukes;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

@Component
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
}
