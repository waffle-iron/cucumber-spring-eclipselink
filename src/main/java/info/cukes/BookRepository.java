package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * <p>BookRepository interface.</p>
 *
 * @author andy
 * @version $Id: $Id
 */
public interface BookRepository extends JpaRepository<Book, Long>, QueryDslPredicateExecutor
{

}
