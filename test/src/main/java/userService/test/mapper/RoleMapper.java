package userService.test.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import userService.test.dto.request.RoleRequest;
import userService.test.dto.response.RoleResponse;
import userService.test.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
