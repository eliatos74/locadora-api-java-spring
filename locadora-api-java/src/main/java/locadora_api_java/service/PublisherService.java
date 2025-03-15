package locadora_api_java.service;

import jakarta.validation.constraints.NotBlank;
import locadora_api_java.entity.Publisher;
import locadora_api_java.exception.EmailUniqueViolationException;
import locadora_api_java.exception.EntityNotFoundException;
import locadora_api_java.repository.PublisherRepository;
import locadora_api_java.web.controller.dto.publisher.PublisherPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.publisher.PublisherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher save(Publisher publisher) {
        try {
            return publisherRepository.save(publisher);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new EmailUniqueViolationException(String.format("Email %s ja existe", publisher.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public Publisher findId(Long id) {
        return publisherRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Editora com id = %s não encontrado", id))
        );
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        Publisher publihser = findId(id);
        publihser.setName(publisher.getName());
        publihser.setEmail(publisher.getEmail());
        publihser.setTelephone(publisher.getTelephone());
        publihser.setSite(publisher.getSite());
        return publisherRepository.save(publihser);
    }

    public void deletePublisher(Long id) {
        Publisher publisher = findId(id);
        publisherRepository.delete(publisher);
    }

    @Transactional(readOnly = true)
    public PublisherPaginatedResponseDTO<PublisherResponseDTO> getFilteredPublisher(String search, Integer page, Integer size, String sort, String direction) {
        Sort sorted = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sorted);

        Page<Publisher> publishers = publisherRepository.searchPublishers(search, pageable);

        List<PublisherResponseDTO> publisherResponses = publishers.getContent().stream()
                .map(publisher -> new PublisherResponseDTO(
                        publisher.getId(),
                        publisher.getName(),
                        publisher.getEmail(),
                        publisher.getTelephone(),
                        publisher.getSite()
                )).toList();

        return new PublisherPaginatedResponseDTO<>(
                publisherResponses,
                publishers.getNumber(),
                publishers.getSize(),
                publishers.getTotalElements(),
                publishers.getTotalPages()
        );
    }

}