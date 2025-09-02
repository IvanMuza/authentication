package co.com.crediya.model.user.exceptions;

public class DocumentAlreadyExistsException extends BaseBusinessException {
    public DocumentAlreadyExistsException(String code, String message) {
        super(code, message);
    }
}
