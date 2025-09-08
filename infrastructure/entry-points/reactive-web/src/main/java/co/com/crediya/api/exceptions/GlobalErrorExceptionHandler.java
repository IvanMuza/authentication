package co.com.crediya.api.exceptions;

import co.com.crediya.model.user.exceptions.BaseBusinessException;
import co.com.crediya.model.user.exceptions.UserNotAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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
        String code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (ex instanceof UserNotAuthorizedException) {
            status = HttpStatus.FORBIDDEN;
            code = String.valueOf(HttpStatus.FORBIDDEN.value());
        } else if (ex instanceof BaseBusinessException) {
            status = HttpStatus.BAD_REQUEST;
            code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        }
        serverWebExchange.getResponse().setStatusCode(status);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(code, ex.getMessage(), serverWebExchange.getRequest().getPath().value());
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            return serverWebExchange.getResponse()
                    .writeWith(Mono.just(serverWebExchange.getResponse().bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
