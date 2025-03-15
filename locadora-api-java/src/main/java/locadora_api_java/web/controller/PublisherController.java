package locadora_api_java.web.controller;

import jakarta.validation.Valid;
import locadora_api_java.entity.Publisher;
import locadora_api_java.service.PublisherService;
import locadora_api_java.web.controller.dto.publisher.PublisherDTO;
import locadora_api_java.web.controller.dto.publisher.PublisherPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.publisher.PublisherResponseDTO;
import locadora_api_java.web.controller.dto.publisher.PublisherUpdateDTO;
import locadora_api_java.web.controller.dto.publisher.mapper.PublisherMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("publishers")
public class PublisherController {

    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    public PublisherController(PublisherService publisherService, PublisherMapper publisherMapper) {
        this.publisherService = publisherService;
        this.publisherMapper = publisherMapper;
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> createPublisher(@Valid @RequestBody PublisherDTO request) {
        Publisher publisher = publisherService.save(publisherMapper.toPublisher(request));
        var response = publisherMapper.toDTO(publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> getPublisher(@PathVariable Long id) {
        Publisher publisher = publisherService.findId(id);
        var response = publisherMapper.toDTO(publisher);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PublisherResponseDTO> updatePublisher(@Valid @RequestBody PublisherUpdateDTO request) {
        Publisher publisher = publisherService.updatePublisher(request.id(), publisherMapper.toPublisher(new PublisherDTO(request.name(), request.email(), request.telephone(), request.site())));
        var response = publisherMapper.toDTO(publisher);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PublisherPaginatedResponseDTO<PublisherResponseDTO>> getAllPublishers(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var response = publisherService.getFilteredPublisher(search, page, size, sort, direction);
        return ResponseEntity.ok(response);
    }

}
