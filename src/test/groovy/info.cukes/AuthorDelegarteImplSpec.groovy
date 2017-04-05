package info.cukes

/**
 * @author glick
 */
class AuthorDelegarteImplSpec {

  def "add books to an authorDelegate"() {
    AuthorDelegate authorDelegate = new AuthorDelegateImpl()

    List<Author> authors = Arrays.asList(new Author("Red Baron"))

    Book book = new Book("Seven Pillars of Wisdom")

    authorDelegate.assignBookToAuthors(authors, book)
  }
}
