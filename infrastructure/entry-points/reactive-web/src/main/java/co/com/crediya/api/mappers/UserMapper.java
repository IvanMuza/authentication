package co.com.crediya.api.mappers;

import co.com.crediya.api.dtos.RegisterUserDto;
import co.com.crediya.api.dtos.UserLoanApplicationDto;
import co.com.crediya.api.dtos.UserResponseDto;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(RegisterUserDto request);
    UserResponseDto toResponse(User user);
    UserLoanApplicationDto toUserLoanResponse(User user);
}
