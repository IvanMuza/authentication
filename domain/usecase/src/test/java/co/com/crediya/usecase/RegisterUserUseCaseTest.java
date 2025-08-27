package co.com.crediya.usecase;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

class RegisterUserUseCaseTest {

    private UserRepository userRepository;
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        registerUserUseCase = new RegisterUserUseCase(userRepository);
    }

    @Test
    void shouldRegisterUserWhenEmailDoesNotExist() {
        User user = User.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Muza")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("Calle 123")
                .phone("123456789")
                .email("ivan@test.com")
                .baseSalary(5_000_000D)
                .build();

        Mockito.when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(Mono.just(false));
        Mockito.when(userRepository.save(user))
                .thenReturn(Mono.just(user));

        StepVerifier.create(registerUserUseCase.registerUser(user))
                .expectNext(user)
                .verifyComplete();

        Mockito.verify(userRepository).save(user);
    }

}