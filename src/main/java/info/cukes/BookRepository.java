package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>BookRepository interface.</p>
 *
 * @author andy
 * @version $Id: $Id
 */
public interface BookRepository extends JpaRepository<Book, Long>
{

}
