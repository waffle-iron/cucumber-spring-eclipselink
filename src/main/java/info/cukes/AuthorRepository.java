package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * <p>AuthorRepository interface.</p>
 *
 * @author glick
 */
public interface AuthorRepository extends JpaRepository<Author, Long>, QueryDslPredicateExecutor<Author>
{
    Author findByAuthorName(String name);
}
