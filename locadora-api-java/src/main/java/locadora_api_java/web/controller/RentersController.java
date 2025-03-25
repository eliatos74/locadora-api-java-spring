package locadora_api_java.web.controller;


import jakarta.validation.Valid;
import locadora_api_java.entity.Renter;
import locadora_api_java.service.RenterService;
import locadora_api_java.web.controller.dto.renter.*;
import locadora_api_java.web.controller.dto.renter.mapper.RenterMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("renters")
public class RentersController {

    private final RenterService renterService;
    private final RenterMapper renterMapper;

    public RentersController(RenterService renterService, RenterMapper renterMapper) {
        this.renterService = renterService;
        this.renterMapper = renterMapper;
    }

    @PostMapping
    public ResponseEntity<RenterResponseDTO> createRenter(@Valid @RequestBody RenterCreateDTO requst) {
        Renter renter = renterService.createRenter(renterMapper.toRenter(requst));
        var response = renterMapper.toDTO(renter);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RenterResponseDTO> getRenter(@PathVariable Long id) {
        Renter renter = renterService.findRenter(id);
        var response = renterMapper.toDTO(renter);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<RenterResponseDTO> updateRenter(@Valid @RequestBody RenterUpdateDTO request) {
        Renter renter = renterService.updateRenter(request.id(), renterMapper.toRenter(new RenterCreateDTO(
                request.name(),
                request.email(),
                request.telephone(),
                request.address(),
                request.cpf()
        )));

        var response = renterMapper.toDTO(renter);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RenterResponseDTO> deleteRenter(@PathVariable Long id) {
        renterService.deleteRenter(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<RenterPaginatedResponseDTO<RenterResponseInfoDTO>> getAllRenters(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var response = renterService.getFilteredRenter(search, page, size, sort, direction);
        return ResponseEntity.ok(response);
    }

}
