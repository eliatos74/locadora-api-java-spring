package locadora_api_java.web.controller.dto.book;

import java.util.List;

public record BookPaginatedResponseDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}
