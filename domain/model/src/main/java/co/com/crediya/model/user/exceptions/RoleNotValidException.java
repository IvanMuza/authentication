package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class RoleNotValidException extends BaseBusinessException {
    public RoleNotValidException() {
        super(ErrorCodesEnums.ROLE_INVALID_400);
    }
}
