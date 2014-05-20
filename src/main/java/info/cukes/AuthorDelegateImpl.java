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
  public String recursionSafeAuthorsToString(final List<Author> authors)
  {
    StringBuilder builder = new StringBuilder();

    String delimiter = "";

    builder.append("Books{");

    for (Author author : authors)
    {
      builder.append(delimiter)
        .append("Book{")
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
