package locadora_api_java.repository;

import locadora_api_java.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeRepository extends JpaRepository<Code, Long> {

    @Query("""
                    SELECT c FROM Code c
                    where :id = c.user.id
            """)
    Code findByCodeByUserId(Long id);

}
