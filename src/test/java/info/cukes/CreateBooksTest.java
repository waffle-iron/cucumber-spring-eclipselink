package info.cukes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * <p>CreateBooksTest test class.</p>
 *
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@EnableLoadTimeWeaving
@EnableTransactionManagement
public class CreateBooksTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Inject
  BookDelegate bookDelegate;

  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  BookRepository bookRepository;

  @Before
  public void setUp()
  {
    bookRepository.deleteAll();
  }

  @Test
  @Transactional
  public void testSpringEclipselink()
  {
    List<String> bookTitles = new ArrayList<>();

    Assertions.assertThat(bookTitles).hasSize(0);

    Book book = new Book();

    String bookTitle = "The Surefire Book";

    book.setTitle(bookTitle);

    bookRepository.save(book);

    bookTitles.add(bookTitle);

    book = new Book();

    bookTitle = "Surefire Recipes";

    book.setTitle(bookTitle);

    bookRepository.save(book);

    bookTitles.add(bookTitle);

    List<Book> books = bookRepository.findAll();

    List<String> allBookTitles = bookDelegate.getListOfTitles(books);

    Assertions.assertThat(allBookTitles).hasSize(2);

    LOGGER.info("all book titles are " + allBookTitles);
    LOGGER.info("book titles written are " + bookTitles);

    Assertions.assertThat(allBookTitles).containsAll(bookTitles);
  }
}
