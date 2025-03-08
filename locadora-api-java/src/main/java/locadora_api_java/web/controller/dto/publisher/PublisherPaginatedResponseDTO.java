package locadora_api_java.web.controller.dto.publisher;

import java.util.List;

public record PublisherPaginatedResponseDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}
