package info.cukes;

import org.junit.Test;

import org.assertj.core.api.Assertions;

/**
 * @author glick
 */
public class MetaModelInstantiationTest
{
  @Test
  public void testMetaModelInstances()
  {
    Author_ author_ = new Author_();

    Book_ book_ = new Book_();

    Assertions.assertThat(author_).isNotEqualTo(book_);
  }
}
