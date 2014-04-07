package info.cukes;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaBookRepository implements BookRepository
{
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  @Override
  public void save(Book book)
  {
    entityManager.persist(book);
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  @Override
  public List<Book> findAllBooks()
  {
    @SuppressWarnings("JpaQlInspection")
    Query query = entityManager.createQuery("SELECT b FROM Book b");

    List<Book> bookList = query.getResultList();

    return bookList;
  }
}
