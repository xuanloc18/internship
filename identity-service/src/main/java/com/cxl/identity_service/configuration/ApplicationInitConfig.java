package com.cxl.identity_service.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.enums.Role;
import com.cxl.identity_service.respository.RoleRepository;
import com.cxl.identity_service.respository.UserRespository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRespository userRespository) {
        return args -> { // args đại diện cho các câu lệnh
            if (userRespository.findByUserName("admin").isEmpty()) {
                Set<com.cxl.identity_service.entity.Role> roles = new HashSet<>();
                var role = roleRepository.findById(Role.ADMIN.name()).orElseThrow(() -> new RuntimeException(""));
                roles.add(role);
                User user = User.builder()
                        .userName("admin")
                        .passWord(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRespository.save(user);
                log.warn("admin user has been reated with default password:admin,please change it");
            }
        };
    }
}
