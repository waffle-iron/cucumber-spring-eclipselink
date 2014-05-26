package info.cukes;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import java.lang.invoke.MethodHandles;

import java.util.ArrayList;
import java.util.List;

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

import javax.annotation.PostConstruct;

/**
 * <p>Author class.</p>
 *
 * @author glick
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Configurable(autowire= Autowire.BY_TYPE, dependencyCheck=true)
@Entity
@Table(name = "author")
@TableGenerator(name="author",
  table="sequences",
  pkColumnName = "sequenceKey",
  valueColumnName = "sequenceValue",
  pkColumnValue = "author",
  initialValue = 0,
  allocationSize = 1)
public class Author
{
  private static transient final Logger LOGGER
    = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @PostConstruct
  public void postConstruct()
  {
    LOGGER.warn("XXXXXXXX postConstruct executed XXXXXXXXX");
  }

  // @ToDo
  // am looking for an alternate solution to the fact that Spring seemingly cannot directly inject
  // into JPA container managed beans, so AuthorDelegate objects cannot be injected into Book instances
  // and BookDelegate's cannot be injected into Author instances
  // am looking into CDI to see if it offers a convenient work around
  // my attempts to get aspectj weaving working, as has been suggested in a number of posts did not succeed,
  // at least not so far
  @Transient
  // @Inject
  // BookDelegate bookDelegate;
  BookDelegate bookDelegate = new BookDelegateImpl();

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

  // ToDo: weak logic in equals and hashcode around the fact that there is a endless recursion opportunity
  // ToDo: I chose a too aggressive work around, what should happen is that the booksAuthored comparison ought
  // ToDo: to be handled in the delegate

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
      && bookDelegate.compareBookLists(getAuthoredBooks(), author.getAuthoredBooks());
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
