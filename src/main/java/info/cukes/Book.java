package info.cukes;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
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

/**
 * <p>Book class.</p>
 *
 * @author andy
 * @version $Id: $Id
 */
@SuppressWarnings("JpaDataSourceORMInspection")
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
  private List<Author> bookAuthors = new ArrayList<>();

  /**
   * <p>Constructor for Book.</p>
   */
  public Book() {}

  /**
   * <p>Constructor for Book.</p>
   *
   * @param bookTitle a {@link java.lang.String} object.
   */
  public Book(String bookTitle)
  {
    setTitle(bookTitle);
  }

  /**
   * <p>Getter for the field <code>title</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  @SuppressWarnings("UnusedDeclaration")
  public String getTitle()
  {
    return title;
  }

  /**
   * <p>Setter for the field <code>title</code>.</p>
   *
   * @param title a {@link java.lang.String} object.
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * <p>Getter for the field <code>bookAuthors</code>.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<Author> getBookAuthors()
  {
    List<Author> immutableAuthors = ImmutableList.copyOf(bookAuthors);

    return immutableAuthors;
  }

  /**
   * <p>addAnAuthor.</p>
   *
   * @param author a {@link info.cukes.Author} object.
   */
  public void addAnAuthor(Author author)
  {
    bookAuthors.add(author);
  }

  /**
   * <p>Getter for the field <code>book</code>.</p>
   *
   * @return a {@link java.lang.Long} object.
   */
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

  /**
   * <p>getListOfTitles.</p>
   *
   * @param bookList a {@link java.util.List} object.
   * @return a {@link java.util.List} object.
   */
  public static List<String> getListOfTitles(List<Book> bookList)
  {
    List<String> titlesOfBooks = Lists.transform(bookList, extractTitles);

    return titlesOfBooks;
  }

  /** {@inheritDoc} */
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

    return !(bookAuthors != null ? !bookAuthors.equals(book.bookAuthors) : book.bookAuthors != null)
      && title.equals(book.title);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode()
  {
    int result = title.hashCode();
    result = 31 * result + (bookAuthors != null ? bookAuthors.hashCode() : 0);
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return "Book{" +
      "book=" + book +
      ", title='" + title + '\'' +
      ", bookAuthors=" + bookAuthors +
      '}';
  }
}
