package locadora_api_java.web.controller.dto.publisher;

public record PublisherResponseDTO(
        Long id,
        String name,
        String email,
        String telephone,
        String site

) {
}
