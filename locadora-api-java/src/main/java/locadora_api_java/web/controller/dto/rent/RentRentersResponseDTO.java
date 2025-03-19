package locadora_api_java.web.controller.dto.rent;

public record RentRentersResponseDTO(
        Long id,
        String name,
        Long totalRents,
        Long activeRents
) {
}
