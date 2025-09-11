package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class DocumentAlreadyExistsException extends BaseBusinessException {
    public DocumentAlreadyExistsException() {
        super(ErrorCodesEnums.DOCUMENT_NUMBER_ALREADY_EXISTS_400);
    }
}
