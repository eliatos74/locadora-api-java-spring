package locadora_api_java.web.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserChangePasswordDTO(
        @NotBlank
        @Size(min = 1, max = 100)
        String newPassword,

        @NotBlank
        @Size(min = 1, max = 100)
        String repeatPassword,

        @NotBlank
        @Email(message = "formato de e-mail esta invalido.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @NotNull
        Long otpCode
) {
}
