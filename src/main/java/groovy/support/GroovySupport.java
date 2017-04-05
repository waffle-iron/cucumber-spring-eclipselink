package groovy.support;

/**
 *
 * the GroovySupport class offers Java rather than Groovy semantics for the operators == and !=
 * Groovy naively reports them as the result of the Java equals object, while when testing Java we need
 * them to be implemented in terms of Java semantics
 *
 * @author glick
 */
public class GroovySupport
{
  private GroovySupport(){}

  public static boolean isSameObject(Object first, Object second) {
    return first == second;
  }

  public static boolean isDifferentObject(Object first, Object second) {
    return first != second;
  }
}
