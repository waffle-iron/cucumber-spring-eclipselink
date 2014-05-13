package info.cukes;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

@Component
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
}
