package co.com.crediya.api.config;

import co.com.crediya.model.user.gateways.JwtProviderPort;
import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.auth.LoginUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginCasesConfig {
    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            JwtProviderPort jwtProviderPort,
            RoleRepository roleRepository) {
        return new LoginUseCase(
                userRepository, jwtProviderPort, roleRepository);
    }
}
