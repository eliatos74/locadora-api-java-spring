package locadora_api_java.service;

import jakarta.annotation.PostConstruct;
import locadora_api_java.entity.Rent;
import locadora_api_java.enums.RentStatus;
import locadora_api_java.exception.InvalidDevolutionDateException;
import locadora_api_java.exception.PendingRentException;
import locadora_api_java.exception.RankPositionNotFoundException;
import locadora_api_java.exception.RentNotFound;
import locadora_api_java.repository.RentRepository;
import locadora_api_java.web.controller.dto.rent.RentMostRentendResponseDTO;
import locadora_api_java.web.controller.dto.rent.RentPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.rent.RentRentersResponseDTO;
import locadora_api_java.web.controller.dto.rent.RentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final RenterService renterService;
    private final BookService bookService;

    public RentService(RentRepository rentRepository, RenterService renterService, BookService bookService) {
        this.rentRepository = rentRepository;
        this.renterService = renterService;
        this.bookService = bookService;
    }

    public Rent createRent(Rent rent) {
        renterService.findRenter(rent.getRenterId());

        if (rentRepository.existsByRenterIdAndBookId(rent.getRenterId(), rent.getBookId())) {
            throw new PendingRentException("este locatário tem um aluguel pendente com este livro");
        }

        rent.setRentDate(LocalDate.now());
        if (rent.getDeadLineDate().isBefore(rent.getRentDate())) {
            throw new InvalidDevolutionDateException(String.format("data de devolução não pode ser menor que a data de hoje: %s", rent.getRentDate().toString()));
        }

        rent.setStatus(RentStatus.IN_TIME);
        bookService.updateBookOnRent(rent.getBookId());

        return rentRepository.save(rent);
    }

    public void deliverRent(Long id) {

        Rent rent = rentRepository.findById(id).orElseThrow(
                () -> new RentNotFound("aluguel não encontrado")
        );

        bookService.updateBookOnReturn(rent.getBookId());
        if (rent.getStatus() == RentStatus.IN_TIME) {
            rent.setStatus(RentStatus.DELIVERED);
        } else if (rent.getStatus() == RentStatus.DELAYED) {
            rent.setStatus(RentStatus.DELIVERED_WITH_DELAY);
        }
        rent.setDevolutionDate(LocalDate.now());

        rentRepository.save(rent);
    }

    public RentMostRentendResponseDTO mostBookRented(Long position) {
        var result = rentRepository.findBookByRank(position - 1);
        if (result == null) {
            throw new RankPositionNotFoundException(String.format("não foi encontrado nenhum livro para a posição de rank: %S", position));
        }
        return result;
    }

    @PostConstruct
    @Transactional
    public void updatStatusWhenStartingApplication() {
        updateStatusRentsOverdue();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateStatusRentsOverdue() {
        LocalDate today = LocalDate.now();

        List<Rent> overdueRents = rentRepository.findByDeadLineDateBeforeAndStatus(LocalDate.now(), RentStatus.IN_TIME);
        for (Rent rent : overdueRents) {
            rent.setStatus(RentStatus.DELAYED);
        }

        rentRepository.saveAll(overdueRents);
    }

    public RentPaginatedResponseDTO<RentResponseDTO> getFilteredRent(String search, RentStatus status, Integer page, Integer size, String sort, String direction) {
        Sort sorted = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sorted);

        Page<Rent> rents = rentRepository.searchRents(search, status, pageable);

        List<RentResponseDTO> rentResponses = rents.getContent().stream()
                .map(rent -> {
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
                }).toList();

        return new RentPaginatedResponseDTO<>(
                rentResponses,
                rents.getNumber(),
                rents.getSize(),
                rents.getTotalElements(),
                rents.getTotalPages()
        );
    }

    public RentPaginatedResponseDTO getFilteredRentRenters(String search, Integer page, Integer size, String sort, String direction) {
        String sortField = "";
        if ("id".equals(sort)) {
            sortField = "renterId";
        } else if ("name".equals(sort)) {
            sortField = "renter.name";
        } else {
            sortField = "renterId";
        }

        Sort sorted = Sort.by(Sort.Direction.fromString(direction), sortField);
        Pageable pageable = PageRequest.of(page, size, sorted);

        Page<RentRentersResponseDTO> rentRenters = rentRepository.findRenterRentStats(search, pageable);

        List<RentRentersResponseDTO> rentRentersResponses = rentRenters.getContent().stream()
                .map(rentRenter -> {
                            return new RentRentersResponseDTO(
                                    rentRenter.id(),
                                    rentRenter.name(),
                                    rentRenter.totalRents(),
                                    rentRenter.activeRents()
                            );
                        }
                ).toList();

        return new RentPaginatedResponseDTO<>(
                rentRentersResponses,
                rentRenters.getNumber(),
                rentRenters.getSize(),
                rentRenters.getTotalElements(),
                rentRenters.getTotalPages()
        );
    }
}
