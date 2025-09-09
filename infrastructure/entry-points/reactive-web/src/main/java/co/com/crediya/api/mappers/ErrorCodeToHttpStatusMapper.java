package co.com.crediya.api.mappers;

import co.com.crediya.model.user.enums.ErrorCodesEnums;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeToHttpStatusMapper {
    public HttpStatus map(ErrorCodesEnums errorCode) {
        return switch (errorCode) {
            case USER_NOT_AUTHORIZED_TO_CREATE_401 -> HttpStatus.UNAUTHORIZED;
            case USER_DOCUMENT_NOT_FOUND_404,
                 USER_LOGIN_NOT_FOUND_404 -> HttpStatus.NOT_FOUND;
            case DOCUMENT_NUMBER_REQUIRED_400,
                 FIRST_NAME_REQUIRED_400,
                 LAST_NAME_REQUIRED_400,
                 EMAIL_REQUIRED_400,
                 EMAIL_INVALID_400,
                 BASE_SALARY_INVALID_400,
                 CREDENTIALS_INVALID_400,
                 ROLE_INVALID_400,
                 DOCUMENT_NUMBER_ALREADY_EXISTS_400,
                 EMAIL_ALREADY_EXISTS_400,
                 USER_REQUIRED_400 -> HttpStatus.BAD_REQUEST;
        };
    }
}
