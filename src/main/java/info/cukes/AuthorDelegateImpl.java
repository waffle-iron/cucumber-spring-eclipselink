package info.cukes;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.lang.invoke.MethodHandles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Component
public class AuthorDelegateImpl implements AuthorDelegate
{
  @SuppressWarnings("UnusedDeclaration")
  private static transient final Logger LOGGER
    = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @PostConstruct
  public void postConstruct()
  {
    LOGGER.warn("XXXXXXXX postConstruct executed XXXXXXXXX");
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
        .append("Author{")
        .append(author.getAuthor())
        .append(", '")
        .append(author.getAuthorName())
        .append("'}");

      delimiter = ", ";
    }

    builder.append("}");

    return builder.toString();
  }

  @Override
  public boolean compareAuthorLists(List<Author> thisBooksAuthors, List<Author> thatBooksAuthors)
  {
    if (thisBooksAuthors.size() != thatBooksAuthors.size())
    {
      return false;
    }
    else if (thisBooksAuthors.size() > 0)
    {
      List<String> thisBookAuthorsList = getListOfAuthorNames(thisBooksAuthors);
      List<String> thatBookAuthorsList = getListOfAuthorNames(thatBooksAuthors);

      if (!thisBookAuthorsList.containsAll(thatBookAuthorsList)
        || !thatBookAuthorsList.containsAll(thisBookAuthorsList))
      {
        return false;
      }
      else
      {
        Map<String, List<String>> thisBookAuthorsMap = authorToTitlesMap(thisBooksAuthors);
        Map<String, List<String>> thatBookAuthorsMap = authorToTitlesMap(thatBooksAuthors);

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

  // @InvestigateAnomaly
  // found a strange happenstance WRT the BookDelegate in the context of this class, using a BookDelegate,
  // rather than unpacking it and using it's getAuthorList method rather thn the unwound version which works
  // was seeing NPE's. Not sure why. There is probably an interaction between the AuthorDelegate and the
  // BookDelegate that I'm not aware of. First noticed the issue when I was using an injected BookDelegate,
  // which was failing, but that isn't it.
  private Map<String, List<String>> authorToTitlesMap(List<Author> authors)
  {
    Map<String, List<String>> authorToBooksMap = new HashMap<>();

    for (Author author : authors)
    {
      List<String> authorTitleList = Lists.transform(author.getAuthoredBooks(), new Function<Book, String>()
      {
        @Override
        public String apply(Book book)
        {
          return book.getTitle();
        }
      });

//      List<String> authorTitleList = bookDelegate.getListOfTitles(author.getAuthoredBooks());

      authorToBooksMap.put(author.getAuthorName(), authorTitleList);
    }

    return authorToBooksMap;
  }
}
