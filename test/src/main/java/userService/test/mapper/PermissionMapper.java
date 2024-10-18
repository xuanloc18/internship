package userService.test.mapper;


import org.mapstruct.Mapper;
import userService.test.dto.request.PermissionRequest;
import userService.test.dto.response.PermissionResponse;
import userService.test.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
