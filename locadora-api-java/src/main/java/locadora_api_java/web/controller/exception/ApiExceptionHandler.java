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

    @ExceptionHandler(CodeInvalidOrExpired.class)
    public ResponseEntity<ErrorMessage> codeInvalidOrExpired(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorMessage> passwordMismatchException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ErrorMessage> emailSendingException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(UserByEmailNotFoundException.class)
    public ResponseEntity<ErrorMessage> userByEmailNotFoundException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(RankPositionNotFoundException.class)
    public ResponseEntity<ErrorMessage> rankPositionNotFoundException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(RentNotFound.class)
    public ResponseEntity<ErrorMessage> rentNotFound(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(PendingRentException.class)
    public ResponseEntity<ErrorMessage> pendingRentException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(InvalidDevolutionDateException.class)
    public ResponseEntity<ErrorMessage> invalidDevolutionDateException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(BookOutOfStockException.class)
    public ResponseEntity<ErrorMessage> bookOutOfStockException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

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

    @ExceptionHandler(NameUniqueViolationException.class)
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
