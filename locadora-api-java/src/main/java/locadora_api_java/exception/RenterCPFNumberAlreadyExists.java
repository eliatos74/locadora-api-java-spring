package locadora_api_java.exception;

public class RenterCPFNumberAlreadyExists extends RuntimeException {
    public RenterCPFNumberAlreadyExists(String msg) {
        super(msg);
    }
}
