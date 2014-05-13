package info.cukes;

import java.util.List;

/**
 * @author glick
 */
public interface BookDelegate
{
  List<String> getListOfTitles(List<Book> bookList);
}
