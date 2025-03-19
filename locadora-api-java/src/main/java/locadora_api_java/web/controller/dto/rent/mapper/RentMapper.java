package locadora_api_java.web.controller.dto.rent.mapper;

import locadora_api_java.entity.Rent;
import locadora_api_java.service.BookService;
import locadora_api_java.service.RenterService;
import locadora_api_java.web.controller.dto.rent.RentCreateDTO;
import locadora_api_java.web.controller.dto.rent.RentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RentMapper {

    private final RenterService renterService;
    private final BookService bookService;

    public RentMapper(RenterService renterService, BookService bookService) {
        this.renterService = renterService;
        this.bookService = bookService;
    }

    public Rent toRent(RentCreateDTO rentCreateDTO) {
        Rent rent = new Rent();

        rent.setRenterId(rentCreateDTO.renterId());
        rent.setBookId(rentCreateDTO.bookId());
        rent.setDeadLineDate(rentCreateDTO.deadline());

        return rent;
    }

    public RentResponseDTO toDTO(Rent rent) {

        String renterName = renterService.findRenter(rent.getRenterId()).getName();
        String bookName = bookService.findId(rent.getBookId()).getName();

        return new RentResponseDTO(
                rent.getId(),
                renterName,
                bookName,
                rent.getStatus().toString(),
                rent.getDevolutionDate() != null ? rent.getDevolutionDate().toString() : null,
                rent.getDeadLineDate().toString(),
                rent.getRentDate().toString()
        );
    }
}
