package locadora_api_java.exception;

public class EmailUniqueViolationException extends RuntimeException {
    public EmailUniqueViolationException(String msg) {
        super(msg);
    }
}
