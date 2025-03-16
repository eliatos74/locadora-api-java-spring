package locadora_api_java.web.controller.dto.renter;

public record RenterResponseDTO(
        Long id,
        String name,
        String email,
        String telephone,
        String address,
        String cpf
) {
}
