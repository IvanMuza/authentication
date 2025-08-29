package co.com.crediya.usecase;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.EmailAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailNotValidException;
import co.com.crediya.model.user.exceptions.ValidationException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    private UserRepository userRepository;
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        registerUserUseCase = new RegisterUserUseCase(userRepository);
    }

    private User buildValidUser() {
        return User.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Muza")
                .birthDate(LocalDate.of(2000, 6, 12))
                .address("Carrera 80 #8-08")
                .phone("+57 123456789")
                .email("ivanmuza@test.com")
                .baseSalary(6_000_000D)
                .build();
    }

    @Test
    void shouldRegisterUserWhenValid() {
        User user = buildValidUser();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).save(user);
    }

    @Test
    void shouldFailWhenUserIsNull() {
        StepVerifier.create(registerUserUseCase.registerUser(null))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenFirstNameIsMissing() {
        User user = buildValidUser();
        user.setFirstName("");

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenLastNameIsMissing() {
        User user = buildValidUser();
        user.setLastName(null);

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenEmailIsMissing() {
        User user = buildValidUser();
        user.setEmail("");

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenBaseSalaryIsNull() {
        User user = buildValidUser();
        user.setBaseSalary(null);

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenBaseSalaryIsOutOfRange() {
        User user = buildValidUser();
        user.setBaseSalary(20_000_000D);

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        User user = buildValidUser();
        user.setEmail("invalid-email");

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(EmailNotValidException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenEmailAlreadyExists() {
        User user = buildValidUser();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectError(EmailAlreadyExistsException.class)
                .verify();

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository, never()).save(any());
    }

}