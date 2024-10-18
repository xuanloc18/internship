package userService.test.respository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.test.entity.User;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);

    boolean existsByuserName(String username);
}
