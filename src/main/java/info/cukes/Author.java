package info.cukes;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
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
  private List<Book> authorOf = new ArrayList<>();

  public Author()
  {}

  public Author(String authorName)
  {
    this.authorName = authorName;
  }

  @SuppressWarnings("UnusedDeclaration")
  public Long getAuthor()
  {
    return author;
  }

  public String getAuthorName()
  {
    return authorName;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void setAuthorName(String authorName)
  {
    this.authorName = authorName;
  }

  public void addAuthoredBook(Book book)
  {
    authorOf.add(book);
  }

  public List<Book> getAuthoredBooks()
  {
    List<Book> authoredBooks = Collections.unmodifiableList(authorOf);

    return authoredBooks;
  }

  private static Function<Author, String> authorNameExtractor = new Function<Author, String>()
  {
    @Override
    public String apply(Author author)
    {
      return author.getAuthorName();
    }
  };

  @SuppressWarnings("UnusedDeclaration")
  public static List<String> getListOfAuthorNames(List<Author> authorList)
  {
    List<String> listOfAuthorNames = Lists.transform(authorList, authorNameExtractor);

    return listOfAuthorNames;
  }

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
      && !(authorOf != null ? !authorOf.equals(author.authorOf) : author.authorOf != null);

  }

  @Override
  public int hashCode()
  {
    int result = authorName.hashCode();
    result = 31 * result + (authorOf != null ? authorOf.hashCode() : 0);
    return result;
  }

  @Override
  public String toString()
  {
    return "Author{" +
      "author=" + author +
      ", authorName='" + authorName + '\'' +
      ", authorOf=" + authorOf +
      '}';
  }
}
