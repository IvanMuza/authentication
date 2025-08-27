package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.enums.ErrorCodesEnums;
import co.com.crediya.model.user.exceptions.EmailAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailNotValidException;
import co.com.crediya.model.user.exceptions.ValidationException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.helper.EmailValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public Mono<User> registerUser(User user) {
        if (user == null) {
            return Mono.error(new ValidationException(ErrorCodesEnums.USER_REQUIRED.getCode(),
                    ErrorCodesEnums.USER_REQUIRED.getDefaultMessage()));
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
        if (user.getBaseSalary() == null || user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000) {
            return Mono.error(new ValidationException(ErrorCodesEnums.BASE_SALARY_INVALID.getCode(),
                    ErrorCodesEnums.BASE_SALARY_INVALID.getDefaultMessage()));
        }
        if (!EmailValidator.isValid(user.getEmail())) {
            return Mono.error(new EmailNotValidException(ErrorCodesEnums.EMAIL_INVALID.getCode(),
                    ErrorCodesEnums.EMAIL_INVALID.getDefaultMessage()));
        }

        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> exists
                        ? Mono.error(new EmailAlreadyExistsException(ErrorCodesEnums.EMAIL_ALREADY_EXISTS.getCode(),
                        ErrorCodesEnums.EMAIL_ALREADY_EXISTS.getDefaultMessage()))
                        : userRepository.save(user)
                );
    }

}
