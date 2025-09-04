package co.com.crediya.api;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class LoggingWebFilter implements WebFilter {
    private static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();

        String correlationId = getOrCreateCorrelationId(exchange);

        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethod().toString();
        String path = request.getPath().value();

        log.info("[{}] Incoming request: {} {}", correlationId, method, path);

        return chain.filter(exchange)
                .doFinally(signalType -> {
                    long duration = System.currentTimeMillis() - startTime;
                    HttpStatusCode statusCode = exchange.getResponse().getStatusCode();

                    log.info("[{}] Response: {} {} -> {} ({} ms)",
                            correlationId, method, path, statusCode, duration);
                });
    }

    private String getOrCreateCorrelationId(ServerWebExchange exchange) {
        List<String> headers = exchange.getRequest().getHeaders().get(CORRELATION_ID);
        String correlationId = (headers != null && !headers.isEmpty()) ? headers.get(0) : UUID.randomUUID().toString();

        exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);

        return correlationId;
    }
}
