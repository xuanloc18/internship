package com.cxl.identity_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.cxl.identity_service.dto.request.APIResponse;
import com.cxl.identity_service.dto.request.UserCreationRequest;
import com.cxl.identity_service.dto.request.UserUpdateRequest;
import com.cxl.identity_service.dto.response.UserResponse;
import com.cxl.identity_service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    APIResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {

        return APIResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    APIResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username :{}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return APIResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userID}")
    APIResponse<UserResponse> getUsers(@PathVariable("userID") String userID) {
        return APIResponse.<UserResponse>builder()
                .result(userService.getUser(userID))
                .build();
    }

    @GetMapping("/myInfor")
    APIResponse<UserResponse> getUser() {
        return APIResponse.<UserResponse>builder()
                .result(userService.getMyInfor())
                .build();
    }

    @PutMapping({"/{userID}"})
    UserResponse updateUser(@PathVariable("userID") String userID, @RequestBody UserUpdateRequest request) {
        return userService.updareUser(userID, request);
    }

    @DeleteMapping({"/{userID}"})
    String deleteUser(@PathVariable("userID") String userID) {
        userService.del(userID);
        return "User had deleted";
    }
}
