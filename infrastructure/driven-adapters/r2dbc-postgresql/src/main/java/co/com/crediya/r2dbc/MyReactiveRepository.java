package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MyReactiveRepository extends ReactiveCrudRepository<UserEntity, UUID>,
        ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<Boolean> existsByEmail(String email);
}
