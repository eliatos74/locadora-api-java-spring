package locadora_api_java.web.controller.dto.user;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String role
) {
}
