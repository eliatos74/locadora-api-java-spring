package locadora_api_java.web.controller;

import jakarta.validation.Valid;
import locadora_api_java.entity.Rent;
import locadora_api_java.enums.RentStatus;
import locadora_api_java.service.RentService;
import locadora_api_java.web.controller.dto.rent.*;
import locadora_api_java.web.controller.dto.rent.mapper.RentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("rents")
public class RentController {
    private final RentService rentService;
    private final RentMapper rentMapper;

    public RentController(RentService rentService, RentMapper rentMapper) {
        this.rentService = rentService;
        this.rentMapper = rentMapper;
    }

    @PostMapping
    public ResponseEntity<RentResponseDTO> createRent(@Valid @RequestBody RentCreateDTO request) {
        Rent rent = rentService.createRent(rentMapper.toRent(request));
        var response = rentMapper.toDTO(rent);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity deliverRent(@PathVariable Long id) {
        rentService.deliverRent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("mostRentedBook/{position}")
    public ResponseEntity<RentMostRentendResponseDTO> mostRentedBook(@PathVariable Long position) {
        var rankedBook = rentService.mostBookRented(position);
        return ResponseEntity.ok(rankedBook);
    }

    @GetMapping
    public ResponseEntity<RentPaginatedResponseDTO<RentResponseDTO>> getAllRents(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) RentStatus status,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        var response = rentService.getFilteredRent(search, status, page, size, sort, direction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/renters")
    public ResponseEntity<RentPaginatedResponseDTO<RentRentersResponseDTO>> getAllRentRenters(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        var response = rentService.getFilteredRentRenters(search, page, size, sort, direction);
        return ResponseEntity.ok(response);
    }
}
