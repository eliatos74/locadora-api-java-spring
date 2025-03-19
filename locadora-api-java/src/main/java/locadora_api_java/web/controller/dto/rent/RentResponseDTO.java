package locadora_api_java.web.controller.dto.rent;

public record RentResponseDTO(
        Long id,
        String renterName,
        String bookName,
        String status,
        String devolutionDate,
        String deadLineDate,
        String rentDate
) {
}