package info.cukes;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
  @Transient
  // @Inject
  // BookDelegate bookDelegate;
  BookDelegate bookDelegate = new BookDelegateImpl();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "author")
  private Long author;

  @Column(name = "title", nullable = false)
  private String authorName;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "book_authors",
    joinColumns = @JoinColumn(name = "author"),
    inverseJoinColumns = @JoinColumn(name="book"))
  private List<Book> booksAuthored = new ArrayList<>();

  /**
   * <p>Constructor for Author.</p>
   */
  public Author(){}

  /**
   * <p>Constructor for Author.</p>
   *
   * @param authorName a {@link java.lang.String} object.
   */
  public Author(String authorName)
  {
    this();
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
      ", booksAuthored=" + bookDelegate.recursionSafeBooksToString(booksAuthored) +
      '}';
  }
}
