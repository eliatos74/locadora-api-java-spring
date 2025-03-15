package locadora_api_java.web.controller.dto.book;

public record BookResponseInfoDTO(
        Long id,
        String name,
        String author,
        Long totalQuantity,
        Long availableQuantity,
        Long inUseQuantity
) {
}
