package locadora_api_java.web.controller.dto.book;

public record BookResponseDTO(
        Long id,
        String name,
        String author,
        Long totalQuantity,
        String launchDate,
        String publisherName) {
}