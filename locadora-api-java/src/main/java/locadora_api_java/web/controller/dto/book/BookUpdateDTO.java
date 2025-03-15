package locadora_api_java.web.controller.dto.book;

import jakarta.validation.constraints.*;

public record BookUpdateDTO(
        @NotBlank
        @Positive
        Long id,
        @NotBlank @Size(min = 1, max = 50)
        String name,

        @NotBlank @Size(min = 1, max = 50)
        String author,

        @NotNull @Min(1) @Max(1_000_000)
        Long totalQuantity,

        String launchDate,

        @NotNull
        @Positive
        Long publisherId) {
}
