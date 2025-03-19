package locadora_api_java.web.controller.dto.rent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RentCreateDTO(
        @NotNull
        Long renterId,

        @NotNull
        Long bookId,

        @NotNull
        LocalDate deadline
) {
}
