package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserNotAuthorizedException extends BaseBusinessException {
    public UserNotAuthorizedException() {
        super(ErrorCodesEnums.USER_NOT_AUTHORIZED_TO_CREATE_401);
    }
}
