package com.cxl.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.cxl.identity_service.dto.request.UserCreationRequest;
import com.cxl.identity_service.dto.request.UserUpdateRequest;
import com.cxl.identity_service.dto.response.UserResponse;
import com.cxl.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //   @Mapping(target = "lastName",ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User updateUser(@MappingTarget User user, UserUpdateRequest request);
}
