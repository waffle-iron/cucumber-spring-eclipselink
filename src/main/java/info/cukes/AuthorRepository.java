package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>AuthorRepository interface.</p>
 *
 * @author glick
 * @version $Id: $Id
 */
public interface AuthorRepository extends JpaRepository<Author, Long>
{
}
