package userService.test.service;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.test.dto.request.RoleRequest;
import userService.test.dto.response.RoleResponse;
import userService.test.entity.Role;
import userService.test.mapper.RoleMapper;
import userService.test.respository.PermissionRespository;
import userService.test.respository.RoleRepository;

import java.util.HashSet;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionRespository permissionRespository;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        //        var permission =permissionRespository.findAllById(request.getPermissions());
        //
        //        role.setPermissions(new HashSet<>(permission));
        //       return roleMapper.toRoleResponse(roleRepository.save(role));
        var permissions = permissionRespository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        //       log.warn( roleMapper.toRoleResponse(role).getPemissions().toString());
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(role -> roleMapper.toRoleResponse(role)).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
