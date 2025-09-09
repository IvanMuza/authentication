package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class EmailNotValidException extends BaseBusinessException {
    public EmailNotValidException() {
        super(ErrorCodesEnums.EMAIL_INVALID_400);
    }
}
