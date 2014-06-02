package info.cukes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author glick
 */
@Configuration
public class DelegateConfiguration
{
  AuthorDelegate authorDelegate = new AuthorDelegateImpl();
  BookDelegate bookDelegate = new BookDelegateImpl();

  @Bean
  public AuthorDelegate getAuthorDelegate()
  {
    authorDelegate.setBookDelegate(bookDelegate);

    return authorDelegate;
  }

  @Bean
  public BookDelegate getBookDelegate()
  {
    bookDelegate.setAuthorDelegate(authorDelegate);

    return bookDelegate;
  }
}
