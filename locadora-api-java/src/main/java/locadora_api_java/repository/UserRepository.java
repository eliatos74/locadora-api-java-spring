package locadora_api_java.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import locadora_api_java.entity.User;
import locadora_api_java.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
                SELECT u FROM User u 
                WHERE (:search IS NULL OR :search = ''
                    OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(u.role) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            """)
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);

    User findUserByEmail(String email);

    Optional<User> findByName(String name);

    @Query("""
                    SELECT u.role FROM User u
                    where u.name like :name
            """)
    UserRole findRoleByName(String name);
}
