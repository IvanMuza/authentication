package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);

    Mono<Boolean> existsByEmail(String email);

    Flux<User> findAll();

    Mono<Boolean> existsByDocumentNumber(String documentNumber);

    Mono<User> findByDocumentNumber(String documentNumber);

    Mono<User> findByEmail(String email);
}
