package locadora_api_java.web.controller.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @Size(min = 1, max = 30)
        String name,

        @NotBlank
        @Size(min = 1, max = 100)
        String password
) {
}
