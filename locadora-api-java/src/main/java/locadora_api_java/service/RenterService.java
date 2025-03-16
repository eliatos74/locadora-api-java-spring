package locadora_api_java.service;

import jakarta.validation.constraints.NotBlank;
import locadora_api_java.entity.Renter;
import locadora_api_java.exception.EntityNotFoundException;
import locadora_api_java.exception.RenterCPFNumberAlreadyExists;
import locadora_api_java.repository.RenterRepository;
import locadora_api_java.web.controller.dto.renter.RenterPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.renter.RenterResponseInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RenterService {

    private final RenterRepository renterRepository;

    public RenterService(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    public Renter createRenter(Renter renter) {
        try {
            return this.renterRepository.save(renter);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new RenterCPFNumberAlreadyExists("Locatario com esse CPF já existe");
        }
    }

    @Transactional(readOnly = true)
    public Renter findRenter(Long id) {
        return renterRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Locatario com id = %s não encontrado", id))
        );
    }

    public Renter updateRenter(@NotBlank Long id, Renter renterUpdated) {
        Renter renter = findRenter(id);

        renter.setName(renterUpdated.getName());
        renter.setEmail(renterUpdated.getEmail());
        renter.setTelephone(renterUpdated.getTelephone());
        renter.setAddress(renterUpdated.getAddress());
        renter.setCpf(renterUpdated.getCpf());

        return renterRepository.save(renter);
    }

    public void deleteRenter(Long id) {
        Renter renter = findRenter(id);
        renterRepository.delete(renter);
    }

    @Transactional(readOnly = true)
    public RenterPaginatedResponseDTO<RenterResponseInfoDTO> getFilteredRenter(String search, Integer page, Integer size, String sort, String direction) {
        Sort sorted = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sorted);

        Page<Renter> renters = renterRepository.searchPublishers(search, pageable);

        List<RenterResponseInfoDTO> renterResponses = renters.getContent().stream()
                .map(renter -> new RenterResponseInfoDTO(
                        renter.getId(),
                        renter.getName(),
                        renter.getEmail(),
                        renter.getTelephone()
                )).toList();

        return new RenterPaginatedResponseDTO<>(
                renterResponses,
                renters.getNumber(),
                renters.getSize(),
                renters.getTotalElements(),
                renters.getTotalPages()
        );

    }
}