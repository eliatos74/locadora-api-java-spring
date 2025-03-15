package locadora_api_java.exception;

public class BookIsCurrentlyRentedException extends RuntimeException{
    public BookIsCurrentlyRentedException(String msg) {
        super(msg);
    }
}
