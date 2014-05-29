package info.cukes;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * <p>BookDelegateImpl class.</p>
 *
 * @author glick
 */
@ApplicationScoped
@Component
public class BookDelegateImpl implements BookDelegate
{
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

  /**
   * start with the lists of the books of the current author and that of the author to compare,
   * if the size of the lists differ no match - return false
   *
   * for matching lists then for each of the books find the authors of that book and put the
   * results in a map book -> authors
   * compare the maps of the books and their authors using the Maps.difference method from the
   * Guava libraries return true if they are the same else false
   */
  @Override
  public boolean compareBookLists(List<Book> booksOfThisAuthor, List<Book> booksOfThatAuthor)
  {
    if (booksOfThisAuthor.size() != booksOfThatAuthor.size())
    {
      return false;
    }
    else if (booksOfThisAuthor.size() > 0)
    {
      List<String> titlesForBooksOfThisAuthor = getListOfTitles(booksOfThisAuthor);

      List<String> titlesForBooksOfThatAuthor = getListOfTitles(booksOfThatAuthor);

      if (!titlesForBooksOfThisAuthor.containsAll(titlesForBooksOfThatAuthor)
        || !titlesForBooksOfThatAuthor.containsAll(titlesForBooksOfThisAuthor))
      {
        return false;
      }
      else
      {
        Map<String, List<String>> thisAuthorsTitlesToTheirAuthorsMap
          = getTitleToAuthorsMap(booksOfThisAuthor);
        Map<String, List<String>> thatAuthorsTitlesToTheirAuthorsMap
          = getTitleToAuthorsMap(booksOfThatAuthor);

        MapDifference<String, List<String>> titlesMapDifferences
          = Maps.difference(thisAuthorsTitlesToTheirAuthorsMap, thatAuthorsTitlesToTheirAuthorsMap);

        if (!titlesMapDifferences.areEqual())
        {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * given a list of books
   * @param books list
   * @return a Map which represents a list of authors per title for each title in the list
   */
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
