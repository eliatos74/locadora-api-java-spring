package locadora_api_java.web.controller.dto.rent;

import java.util.List;

public record RentPaginatedResponseDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {

}
