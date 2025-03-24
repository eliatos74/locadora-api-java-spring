package locadora_api_java.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String msg) {
        super(msg);
    }
}
