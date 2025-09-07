package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SpringRoleRepository extends ReactiveCrudRepository<RoleEntity, Long> {
    Mono<RoleEntity> findByName(String name);
}
