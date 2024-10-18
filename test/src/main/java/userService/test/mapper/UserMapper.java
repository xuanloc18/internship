package userService.test.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import userService.test.dto.request.UserCreationRequest;
import userService.test.dto.request.UserUpdateRequest;
import userService.test.dto.response.UserResponse;
import userService.test.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //   @Mapping(target = "lastName",ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User updateUser(@MappingTarget User user, UserUpdateRequest request);
}
