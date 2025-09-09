package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserLoginNotFound extends BaseBusinessException {
    public UserLoginNotFound() {
        super(ErrorCodesEnums.USER_LOGIN_NOT_FOUND_404);
    }
}
