package locadora_api_java.exception;

public class RentNotFound extends RuntimeException{
    public RentNotFound(String msg){
        super(msg);
    }
}
