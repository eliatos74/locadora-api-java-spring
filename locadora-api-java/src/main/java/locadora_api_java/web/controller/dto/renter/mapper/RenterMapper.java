package locadora_api_java.web.controller.dto.renter.mapper;

import locadora_api_java.entity.Renter;
import locadora_api_java.web.controller.dto.renter.RenterCreateDTO;
import locadora_api_java.web.controller.dto.renter.RenterResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RenterMapper {
    public Renter toRenter(RenterCreateDTO renterCreateDTO) {
        Renter renter = new Renter();

        renter.setName(renterCreateDTO.name());
        renter.setEmail(renterCreateDTO.email());
        renter.setTelephone(renterCreateDTO.telephone());
        renter.setAddress(renterCreateDTO.address());
        renter.setCpf(renterCreateDTO.cpf());

        return renter;
    }

    public RenterResponseDTO toDTO(Renter renter) {
        return new RenterResponseDTO(
                renter.getId(), renter.getName(), renter.getEmail(), renter.getTelephone(), renter.getAddress(), renter.getCpf()
        );
    }
}
