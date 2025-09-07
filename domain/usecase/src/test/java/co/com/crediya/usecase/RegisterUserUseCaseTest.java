package co.com.crediya.usecase;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.DocumentAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailNotValidException;
import co.com.crediya.model.user.exceptions.ValidationException;
import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.user.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        registerUserUseCase = new RegisterUserUseCase(userRepository, roleRepository);
    }

    private User buildValidUser() {
        return User.builder()
                .documentNumber("12345")
                .firstName("Ivan")
                .lastName("Muza")
                .birthDate(LocalDate.of(2000, 6, 12))
                .address("Carrera 80 #8-08")
                .phone("+57 123456789")
                .email("ivanmuza@test.com")
                .baseSalary(6_000_000D)
                .roleId(1L)
                .password("12345")
                .build();
    }

    @Test
    void shouldRegisterUserWhenValid() {
        User user = buildValidUser();

        when(userRepository.existsByDocumentNumber(user.getDocumentNumber())).thenReturn(Mono.just(false));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));
        when(roleRepository.findRoleIdByName("Costumer")).thenReturn(Mono.just(1L));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        StepVerifier.create(registerUserUseCase.registerUser(user, "Costumer"))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).save(user);
    }

    @Test
    void shouldFailWhenUserIsNull() {
        StepVerifier.create(registerUserUseCase.registerUser(null, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository, roleRepository);
    }

    @Test
    void shouldFailWhenDocumentNumberIsMissing() {
        User user = buildValidUser();
        user.setDocumentNumber("");

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository, roleRepository);
    }

    @Test
    void shouldFailWhenFirstNameIsMissing() {
        User user = buildValidUser();
        user.setFirstName("");

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository, roleRepository);
    }

    @Test
    void shouldFailWhenLastNameIsMissing() {
        User user = buildValidUser();
        user.setLastName(null);

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenEmailIsMissing() {
        User user = buildValidUser();
        user.setEmail("");

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenBaseSalaryIsNull() {
        User user = buildValidUser();
        user.setBaseSalary(null);

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository, roleRepository);
    }

    @Test
    void shouldFailWhenBaseSalaryIsOutOfRange() {
        User user = buildValidUser();
        user.setBaseSalary(20_000_000D);

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(ValidationException.class)
                .verify();

        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        User user = buildValidUser();
        user.setEmail("invalid-email");

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(EmailNotValidException.class)
                .verify();

        verifyNoInteractions(userRepository, roleRepository);
    }

    @Test
    void shouldFailWhenEmailAlreadyExists() {
        User user = buildValidUser();

        when(userRepository.existsByDocumentNumber(user.getDocumentNumber())).thenReturn(Mono.just(false));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));
        when(roleRepository.findRoleIdByName(anyString())).thenReturn(Mono.just(1L));

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(EmailAlreadyExistsException.class)
                .verify();

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldFailWhenDocumentNumberAlreadyExists() {
        User user = buildValidUser();

        when(userRepository.existsByDocumentNumber(user.getDocumentNumber())).thenReturn(Mono.just(true));
        when(roleRepository.findRoleIdByName(anyString())).thenReturn(Mono.just(1L));

        StepVerifier.create(registerUserUseCase.registerUser(user, "invalid"))
                .expectError(DocumentAlreadyExistsException.class)
                .verify();

        verify(userRepository).existsByDocumentNumber(user.getDocumentNumber());
        verify(userRepository, never()).save(any());
    }

}