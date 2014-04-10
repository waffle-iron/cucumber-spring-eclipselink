package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author glick
 */
public interface AuthorRepository extends JpaRepository<Author, Long>
{
}
