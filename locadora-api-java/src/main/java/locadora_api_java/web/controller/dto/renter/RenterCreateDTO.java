package locadora_api_java.web.controller.dto.renter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RenterCreateDTO(
        @NotBlank
        @Size(min = 1, max = 50)
        String name,

        @NotBlank
        @Email(message = "formato de e-mail esta invalido.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @Pattern(message = "formato de numero de telefone invalido", regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
        String telephone,

        @Size(min = 1, max = 255)
        String address,

        @NotBlank
        @Pattern(message = "formato de CPF invalido", regexp = "^([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}/?[0-9]{4}-?[0-9]{2})|([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-?[0-9]{2})$")
        String cpf
) {
}
