package locadora_api_java.repository;

import locadora_api_java.entity.Rent;
import locadora_api_java.enums.RentStatus;
import locadora_api_java.web.controller.dto.rent.RentMostRentendResponseDTO;
import locadora_api_java.web.controller.dto.rent.RentRentersResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentRepository extends JpaRepository<Rent, Long> {

    boolean existsByRenterIdAndBookId(Long renterId, Long bookId);


    //    SELECT book_id, COUNT(*) AS rentedNumber
//    FROM rents
//    GROUP BY book_id
//    ORDER BY rentedNumber DESC
//    LIMIT 1 OFFSET 1;
    @Query("""
            SELECT new locadora_api_java.web.controller.dto.rent.RentMostRentendResponseDTO(b.name, COUNT(r))
            FROM Rent r
            JOIN Book b ON r.bookId = b.id
            GROUP BY r.bookId, b.name
            ORDER BY COUNT(r) DESC
            LIMIT 1 OFFSET :position
            """)
    RentMostRentendResponseDTO findBookByRank(@Param("position") Long position);

    @Query("""
                SELECT r FROM Rent r
                JOIN Renter renter ON r.renterId = renter.id
                JOIN Book book ON r.bookId = book.id
                WHERE (:search IS NULL OR LOWER(renter.name) LIKE LOWER(CONCAT('%', :search, '%')) 
                    OR LOWER(book.name) LIKE LOWER(CONCAT('%', :search, '%')))
                AND (:status IS NULL OR r.status = :status)
            """)
    Page<Rent> searchRents(@Param("search") String search,
                           @Param("status") RentStatus status,
                           Pageable pageable);


    //    SELECT renter_id,
//    COUNT(*) AS totalRents,
//    SUM(CASE WHEN status <> 'DELIVERED' THEN 1 ELSE 0 END) AS activeRents
//    FROM rents
//    GROUP BY renter_id;
    @Query("""
                SELECT new locadora_api_java.web.controller.dto.rent.RentRentersResponseDTO(r.renterId, renter.name, COUNT(r), SUM(CASE WHEN r.status <> 'DELIVERED' THEN 1 ELSE 0 END))
                FROM Rent r
                JOIN Renter renter ON r.renterId = renter.id
                WHERE (:search IS NULL OR LOWER(renter.name) LIKE LOWER(CONCAT('%', :search, '%')))
                GROUP BY r.renterId, renter.name
            """)
    Page<RentRentersResponseDTO> findRenterRentStats(@Param("search") String search, Pageable pageable);

}
