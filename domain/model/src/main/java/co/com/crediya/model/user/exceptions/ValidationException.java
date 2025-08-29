package co.com.crediya.model.user.exceptions;

public class ValidationException extends BaseBusinessException {
    public ValidationException(String code, String message) {
        super(code, message);
    }
}
