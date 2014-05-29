package info.cukes;

import org.junit.Test;

/**
 * <p>DomainEntityToStringNoDatabaseTest test class.</p>
 *
 * @author glick
 */
public class DomainEntityToStringNoDatabaseTest
{
  @Test
  public void testAuthor_NoBooks_ToString()
  {
    Author tomBrown = new Author("Tom Brown");

    System.out.println("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testAuthor_OneBook_NoAuthor_ToString()
  {
    Author tomBrown = new Author("Tom Brown");

    Book theSearch = new Book("The Search");

    tomBrown.addAuthoredBook(theSearch);

    System.out.println("Tom Brown should produce a legal string " + tomBrown);
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

    System.out.println("Tom Brown should produce a legal string " + tomBrown);
  }

  @Test
  public void testBook_NoAuthor_ToString()
  {
    Book theSearch = new Book("The Search");

    System.out.println("The Search should produce a legal string " + theSearch);
  }

  @Test
  public void testBook_OneAuthor_ToString()
  {
    Book theSearch = new Book("The Search");

    Author tomBrown = new Author("Tom Brown");

    theSearch.addAnAuthor(tomBrown);

    System.out.println("The Search should produce a legal string " + theSearch);
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

    System.out.println("The Mote in God's Eye should produce a legal string " + theMoteInGodsEye);
  }
}
