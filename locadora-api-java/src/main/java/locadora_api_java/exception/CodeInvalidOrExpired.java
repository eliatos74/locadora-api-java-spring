package locadora_api_java.exception;

public class CodeInvalidOrExpired extends RuntimeException {
    public CodeInvalidOrExpired(String msg) {
        super(msg);
    }
}
