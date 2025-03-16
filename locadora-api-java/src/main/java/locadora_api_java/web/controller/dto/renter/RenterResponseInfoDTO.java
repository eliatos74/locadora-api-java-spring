package locadora_api_java.web.controller.dto.renter;

public record RenterResponseInfoDTO(
        Long id,
        String name,
        String email,
        String telephone
) {
}
