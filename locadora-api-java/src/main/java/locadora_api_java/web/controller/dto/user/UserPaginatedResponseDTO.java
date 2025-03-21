package locadora_api_java.web.controller.dto.user;

import java.util.List;

public record UserPaginatedResponseDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}
