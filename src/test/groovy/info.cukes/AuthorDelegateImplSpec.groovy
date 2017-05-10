package info.cukes

/**
 * @author glick
 */
class AuthorDelegateImplSpec {

  def "add books to an authorDelegate"() {
    AuthorDelegate authorDelegate = new AuthorDelegateImpl()

    List<Author> authors = Arrays.asList(new Author("Red Baron"))

    Book book = new Book("Seven Pillars of Wisdom")

    authorDelegate.assignBookToAuthors(authors, book)
  }

  def "compare author lists"() {

    AuthorDelegate authorDelegate = new AuthorDelegateImpl()

    List<Author> authors = Arrays.asList(new Author("Red Baron"))
    List<Author> emptyOtherAuthors = Collections.emptyList()

    authorDelegate.compareAuthorLists(authors, emptyOtherAuthors)

    List<Author> otherAuthors = Arrays.asList(new Author("Snoopy"))
    authorDelegate.compareAuthorLists(authors, otherAuthors)

    otherAuthors = Arrays.asList(new Author("Red Baron"))
    authorDelegate.compareAuthorLists(authors, otherAuthors)
  }
}
