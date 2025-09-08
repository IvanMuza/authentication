package co.com.crediya.usecase.registeruser.auth;

import co.com.crediya.model.user.enums.ErrorCodesEnums;
import co.com.crediya.model.user.exceptions.RoleNotValidException;
import co.com.crediya.model.user.exceptions.UserNotFoundException;
import co.com.crediya.model.user.exceptions.ValidationException;
import co.com.crediya.model.user.gateways.JwtProviderPort;
import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;
    private final JwtProviderPort jwtProvider;
    private final RoleRepository roleRepository;

    public Mono<String> login(String email, String password) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException(
                        ErrorCodesEnums.USER_EMAIL_NOT_FOUND.getCode(),
                        ErrorCodesEnums.USER_EMAIL_NOT_FOUND.getDefaultMessage())))
                .flatMap(user -> {
                    if (!user.getPassword().equals(password)) {
                        return Mono.error(new ValidationException(
                                ErrorCodesEnums.CREDENTIALS_INVALID.getCode(),
                                ErrorCodesEnums.CREDENTIALS_INVALID.getDefaultMessage()
                        ));
                    }
                    return roleRepository.findRoleNameById(user.getRoleId())
                            .map(roleName -> jwtProvider.generateToken(user.getEmail(), roleName))
                            .switchIfEmpty(Mono.error(new RoleNotValidException(
                                    ErrorCodesEnums.ROLE_INVALID.getCode(),
                                    ErrorCodesEnums.ROLE_INVALID.getDefaultMessage()
                            )));
                });
    }
}
