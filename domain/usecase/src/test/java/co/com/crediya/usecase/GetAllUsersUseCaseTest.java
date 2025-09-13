package co.com.crediya.usecase;


import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.user.GetAllUsersUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllUsersUseCaseTest {
    private UserRepository userRepository;
    private GetAllUsersUseCase getAllUsersUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        getAllUsersUseCase = new GetAllUsersUseCase(userRepository);
    }

    private User buildValidFirstUser() {
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

    private User buildValidSecondUser() {
        return User.builder()
                .documentNumber("56678")
                .firstName("Daniel")
                .lastName("Maestre")
                .birthDate(LocalDate.of(2002, 8, 20))
                .address("Calle 9 #6-87")
                .phone("+57 576856")
                .email("danielmaestre@test.com")
                .baseSalary(8_000_000D)
                .roleId(2L)
                .password("test")
                .build();
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = buildValidFirstUser();
        User user2 = buildValidSecondUser();

        when(userRepository.findAll()).thenAnswer(invocation -> Flux.just(user1, user2));

        StepVerifier.create(getAllUsersUseCase.getAllUsers())
                .expectNext(user1)
                .expectNext(user2)
                .verifyComplete();

        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnEmptyWhenNoUsersExist() {
        when(userRepository.findAll()).thenAnswer(invocation -> Flux.empty());

        StepVerifier.create(getAllUsersUseCase.getAllUsers())
                .verifyComplete();

        verify(userRepository).findAll();
    }
}
