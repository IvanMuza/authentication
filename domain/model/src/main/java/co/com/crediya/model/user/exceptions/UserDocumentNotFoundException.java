package co.com.crediya.model.user.exceptions;

import co.com.crediya.model.user.enums.ErrorCodesEnums;

public class UserDocumentNotFoundException extends BaseBusinessException {
    public UserDocumentNotFoundException() {
        super(ErrorCodesEnums.USER_DOCUMENT_NOT_FOUND_404);
    }
}
