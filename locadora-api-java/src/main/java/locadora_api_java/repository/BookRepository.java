package locadora_api_java.repository;

import locadora_api_java.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
        SELECT b FROM Book b 
        WHERE (:search IS NULL OR :search = ''
            OR LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(b.author) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Book> searchBooks(@Param("search") String search, Pageable pageable);
}