package co.com.crediya.model.user.exceptions;

public class UserNotAuthorizedException extends BaseBusinessException {
    public UserNotAuthorizedException(String code, String message) {
        super(code, message);
    }
}
