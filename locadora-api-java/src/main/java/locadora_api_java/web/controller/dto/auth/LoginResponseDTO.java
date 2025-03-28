package locadora_api_java.web.controller.dto.auth;

public record LoginResponseDTO(
        String username,
        String token,
        String role
) {
}
