package co.com.crediya.api;

import co.com.crediya.api.dtos.RegisterUserDto;
import co.com.crediya.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
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
            ),
            @RouterOperation(
                    path = "/api/v1/users/tasks",
                    beanClass = Handler.class,
                    beanMethod = "listenGetAllUsersTask",
                    operation = @Operation(
                            operationId = "listenGetAllUsersTask",
                            summary = "Get all users from tasks",
                            description = "Streams all users retrieved by the RegisterUserUseCase.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                                            content = @Content(mediaType = "text/event-stream",
                                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))),
                                    @ApiResponse(responseCode = "500", description = "Internal server error",
                                            content = @Content(mediaType = "application/json"))
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/users/{documentNumber}",
                    beanClass = Handler.class,
                    beanMethod = "listenGetExistsByDocument",
                    operation = @Operation(
                            operationId = "listenGetExistsByDocument",
                            summary = "Find user by document number",
                            description = "Returns the user object if found, otherwise a 400 not found error",
                            parameters = {
                                    @Parameter(
                                            name = "documentNumber",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Document number of the user to retrieve"
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "User found successfully",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = User.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "User not found with provided document number",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = String.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = String.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/users/exists/{email}",
                    beanClass = Handler.class,
                    beanMethod = "listenGetExistsByEmail",
                    operation = @Operation(
                            operationId = "listenGetExistsByEmail",
                            summary = "Find user by email",
                            description = "Returns true if found, otherwise return false",
                            parameters = {
                                    @Parameter(
                                            name = "email",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Email of the user to check"
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Boolean result indicating existence",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = Boolean.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Boolean result indicating user not found",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = Boolean.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = String.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/users"), handler::listenPostRegisterUser)
                .and(route(GET("/api/v1/users/tasks"), handler::listenGetAllUsersTask)
                        .and(route(GET("/api/v1/users/{documentNumber}"), handler::listenGetExistsByDocument))
                        .and(route(GET("/api/v1/users/exists/{email}"), handler::listenGetExistsByEmail)));
    }
}
