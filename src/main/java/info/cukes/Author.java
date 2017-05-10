package info.cukes;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// import javax.persistence.GeneratedValue;
// import javax.persistence.TableGenerator;
// import javax.validation.constraints.NotNull;

/**
 * <p>Author class.</p>
 *
 * @author glick
 */
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam", "WeakerAccess", "SameParameterValue"})
@Entity
@Table(name = "author")
@Vetoed
public class Author
{
  /*
   *  had attempted to use injection to insert an AuthorDelegate, couldn't figure out how to do that,
   *  attempted to use AspectJ static weaving using the aspectj-maven-plugin, couldn't get that to work and
   *  don't know why it didn't
   *
   *  also attempted to use the CDI injection system, the @Vetoed annotation is a hold over from that, I didn't
   *  understand how CDI ought to work. Want to explore that some more, used Apache DeltaSpike would like to
   *  try it out in a more orderly
   *
   *  also want to explore hk2 as an injector and possibly guice
   *
   *  the issue is that because a JPA Entity is actually managed by the JPA container the "ordinary" dependency
   *  injectors don't have access to those classes. I'm not sure what the secret sauce needs to be. once again
   *  things militate on the side of the anemic domain model spooge
   */
  @Transient
  private BookDelegate bookDelegate;

  @SuppressWarnings("UnusedDeclaration")
  @Id
  @Column(name = "author")
  @Size(min = 36, max = 36)
  private String author = UUID.randomUUID().toString().toUpperCase();

  @Column(name = "authorName", nullable = false)
  @NotEmpty
  private String authorName;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "book_authors",
    joinColumns = @JoinColumn(name = "author"),
    inverseJoinColumns = @JoinColumn(name = "book"))
  @NotEmpty
  private List<Book> booksAuthored = new ArrayList<>();

  /**
   * <p>Constructor for Author.</p>
   */
  public Author()
  {
    bookDelegate = new BookDelegateImpl();

    AuthorDelegate authorDelegate = new AuthorDelegateImpl();

    bookDelegate.setAuthorDelegate(authorDelegate);

    authorDelegate.setBookDelegate(bookDelegate);
  }

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
  public String getAuthor()
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
  @SuppressWarnings("WeakerAccess")
  public void addAuthoredBook(Book book)
  {
    booksAuthored.add(book);
  }

  /**
   * <p>getAuthoredBooks.</p>
   *
   * @return a {@link java.util.List} object.
   */
  @SuppressWarnings("WeakerAccess")
  public List<Book> getAuthoredBooks()
  {
    List<Book> immutableListOfBooksAuthored = ImmutableList.copyOf(booksAuthored);

    return immutableListOfBooksAuthored;
  }

  @SuppressWarnings("WeakerAccess")
  public boolean hasAuthoredBook(Book aBook)
  {
    return booksAuthored.contains(aBook);
  }

  /**
   * {@inheritDoc}
   */
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

    Author compareToAuthor = (Author) o;

    return this.author.equals(compareToAuthor.getAuthor()) && authorName.equals(compareToAuthor.authorName);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode()
  {
    int result = authorName.hashCode();
    result = 31 * result + booksAuthored.size();
    return result;
  }

  /**
   * {@inheritDoc}
   */
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
