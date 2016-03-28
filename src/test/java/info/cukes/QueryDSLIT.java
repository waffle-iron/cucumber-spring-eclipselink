package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.mysema.query.types.expr.BooleanExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * <p>QueryDSLIT test class.</p>
 *
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@EnableTransactionManagement
public class QueryDSLIT
{
  @Inject
  AuthorDelegate authorDelegate;

  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  AuthorRepository authorRepository;

  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  BookRepository bookRepository;

  @Test
  @Transactional
  public void testQuerySome()
  {
    List<String> moreAuthorNames = Arrays.asList("Mark Shead", "Andy Glick", "Christian Garcia",
      "Jim Laspada", "Monica Batra", "Jeffrey Braxton");

    int moreAuthorNamesCount = moreAuthorNames.size();

    Author anAuthor;

    for (String authorName : moreAuthorNames)
    {
      anAuthor = new Author(authorName);

      authorRepository.save(anAuthor);
    }

    authorRepository.flush();
    bookRepository.flush();

    List<Author> allPersistentAuthors = authorRepository.findAll();

    Assertions.assertThat(allPersistentAuthors).hasSize(moreAuthorNamesCount);

    QAuthor author = QAuthor.author1;

    BooleanExpression authorsToLookup = author.authorName.eq("Andy Glick")
      .or(author.authorName.eq("Jim Laspada")).or(author.authorName.eq("Jeffrey Braxton"));

    Iterable<Author> locatedAuthors = authorRepository.findAll(authorsToLookup);

    List<Author> authorList = new ArrayList<>();

    Iterables.addAll(authorList, locatedAuthors);

    List<String> authorNameList = authorDelegate.getListOfAuthorNames(authorList);

    List<String> authorNamesToFind = Arrays.asList("Andy Glick", "Jim Laspada", "Jeffrey Braxton");

    Assertions.assertThat(authorNameList).hasSameSizeAs(authorNamesToFind);

    Assertions.assertThat(authorNameList).containsAll(authorNamesToFind);

    Assertions.assertThat(authorNamesToFind).containsAll(authorNameList);
  }
}
