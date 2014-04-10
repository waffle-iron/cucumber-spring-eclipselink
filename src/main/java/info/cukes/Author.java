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

/**
 * @author glick
 */
@Entity
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

  public Long getAuthor()
  {
    return author;
  }

  public String getAuthorName()
  {
    return authorName;
  }

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

  private static Function<Author, String> extractTitles = new Function<Author, String>()
  {
    @Override
    public String apply(Author author)
    {
      return author.getAuthorName();
    }
  };

  public static List<String> getListOfTitles(List<Author> authorList)
  {
    List<String> titlesOfAuthorNames = Lists.transform(authorList, extractTitles);

    return titlesOfAuthorNames;
  }
}
