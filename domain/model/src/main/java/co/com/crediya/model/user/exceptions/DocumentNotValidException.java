package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class DocumentNotValidException extends BaseBusinessException {
    public DocumentNotValidException() {
        super(ErrorCodesEnums.DOCUMENT_NUMBER_REQUIRED_400);
    }
}
