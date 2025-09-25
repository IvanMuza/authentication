package co.com.crediya.security.config;

import co.com.bancolombia.secretsmanager.api.GenericManagerAsync;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import co.com.crediya.model.user.secrets.SecretManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecretProvider {
    private final GenericManagerAsync secretsManager;
    private final String secretName;

    public SecretProvider(GenericManagerAsync secretsManager,
                          @Value("${aws.secretName}") String secretName) {
        this.secretsManager = secretsManager;
        this.secretName = secretName;
    }

    public Mono<String> getJwtSecret() {
        return Mono.defer(() -> {
            try {
                return secretsManager.getSecret(secretName, SecretManager.class)
                        .map(SecretManager::getJWT_SECRET);
            } catch (SecretException e) {
                return Mono.error(e);
            }
        });
    }

}
