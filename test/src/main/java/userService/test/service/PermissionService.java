package userService.test.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.test.dto.request.PermissionRequest;
import userService.test.dto.response.PermissionResponse;
import userService.test.entity.Permission;
import userService.test.mapper.PermissionMapper;
import userService.test.respository.PermissionRespository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PermissionService {

    @Autowired
    PermissionRespository permissionRespository;

    @Autowired
    PermissionMapper mapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = mapper.toPermission(request);

        return mapper.toPermissionResponse(permissionRespository.save(permission));
    }

    public List<PermissionResponse> getListsPer() {
        return permissionRespository.findAll().stream()
                .map(permission -> mapper.toPermissionResponse(permission))
                .toList();
    }

    public void deletePermission(String name) {
        permissionRespository.deleteById(name);
    }
}
