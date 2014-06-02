package info.cukes;

import org.springframework.beans.factory.annotation.Configurable;

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

import javax.inject.Inject;

/**
 * <p>Book class.</p>
 *
 * @author glick
 */
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
@Configurable
public class Book
{
  @Transient
  @Inject
  AuthorDelegate authorDelegate;

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
  public Book() {}

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
  public Long getBook()
  {
    return book;
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
    return "Book{" +
      "book=" + book +
      ", title='" + title + '\'' +
      ", bookAuthors=" + authorDelegate.recursionSafeAuthorsToString(bookAuthors) +
      '}';
  }
}
