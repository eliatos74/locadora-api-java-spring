package locadora_api_java.web.controller.dto.renter;

import java.util.List;

public record RenterPaginatedResponseDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}
