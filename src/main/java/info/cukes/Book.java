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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import javax.annotation.PostConstruct;

/**
 * <p>Book class.</p>
 *
 * @author andy
 * @version $Id: $Id
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Configurable(autowire= Autowire.BY_TYPE, dependencyCheck=true)
@Entity
@TableGenerator(name="book",
  table="sequences",
  pkColumnName = "sequenceKey",
  valueColumnName = "sequenceValue",
  pkColumnValue = "book",
  initialValue = 0,
  allocationSize = 1)
@Table(name = "book")
public class Book
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
  // AuthorDelegate authorDelegate;
  AuthorDelegate authorDelegate = new AuthorDelegateImpl();

  @Id
  @GeneratedValue(generator = "book")
  @Column(name= "book")
  private Long book;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToMany(mappedBy = "booksAuthored", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Author> bookAuthors = new ArrayList<>();

  /**
   * <p>Constructor for Book.</p>
   */
  public Book(){}

  /**
   * <p>Constructor for Book.</p>
   *
   * @param bookTitle a {@link String} object.
   */
  public Book(String bookTitle)
  {
    this();
    setTitle(bookTitle);
  }

  /**
   * <p>Getter for the field <code>title</code>.</p>
   *
   * @return a {@link String} object.
   */
  @SuppressWarnings("UnusedDeclaration")
  public String getTitle()
  {
    return title;
  }

  /**
   * <p>Setter for the field <code>title</code>.</p>
   *
   * @param title a {@link String} object.
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
   * @return a {@link Long} object.
   */
  @SuppressWarnings("UnusedDeclaration")
  public Long getBook()
  {
    return book;
  }

  // ToDo: weak logic in equals and hashcode around the fact that there is a endless recursion opportunity
  // ToDo: I chose a too aggressive work around, what should happen is that the bookAuthors comparison ought
  // ToDo: to be handled in the delegate
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

    return title.equals(book.title)
      && authorDelegate.compareAuthorLists(getBookAuthors(), book.getBookAuthors());
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode()
  {
    int result = title.hashCode();
    result = 31 * result + bookAuthors.size();
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    if (authorDelegate == null)
    {
      throw new RuntimeException("authorDelegate may not be null");
    }

    return "Book{" +
      "book=" + book +
      ", title='" + title + '\'' +
      ", bookAuthors=" + authorDelegate.recursionSafeAuthorsToString(bookAuthors) +
      '}';
  }
}
