package info.cukes;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookDelegateImpl implements BookDelegate
{
  AuthorDelegate authorDelegate = new AuthorDelegateImpl();

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
        .append(book.getBook())
        .append(", '")
        .append(book.getTitle())
        .append("'}");

      delimiter = ", ";
    }

    builder.append("}");

    return builder.toString();
  }

  @Override
  public boolean compareBookLists(List<Book> thisAuthorsBooks, List<Book> thatAuthorsBooks)
  {
    if (thisAuthorsBooks.size() != thatAuthorsBooks.size())
    {
      return false;
    }
    else if (thisAuthorsBooks.size() > 0)
    {
      List<String> thisBookTitlesList = getListOfTitles(thisAuthorsBooks);

      List<String> thatBookTitlesList = getListOfTitles(thatAuthorsBooks);

      if (!thisBookTitlesList.containsAll(thatBookTitlesList)
        || !thatBookTitlesList.containsAll(thisBookTitlesList))
      {
        return false;
      }
      else
      {
        Map<String, List<String>> thisAuthorsBooksMap = getTitleToAuthorsMap(thisAuthorsBooks);
        Map<String, List<String>> thatAuthorsBooksMap = getTitleToAuthorsMap(thatAuthorsBooks);

        MapDifference<String, List<String>> booksMapDifferences
          = Maps.difference(thisAuthorsBooksMap, thatAuthorsBooksMap);

        if (!booksMapDifferences.areEqual())
        {
          return false;
        }
      }
    }

    return true;
  }

  // @InvestigateAnomaly
  // there is/was a weirdness here -- when I attempted to @Inject the AuthorDelegate it failed to work when
  // referenced in this method. The object was injected in the rest of the methods in this class, but not
  // when the logic hit this method. How weird is that???
  //
  // Anyway, it is now working this way. When I sort out what CDI may be able to add, if I ever do,
  // will return to this.
  private Map<String, List<String>> getTitleToAuthorsMap(List<Book> books)
  {
    Map<String, List<String>> titleToAuthorsMap = new HashMap<>();

    for (Book book : books)
    {
      List<String> authorNameList = authorDelegate.getListOfAuthorNames(book.getBookAuthors());

        titleToAuthorsMap.put(book.getTitle(), authorNameList);
    }

    return titleToAuthorsMap;
  }
}
