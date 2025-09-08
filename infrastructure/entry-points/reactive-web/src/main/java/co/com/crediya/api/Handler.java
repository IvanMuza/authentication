package co.com.crediya.api;

import co.com.crediya.api.dtos.LoginRequestDto;
import co.com.crediya.api.dtos.LoginResponseDto;
import co.com.crediya.api.dtos.RegisterUserDto;
import co.com.crediya.api.mappers.UserMapper;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.enums.ErrorCodesEnums;
import co.com.crediya.model.user.exceptions.UserNotFoundException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.registeruser.auth.LoginUseCase;
import co.com.crediya.usecase.registeruser.user.GetAllUsersUseCase;
import co.com.crediya.usecase.registeruser.user.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterUserUseCase registerUserUseCase;
    private final UserMapper userMapper;
    private final TransactionalOperator transactionalOperator;
    private final UserRepository userRepository;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final LoginUseCase loginUseCase;

    public Mono<ServerResponse> listenPostRegisterUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserDto.class)
                .flatMap(dto -> {
                    User user = userMapper.toDomain(dto);
                    return registerUserUseCase.registerUser(user, dto.getRoleName());
                })
                .map(userMapper::toResponse)
                .flatMap(userResponse -> ServerResponse
                        .status(HttpStatus.CREATED.value())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse))
                .as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> listenGetAllUsersTask(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(getAllUsersUseCase.getAllUsers(), GetAllUsersUseCase.class)
                .as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> listenGetExistsByDocument(ServerRequest serverRequest) {
        String documentNumber = serverRequest.pathVariable("documentNumber");
        return userRepository.findByDocumentNumber(documentNumber)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(Mono.error(new UserNotFoundException(
                        ErrorCodesEnums.USER_NOT_FOUND.getCode(),
                        ErrorCodesEnums.USER_NOT_FOUND.getDefaultMessage()
                )))
                .as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> listenGetExistsByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
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
                })
                .as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> listenPostLogin(ServerRequest request) {
        return request.bodyToMono(LoginRequestDto.class)
                .flatMap(dto -> loginUseCase.login(dto.getEmail(), dto.getPassword()))
                .flatMap(token -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new LoginResponseDto(token)))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .bodyValue(Map.of("error", e.getMessage())))
                .as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> listenGetFindByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
        return userRepository.findByEmail(email)
                .map(userMapper::toUserLoanResponse)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(Mono.error(new UserNotFoundException(
                        ErrorCodesEnums.USER_EMAIL_NOT_FOUND.getCode(),
                        ErrorCodesEnums.USER_EMAIL_NOT_FOUND.getDefaultMessage()
                )))
                .as(transactionalOperator::transactional);
    }

}
