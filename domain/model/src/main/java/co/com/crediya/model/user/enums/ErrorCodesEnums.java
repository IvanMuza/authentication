package co.com.crediya.model.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodesEnums {
    USER_REQUIRED("USER_REQUIRED", "User must not be null"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with provided document number"),
    USER_EMAIL_NOT_FOUND("USER_EMAIL_NOT_FOUND", "User not found with provided email"),
    DOCUMENT_NUMBER_REQUIRED("DOCUMENT_NUMBER_REQUIRED", "Document number cannot be empty"),
    DOCUMENT_NUMBER_ALREADY_EXISTS("DOCUMENT_NUMBER_ALREADY_EXISTS", "Document number already exists"),
    FIRST_NAME_REQUIRED("FIRST_NAME_REQUIRED", "First name cannot be empty"),
    LAST_NAME_REQUIRED("LAST_NAME_REQUIRED", "Last name cannot be empty"),
    EMAIL_REQUIRED("EMAIL_REQUIRED", "Email is required"),
    EMAIL_INVALID("EMAIL_INVALID", "Email is not valid"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email already registered"),
    BASE_SALARY_INVALID("BASE_SALARY_INVALID", "Base salary is not valid");

    private final String code;
    private final String defaultMessage;

}
