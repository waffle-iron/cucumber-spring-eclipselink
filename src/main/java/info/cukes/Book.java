package info.cukes;

import com.google.common.collect.ImmutableList;

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

/**
 * <p>Book class.</p>
 *
 * @author andy
 * @version $Id: $Id
 */
@SuppressWarnings("JpaDataSourceORMInspection")
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
  // @InvestigateAnomaly
  // due to something that I don't understand, attempting to get a
  // AuthorDelegate to be injected by Spring consistently fails
  // the class/object can be injected in every other class I have tried
  // and it is the same for BookDelegate in the Author class
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

    return title.equals(book.title) && bookAuthors.size() == book.getBookAuthors().size();
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
