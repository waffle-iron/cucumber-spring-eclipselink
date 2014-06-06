package info.cukes;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("UnusedDeclaration")
@Configuration
public class BeanProvider
{
  boolean beansNotInitialized = true;

  static AuthorDelegate authorDelegate = null;
  static BookDelegate bookDelegate = null;

  @Bean
  public AuthorDelegate getAuthorDelegate()
  {
    if (beansNotInitialized)
    {
      authorDelegate = new AuthorDelegateImpl();

      bookDelegate = new BookDelegateImpl();

      authorDelegate.setBookDelegate(bookDelegate);

      bookDelegate.setAuthorDelegate(authorDelegate);

      beansNotInitialized = false;
    }

    return authorDelegate;
  }

  @Bean
  public BookDelegate getBookDelegate()
  {
    if (beansNotInitialized)
    {
      bookDelegate = new BookDelegateImpl();

      authorDelegate = new AuthorDelegateImpl();

      bookDelegate.setAuthorDelegate(authorDelegate);

      authorDelegate.setBookDelegate(bookDelegate);

      beansNotInitialized = false;
    }

    return bookDelegate;
  }
}

