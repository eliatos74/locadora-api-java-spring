package locadora_api_java.web.controller.dto.book.mapper;


import locadora_api_java.entity.Book;
import locadora_api_java.repository.PublisherRepository;
import locadora_api_java.web.controller.dto.book.BookCreateDTO;
import locadora_api_java.web.controller.dto.book.BookResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookMapper {

    private final PublisherRepository publisherRepository;

    public BookMapper(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Book toBook(BookCreateDTO bookDTO) {

        Book book = new Book();

        book.setName(bookDTO.name());
        book.setAuthor(bookDTO.author());
        book.setTotalQuantity(bookDTO.totalQuantity());
        book.setLauchDate(LocalDate.parse(bookDTO.launchDate()));
        book.setPublisherId(bookDTO.publisherId());

        return book;
    }

    public BookResponseDTO toDTO(Book book) {

        String publisherName = publisherRepository.findById(book.getPublisherId()).get().getName();

        return new BookResponseDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getTotalQuantity(),
                book.getLauchDate().toString(),
                publisherName
        );
    }
}
