package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.enums.ErrorCodesEnums;
import co.com.crediya.model.user.exceptions.DocumentAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailNotValidException;
import co.com.crediya.model.user.exceptions.ValidationException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.helper.EmailValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private static final Double MIN_VALUE_BASE_SALARY = 0.0;
    private static final Double MAX_VALUE_BASE_SALARY = 15000000.0;

    public Mono<User> registerUser(User user) {
        if (user == null) {
            return Mono.error(new ValidationException(ErrorCodesEnums.USER_REQUIRED.getCode(),
                    ErrorCodesEnums.USER_REQUIRED.getDefaultMessage()));
        }

        if (user.getDocumentNumber() == null || user.getDocumentNumber().isBlank()) {
            return Mono.error(new ValidationException(ErrorCodesEnums.DOCUMENT_NUMBER_REQUIRED.getCode(),
                    ErrorCodesEnums.DOCUMENT_NUMBER_REQUIRED.getDefaultMessage()));
        }

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            return Mono.error(new ValidationException(ErrorCodesEnums.FIRST_NAME_REQUIRED.getCode(),
                    ErrorCodesEnums.FIRST_NAME_REQUIRED.getDefaultMessage()));
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return Mono.error(new ValidationException(ErrorCodesEnums.LAST_NAME_REQUIRED.getCode(),
                    ErrorCodesEnums.LAST_NAME_REQUIRED.getDefaultMessage()));
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Mono.error(new ValidationException(ErrorCodesEnums.EMAIL_REQUIRED.getCode(),
                    ErrorCodesEnums.EMAIL_REQUIRED.getDefaultMessage()));
        }
        if (user.getBaseSalary() == null || user.getBaseSalary() < MIN_VALUE_BASE_SALARY || user.getBaseSalary() > MAX_VALUE_BASE_SALARY) {
            return Mono.error(new ValidationException(ErrorCodesEnums.BASE_SALARY_INVALID.getCode(),
                    ErrorCodesEnums.BASE_SALARY_INVALID.getDefaultMessage()));
        }
        if (!EmailValidator.isValid(user.getEmail())) {
            return Mono.error(new EmailNotValidException(ErrorCodesEnums.EMAIL_INVALID.getCode(),
                    ErrorCodesEnums.EMAIL_INVALID.getDefaultMessage()));
        }

        return userRepository.existsByDocumentNumber(user.getDocumentNumber())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DocumentAlreadyExistsException(
                                ErrorCodesEnums.DOCUMENT_NUMBER_ALREADY_EXISTS.getCode(),
                                ErrorCodesEnums.DOCUMENT_NUMBER_ALREADY_EXISTS.getDefaultMessage()
                        ));
                    }
                    return userRepository.existsByEmail(user.getEmail())
                            .flatMap(emailExists -> emailExists
                                    ? Mono.error(new EmailAlreadyExistsException(
                                    ErrorCodesEnums.EMAIL_ALREADY_EXISTS.getCode(),
                                    ErrorCodesEnums.EMAIL_ALREADY_EXISTS.getDefaultMessage()))
                                    : userRepository.save(user)
                            );
                });
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

}
