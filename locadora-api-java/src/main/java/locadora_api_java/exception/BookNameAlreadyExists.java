package locadora_api_java.exception;

public class BookNameAlreadyExists extends RuntimeException {
    public BookNameAlreadyExists(String msg) {
        super(msg);
    }
}
