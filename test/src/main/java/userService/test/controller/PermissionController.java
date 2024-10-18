package userService.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.request.PermissionRequest;
import userService.test.dto.response.APIResponse;
import userService.test.dto.response.PermissionResponse;
import userService.test.service.PermissionService;

import java.util.List;

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
