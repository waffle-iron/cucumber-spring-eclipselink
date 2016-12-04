package info.cukes

import spock.lang.Shared
import spock.lang.Specification

/**
 * @author glick
 */

class AuthorSpec extends Specification {

  @Shared Author clapton = new Author("clapton")

  def "the same author objects are equal by object equality"() {

    when: "an author is compared to iself"

    then: "the same author object equals itself"
      clapton == clapton
      clapton.equals(clapton)
  }

  def "different author objects are not equal"() {
    given: "another author named winwood"
    def winwood = new Author("winwood")

    when: "clapton is compared to winwood"

    then: "the authors are different"
    clapton != winwood
    !clapton.equals(winwood)
    winwood != clapton
    !winwood.equals(clapton)
  }

  def "2 authors with the same name aren't the same object but pass equals"() {
    given: "a second author named clapton"
    def clapton2 = new Author("clapton")

    when: "clapton is compared to clapton2 "

    then: "the 2 author pass the equals test -- groovy/spock don't recognize them as diffewrent objects as Java would have"
    clapton.equals(clapton2)
    clapton2.equals(clapton)
  }
}
