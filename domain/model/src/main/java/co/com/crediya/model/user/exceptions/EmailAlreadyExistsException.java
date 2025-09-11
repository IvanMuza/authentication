package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class EmailAlreadyExistsException extends BaseBusinessException {
    public EmailAlreadyExistsException() {
        super(ErrorCodesEnums.EMAIL_ALREADY_EXISTS_400);
    }
}
