package info.cukes;

import java.util.List;

/**
 * <p>AuthorDelegate interface.</p>
 *
 * @author glick
 */
public interface AuthorDelegate
{
  void setBookDelegate(BookDelegate bookDelegate);

  List<String> getListOfAuthorNames(List<Author> authorList);

  List<Author> assignBookToAuthors(List<Author> authorList, Book book);

  String recursionSafeAuthorsToString(List<Author> authors);

  boolean compareAuthorLists(List<Author> thisBooksAuthors, List<Author> thatBooksAuthors);
}
