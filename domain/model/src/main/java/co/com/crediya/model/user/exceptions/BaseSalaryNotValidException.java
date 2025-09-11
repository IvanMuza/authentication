package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class BaseSalaryNotValidException extends BaseBusinessException {
    public BaseSalaryNotValidException() {
        super(ErrorCodesEnums.BASE_SALARY_INVALID_400);
    }
}
