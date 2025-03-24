package locadora_api_java.exception;

public class UserByEmailNotFoundException extends RuntimeException{
    public UserByEmailNotFoundException(String msg){
        super(msg);
    }
}
