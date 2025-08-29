package co.com.crediya.model.user.enums;

import lombok.Getter;

@Getter
public enum ErrorCodesEnums {
    USER_REQUIRED("USER_REQUIRED", "User must not be null"),
    FIRST_NAME_REQUIRED("FIRST_NAME_REQUIRED", "First name cannot be empty"),
    LAST_NAME_REQUIRED("LAST_NAME_REQUIRED", "Last name cannot be empty"),
    EMAIL_REQUIRED("EMAIL_REQUIRED", "Email is required"),
    EMAIL_INVALID("EMAIL_INVALID", "Email is not valid"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email already registered"),
    BASE_SALARY_INVALID("BASE_SALARY_INVALID", "Base salary is not valid");

    private final String code;
    private final String defaultMessage;

    ErrorCodesEnums(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

}
