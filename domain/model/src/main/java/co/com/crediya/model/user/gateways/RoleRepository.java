package co.com.crediya.model.user.gateways;

import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<String> findRoleNameById(Long roleId);
    Mono<Long> findRoleIdByName(String roleName);
}
