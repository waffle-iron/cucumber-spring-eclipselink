package info.cukes;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

/**
 * <p>AuthorDelegateImpl class.</p>
 *
 * @author glick
 */
@ApplicationScoped
@Component
public class AuthorDelegateImpl implements AuthorDelegate
{
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
    List<String> listOfAuthorNames = Lists.transform(authorList, new Function<Author, String>()
    {
      @Override
      public String apply(Author author)
      {
        return author.getAuthorName();
      }
    });

    return listOfAuthorNames;
  }

  @Override
  public List<Author> assignBookToAuthors(List<Author> authorList, final Book book)
  {
    List<Author> transformedAuthors = Lists.transform(authorList, new Function<Author, Author>()
    {
      @Override
      public Author apply(Author author)
      {
        author.addAuthoredBook(book);

        book.addAnAuthor(author);

        return author;
      }
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
   *
   * for matching lists then for each of the authors find the books that person authored and put the results
   * in a map
   * author -> books authored
   * compare the maps of the authors and their titles using the Maps.difference method from the
   * Guava libraries return true if they are the same else false
   */
  @Override
  public boolean compareAuthorLists(List<Author> authorsOfThisBook, List<Author> authorsOfThatBook)
  {
    if (authorsOfThisBook.size() != authorsOfThatBook.size())
    {
      return false;
    }
    else if (authorsOfThisBook.size() > 0)
    {
      List<String> namesOfAuthorsOfThisBook = getListOfAuthorNames(authorsOfThisBook);
      List<String> namesOfAuthorsOfThatBook = getListOfAuthorNames(authorsOfThatBook);

      if (!namesOfAuthorsOfThisBook.containsAll(namesOfAuthorsOfThatBook)
        || !namesOfAuthorsOfThatBook.containsAll(namesOfAuthorsOfThisBook))
      {
        return false;
      }
      else
      {
        Map<String, List<String>> thisBookAuthorsMap = authorToTitlesMap(authorsOfThisBook);
        Map<String, List<String>> thatBookAuthorsMap = authorToTitlesMap(authorsOfThatBook);

        MapDifference<String, List<String>> authorsMapDifferences
          = Maps.difference(thisBookAuthorsMap, thatBookAuthorsMap);

        if (!authorsMapDifferences.areEqual())
        {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * given a list of authors
   * @param authors list
   * @return a Map which represents a list of titles per author for each author in the list
   */
  private Map<String, List<String>> authorToTitlesMap(List<Author> authors)
  {
    Map<String, List<String>> authorToBooksMap = new LinkedHashMap<>();

    for (Author author : authors)
    {
      List<String> listOfTitlesByAuthor = bookDelegate.getListOfTitles(author.getAuthoredBooks());

      authorToBooksMap.put(author.getAuthorName(), listOfTitlesByAuthor);
    }

    return authorToBooksMap;
  }
}
