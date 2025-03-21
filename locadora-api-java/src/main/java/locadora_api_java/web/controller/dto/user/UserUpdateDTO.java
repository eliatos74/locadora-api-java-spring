package locadora_api_java.web.controller.dto.user;

import jakarta.validation.constraints.*;

public record UserUpdateDTO(
        @NotNull
        Long id,

        @Size(min = 1, max = 30)
        String name,

        @NotBlank
        @Email(message = "formato de e-mail esta invalido.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @NotBlank
        @Pattern(regexp = "ADMIN|VISITOR", message = "Role deve ser ADMIN ou VISITOR")
        String role
) {
}
