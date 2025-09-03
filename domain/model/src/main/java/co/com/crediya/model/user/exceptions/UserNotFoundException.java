package co.com.crediya.model.user.exceptions;

public class UserNotFoundException extends BaseBusinessException {
    public UserNotFoundException(String code, String message) {
        super(code, message);
    }
}
