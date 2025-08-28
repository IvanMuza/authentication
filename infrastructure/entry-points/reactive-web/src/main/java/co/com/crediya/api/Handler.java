package co.com.crediya.api;

import co.com.crediya.api.dtos.RegisterUserDto;
import co.com.crediya.api.mappers.UserMapper;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterUserUseCase registerUserUseCase;
    private final UserMapper userMapper;
    private final TransactionalOperator transactionalOperator;

    public Mono<ServerResponse> listenPostUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserDto.class)
                .map(userMapper::toDomain)
                .flatMap(registerUserUseCase::registerUser)
                .map(userMapper::toResponse)
                .flatMap(userResponse -> ServerResponse
                        .status(HttpStatus.CREATED.value())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse)
                ).as(transactionalOperator::transactional);
    }
}
