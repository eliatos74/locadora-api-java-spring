package locadora_api_java.exception;

public class InvalidDevolutionDateException extends RuntimeException {
    public InvalidDevolutionDateException(String msg) {
        super(msg);
    }
}
