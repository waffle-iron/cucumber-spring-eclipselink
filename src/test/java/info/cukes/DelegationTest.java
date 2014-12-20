package info.cukes;

import org.junit.Test;

import org.assertj.core.api.Assertions;

/**
 * @author glick
 */
public class DelegationTest
{
  @Test
  public void testBooksSameNameDifferentAuthors()
  {
    Book g1 = new Book("Gettysburg");

    Book g2 = new Book("Gettysburg");

    Author newtGingrich = new Author("Newt Gingrich");
    Author billOreilly = new Author("Bill OReilly");

    g1.addAnAuthor(newtGingrich);
    g1.addAnAuthor(billOreilly);

    Author billClinton = new Author("Bill Clinton");
    Author danQuayle = new Author("Dan Quayle");

    g2.addAnAuthor(billClinton);

    Assertions.assertThat(g1).isNotEqualTo(g2);

    g2.addAnAuthor(danQuayle);

    Assertions.assertThat(g1).isNotEqualTo(g2);
  }
}
