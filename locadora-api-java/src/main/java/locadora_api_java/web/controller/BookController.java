package locadora_api_java.web.controller;

import jakarta.validation.Valid;
import locadora_api_java.entity.Book;
import locadora_api_java.service.BookService;
import locadora_api_java.web.controller.dto.book.*;
import locadora_api_java.web.controller.dto.book.mapper.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookCreateDTO request) {
        Book book = bookService.create(bookMapper.toBook(request));
        var response = bookMapper.toDTO(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        Book book = bookService.findId(id);
        var response = bookMapper.toDTO(book);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookUpdateDTO request) {
        Book book = bookService.updateBook(
                request.id(),
                bookMapper.toBook(new BookCreateDTO(request.name(), request.author(), request.totalQuantity(), request.launchDate(), request.publisherId()))
        );
        var response = bookMapper.toDTO(book);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponseDTO> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<BookPaginatedResponseDTO<BookResponseInfoDTO>> getAllBooks(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        var response = bookService.getFilteredBook(search, page, size, sort, direction);
        return ResponseEntity.ok(response);
    }
}
