package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class CredentialsInvalidException extends BaseBusinessException {
    public CredentialsInvalidException() {
        super(ErrorCodesEnums.CREDENTIALS_INVALID_400);
    }
}
