package locadora_api_java.service;


import locadora_api_java.entity.Book;
import locadora_api_java.exception.*;
import locadora_api_java.repository.BookRepository;
import locadora_api_java.web.controller.dto.book.BookPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.book.BookResponseInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherService publisherService;

    public BookService(BookRepository bookRepository, PublisherService publisherService) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
    }

    public Book create(Book book) {
        var publisher = publisherService.findId(book.getPublisherId());
        try {
            book.setAvailableQuantity(book.getTotalQuantity());
            book.setInUseQuantity(0L);
            return bookRepository.save(book);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new BookNameAlreadyExists(String.format("Livro com o nome (%s) ja existe", book.getName()));
        }
    }

    @Transactional(readOnly = true)
    public Book findId(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Livro com id = %s não encontrado", id))
        );
    }

    public Book updateBook(Long id, Book book) {
        Book bookToUpdate = findId(id);

        validateTotalQuantityIncrease(book.getTotalQuantity(), bookToUpdate.getTotalQuantity());

        Long newAvailableQuantity = calculateAvailableQuantity(book.getTotalQuantity(), bookToUpdate.getInUseQuantity());

        bookToUpdate.setName(book.getName());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setTotalQuantity(book.getTotalQuantity());
        bookToUpdate.setAvailableQuantity(newAvailableQuantity);
        bookToUpdate.setLauchDate(book.getLauchDate());
        bookToUpdate.setPublisherId(book.getPublisherId());

        return bookRepository.save(bookToUpdate);
    }

    private void validateTotalQuantityIncrease(Long newTotal, Long currentTotal) {
        if (newTotal < currentTotal) {
            throw new BookTotalQuantityCannotBeDecreased("A quantidade total do livro não pode ser diminuída");
        }
    }

    private Long calculateAvailableQuantity(Long newTotal, Long inUseQuantity) {
        return newTotal - inUseQuantity;
    }

    public void updateBookOnRent(Long id) {
        Book bookToUpdate = findId(id);

        if (bookToUpdate.getInUseQuantity() == bookToUpdate.getTotalQuantity()) {
            throw new BookOutOfStockException("Livro sem estoque.");
        }

        bookToUpdate.setInUseQuantity(bookToUpdate.getInUseQuantity() + 1L);
        bookToUpdate.setAvailableQuantity(bookToUpdate.getAvailableQuantity() - 1L);

    }

    public void updateBookOnReturn(Long id) {
        Book bookToUpdate = findId(id);

        bookToUpdate.setAvailableQuantity(bookToUpdate.getAvailableQuantity() + 1L);
        bookToUpdate.setInUseQuantity(bookToUpdate.getInUseQuantity() - 1L);

    }

    public void deleteBook(Long id) {
        Book bookToDelete = findId(id);
        validateNoBookInUse(bookToDelete);
        bookRepository.delete(bookToDelete);
    }

    public void validateNoBookInUse(Book bookToDelete) {
        if (bookToDelete.getInUseQuantity() > 0) {
            throw new BookIsCurrentlyRentedException("Este livro está alugado e não pode ser excluido no momento.");
        }
    }

    @Transactional(readOnly = true)
    public BookPaginatedResponseDTO<BookResponseInfoDTO> getFilteredBook(String search, Integer page, Integer size, String sort, String direction) {
        Sort sorted = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sorted);

        Page<Book> books = bookRepository.searchBooks(search, pageable);

        List<BookResponseInfoDTO> bookResponses = books.getContent().stream()
                .map(book -> new BookResponseInfoDTO(
                        book.getId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getTotalQuantity(),
                        book.getAvailableQuantity(),
                        book.getInUseQuantity()
                )).toList();

        return new BookPaginatedResponseDTO<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages()
        );
    }
}
