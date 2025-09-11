package co.com.crediya.usecase;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.CredentialsInvalidException;
import co.com.crediya.model.user.exceptions.RoleNotValidException;
import co.com.crediya.model.user.exceptions.UserLoginNotFound;
import co.com.crediya.model.user.gateways.JwtProviderPort;
import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.auth.LoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class LoginUseCaseTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JwtProviderPort jwtProvider;
    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        jwtProvider = Mockito.mock(JwtProviderPort.class);

        loginUseCase = new LoginUseCase(userRepository, jwtProvider, roleRepository);
    }

    private User buildValidUser() {
        return User.builder()
                .documentNumber("12345")
                .firstName("Ivan")
                .lastName("Muza")
                .email("ivanmuza@test.com")
                .baseSalary(6_000_000D)
                .roleId(1L)
                .password("12345")
                .build();
    }

    @Test
    void shouldLoginSuccessfully() {
        User user = buildValidUser();
        String expectedToken = "fake-jwt-token";

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(roleRepository.findRoleNameById(user.getRoleId())).thenReturn(Mono.just("Admin"));
        when(jwtProvider.generateToken(user.getEmail(), "Admin")).thenReturn(expectedToken);

        StepVerifier.create(loginUseCase.login(user.getEmail(), user.getPassword()))
                .expectNext(expectedToken)
                .verifyComplete();

        verify(userRepository).findByEmail(user.getEmail());
        verify(roleRepository).findRoleNameById(user.getRoleId());
        verify(jwtProvider).generateToken(user.getEmail(), "Admin");
    }

    @Test
    void shouldFailWhenUserNotFound() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Mono.empty());

        StepVerifier.create(loginUseCase.login("notfound@test.com", "12345"))
                .expectError(UserLoginNotFound.class)
                .verify();

        verify(userRepository).findByEmail("notfound@test.com");
        verifyNoInteractions(roleRepository, jwtProvider);
    }

    @Test
    void shouldFailWhenPasswordInvalid() {
        User user = buildValidUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));

        StepVerifier.create(loginUseCase.login(user.getEmail(), "wrongPassword"))
                .expectError(CredentialsInvalidException.class)
                .verify();

        verify(userRepository).findByEmail(user.getEmail());
        verifyNoInteractions(roleRepository, jwtProvider);
    }

    @Test
    void shouldFailWhenRoleNotFound() {
        User user = buildValidUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(roleRepository.findRoleNameById(user.getRoleId())).thenReturn(Mono.empty());

        StepVerifier.create(loginUseCase.login(user.getEmail(), user.getPassword()))
                .expectError(RoleNotValidException.class)
                .verify();

        verify(userRepository).findByEmail(user.getEmail());
        verify(roleRepository).findRoleNameById(user.getRoleId());
        verifyNoInteractions(jwtProvider);
    }
}
