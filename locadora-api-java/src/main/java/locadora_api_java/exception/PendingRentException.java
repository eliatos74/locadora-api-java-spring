package locadora_api_java.exception;

public class PendingRentException extends RuntimeException{
    public PendingRentException(String msg) {
        super(msg);
    }
}
