package co.com.crediya.api;

import co.com.crediya.api.dtos.RegisterUserDto;
import co.com.crediya.api.mappers.UserMapper;
import co.com.crediya.model.user.enums.ErrorCodesEnums;
import co.com.crediya.model.user.exceptions.UserNotFoundException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.GetAllUsersUseCase;
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
    private final UserRepository userRepository;
    private final GetAllUsersUseCase getAllUsersUseCase;

    public Mono<ServerResponse> listenPostUseCase(ServerRequest serverRequest) {
        log.info("listenPostUseCase");
        return serverRequest.bodyToMono(RegisterUserDto.class)
                .map(userMapper::toDomain)
                .flatMap(registerUserUseCase::registerUser)
                .map(userMapper::toResponse)
                .flatMap(userResponse -> {
                    log.info("Successfully created user: {}", userResponse.getEmail());
                    return ServerResponse
                            .status(HttpStatus.CREATED.value())
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userResponse);
                }).as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> listenGetAllUsersTask(ServerRequest serverRequest) {
        log.info("listenGetAllUsersTask");
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(getAllUsersUseCase.getAllUsers(), GetAllUsersUseCase.class);
    }

    public Mono<ServerResponse> listenGetExistsByDocument(ServerRequest serverRequest) {
        String documentNumber = serverRequest.pathVariable("documentNumber");
        log.info("listenGetExistsByDocument");
        return userRepository.findByDocumentNumber(documentNumber)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(Mono.error(new UserNotFoundException(
                        ErrorCodesEnums.USER_NOT_FOUND.getCode(),
                        ErrorCodesEnums.USER_NOT_FOUND.getDefaultMessage()
                )));
    }

    public Mono<ServerResponse> listenGetExistsByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
        log.info("listenGetExistsByEmail, email={}", email);
        return userRepository.existsByEmail(email)
                .flatMap(exists -> {
                    if (exists) {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(true);
                    } else {
                        return ServerResponse.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(false);
                    }
                });
    }

}
