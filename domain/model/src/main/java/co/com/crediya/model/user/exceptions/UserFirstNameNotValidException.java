package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserFirstNameNotValidException extends BaseBusinessException {
    public UserFirstNameNotValidException() {
        super(ErrorCodesEnums.FIRST_NAME_REQUIRED_400);
    }
}
