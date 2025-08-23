package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public Mono<User> registerUser(User user) {
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            return Mono.error(new ValidationException("First name cannot be empty"));
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return Mono.error(new ValidationException("Last name cannot be empty"));
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Mono.error(new ValidationException("Email is required"));
        }
        if (user.getBaseSalary() == null || user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000) {
            return Mono.error(new ValidationException("Base salary is not valid"));
        }

        // Check if email already exists
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ValidationException("Email already registered"));
                    }
                    return userRepository.save(user);
                });

    }

}
