package com.cxl.identity_service.mapper;

import org.mapstruct.Mapper;

import com.cxl.identity_service.dto.request.PermissionRequest;
import com.cxl.identity_service.dto.response.PermissionResponse;
import com.cxl.identity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
