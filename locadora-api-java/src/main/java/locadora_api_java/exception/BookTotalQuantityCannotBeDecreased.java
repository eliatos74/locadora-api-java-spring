package locadora_api_java.exception;

public class BookTotalQuantityCannotBeDecreased extends RuntimeException{
    public BookTotalQuantityCannotBeDecreased(String msg) {
        super(msg);
    }
}
