package co.com.crediya.model.user.exceptions;

public class EmailAlreadyExistsException extends BaseBusinessException {
    public EmailAlreadyExistsException(String code, String message) {
        super(code, message);
    }
}
