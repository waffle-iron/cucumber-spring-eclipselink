package info.cukes

import spock.lang.Specification

/**
 * @author glick
 */
class QBookSpec extends Specification {

  def "exercise QBook for covderage"() {
    given: "QBook weaving artifacts must be exercised for coverage purposes"
    QBook book1 = new QBook("book1");
  }
}