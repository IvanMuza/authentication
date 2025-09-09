package co.com.crediya.usecase.registeruser.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.*;
import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.helper.EmailValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final Double MIN_VALUE_BASE_SALARY = 0.0;
    private static final Double MAX_VALUE_BASE_SALARY = 15000000.0;

    public Mono<User> registerUser(User user, String roleName) {
        if (user == null) {
            return Mono.error(new UserNotValidException());
        }

        if (user.getDocumentNumber() == null || user.getDocumentNumber().isBlank()) {
            return Mono.error(new DocumentNotValidException());
        }

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            return Mono.error(new UserFirstNameNotValidException());
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return Mono.error(new UserLastNameNotValidException());
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Mono.error(new EmailNotValidException());
        }
        if (user.getBaseSalary() == null || user.getBaseSalary() < MIN_VALUE_BASE_SALARY || user.getBaseSalary() > MAX_VALUE_BASE_SALARY) {
            return Mono.error(new BaseSalaryNotValidException());
        }
        if (!EmailValidator.isValid(user.getEmail())) {
            return Mono.error(new EmailNotValidException());
        }

        return roleRepository.findRoleIdByName(roleName)
                .switchIfEmpty(Mono.error(new RoleNotValidException()))
                .flatMap(roleId -> {
                    user.setRoleId(roleId);
                    return userRepository.existsByDocumentNumber(user.getDocumentNumber())
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.error(new DocumentAlreadyExistsException());
                                }
                                return userRepository.existsByEmail(user.getEmail())
                                        .flatMap(emailExists -> emailExists
                                                ? Mono.error(new EmailAlreadyExistsException())
                                                : userRepository.save(user)
                                        );
                            });
                });

    }
}
