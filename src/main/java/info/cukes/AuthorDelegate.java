package info.cukes;

import java.util.List;

/**
 * @author glick
 */
public interface AuthorDelegate
{
  List<String> getListOfAuthorNames(List<Author> authorList);

  String recursionSafeAuthorsToString(List<Author> authors);
}
