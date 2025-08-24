package co.com.crediya.api.mappers;

import co.com.crediya.api.dtos.RegisterUserRequest;
import co.com.crediya.api.dtos.UserResponse;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(RegisterUserRequest request);
    UserResponse toResponse(User user);
}
