package com.cxl.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cxl.identity_service.dto.request.UserCreationRequest;
import com.cxl.identity_service.dto.request.UserUpdateRequest;
import com.cxl.identity_service.dto.response.UserResponse;
import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.exception.AppException;
import com.cxl.identity_service.exception.ErrorCode;
import com.cxl.identity_service.mapper.UserMapper;
import com.cxl.identity_service.respository.RoleRepository;
import com.cxl.identity_service.respository.UserRespository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRespository.existsByuserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        HashSet<com.cxl.identity_service.entity.Role> roles = new HashSet<>();
        var role = roleRepository
                .findById(com.cxl.identity_service.enums.Role.USER.name())
                .orElseThrow(() -> new RuntimeException(""));
        roles.add(role);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRespository.save(user));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')") // thỏa điều kiện mới được vào
    public List<UserResponse> getUsers() {
        log.info("in method getusers");
        return userRespository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @PostAuthorize("returnObject.userName==authentication.name") // thực hiện xong mới so sánh điều kiện
    public UserResponse getUser(String id) {
        log.info("in method getusers by id");

        return userMapper.toUserResponse(
                userRespository.findById(id).orElseThrow(() -> new RuntimeException("user not found")));
    }

    public UserResponse updareUser(String userID, UserUpdateRequest request) {
        User user = userRespository.findById(userID).orElseThrow(() -> new RuntimeException("user not found"));
        user = userMapper.updateUser(user, request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRespository.save(user));
    }

    public UserResponse getMyInfor() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user =
                userRespository.findByUserName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public void del(String id) {
        userRespository.deleteById(id);
    }
}
