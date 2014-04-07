package info.cukes;

import java.util.List;

import javax.validation.constraints.NotNull;

public interface BookRepository
{
  @NotNull
  void save(Book book);

  List<Book> findAllBooks();
}
