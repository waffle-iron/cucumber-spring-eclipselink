package info.cukes

import spock.lang.Shared
import spock.lang.Specification

import static groovy.support.GroovySupport.isDifferentObject
import static groovy.support.GroovySupport.isSameObject

/**
 * @author glick
 */
class BookSpec extends Specification {

  @Shared
  Book sabriel = new Book("Sabriel")

  @Shared
  Author garthNix = new Author("Garth Nix")

  @Shared
  Author ericClapton = new Author("Eric Clapton")

  def "a book without any authors returns false fromm hasAsAuthor tests"() {

    given: "a book has no authors"

    when: "asked if a book has author x"

    then: "then the book returns false"
    !sabriel.hasAsAuthor(ericClapton)
  }

  @SuppressWarnings("ChangeToOperator")
  "a book compared to itself satifies == as well as equals"() {
    given: "a book entitled Sabriel"
    sabriel.addAnAuthor(garthNix)

    when: "asked if it is the same object as itself"

    then: "it answers true"
    isSameObject(sabriel, sabriel)
    sabriel.equals(sabriel)
  }

  @SuppressWarnings(["ChangeToOperator", "GrEqualsBetweenInconvertibleTypes"])
  "if an object of class Book is compared to an object of a different class then they are not equal" () {
    given: "a book and an author"

    when: "the book is asked if it equals an author"

    then: "it replies false"
    !sabriel.equals(ericClapton)
  }

  def "a book has a hashcode"() {
    given: "a book"

    when: "the book is asked for its hashCode"

    then: "it respnds with it"
    sabriel.hashCode() != 0
  }

  def "there are 2 different books"() {
    given: "a book Sabriel and another book Silent Spring"
    Book silentSpring = new Book("Silent Spring")
    
    when: "asked if Sabriel is Silent Spring"

    then: "Sabriel answers that they are not the same book"
    isDifferentObject(sabriel, silentSpring)
    !sabriel.equals(silentSpring)
  }

  def "there are 2 books with the same title but different sets of authors"() {
    given: "there are 2 books with different author lists"
    Book fatFlap1 = new Book("Fat Flap")
    Author flapAuthor1 = new Author("Ralph Sport Sport")
    fatFlap1.addAnAuthor(flapAuthor1)
    flapAuthor1.addAuthoredBook(fatFlap1)

    Book fatFlap2 = new Book("Fat Flap")
    Author flapAuthor2 = new Author("Fubar Sam")
    fatFlap2.addAnAuthor(flapAuthor2)
    flapAuthor2.addAuthoredBook(fatFlap2)

    when: "asked if the books are the same"

    then: "they are not the same book"
    isDifferentObject(fatFlap1, fatFlap2)
    !fatFlap1.equals(fatFlap2)
    !fatFlap2.equals(fatFlap1)
  }
}
