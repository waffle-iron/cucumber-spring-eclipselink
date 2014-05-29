package info.cukes;

import java.util.List;

/**
 * <p>BookDelegate interface.</p>
 *
 * @author glick
 */
public interface BookDelegate
{
  void setAuthorDelegate(AuthorDelegate authorDelegate);

  List<String> getListOfTitles(List<Book> bookList);

  String recursionSafeBooksToString(List<Book> books);

  boolean compareBookLists(List<Book> thisAuthorsBooks, List<Book> thatAuthorsBooks);
}
