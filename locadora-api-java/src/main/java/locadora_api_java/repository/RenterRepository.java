package locadora_api_java.repository;

import locadora_api_java.entity.Renter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RenterRepository extends JpaRepository<Renter, Long> {
    @Query("""
                SELECT r FROM Renter r 
                WHERE (:search IS NULL OR :search = ''
                    OR LOWER(r.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(r.email) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(r.telephone) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            """)
    Page<Renter> searchPublishers(@Param("search") String search, Pageable pageable);
}
