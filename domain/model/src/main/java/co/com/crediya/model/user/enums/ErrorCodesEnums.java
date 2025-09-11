package co.com.crediya.model.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodesEnums {
    USER_REQUIRED_400("USER_REQUIRED_400", "User must not be null"),
    USER_DOCUMENT_NOT_FOUND_404("USER_DOCUMENT_NOT_FOUND_404", "User not found with provided document number"),
    DOCUMENT_NUMBER_REQUIRED_400("DOCUMENT_NUMBER_REQUIRED_400", "Document number cannot be empty"),
    DOCUMENT_NUMBER_ALREADY_EXISTS_400("DOCUMENT_NUMBER_ALREADY_EXISTS_400", "Document number already exists"),
    FIRST_NAME_REQUIRED_400("FIRST_NAME_REQUIRED_400", "First name cannot be empty"),
    LAST_NAME_REQUIRED_400("LAST_NAME_REQUIRED_400", "Last name cannot be empty"),
    EMAIL_REQUIRED_400("EMAIL_REQUIRED_400", "Email is required"),
    EMAIL_INVALID_400("EMAIL_INVALID_400", "Email is not valid"),
    EMAIL_ALREADY_EXISTS_400("EMAIL_ALREADY_EXISTS_400", "Email already registered"),
    BASE_SALARY_INVALID_400("BASE_SALARY_INVALID_400", "Base salary is not valid"),
    CREDENTIALS_INVALID_400("CREDENTIALS_INVALID_400", "Invalid credentials"),
    ROLE_INVALID_400("ROLE_INVALID_400", "Role is not valid"),
    USER_NOT_AUTHORIZED_TO_CREATE_401("USER_NOT_AUTHORIZED_TO_CREATE_401", "User not authorized to create users"),
    USER_LOGIN_NOT_FOUND_404("USER_LOGIN_NOT_FOUND_404", "User not found with provided login"),
    USER_NOT_AUTHENTICATED_403("USER_NOT_AUTHENTICATED_403", "User must be authenticated");

    private final String code;
    private final String defaultMessage;

}
