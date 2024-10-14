package com.cxl.identity_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cxl.identity_service.dto.request.APIResponse;
import com.cxl.identity_service.dto.request.PermissionRequest;
import com.cxl.identity_service.dto.response.PermissionResponse;
import com.cxl.identity_service.service.PermissionService;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping
    APIResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return APIResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    APIResponse<List<PermissionResponse>> getAll() {
        return APIResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getListsPer())
                .build();
    }

    @DeleteMapping("/{permission}")
    APIResponse<Void> delete(@PathVariable String permission) {
        permissionService.deletePermission(permission);
        return APIResponse.<Void>builder().build();
    }
}
