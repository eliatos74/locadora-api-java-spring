package locadora_api_java.exception;

public class NameUniqueViolationException extends RuntimeException {
    public NameUniqueViolationException(String msg) {
        super(msg);
    }
}
