package co.com.crediya.model.user.exceptions;

public class EmailNotValidException extends BaseBusinessException {
    public EmailNotValidException(String code, String message) {
        super(code, message);
    }
}
