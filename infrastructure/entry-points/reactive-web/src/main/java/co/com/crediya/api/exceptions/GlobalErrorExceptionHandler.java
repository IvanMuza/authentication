package co.com.crediya.api.exceptions;

import co.com.crediya.model.user.exceptions.BaseBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String code = "500";
        if (ex instanceof BaseBusinessException) {
            status = HttpStatus.BAD_REQUEST;
            code = ((BaseBusinessException) ex).getCode();
        }
        serverWebExchange.getResponse().setStatusCode(status);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Error error = new Error(code, ex.getMessage(), serverWebExchange.getRequest().getPath().value());
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(error);
            return serverWebExchange.getResponse()
                    .writeWith(Mono.just(serverWebExchange.getResponse().bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
