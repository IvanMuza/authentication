package co.com.crediya.api.exceptions;

import co.com.crediya.api.mappers.ErrorCodeToHttpStatusMapper;
import co.com.crediya.model.user.enums.ErrorCodesEnums;
import co.com.crediya.model.user.exceptions.BaseBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalErrorExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorCodeToHttpStatusMapper  errorCodeToHttpStatusMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

        if (ex instanceof BaseBusinessException baseBusinessException) {
            status = errorCodeToHttpStatusMapper.map(
                    ErrorCodesEnums.valueOf(baseBusinessException.getCode())
            );
            code = baseBusinessException.getCode();
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
