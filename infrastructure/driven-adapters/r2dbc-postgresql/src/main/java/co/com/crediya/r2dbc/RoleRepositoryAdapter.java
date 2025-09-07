package co.com.crediya.r2dbc;

import co.com.crediya.model.user.gateways.RoleRepository;
import co.com.crediya.r2dbc.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {
    private final SpringRoleRepository springRoleRepository;

    @Override
    public Mono<String> findRoleNameById(Long roleId) {
        return springRoleRepository.findById(roleId)
                .map(RoleEntity::getName);
    }

    public Mono<Long> findRoleIdByName(String roleName) {
        return springRoleRepository.findByName(roleName)
                .map(RoleEntity::getId);
    }
}
