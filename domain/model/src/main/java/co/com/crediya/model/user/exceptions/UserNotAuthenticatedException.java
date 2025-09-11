package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserNotAuthenticatedException extends BaseBusinessException {
    public UserNotAuthenticatedException() {
        super(ErrorCodesEnums.USER_NOT_AUTHENTICATED_403);
    }
}
