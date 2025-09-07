package co.com.crediya.model.user.exceptions;

public class RoleNotValidException extends BaseBusinessException {
    public RoleNotValidException(String code, String message) {
        super(code, message);
    }
}
