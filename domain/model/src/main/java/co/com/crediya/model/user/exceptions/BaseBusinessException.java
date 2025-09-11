package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;
import lombok.Getter;

@Getter
public class BaseBusinessException extends RuntimeException {
    private final String code;

    public BaseBusinessException(ErrorCodesEnums errorCode) {
        super(errorCode.getDefaultMessage());
        this.code = errorCode.getCode();
    }

}
