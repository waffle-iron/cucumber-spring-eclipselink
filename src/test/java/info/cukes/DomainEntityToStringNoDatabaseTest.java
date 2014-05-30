package info.cukes;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * <p>DomainEntityToStringNoDatabaseTest test class.</p>
 *
 * @author glick
 */
public class DomainEntityToStringNoDatabaseTest
{
  private static transient final Logger LOGGER
    = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  @Test
  public void testAuthor_NoBooks_ToString()
  {
    Author tomBrown = new Author("Tom Brown");

    LOGGER.warn("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testAuthor_OneBook_NoAuthor_ToString()
  {
    Author tomBrown = new Author("Tom Brown");

    Book theSearch = new Book("The Search");

    tomBrown.addAuthoredBook(theSearch);

    LOGGER.warn("Tom Brown should produce a legal string " + tomBrown);
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

    LOGGER.warn("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testBook_NoAuthor_ToString()
  {
    Book theSearch = new Book("The Search");

    LOGGER.warn("The Search should produce a legal string " + theSearch);
  }

  @Test
  public void testBook_OneAuthor_ToString()
  {
    Book theSearch = new Book("The Search");

    Author tomBrown = new Author("Tom Brown");

    theSearch.addAnAuthor(tomBrown);

    LOGGER.warn("The Search should produce a legal string " + theSearch);
  }

  @Test
  public void testBook_MultipleAuthor_To_String()
  {
    Author larryNiven = new Author("Larry Niven");

    Author jerryPournelle = new Author("Jerry Pournelle");

    Book theMoteInGodsEye = new Book("The Mote in God's Eye");

    theMoteInGodsEye.addAnAuthor(larryNiven);

    theMoteInGodsEye.addAnAuthor(jerryPournelle);

    larryNiven.addAuthoredBook(theMoteInGodsEye);

    jerryPournelle.addAuthoredBook(theMoteInGodsEye);

    Book ringWorld = new Book("Ring World");

    ringWorld.addAnAuthor(larryNiven);

    larryNiven.addAuthoredBook(ringWorld);

    LOGGER.warn("The Mote in God's Eye should produce a legal string " + theMoteInGodsEye);

    LOGGER.warn("Larry Niven should produce a legal string " + larryNiven);

    LOGGER.warn("Jerry Pournelle should produce a legal string " + jerryPournelle);

    LOGGER.warn("Ring World should produce a legal string " + ringWorld);
  }
}
