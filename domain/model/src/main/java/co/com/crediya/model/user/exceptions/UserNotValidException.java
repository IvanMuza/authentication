package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserNotValidException extends BaseBusinessException {
    public UserNotValidException() {
        super(ErrorCodesEnums.USER_REQUIRED_400);
    }
}
