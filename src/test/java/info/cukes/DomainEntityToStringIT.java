package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.invoke.MethodHandles;

/**
 * <p>DomainEntityToStringIT test class.</p>
 *
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DomainEntityToStringIT
{
  private static final transient Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Test
  public void testAuthor_NoBooks_ToString()
  {
    Author tomBrown = new Author("Tom Brown");

    LOGGER.info("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testAuthor_OneBook_NoAuthor_ToString()
  {
    Author tomBrown = new Author("Tom Brown");

    Book theSearch = new Book("The Search");

    tomBrown.addAuthoredBook(theSearch);

    LOGGER.info("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testAuthor_MultipleBooks_To_String()
  {
    Author tomBrown = new Author("Tom Brown");

    Book theSearch = new Book("The Search");

    tomBrown.addAuthoredBook(theSearch);

    theSearch.addAnAuthor(tomBrown);

    Book theTracker = new Book("The Tracker");

    tomBrown.addAuthoredBook(theTracker);

    theTracker.addAnAuthor(tomBrown);

    LOGGER.info("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testBook_NoAuthor_ToString()
  {
    Book theSearch = new Book("The Search");

    LOGGER.info("The Search should produce a legal string " + theSearch);
  }

  @Test
  public void testBook_OneAuthor_ToString()
  {
    Book theSearch = new Book("The Search");

    Author tomBrown = new Author("Tom Brown");

    theSearch.addAnAuthor(tomBrown);

    LOGGER.info("The Search should produce a legal string " + theSearch);
  }

  @Test
  public void testBook_MultipleAuthor_To_String()
  {
    Author larryNiven = new Author("Larry Niven");

    Author jerryPournelle = new Author("Jerry Pournelle");

    Book theMoteInGodsEye = new Book("The Mote in God's Eye");

    theMoteInGodsEye.addAnAuthor(larryNiven);

    theMoteInGodsEye.addAnAuthor(larryNiven);

    larryNiven.addAuthoredBook(theMoteInGodsEye);

    jerryPournelle.addAuthoredBook(theMoteInGodsEye);

    LOGGER.info("The Mote in God's Eye should produce a legal string " + theMoteInGodsEye);
  }
}
