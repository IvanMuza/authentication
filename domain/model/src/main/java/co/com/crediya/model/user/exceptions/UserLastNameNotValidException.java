package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserLastNameNotValidException extends BaseBusinessException {
    public UserLastNameNotValidException() {
        super(ErrorCodesEnums.LAST_NAME_REQUIRED_400);
    }
}
