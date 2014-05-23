package info.cukes;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class AuthorDelegateImpl implements AuthorDelegate
{
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
}
