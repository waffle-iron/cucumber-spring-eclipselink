package info.cukes

import spock.lang.Shared
import spock.lang.Specification

import static groovy.support.GroovySupport.isDifferentObject as isDifferentObject
import static groovy.support.GroovySupport.isSameObject as isSameObject

/**
 * @author glick
 */

class AuthorSpec extends Specification {

  @Shared
  Author clapton = new Author("clapton")

  @SuppressWarnings(["ChangeToOperator", "GrUnnecessaryDefModifier"])
  def "the same author objects are equal by object equality"() {

    when: "an author is compared to iself"

    then: "the same author object equals itself"
      isSameObject(clapton, clapton)
      clapton.equals(clapton)
  }

  @SuppressWarnings(["ChangeToOperator", "GrUnnecessaryDefModifier"])
  def "different author objects are not equal"() {
    given: "another author named winwood"
    def winwood = new Author("winwood")

    when: "clapton is compared to winwood"

    then: "the authors are different"
    isDifferentObject(clapton, winwood)
    !clapton.equals(winwood)
    isDifferentObject(winwood, clapton)
    !winwood.equals(clapton)
  }

  @SuppressWarnings(["ChangeToOperator", "GrUnnecessaryDefModifier"])
  def "2 authors with the same name aren't the same object but pass equals"() {
    given: "a second author named clapton"
    def clapton2 = new Author("clapton")

    when: "clapton is compared to clapton2 "

    then: "the 2 author pass the equals test -- groovy/spock doesn't recognize them as different objects using == as Java would"
    isDifferentObject(clapton, clapton2)

    !clapton.equals(clapton2)
    !clapton2.equals(clapton)
  }
}
