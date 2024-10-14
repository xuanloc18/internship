package com.cxl.identity_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxl.identity_service.dto.request.PermissionRequest;
import com.cxl.identity_service.dto.response.PermissionResponse;
import com.cxl.identity_service.entity.Permission;
import com.cxl.identity_service.mapper.PermissionMapper;
import com.cxl.identity_service.respository.PermissionRespository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
