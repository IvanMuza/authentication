package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;

public record GetAllUsersUseCase (UserRepository userRepository) {

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

}
