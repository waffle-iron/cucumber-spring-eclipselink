package info.cukes;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name= "book")
  private Long book;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToMany
  @JoinTable(name = "book_authors",
    joinColumns = @JoinColumn(name = "book"),
    inverseJoinColumns = @JoinColumn(name = "author"))
  private List<Author> bookAuthors;

  public Book() {};

  public Book(String bookTitle)
  {
    setTitle(bookTitle);
  }

  @SuppressWarnings("UnusedDeclaration")
  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  @SuppressWarnings("UnusedDeclaration")
  public Long getBook()
  {
    return book;
  }

  private static Function<Book, String> extractTitles = new Function<Book, String>()
  {
    @Override
    public String apply(Book book)
    {
      return book.getTitle();
    }
  };

  public static List<String> getListOfTitles(List<Book> bookList)
  {
    List<String> titlesOfBooks = Lists.transform(bookList, extractTitles);

    return titlesOfBooks;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof Book))
    {
      return false;
    }

    Book book = (Book) o;

    return title.equals(book.title);
  }

  @Override
  public int hashCode()
  {
    return title.hashCode();
  }

  @Override
  public String toString()
  {
    return "Book{" +
      "book=" + book +
      ", title='" + title + '\'' +
      '}';
  }
}
