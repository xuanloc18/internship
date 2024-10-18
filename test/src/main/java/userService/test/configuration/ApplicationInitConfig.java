package userService.test.configuration;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import userService.test.entity.Role;
import userService.test.entity.User;
import userService.test.respository.RoleRepository;
import userService.test.respository.UserRespository;

import java.util.HashSet;
import java.util.Set;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Bean
    @Transactional
    ApplicationRunner applicationRunner(UserRespository userRespository) {
        return args -> { // args đại diện cho các câu lệnh
            if (userRespository.findByUserName("admin").isEmpty()) {
                Set<Role> roles = new HashSet<>();
                var role = roleRepository.findById(userService.test.enums.Role.ADMIN.name()).orElseThrow(() -> new RuntimeException(""));
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
