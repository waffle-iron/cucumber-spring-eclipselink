package info.cukes;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.google.common.collect.ImmutableList;

/**
 * <p>Author class.</p>
 *
 * @author glick
 */
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam"})
@Entity
@Table(name = "author")
@TableGenerator(name="author",
  table="sequences",
  pkColumnName = "sequenceKey",
  valueColumnName = "sequenceValue",
  pkColumnValue = "author",
  initialValue = 0,
  allocationSize = 1)
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
  @GeneratedValue(generator = "author")
  @Column(name = "author")
  private Long author;

  @Column(name = "authorName", nullable = false)
  private String authorName;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "book_authors",
    joinColumns = @JoinColumn(name = "author"),
    inverseJoinColumns = @JoinColumn(name="book"))
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

    return authorName.equals(author.authorName);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode()
  {
    int result = authorName.hashCode();
    result = 31 * result + booksAuthored.size();
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
