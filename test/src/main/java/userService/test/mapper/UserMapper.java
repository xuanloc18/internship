package userService.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import userService.test.dto.request.UserRequestCreate;
import userService.test.dto.request.UserRequestUpdate;
import userService.test.dto.response.UserResponse;
import userService.test.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestCreate userRequestCreate);
    UserResponse toUserResponse (User user);
    User updateUser(@MappingTarget User user, UserRequestUpdate userRequestUpdate);
}
