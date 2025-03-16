package locadora_api_java.web.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import locadora_api_java.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(RenterCPFNumberAlreadyExists.class)
    public ResponseEntity<ErrorMessage> renterCPFNumberAlreadyExists(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(BookIsCurrentlyRentedException.class)
    public ResponseEntity<ErrorMessage> bookIsCurrentlyRentedException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(BookTotalQuantityCannotBeDecreased.class)
    public ResponseEntity<ErrorMessage> bookTotalQuantityCannotBeDecreased(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(BookNameAlreadyExists.class)
    public ResponseEntity<ErrorMessage> bookNameAlreadyExists(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(EmailUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> emailUniqueViolationException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            BindingResult result
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s).", result));
    }
}
