package locadora_api_java.exception;

public class BookOutOfStockException extends RuntimeException{
    public BookOutOfStockException(String msg){
        super(msg);
    }
}
