package co.com.crediya.api;

import co.com.crediya.api.dtos.RegisterUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/users",
                    beanClass = Handler.class,
                    beanMethod = "listenPostUseCase",
                    operation = @Operation(
                            operationId = "listenPostUseCase",
                            summary = "Register a new user",
                            description = "Creates a new user in the system based on the provided RegisterUserDto",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "User data to be registered",
                                    content = @Content(schema = @Schema(implementation = RegisterUserDto.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "User successfully registered",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = RegisterUserDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Validation error",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = RegisterUserDto.class))),
                                    @ApiResponse(responseCode = "500", description = "Internal server error",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = RegisterUserDto.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/users"), handler::listenPostUseCase);
    }
}
