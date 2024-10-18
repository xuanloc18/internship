package userService.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import userService.test.dto.request.RoleRequest;
import userService.test.dto.response.APIResponse;
import userService.test.dto.response.RoleResponse;
import userService.test.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping
    APIResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return APIResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    APIResponse<List<RoleResponse>> getAll() {
        return APIResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    APIResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return APIResponse.<Void>builder().build();
    }
}
