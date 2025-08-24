package co.com.crediya.api;

import co.com.crediya.api.dtos.RegisterUserRequest;
import co.com.crediya.api.mappers.UserMapper;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterUserUseCase registerUserUseCase;
    private final UserMapper userMapper;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return serverRequest.bodyToMono(RegisterUserRequest.class)
                .map(userMapper::toDomain)
                .flatMap(registerUserUseCase::registerUser)
                .map(userMapper::toResponse)
                .flatMap(userResponse -> ServerResponse
                        .status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse)
                );
    }
}
