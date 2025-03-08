package locadora_api_java.web.controller.dto.publisher.mapper;


import locadora_api_java.entity.Publisher;
import locadora_api_java.web.controller.dto.publisher.PublisherDTO;
import locadora_api_java.web.controller.dto.publisher.PublisherResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {
    public Publisher toPublisher(PublisherDTO publisherCreateDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherCreateDTO.name());
        publisher.setEmail(publisherCreateDTO.email());
        publisher.setTelephone(publisherCreateDTO.telephone());
        publisher.setSite(publisherCreateDTO.site());

        return publisher;
    }

    public PublisherResponseDTO toDTO(Publisher publisher) {
        return new PublisherResponseDTO(publisher.getId(), publisher.getName(), publisher.getEmail(), publisher.getTelephone(), publisher.getSite());
    }
}
