package co.com.crediya.api.config;

import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.user.GetAllUsersUseCase;
import co.com.crediya.usecase.registeruser.user.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCasesConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        return new RegisterUserUseCase(userRepository, roleRepository);
    }

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(UserRepository userRepository) {
        return new GetAllUsersUseCase(userRepository);
    }
}
