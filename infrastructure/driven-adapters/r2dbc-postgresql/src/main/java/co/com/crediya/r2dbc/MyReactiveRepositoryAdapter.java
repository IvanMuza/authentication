package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Repository
public class MyReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<User, UserEntity, UUID, MyReactiveRepository>
        implements UserRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    public Flux<User> findAll() {
        return super.findAll().
                delayElements(Duration.ofMillis(1000));
    }

    @Override
    public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
        return this.repository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<User> findByDocumentNumber(String documentNumber) {
        return this.repository.findByDocumentNumber(documentNumber);
    }

}
