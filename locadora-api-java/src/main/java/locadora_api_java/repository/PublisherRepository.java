package locadora_api_java.repository;


import locadora_api_java.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query("""
                SELECT p FROM Publisher p 
                WHERE (:search IS NULL OR :search = ''
                    OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(p.site) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(p.telephone) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            """)
    Page<Publisher> searchPublishers(@Param("search") String search, Pageable pageable);
}
