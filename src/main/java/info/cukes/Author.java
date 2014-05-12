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
 * <p>Author class.</p>
 *
 * @author glick
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "author")
public class Author
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "author")
  private Long author;

  @Column(name = "title", nullable = false)
  private String authorName;

  @ManyToMany
  @JoinTable(name = "book_authors",
    joinColumns = @JoinColumn(name = "author"),
    inverseJoinColumns = @JoinColumn(name="book"))
  private List<Book> booksAuthored = new ArrayList<>();

  /**
   * <p>Constructor for Author.</p>
   */
  public Author()
  {}

  /**
   * <p>Constructor for Author.</p>
   *
   * @param authorName a {@link java.lang.String} object.
   */
  public Author(String authorName)
  {
    this.authorName = authorName;
  }

  /**
   * <p>Getter for the field <code>author</code>.</p>
   *
   * @return a {@link java.lang.Long} object.
   */
  public Long getAuthor()
  {
    return author;
  }

  /**
   * <p>Getter for the field <code>authorName</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getAuthorName()
  {
    return authorName;
  }

  /**
   * <p>Setter for the field <code>authorName</code>.</p>
   *
   * @param authorName a {@link java.lang.String} object.
   */
  public void setAuthorName(String authorName)
  {
    this.authorName = authorName;
  }

  /**
   * <p>addAuthoredBook.</p>
   *
   * @param book a {@link info.cukes.Book} object.
   */
  public void addAuthoredBook(Book book)
  {
    booksAuthored.add(book);
  }

  /**
   * <p>getAuthoredBooks.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<Book> getAuthoredBooks()
  {
    List<Book> immutableListOfBooksAuthored = ImmutableList.copyOf(booksAuthored);

    return immutableListOfBooksAuthored;
  }

  private static Function<Author, String> authorNameExtractor = new Function<Author, String>()
  {
    @Override
    public String apply(Author author)
    {
      return author.getAuthorName();
    }
  };

  /**
   * <p>getListOfAuthorNames.</p>
   *
   * @param authorList a {@link java.util.List} object.
   * @return a {@link java.util.List} object.
   */
  public static List<String> getListOfAuthorNames(List<Author> authorList)
  {
    List<String> listOfAuthorNames = Lists.transform(authorList, authorNameExtractor);

    return listOfAuthorNames;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof Author))
    {
      return false;
    }

    Author author = (Author) o;

    return authorName.equals(author.authorName)
      && !(booksAuthored != null ? !booksAuthored.equals(author.booksAuthored) : author.booksAuthored != null);

  }

  /** {@inheritDoc} */
  @Override
  public int hashCode()
  {
    int result = authorName.hashCode();
    result = 31 * result + (booksAuthored != null ? booksAuthored.hashCode() : 0);
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return "Author{" +
      "author=" + author +
      ", authorName='" + authorName + '\'' +
      ", booksAuthored=" + recursionSafeBooksToString(booksAuthored) +
      '}';
  }

  public String recursionSafeBooksToString(List<Book> books)
  {
    StringBuilder builder = new StringBuilder();

    String delimiter = "";

    for (Book book : books)
    {
      builder.append(delimiter)
        .append(book.getBook())
        .append(", '")
        .append(book.getTitle())
        .append("'}");

      delimiter = ", ";
    }

    return builder.toString();
  }
}
