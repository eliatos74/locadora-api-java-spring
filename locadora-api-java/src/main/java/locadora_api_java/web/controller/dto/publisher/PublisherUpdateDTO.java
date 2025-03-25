package locadora_api_java.web.controller.dto.publisher;

import jakarta.validation.constraints.*;

public record PublisherUpdateDTO(
        @NotNull
        Long id,

        @Size(min = 1, max = 50)
        @NotBlank
        String name,

        @NotBlank
        @Email(message = "formato de e-mail esta invalido.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @Pattern(message = "formato de numero de telefone invalido", regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
        @Size(min = 1, max = 50)
        @NotBlank
        String telephone,

        @Size(min = 0, max = 50)
        String site
) {
}

